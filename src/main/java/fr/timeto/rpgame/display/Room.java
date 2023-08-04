package fr.timeto.rpgame.display;

import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.character.Character;
import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;
import fr.timeto.rpgame.core.Server;
import fr.timeto.timutilslib.CustomFonts;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class Room extends JPanel implements SwingerEventListener {

    ArrayList<STexturedButton> setGMButtonList = new ArrayList<>();
    STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"));
    public LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 55, 8);
    JLabel idLabel = new JLabel();

    Character selectedCharacter = new Character("Undefined", "NotChosen");
    boolean ready = false;
    JLabel selectedCharacterNameLabel = new JLabel("Personnage non choisi");
    STexturedButton selectCharacterButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/selectCharacter-normal.png"), getResourceIgnorePath("/assets/rpgame/room/selectCharacter-hover.png"));
    STexturedButton readyButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/ready-no-normal.png"), getResourceIgnorePath("/assets/rpgame/room/ready-no-hover.png"));

    public Room() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        this.add(spinner);
        spinner.setVisible(false);

        quitButton.setBounds(20, 20);
        quitButton.addEventListener(this);
        this.add(quitButton);

        idLabel.setText("Votre ID est " + Client.getId());
        idLabel.setForeground(new Color(128, 15, 15));
        idLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(idLabel);

        selectedCharacterNameLabel.setBounds(201, 612, 558, 40);
        selectedCharacterNameLabel.setForeground(Color.WHITE);
        selectedCharacterNameLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        selectedCharacterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(selectedCharacterNameLabel);

        selectCharacterButton.setBounds(241, 697); // TODO Placer tout ce bordel là flemme
        selectCharacterButton.addEventListener(this);
        this.add(selectCharacterButton);

        readyButton.setBounds(372, 800);
        readyButton.addEventListener(this);
        this.add(readyButton);

    }

    int lastWidth = 1920;
    int lastHeight = 1080;
    boolean sizeChanged = true;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int iiii = 0;
        while (iiii != setGMButtonList.size()) {
            if (!setGMButtonList.get(iiii).isHover()) {
                this.remove(setGMButtonList.get(iiii));
            }
            iiii++;
        }

        ConnectedClient thisClient = null;
        if (Client.connectedClients != null) {
            int iii = 0;
            while (iii != Client.connectedClients.size()) {
                ConnectedClient tested = Client.connectedClients.get(iii);
                if (Objects.equals(Client.getId(), tested.getId())) {
                    thisClient = tested;
                    break;
                }
                iii++;
            }
        }

        float widthFactor = 1;
        float heightFactor = 1;

        if (this.getWidth() == lastWidth) {
            if (this.getHeight() == lastHeight) {
                sizeChanged = false;
            }
        }

        lastWidth = this.getWidth();
        lastHeight = this.getHeight();

        if (this.getWidth() < 1920) {
            widthFactor = (float) this.getWidth() / 1920;
        }

        if (this.getHeight() < 1080) {
            heightFactor = (float) this.getHeight() / 1080;
        }

        int quitButtonWidth = Math.round(72 * widthFactor);
        int quitButtonHeight = Math.round(54 * heightFactor);
        quitButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), quitButtonWidth, quitButtonHeight));
        quitButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"), quitButtonWidth, quitButtonHeight));
        quitButton.setBounds(Math.round(20 * widthFactor), Math.round(20 * heightFactor));

        spinner.setPreferredSize(new Dimension(Math.round(55 * widthFactor), Math.round(55 * widthFactor)));
        spinner.setThickness(Math.round(8 * widthFactor));
        spinner.setBounds(Client.gameFrame.getWidth() - Math.round(120 * widthFactor), Math.round(10 * heightFactor), (int) Math.round(spinner.getPreferredSize().getWidth() + 50 * widthFactor), (int) Math.round(spinner.getPreferredSize().getHeight() + 50 * heightFactor));

        idLabel.setBounds(Math.round(50 * widthFactor), Client.gameFrame.getHeight() - 75, Client.gameFrame.getWidth() - Math.round(100 * widthFactor), 25);
        idLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f * heightFactor));

        Graphics2D g2d = (Graphics2D) g;
        Font font = CustomFonts.kollektifBoldFont.deriveFont(30f * heightFactor);

        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRoundRect(Math.round(960 * widthFactor),
                Math.round(99 * heightFactor),
                Math.round(899 * widthFactor),
                Math.round(932 * heightFactor),
                10,
                10);

        g2d.setColor(new Color(61, 9, 9));
        g2d.fillRoundRect(Math.round(963 * widthFactor),
                Math.round(102 * heightFactor),
                Math.round(893 * widthFactor),
                Math.round(926 * heightFactor),
                10,
                10);

        if (Client.connectedClients != null) {
            int i = 0;
            int y = 125;
            while (i != Client.connectedClients.size()) {
                ConnectedClient client = Client.connectedClients.get(i);

                g2d.setColor(new Color(52, 8, 8));
                g2d.fillRoundRect(Math.round(990 * widthFactor),
                        Math.round(y * heightFactor),
                        Math.round(840 * widthFactor),
                        Math.round(133 * heightFactor),
                        10,
                        10);

                String text;
                if (client.isGM()) {
                    text = "Ma\u00eetre du jeu";
                } else {
                    if (Objects.equals(client.getCharacter().getName(), "Undefined")) {
                        text = "Personnage non choisi";
                    } else {
                        text = client.getCharacter().getFullName();
                    }
                }

                Color colorForName = Color.WHITE;
                if (Objects.equals(client.getId(), Client.getId())) {
                    text = text + " (Vous)";
                    colorForName = new Color(237, 255, 152);
                } else {
                    text = text + " (" + client.getId() + ")";
                }

                JLabel testLabel = new JLabel(text);
                testLabel.setFont(font);
                Dimension dimension = GameFrame.getStringSize(testLabel, text, Math.round((27 * widthFactor) * 2), Math.round((7 * heightFactor) * 2));
                int nameZoneX = Math.round(1004 * widthFactor);
                int nameZoneY = Math.round((y + 14) * heightFactor);
                g2d.setColor(new Color(80, 10, 10));
                g2d.fillRoundRect(nameZoneX, nameZoneY, dimension.width, dimension.height, 30, 30);

                g2d.setColor(colorForName);
                g2d.setFont(font);
                g2d.drawString(text, nameZoneX + (27 * widthFactor), Math.round(nameZoneY + dimension.getHeight() - ((8 * heightFactor) * 2)));

                try {
                    int gmButtonWidth = Math.round(48 * widthFactor);
                    int gmButtonHeight = Math.round(37 * heightFactor);

                    if (thisClient.isGM() && !Objects.equals(Client.getId(), client.getId())) {
                        client.setGMButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-normal.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setTextureDisabled(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setBounds(Math.round((990 + 813 - 30) * widthFactor), nameZoneY);
                        if (!client.setGMButton.isHover()) {
                            this.add(client.setGMButton);
                        }
                        setGMButtonList.add(client.setGMButton);
                    }

                    if (client.isGM()) {
                        client.setGMButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-normal.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setTextureDisabled(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"), gmButtonWidth, gmButtonHeight));
                        client.setGMButton.setBounds(Math.round((990 + 813 - 30) * widthFactor), nameZoneY);
                        this.add(client.setGMButton);
                        client.setGMButton.setEnabled(false);
                        setGMButtonList.add(client.setGMButton);
                    }

                } catch (NullPointerException ignored) {
                }

                y += 162;
                i++;
            }
        }

        if (sizeChanged) {
            // GM resize buttons
            //TODO

            // Player resize buttons
            selectedCharacterNameLabel.setBounds(Math.round(201*widthFactor),
                    Math.round(612*heightFactor),
                    Math.round(558*widthFactor),
                    Math.round(40*heightFactor));
            selectedCharacterNameLabel.setFont(font);

            int selectButtonWidth = Math.round(475 * widthFactor);
            int selectButtonHeight = Math.round(62 * heightFactor);
            selectCharacterButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/selectCharacter-normal.png"), selectButtonWidth, selectButtonHeight));
            selectCharacterButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/selectCharacter-hover.png"), selectButtonWidth, selectButtonHeight));
            selectCharacterButton.setTextureDisabled(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/selectCharacter-disabled.png"), selectButtonWidth, selectButtonHeight));
            selectCharacterButton.setBounds(Math.round(241*widthFactor),
                    Math.round(697*heightFactor));
        }

        if (thisClient.isGM()) {
            // Player buttons invisible
            selectedCharacterNameLabel.setVisible(false);
            selectCharacterButton.setVisible(false);
            readyButton.setVisible(false);
            // GM buttons visible
            // TODO


        } else {
            // GM buttons invisible
            // TODO
            // Player buttons visible
            selectedCharacterNameLabel.setVisible(true);
            selectCharacterButton.setVisible(true);
            readyButton.setVisible(true);

            // Character selector
            g2d.setColor(new Color(52, 8, 8));
            g2d.fillRoundRect(Math.round(176*widthFactor),
                    Math.round(230*heightFactor),
                    Math.round(607*widthFactor),
                    Math.round(670*heightFactor),
                    10, 10);

            g2d.setColor(new Color(61, 9, 9));
            g2d.fillRoundRect(Math.round(179*widthFactor),
                    Math.round(233*heightFactor),
                    Math.round(601*widthFactor),
                    Math.round(664*heightFactor),
                    10, 10);

            //   Character image
            g2d.setColor(new Color(52, 8, 8));
            g2d.fillRect(Math.round(323*widthFactor),
                    Math.round(264*heightFactor),
                    Math.round(315*widthFactor),
                    Math.round(315*heightFactor));

            g2d.setColor(new Color(80, 10, 10));
            g2d.fillRect(Math.round(326*widthFactor),
                    Math.round(267*heightFactor),
                    Math.round(309*widthFactor),
                    Math.round(309*heightFactor));

            int readyButtonWidth = Math.round(216 * widthFactor);
            int readyButtonHeight = Math.round(62 * heightFactor);
            if (ready) {
                readyButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/ready-yes-normal.png"), readyButtonWidth, readyButtonHeight));
                readyButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/ready-yes-hover.png"), readyButtonWidth, readyButtonHeight));
                selectCharacterButton.setEnabled(false);
            } else {
                readyButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/ready-no-normal.png"), readyButtonWidth, readyButtonHeight));
                readyButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/ready-no-hover.png"), readyButtonWidth, readyButtonHeight));
                readyButton.setTextureDisabled(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/ready-no-disabled.png"), readyButtonWidth, readyButtonHeight));
                selectCharacterButton.setEnabled(true);
            }
            readyButton.setBounds(Math.round(372*widthFactor),
                    Math.round(800*heightFactor));

            if (Objects.equals(selectedCharacter.getName(), "Undefined")) {
                readyButton.setEnabled(false);
                selectedCharacterNameLabel.setText("Personnage non choisi");
            } else {
                readyButton.setEnabled(true);
                selectedCharacterNameLabel.setText(selectedCharacter.getFullName());
            }

        }

        sizeChanged = true;
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.disconnect();
            Client.gameFrame.setContentPane(new MainMenu());
        } else if (src == selectCharacterButton) {
            List<Path> pathList = Explorer.dir(GameFrame.RP_CHARACTERSFOLDER.toPath()).files().match(".*\\.character").get();

            ArrayList<String> namesList = new ArrayList<>();
            namesList.add("Personnage non choisi");
            ArrayList<Character> charactersList = new ArrayList<>();
            charactersList.add(new Character("Undefined", "NotChosen"));

            int i = 0;
            while (i != pathList.size()) {
                BufferedReader br;
                Character character;
                try {
                    br = new BufferedReader(new FileReader(pathList.get(i).toFile()));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    sb.append(line);
                    String everything = sb.toString();
                    character = Character.getFromString(everything);

                    namesList.add(character.getFullName());
                    charactersList.add(character);
                } catch (IOException ignored) {
                } finally {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                i++;
            }

            JPanel panel = new JPanel(new GridBagLayout());
            JComboBox comboBox = new JComboBox(namesList.toArray(new String[0])); comboBox.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, comboBox, "S\u00e9lectionner un personnage",
                    JOptionPane.QUESTION_MESSAGE);
            panel.add(comboBox);
            selectedCharacter = charactersList.get(comboBox.getSelectedIndex());

            try {
                Client.sendToServer(Server.FROM_CLIENT.SENDING_CHARACTER.str + selectedCharacter.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (src == readyButton) {
            ready = !ready;
            try {
                Client.sendToServer(Server.FROM_CLIENT.READY.str + ready + "]");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
