package fr.timeto.rpgame.display;

import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.character.Character;
import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;
import fr.timeto.timutilslib.CustomFonts;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class CharacterCreator extends JPanel implements SwingerEventListener {

    STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"));
    CharactersList charactersListScrollPane = new CharactersList();

    public LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 55, 8);

    public CharacterCreator() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        this.add(spinner);
        spinner.setVisible(false);

        quitButton.setBounds(20, 20);
        quitButton.addEventListener(this);
        this.add(quitButton);

        charactersListScrollPane.setBounds(65, 108, 535, 914);
        this.add(charactersListScrollPane);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        charactersListScrollPane.setBounds(Math.round(65 * widthFactor), Math.round(108 * heightFactor), Math.round(535 * widthFactor), Math.round(914 * heightFactor));
        spinner.setPreferredSize(new Dimension(Math.round(55 * widthFactor), Math.round(55 * widthFactor)));
        spinner.setThickness(Math.round(8 * widthFactor));
        spinner.setBounds(Client.gameFrame.getWidth() - Math.round(120 * widthFactor), Math.round(10 * heightFactor), (int) Math.round(spinner.getPreferredSize().getWidth() + 50 * widthFactor), (int) Math.round(spinner.getPreferredSize().getHeight() + 50 * heightFactor));

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Characters list
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRoundRect(Math.round(56 * widthFactor),
                Math.round(99 * heightFactor),
                Math.round(551 * widthFactor),
                Math.round(932 * heightFactor),
                10,
                10);

        g2d.setColor(new Color(61, 9, 9));
        g2d.fillRoundRect(Math.round(59 * widthFactor),
                Math.round(102 * heightFactor),
                Math.round(545 * widthFactor),
                Math.round(926 * heightFactor),
                10,
                10);

        // Character creator form
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRoundRect(Math.round(650 * widthFactor),
                Math.round(99 * heightFactor),
                Math.round(1209 * widthFactor),
                Math.round(932 * heightFactor),
                10,
                10);

        g2d.setColor(new Color(61, 9, 9));
        g2d.fillRoundRect(Math.round(653 * widthFactor),
                Math.round(102 * heightFactor),
                Math.round(1203 * widthFactor),
                Math.round(926 * heightFactor),
                10,
                10);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.gameFrame.setContentPane(new MainMenu());
        }

    }
}

class CharactersList extends JScrollPane {

    Box[] boxes;
    JPanel container = new JPanel();

    ArrayList<Character> charactersList = new ArrayList<>();
    ArrayList<Path> charactersPathsList  = new ArrayList<>();

    public CharactersList() {
        this.setOpaque(false);

        updateCharacterList();

        LayoutManager layout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setPreferredSize(new Dimension(this.getWidth(), 101 * charactersList.size()));
        container.setBackground(Swinger.getTransparentWhite(10));
        container.setOpaque(false);

        this.setViewportView(container);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getVerticalScrollBar().setUI(new NewScrollBarUI());
        this.getHorizontalScrollBar().setUI(new NewScrollBarUI());
        this.getVerticalScrollBar().setUnitIncrement(14);

        this.getViewport().setOpaque(false);
        this.setBorder(null);

        int i = 0;
        boxes = new Box[charactersList.size()];

        for (int ii = i; ii < charactersList.size(); ii++) {
            boxes[ii] = Box.createVerticalBox();
            boxes[ii].createGlue();
            container.add(boxes[ii]);
            container.add(Box.createRigidArea(new Dimension(200, 2)));
            CharacterInList panel1 = new CharacterInList(charactersList.get(ii), charactersPathsList.get(ii));
            boxes[ii].add(panel1);
        }

    }

    public void updateCharacterList() {
        charactersList = new ArrayList<>();
        List<Path> fileList = Explorer.dir(GameFrame.RP_CHARACTERSFOLDER.toPath()).files().get();
        int i = 0;
        if (fileList.size() != 0) {
            while (i != fileList.size()) {
                if (fileList.get(i).toString().endsWith(".character")) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(fileList.get(i).toFile()));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        sb.append(line);
                        String everything = sb.toString();
                        charactersList.add(Character.getFromString(everything));
                        charactersPathsList.add(fileList.get(i));
                    } catch (IOException ignored) {
                    } finally {
                        try {
                            br.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                i++;
            }
        } else {
            Character newCharacter = new Character("Nouveau", "personnage");
            charactersList.add(newCharacter);
            File newFile = new File(GameFrame.RP_CHARACTERSFOLDER, ConnectedClient.generateHash() + ".character");
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            charactersPathsList.add(newFile.toPath());
            try {
                newCharacter.writeToFile(newFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class CharacterInList extends JPanel {

        final Character character;
        final Path path;

        public CharacterInList(Character character, Path path) {
            this.character = character;
            this.path = path;

            this.setLayout(null);
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(512, 101));
            this.setBackground(new Color(52, 8, 8));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            float widthFactor = 1;
            float heightFactor = 1;

            if (this.getWidth() < 512) {
                widthFactor = (float) this.getWidth()/512;
            }

            if (this.getHeight() < 101) {
                heightFactor = (float) this.getHeight()/101;
            }

            this.setPreferredSize(new Dimension(Math.round(512 * widthFactor), Math.round(101 * heightFactor)));

            g2d.setColor(this.getBackground());
            g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);

            Font font = CustomFonts.kollektifFont.deriveFont(31f * heightFactor); // FIXME Le texte ne change pas de taille
            String text = character.getFullName();
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(98 * widthFactor);
            int nameZoneY = Math.round(5 * heightFactor);
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }
    }
}
