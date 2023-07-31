package fr.timeto.rpgame.display;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;
import fr.timeto.timutilslib.CustomFonts;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class Room extends JPanel implements SwingerEventListener {

    ArrayList<STexturedButton> setGMButtonList = new ArrayList<>();
    STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"));
    public LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 55, 8);
    JLabel idLabel = new JLabel();

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
    }

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

                Font font = CustomFonts.kollektifBoldFont.deriveFont(30f * heightFactor);
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
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.disconnect();
            Client.gameFrame.setContentPane(new MainMenu());
        }

    }
}
