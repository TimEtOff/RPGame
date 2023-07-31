package fr.timeto.rpgame.display;

import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.character.Character;
import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.PopUpMessages;
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

    public LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 55, 8);

    ArrayList<CharacterInList> charactersList = new ArrayList<>();

    public CharacterCreator() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        this.add(spinner);
        spinner.setVisible(false);

        quitButton.setBounds(20, 20);
        quitButton.addEventListener(this);
        this.add(quitButton);

        List<Path> pathList = Explorer.dir(GameFrame.RP_CHARACTERSFOLDER.toPath()).files().match(".*\\.character").get();
        if (pathList.size() < 8) {
            int i = pathList.size();
            Client.println(i + " character files found, adding to 8");
            while (i != 8) {
                Character newCharacter = new Character("Nouveau", "personnage");
                File newFile = new File(GameFrame.RP_CHARACTERSFOLDER, ConnectedClient.generateHash() + ".character");
                try {
                    newFile.createNewFile();
                    newCharacter.writeToFile(newFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                i++;
                Client.println("(" + i + ") Added " + newFile.getAbsolutePath());
            }
            pathList = Explorer.dir(GameFrame.RP_CHARACTERSFOLDER.toPath()).files().match(".*\\.character").get();
        } else {
            Client.println("8 character files found");
        }

        int i = 0;
        int x = 65;
        int y = 140;
        while (i != pathList.size()) {
            BufferedReader br;
            Character character = null;
            try {
                br = new BufferedReader(new FileReader(pathList.get(i).toFile()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                sb.append(line);
                String everything = sb.toString();
                character = Character.getFromString(everything);
            } catch (IOException ignored) {
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            CharacterInList characterInList = new CharacterInList(character, pathList.get(i));
            charactersList.add(i, characterInList);

            characterInList.setLocation(x, y);
            characterInList.setSize(characterInList.getPreferredSize());
            this.add(characterInList);
            y += 101;
            y += 6;

            i++;
        }


    }

    protected void changeCharacterTo(Character character) {

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
        spinner.setPreferredSize(new Dimension(Math.round(55 * widthFactor), Math.round(55 * widthFactor)));
        spinner.setThickness(Math.round(8 * widthFactor));
        spinner.setBounds(Client.gameFrame.getWidth() - Math.round(120 * widthFactor), Math.round(10 * heightFactor), (int) Math.round(spinner.getPreferredSize().getWidth() + 50 * widthFactor), (int) Math.round(spinner.getPreferredSize().getHeight() + 50 * heightFactor));

        int i = 0;
        int x = Math.round(65*widthFactor);
        int y = Math.round(140*heightFactor);
        while (i != charactersList.size()) {
            charactersList.get(i).setLocation(x, y);
            y += Math.round(101*heightFactor);
            y += Math.round(6*heightFactor);
            i++;
        }

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Characters list
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRoundRect(Math.round(56 * widthFactor),
                Math.round(131 * heightFactor),
                Math.round(551 * widthFactor),
                Math.round(868 * heightFactor),
                10,
                10);

        g2d.setColor(new Color(61, 9, 9));
        g2d.fillRoundRect(Math.round(59 * widthFactor),
                Math.round(134 * heightFactor),
                Math.round(545 * widthFactor),
                Math.round(862 * heightFactor),
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

        // Character image
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(707 * widthFactor),
                Math.round(143 * heightFactor),
                Math.round(175 * widthFactor),
                Math.round(175 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(710 * widthFactor),
                Math.round(146 * heightFactor),
                Math.round(169 * widthFactor),
                Math.round(169 * heightFactor));

        // Name TextField 1
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(707 * widthFactor),
                Math.round(348 * heightFactor),
                Math.round(364 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(707 * widthFactor),
                Math.round(397 * heightFactor),
                Math.round(364 * widthFactor),
                Math.round(3 * heightFactor));

        // Name TextField 2
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(707 * widthFactor),
                Math.round(430 * heightFactor),
                Math.round(364 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(707 * widthFactor),
                Math.round(479 * heightFactor),
                Math.round(364 * widthFactor),
                Math.round(3 * heightFactor));

        // Center separator
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1128 * widthFactor),
                Math.round(133 * heightFactor),
                Math.round(6 * widthFactor),
                Math.round(864 * heightFactor));

        // Constitution
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1160 * widthFactor),
                Math.round(143 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1163 * widthFactor),
                Math.round(146 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        Font font = CustomFonts.kollektifFont.deriveFont(31f * heightFactor);
        if (true) {
            String text = "Constitution";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1228 * widthFactor);
            int nameZoneY = Math.round(152 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Force
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(215 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(218 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Force";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1257 * widthFactor);
            int nameZoneY = Math.round(224 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Résistance
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(287 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(290 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "R\u00e9sistance";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1257 * widthFactor);
            int nameZoneY = Math.round(296 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Mental
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1515 * widthFactor),
                Math.round(143 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1518 * widthFactor),
                Math.round(146 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Mental";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1583 * widthFactor);
            int nameZoneY = Math.round(152 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Intellect
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(215 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(218 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Intellect";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1612 * widthFactor);
            int nameZoneY = Math.round(224 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Eloquence
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(287 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(290 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Eloquence";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1612 * widthFactor);
            int nameZoneY = Math.round(296 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Dextérité
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1160 * widthFactor),
                Math.round(390 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1163 * widthFactor),
                Math.round(393 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Dext\u00e9rit\u00e9";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1228 * widthFactor);
            int nameZoneY = Math.round(399 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Agilité
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(462 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(465 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Agilit\u00e9";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1257 * widthFactor);
            int nameZoneY = Math.round(471 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Furtivité
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(534 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(537 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Furtivit\u00e9";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1257 * widthFactor);
            int nameZoneY = Math.round(543 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Survie
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1515 * widthFactor),
                Math.round(390 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1518 * widthFactor),
                Math.round(393 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Mental";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1583 * widthFactor);
            int nameZoneY = Math.round(399 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Perception
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(462 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(465 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Perception";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1612 * widthFactor);
            int nameZoneY = Math.round(476 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }

        // Savoir-faire
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(534 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(537 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (true) {
            String text = "Savoir-faire";
            JLabel testLabel = new JLabel(text);
            testLabel.setFont(font);
            Dimension dimension = GameFrame.getStringSize(testLabel, text);
            int nameZoneX = Math.round(1612 * widthFactor);
            int nameZoneY = Math.round(543 * heightFactor); // -5 par rapport au design
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));
        }
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.gameFrame.setContentPane(new MainMenu());
        }

    }
}

class CharacterInList extends JPanel implements SwingerEventListener {

    STexturedButton modifyButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/characters/modify-normal.png"), getResourceIgnorePath("/assets/rpgame/characters/modify-hover.png"));
    STexturedButton resetButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/characters/reset-normal.png"), getResourceIgnorePath("/assets/rpgame/characters/reset-hover.png"));

    Character character;
    final Path path;

    public CharacterInList(Character character, Path path) {
        this.character = character;
        this.path = path;

        this.setLayout(null);
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(533, 101));
        this.setBackground(new Color(52, 8, 8));

        modifyButton.setBounds(107, 57);
        modifyButton.addEventListener(this);
        this.add(modifyButton);

        resetButton.setBounds(235, 57);
        resetButton.addEventListener(this);
        this.add(resetButton);
    }

    int lastWidth = 1920;
    int lastHeight = 1080;
    boolean sizeChanged = true;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        float widthFactor = 1;
        float heightFactor = 1;

        if (this.getParent().getWidth() == lastWidth) {
            if (this.getParent().getHeight() == lastHeight) {
                sizeChanged = false;
            }
        }

        lastWidth = this.getParent().getWidth();
        lastHeight = this.getParent().getHeight();

        if (this.getParent().getWidth() < 1920) {
            widthFactor = (float) this.getParent().getWidth()/1920;
        }

        if (this.getParent().getHeight() < 1080) {
            heightFactor = (float) this.getParent().getHeight()/1080;
        }

        this.setSize(new Dimension(Math.round(533 * widthFactor), Math.round(101 * heightFactor)));

        g2d.setColor(this.getBackground());
        g2d.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);

        // Name
        Font font = CustomFonts.kollektifFont.deriveFont(31f * heightFactor);
        String text = character.getFullName();
        JLabel testLabel = new JLabel(text);
        testLabel.setFont(font);
        Dimension dimension = GameFrame.getStringSize(testLabel, text);
        int nameZoneX = Math.round(107 * widthFactor);
        int nameZoneY = Math.round(5 * heightFactor);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(text, nameZoneX, Math.round(nameZoneY + dimension.getHeight()));

        // Image
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(13*widthFactor), Math.round(12* heightFactor), Math.round(77*widthFactor), Math.round(77*heightFactor));

        if (sizeChanged) {
            int modifyButtonWidth = Math.round(107 * widthFactor);
            int modifyButtonHeight = Math.round(31 * heightFactor);
            modifyButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/modify-normal.png"), modifyButtonWidth, modifyButtonHeight));
            modifyButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/modify-hover.png"), modifyButtonWidth, modifyButtonHeight));
            modifyButton.setBounds(Math.round(107 * widthFactor), Math.round(57 * heightFactor));


            int resetButtonWidth = Math.round(127 * widthFactor);
            int resetButtonHeight = Math.round(31 * heightFactor);
            resetButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/reset-normal.png"), resetButtonWidth, resetButtonHeight));
            resetButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/reset-hover.png"), resetButtonWidth, resetButtonHeight));
            resetButton.setBounds(Math.round(235 * widthFactor), Math.round(57 * heightFactor));
        }

        sizeChanged = true;
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Object e = swingerEvent.getSource();
        if (e == modifyButton) {

            ((CharacterCreator) this.getParent()).changeCharacterTo(character);

        } else if (e == resetButton) {

            Thread ifYes = new Thread(() -> {
                Character newCharacter = new Character("Nouveau", "personnage");
                try {
                    newCharacter.writeToFile(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Client.println("Personnage réinitialisé: " + path.toFile().getAbsolutePath());
                character = newCharacter;
                this.repaint();
            });

            PopUpMessages.yesNoMessage("Attention","Voulez-vous r\u00e9initialiser le personnage \"" + character.getFullName() + "\" ?", ifYes, new Thread());
        }
    }
}
