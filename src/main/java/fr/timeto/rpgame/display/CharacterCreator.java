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
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class CharacterCreator extends JPanel implements SwingerEventListener {

    STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"));

    public LoadingSpinner spinner = new LoadingSpinner(new Color(210, 214, 86), 55, 8);

    STexturedButton saveButton = new STexturedButton(getResourceIgnorePath("/assets/rpgame/characters/save-normal.png"), getResourceIgnorePath("/assets/rpgame/characters/save-hover.png"));

    JTextField nameTextField1 = new JTextField();
    JTextField nameTextField2 = new JTextField();

    JTextField constitutionTextField = new JTextField();
    JTextField forceTextField = new JTextField();
    JTextField resistanceTextField = new JTextField();

    JTextField mentalTextField = new JTextField();
    JTextField intellectTextField = new JTextField();
    JTextField eloquenceTextField = new JTextField();

    JTextField dexteriteTextField = new JTextField();
    JTextField agiliteTextField = new JTextField();
    JTextField furtiviteTextField = new JTextField();

    JTextField survieTextField = new JTextField();
    JTextField perceptionTextField = new JTextField();
    JTextField savoirfaireTextField = new JTextField();

    JTextField specialCategory1TextField = new JTextField();
    JTextField specialCategory1LevelTextField = new JTextField();
    JTextField specialCategory1Ability1TextField = new JTextField();
    JTextField specialCategory1Ability1LevelTextField = new JTextField();
    JTextField specialCategory1Ability2TextField = new JTextField();
    JTextField specialCategory1Ability2LevelTextField = new JTextField();
    JTextField specialCategory1Ability3TextField = new JTextField();
    JTextField specialCategory1Ability3LevelTextField = new JTextField();

    JTextField specialCategory2TextField = new JTextField();
    JTextField specialCategory2LevelTextField = new JTextField();
    JTextField specialCategory2Ability1TextField = new JTextField();
    JTextField specialCategory2Ability1LevelTextField = new JTextField();
    JTextField specialCategory2Ability2TextField = new JTextField();
    JTextField specialCategory2Ability2LevelTextField = new JTextField();
    JTextField specialCategory2Ability3TextField = new JTextField();
    JTextField specialCategory2Ability3LevelTextField = new JTextField();

    ArrayList<CharacterInList> charactersList = new ArrayList<>();

    CharacterInList actualCharacter;

    public CharacterCreator() {
        this.setBackground(new Color(80, 10, 10));
        this.setLayout(null);

        this.add(spinner);
        spinner.setVisible(false);

        quitButton.setBounds(20, 20);
        quitButton.addEventListener(this);
        this.add(quitButton);

        saveButton.setBounds(707, 512);
        saveButton.addEventListener(this);
        this.add(saveButton);

        Font font = CustomFonts.kollektifFont.deriveFont(31f);
        nameTextField1.setBounds(712, 348, 354, 52); // +5 +0 -10 -0
        nameTextField1.setForeground(Color.WHITE);
        nameTextField1.setCaretColor(Color.WHITE);
        nameTextField1.setOpaque(false);
        nameTextField1.setFont(font);
        nameTextField1.setBorder(null);
        this.add(nameTextField1);

        nameTextField2.setBounds(712, 430, 354, 52);
        nameTextField2.setForeground(Color.WHITE);
        nameTextField2.setCaretColor(Color.WHITE);
        nameTextField2.setOpaque(false);
        nameTextField2.setFont(font);
        nameTextField2.setBorder(null);
        this.add(nameTextField2);

        constitutionTextField.setBounds(1166, 150, 48, 48); // +6 +7 -10 -10
        constitutionTextField.setForeground(Color.WHITE);
        constitutionTextField.setCaretColor(Color.WHITE);
        constitutionTextField.setOpaque(false);
        constitutionTextField.setFont(font);
        constitutionTextField.setBorder(null);
        constitutionTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) constitutionTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(constitutionTextField);

        forceTextField.setBounds(1195, 222, 48, 48); // +6 +7 -10 -10
        forceTextField.setForeground(Color.WHITE);
        forceTextField.setCaretColor(Color.WHITE);
        forceTextField.setOpaque(false);
        forceTextField.setFont(font);
        forceTextField.setBorder(null);
        forceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) forceTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(forceTextField);

        resistanceTextField.setBounds(1195, 294, 48, 48); // +6 +7 -10 -10
        resistanceTextField.setForeground(Color.WHITE);
        resistanceTextField.setCaretColor(Color.WHITE);
        resistanceTextField.setOpaque(false);
        resistanceTextField.setFont(font);
        resistanceTextField.setBorder(null);
        resistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) resistanceTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(resistanceTextField);

        mentalTextField.setBounds(1521, 150, 48, 48); // +6 +7 -10 -10
        mentalTextField.setForeground(Color.WHITE);
        mentalTextField.setCaretColor(Color.WHITE);
        mentalTextField.setOpaque(false);
        mentalTextField.setFont(font);
        mentalTextField.setBorder(null);
        mentalTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) mentalTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(mentalTextField);

        intellectTextField.setBounds(1550, 222, 48, 48); // +6 +7 -10 -10
        intellectTextField.setForeground(Color.WHITE);
        intellectTextField.setCaretColor(Color.WHITE);
        intellectTextField.setOpaque(false);
        intellectTextField.setFont(font);
        intellectTextField.setBorder(null);
        intellectTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) intellectTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(intellectTextField);

        eloquenceTextField.setBounds(1550, 294, 48, 48); // +6 +7 -10 -10
        eloquenceTextField.setForeground(Color.WHITE);
        eloquenceTextField.setCaretColor(Color.WHITE);
        eloquenceTextField.setOpaque(false);
        eloquenceTextField.setFont(font);
        eloquenceTextField.setBorder(null);
        eloquenceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) eloquenceTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(eloquenceTextField);

        dexteriteTextField.setBounds(1166, 397, 48, 48); // +6 +7 -10 -10
        dexteriteTextField.setForeground(Color.WHITE);
        dexteriteTextField.setCaretColor(Color.WHITE);
        dexteriteTextField.setOpaque(false);
        dexteriteTextField.setFont(font);
        dexteriteTextField.setBorder(null);
        dexteriteTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) dexteriteTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(dexteriteTextField);

        agiliteTextField.setBounds(1195, 469, 48, 48); // +6 +7 -10 -10
        agiliteTextField.setForeground(Color.WHITE);
        agiliteTextField.setCaretColor(Color.WHITE);
        agiliteTextField.setOpaque(false);
        agiliteTextField.setFont(font);
        agiliteTextField.setBorder(null);
        agiliteTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) agiliteTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(agiliteTextField);

        furtiviteTextField.setBounds(1195, 541, 48, 48); // +6 +7 -10 -10
        furtiviteTextField.setForeground(Color.WHITE);
        furtiviteTextField.setCaretColor(Color.WHITE);
        furtiviteTextField.setOpaque(false);
        furtiviteTextField.setFont(font);
        furtiviteTextField.setBorder(null);
        furtiviteTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) furtiviteTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(furtiviteTextField);

        survieTextField.setBounds(1521, 397, 48, 48); // +6 +7 -10 -10
        survieTextField.setForeground(Color.WHITE);
        survieTextField.setCaretColor(Color.WHITE);
        survieTextField.setOpaque(false);
        survieTextField.setFont(font);
        survieTextField.setBorder(null);
        survieTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) survieTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(survieTextField);

        perceptionTextField.setBounds(1550, 469, 48, 48); // +6 +7 -10 -10
        perceptionTextField.setForeground(Color.WHITE);
        perceptionTextField.setCaretColor(Color.WHITE);
        perceptionTextField.setOpaque(false);
        perceptionTextField.setFont(font);
        perceptionTextField.setBorder(null);
        perceptionTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) perceptionTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(perceptionTextField);

        savoirfaireTextField.setBounds(1550, 541, 48, 48); // +6 +7 -10 -10
        savoirfaireTextField.setForeground(Color.WHITE);
        savoirfaireTextField.setCaretColor(Color.WHITE);
        savoirfaireTextField.setOpaque(false);
        savoirfaireTextField.setFont(font);
        savoirfaireTextField.setBorder(null);
        savoirfaireTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) savoirfaireTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(savoirfaireTextField);

        specialCategory1TextField.setBounds(1234, 640, 248, 52); // +5 +0 -10 -0
        specialCategory1TextField.setForeground(Color.WHITE);
        specialCategory1TextField.setCaretColor(Color.WHITE);
        specialCategory1TextField.setOpaque(false);
        specialCategory1TextField.setFont(font);
        specialCategory1TextField.setBorder(null);
        this.add(specialCategory1TextField);

        specialCategory1LevelTextField.setBounds(1166, 644, 48, 48); // +6 +7 -10 -10
        specialCategory1LevelTextField.setForeground(Color.WHITE);
        specialCategory1LevelTextField.setCaretColor(Color.WHITE);
        specialCategory1LevelTextField.setOpaque(false);
        specialCategory1LevelTextField.setFont(font);
        specialCategory1LevelTextField.setBorder(null);
        specialCategory1LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory1LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory1LevelTextField);

        specialCategory1Ability1TextField.setBounds(1262, 712, 219, 52); // +5 +0 -10 -0
        specialCategory1Ability1TextField.setForeground(Color.WHITE);
        specialCategory1Ability1TextField.setCaretColor(Color.WHITE);
        specialCategory1Ability1TextField.setOpaque(false);
        specialCategory1Ability1TextField.setFont(font);
        specialCategory1Ability1TextField.setBorder(null);
        this.add(specialCategory1Ability1TextField);

        specialCategory1Ability1LevelTextField.setBounds(1195, 716, 48, 48); // +6 +7 -10 -10
        specialCategory1Ability1LevelTextField.setForeground(Color.WHITE);
        specialCategory1Ability1LevelTextField.setCaretColor(Color.WHITE);
        specialCategory1Ability1LevelTextField.setOpaque(false);
        specialCategory1Ability1LevelTextField.setFont(font);
        specialCategory1Ability1LevelTextField.setBorder(null);
        specialCategory1Ability1LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory1Ability1LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory1Ability1LevelTextField);

        specialCategory1Ability2TextField.setBounds(1262, 784, 219, 52); // +5 +0 -10 -0
        specialCategory1Ability2TextField.setForeground(Color.WHITE);
        specialCategory1Ability2TextField.setCaretColor(Color.WHITE);
        specialCategory1Ability2TextField.setOpaque(false);
        specialCategory1Ability2TextField.setFont(font);
        specialCategory1Ability2TextField.setBorder(null);
        this.add(specialCategory1Ability2TextField);

        specialCategory1Ability2LevelTextField.setBounds(1195, 788, 48, 48); // +6 +7 -10 -10
        specialCategory1Ability2LevelTextField.setForeground(Color.WHITE);
        specialCategory1Ability2LevelTextField.setCaretColor(Color.WHITE);
        specialCategory1Ability2LevelTextField.setOpaque(false);
        specialCategory1Ability2LevelTextField.setFont(font);
        specialCategory1Ability2LevelTextField.setBorder(null);
        specialCategory1Ability2LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory1Ability2LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory1Ability2LevelTextField);

        specialCategory1Ability3TextField.setBounds(1262, 856, 219, 52); // +5 +0 -10 -0
        specialCategory1Ability3TextField.setForeground(Color.WHITE);
        specialCategory1Ability3TextField.setCaretColor(Color.WHITE);
        specialCategory1Ability3TextField.setOpaque(false);
        specialCategory1Ability3TextField.setFont(font);
        specialCategory1Ability3TextField.setBorder(null);
        this.add(specialCategory1Ability3TextField);

        specialCategory1Ability3LevelTextField.setBounds(1195, 860, 48, 48); // +6 +7 -10 -10
        specialCategory1Ability3LevelTextField.setForeground(Color.WHITE);
        specialCategory1Ability3LevelTextField.setCaretColor(Color.WHITE);
        specialCategory1Ability3LevelTextField.setOpaque(false);
        specialCategory1Ability3LevelTextField.setFont(font);
        specialCategory1Ability3LevelTextField.setBorder(null);
        specialCategory1Ability3LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory1Ability3LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory1Ability3LevelTextField);

        specialCategory2TextField.setBounds(1589, 640, 248, 52); // +5 +0 -10 -0
        specialCategory2TextField.setForeground(Color.WHITE);
        specialCategory2TextField.setCaretColor(Color.WHITE);
        specialCategory2TextField.setOpaque(false);
        specialCategory2TextField.setFont(font);
        specialCategory2TextField.setBorder(null);
        this.add(specialCategory2TextField);

        specialCategory2LevelTextField.setBounds(1521, 644, 48, 48); // +6 +7 -10 -10
        specialCategory2LevelTextField.setForeground(Color.WHITE);
        specialCategory2LevelTextField.setCaretColor(Color.WHITE);
        specialCategory2LevelTextField.setOpaque(false);
        specialCategory2LevelTextField.setFont(font);
        specialCategory2LevelTextField.setBorder(null);
        specialCategory2LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory2LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory2LevelTextField);

        specialCategory2Ability1TextField.setBounds(1617, 712, 219, 52); // +5 +0 -10 -0
        specialCategory2Ability1TextField.setForeground(Color.WHITE);
        specialCategory2Ability1TextField.setCaretColor(Color.WHITE);
        specialCategory2Ability1TextField.setOpaque(false);
        specialCategory2Ability1TextField.setFont(font);
        specialCategory2Ability1TextField.setBorder(null);
        this.add(specialCategory2Ability1TextField);

        specialCategory2Ability1LevelTextField.setBounds(1550, 716, 48, 48); // +6 +7 -10 -10
        specialCategory2Ability1LevelTextField.setForeground(Color.WHITE);
        specialCategory2Ability1LevelTextField.setCaretColor(Color.WHITE);
        specialCategory2Ability1LevelTextField.setOpaque(false);
        specialCategory2Ability1LevelTextField.setFont(font);
        specialCategory2Ability1LevelTextField.setBorder(null);
        specialCategory2Ability1LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory2Ability1LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory2Ability1LevelTextField);

        specialCategory2Ability2TextField.setBounds(1617, 784, 219, 52); // +5 +0 -10 -0
        specialCategory2Ability2TextField.setForeground(Color.WHITE);
        specialCategory2Ability2TextField.setCaretColor(Color.WHITE);
        specialCategory2Ability2TextField.setOpaque(false);
        specialCategory2Ability2TextField.setFont(font);
        specialCategory2Ability2TextField.setBorder(null);
        this.add(specialCategory2Ability2TextField);

        specialCategory2Ability2LevelTextField.setBounds(1550, 788, 48, 48); // +6 +7 -10 -10
        specialCategory2Ability2LevelTextField.setForeground(Color.WHITE);
        specialCategory2Ability2LevelTextField.setCaretColor(Color.WHITE);
        specialCategory2Ability2LevelTextField.setOpaque(false);
        specialCategory2Ability2LevelTextField.setFont(font);
        specialCategory2Ability2LevelTextField.setBorder(null);
        specialCategory2Ability2LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory2Ability2LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory2Ability2LevelTextField);

        specialCategory2Ability3TextField.setBounds(1617, 856, 219, 52); // +5 +0 -10 -0
        specialCategory2Ability3TextField.setForeground(Color.WHITE);
        specialCategory2Ability3TextField.setCaretColor(Color.WHITE);
        specialCategory2Ability3TextField.setOpaque(false);
        specialCategory2Ability3TextField.setFont(font);
        specialCategory2Ability3TextField.setBorder(null);
        this.add(specialCategory2Ability3TextField);

        specialCategory2Ability3LevelTextField.setBounds(1550, 860, 48, 48); // +6 +7 -10 -10
        specialCategory2Ability3LevelTextField.setForeground(Color.WHITE);
        specialCategory2Ability3LevelTextField.setCaretColor(Color.WHITE);
        specialCategory2Ability3LevelTextField.setOpaque(false);
        specialCategory2Ability3LevelTextField.setFont(font);
        specialCategory2Ability3LevelTextField.setBorder(null);
        specialCategory2Ability3LevelTextField.setHorizontalAlignment(SwingConstants.CENTER);
        ((AbstractDocument) specialCategory2Ability3LevelTextField.getDocument()).setDocumentFilter(new IntFilter());
        this.add(specialCategory2Ability3LevelTextField);


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

        changeCharacterTo(charactersList.get(0));

    }

    protected void changeCharacterTo(CharacterInList character) {
        this.actualCharacter = character;

        nameTextField1.setText(actualCharacter.character.getName());
        nameTextField2.setText(actualCharacter.character.getLastname());

        constitutionTextField.setText(actualCharacter.character.consitutionAbilities.getCategoryLevel() + "");
        forceTextField.setText(actualCharacter.character.consitutionAbilities.get(0).getLevel() + "");
        resistanceTextField.setText(actualCharacter.character.consitutionAbilities.get(1).getLevel() + "");

        mentalTextField.setText(actualCharacter.character.mentalAbilities.getCategoryLevel() + "");
        intellectTextField.setText(actualCharacter.character.mentalAbilities.get(0).getLevel() + "");
        eloquenceTextField.setText(actualCharacter.character.mentalAbilities.get(1).getLevel() + "");

        dexteriteTextField.setText(actualCharacter.character.dexteriteAbilities.getCategoryLevel() + "");
        agiliteTextField.setText(actualCharacter.character.dexteriteAbilities.get(0).getLevel() + "");
        furtiviteTextField.setText(actualCharacter.character.dexteriteAbilities.get(1).getLevel() + "");

        survieTextField.setText(actualCharacter.character.survieAbilities.getCategoryLevel() + "");
        perceptionTextField.setText(actualCharacter.character.survieAbilities.get(0).getLevel() + "");
        savoirfaireTextField.setText(actualCharacter.character.survieAbilities.get(1).getLevel() + "");

        specialCategory1TextField.setText(actualCharacter.character.specialAbilities1.getName());
        specialCategory1LevelTextField.setText(actualCharacter.character.specialAbilities1.getCategoryLevel() + "");
        specialCategory1Ability1TextField.setText(actualCharacter.character.specialAbilities1.get(0).getName());
        specialCategory1Ability1LevelTextField.setText(actualCharacter.character.specialAbilities1.get(0).getLevel() + "");
        specialCategory1Ability2TextField.setText(actualCharacter.character.specialAbilities1.get(1).getName());
        specialCategory1Ability2LevelTextField.setText(actualCharacter.character.specialAbilities1.get(1).getLevel() + "");
        specialCategory1Ability3TextField.setText(actualCharacter.character.specialAbilities1.get(2).getName());
        specialCategory1Ability3LevelTextField.setText(actualCharacter.character.specialAbilities1.get(2).getLevel() + "");

        specialCategory2TextField.setText(actualCharacter.character.specialAbilities2.getName());
        specialCategory2LevelTextField.setText(actualCharacter.character.specialAbilities2.getCategoryLevel() + "");
        specialCategory2Ability1TextField.setText(actualCharacter.character.specialAbilities2.get(0).getName());
        specialCategory2Ability1LevelTextField.setText(actualCharacter.character.specialAbilities2.get(0).getLevel() + "");
        specialCategory2Ability2TextField.setText(actualCharacter.character.specialAbilities2.get(1).getName());
        specialCategory2Ability2LevelTextField.setText(actualCharacter.character.specialAbilities2.get(1).getLevel() + "");
        specialCategory2Ability3TextField.setText(actualCharacter.character.specialAbilities2.get(2).getName());
        specialCategory2Ability3LevelTextField.setText(actualCharacter.character.specialAbilities2.get(2).getLevel() + "");
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == quitButton) {
            Client.gameFrame.setContentPane(new MainMenu());

        } else if (src == saveButton) {

            actualCharacter.character.setName(nameTextField1.getText());
            actualCharacter.character.setLastname(nameTextField2.getText());

            actualCharacter.character.consitutionAbilities.setCategoryLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.consitutionAbilities.getCategoryLevel(),
                            Integer.parseInt(constitutionTextField.getText())
                    )
            );
            actualCharacter.character.consitutionAbilities.get(0).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.consitutionAbilities.get(0).getLevel(),
                            Integer.parseInt(forceTextField.getText())
                    )
            );
            actualCharacter.character.consitutionAbilities.get(1).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.consitutionAbilities.get(1).getLevel(),
                            Integer.parseInt(resistanceTextField.getText())
                    )
            );

            actualCharacter.character.mentalAbilities.setCategoryLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.mentalAbilities.getCategoryLevel(),
                            Integer.parseInt(mentalTextField.getText())
                    )
            );
            actualCharacter.character.mentalAbilities.get(0).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.mentalAbilities.get(0).getLevel(),
                            Integer.parseInt(intellectTextField.getText())
                    )
            );
            actualCharacter.character.mentalAbilities.get(1).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.mentalAbilities.get(1).getLevel(),
                            Integer.parseInt(eloquenceTextField.getText())
                    )
            );

            actualCharacter.character.dexteriteAbilities.setCategoryLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.dexteriteAbilities.getCategoryLevel(),
                            Integer.parseInt(dexteriteTextField.getText())
                    )
            );
            actualCharacter.character.dexteriteAbilities.get(0).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.dexteriteAbilities.get(0).getLevel(),
                            Integer.parseInt(agiliteTextField.getText())
                    )
            );
            actualCharacter.character.dexteriteAbilities.get(1).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.dexteriteAbilities.get(1).getLevel(),
                            Integer.parseInt(furtiviteTextField.getText())
                    )
            );

            actualCharacter.character.survieAbilities.setCategoryLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.survieAbilities.getCategoryLevel(),
                            Integer.parseInt(survieTextField.getText())
                    )
            );
            actualCharacter.character.survieAbilities.get(0).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.survieAbilities.get(0).getLevel(),
                            Integer.parseInt(perceptionTextField.getText())
                    )
            );
            actualCharacter.character.survieAbilities.get(1).setLevel(
                    actualCharacter.character.verifyAbilityChange(
                            actualCharacter.character.survieAbilities.get(1).getLevel(),
                            Integer.parseInt(savoirfaireTextField.getText())
                    )
            );
            actualCharacter.character.specialAbilities1.setName(specialCategory1TextField.getText());
            if (!specialCategory1TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities1.setCategoryLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities1.getCategoryLevel(),
                                Integer.parseInt(specialCategory1LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities1.get(0).setName(specialCategory1Ability1TextField.getText());
            if (!specialCategory1Ability1TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities1.get(0).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities1.get(0).getLevel(),
                                Integer.parseInt(specialCategory1Ability1LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities1.get(1).setName(specialCategory1Ability2TextField.getText());
            if (!specialCategory1Ability2TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities1.get(1).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities1.get(1).getLevel(),
                                Integer.parseInt(specialCategory1Ability2LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities1.get(2).setName(specialCategory1Ability3TextField.getText());
            if (!specialCategory1Ability3TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities1.get(2).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities1.get(2).getLevel(),
                                Integer.parseInt(specialCategory1Ability3LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities2.setName(specialCategory2TextField.getText());
            if (!specialCategory2TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities2.setCategoryLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities2.getCategoryLevel(),
                                Integer.parseInt(specialCategory2LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities2.get(0).setName(specialCategory2Ability1TextField.getText());
            if (!specialCategory2Ability1TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities2.get(0).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities2.get(0).getLevel(),
                                Integer.parseInt(specialCategory2Ability1LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities2.get(1).setName(specialCategory2Ability2TextField.getText());
            if (!specialCategory2Ability2TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities2.get(1).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities2.get(1).getLevel(),
                                Integer.parseInt(specialCategory2Ability2LevelTextField.getText())
                        )
                );
            }
            actualCharacter.character.specialAbilities2.get(2).setName(specialCategory2Ability3TextField.getText());
            if (!specialCategory2Ability3TextField.getText().replaceAll(" ", "").equals("")) {
                actualCharacter.character.specialAbilities2.get(2).setLevel(
                        actualCharacter.character.verifyAbilityChange(
                                actualCharacter.character.specialAbilities2.get(2).getLevel(),
                                Integer.parseInt(specialCategory2Ability3LevelTextField.getText())
                        )
                );
            }

            try {
                actualCharacter.character.writeToFile(actualCharacter.path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            PopUpMessages.doneMessage("Sauvegard\u00e9", "Modifications sauvegard\u00e9es");

            actualCharacter.repaint();
            this.changeCharacterTo(actualCharacter);
        }

    }

    int lastWidth = 1920;
    int lastHeight = 1080;
    boolean sizeChanged = true;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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

        if (sizeChanged) {
            int quitButtonWidth = Math.round(72 * widthFactor);
            int quitButtonHeight = Math.round(54 * heightFactor);
            quitButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-normal.png"), quitButtonWidth, quitButtonHeight));
            quitButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/room/quit-hover.png"), quitButtonWidth, quitButtonHeight));
            quitButton.setBounds(Math.round(20 * widthFactor), Math.round(20 * heightFactor));

            int saveButtonWidth = Math.round(138 * widthFactor);
            int saveButtonHeight = Math.round(31 * heightFactor);
            saveButton.setTexture(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/save-normal.png"), saveButtonWidth, saveButtonHeight));
            saveButton.setTextureHover(Scalr.resize(getResourceIgnorePath("/assets/rpgame/characters/save-hover.png"), saveButtonWidth, saveButtonHeight));
            saveButton.setBounds(Math.round(707 * widthFactor), Math.round(512 * heightFactor));

            spinner.setPreferredSize(new Dimension(Math.round(55 * widthFactor), Math.round(55 * widthFactor)));
            spinner.setThickness(Math.round(8 * widthFactor));
            spinner.setBounds(Client.gameFrame.getWidth() - Math.round(120 * widthFactor), Math.round(10 * heightFactor), (int) Math.round(spinner.getPreferredSize().getWidth() + 50 * widthFactor), (int) Math.round(spinner.getPreferredSize().getHeight() + 50 * heightFactor));

            int i = 0;
            int x = Math.round(65 * widthFactor);
            int y = Math.round(140 * heightFactor);
            while (i != charactersList.size()) {
                charactersList.get(i).setLocation(x, y);
                y += Math.round(101 * heightFactor);
                y += Math.round(6 * heightFactor);
                i++;
            }
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

        Font font = CustomFonts.kollektifFont.deriveFont(31f * heightFactor);
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

        if (sizeChanged) {
            nameTextField1.setFont(font);
            nameTextField1.setBounds(Math.round(712 * widthFactor),
                    Math.round(348 * heightFactor),
                    Math.round(354 * widthFactor),
                    Math.round(52 * heightFactor));
        }

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

        if (sizeChanged) {
            nameTextField2.setFont(font);
            nameTextField2.setBounds(Math.round(712 * widthFactor),
                    Math.round(430 * heightFactor),
                    Math.round(354 * widthFactor),
                    Math.round(52 * heightFactor));
        }

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

        if (sizeChanged) {
            constitutionTextField.setBounds(Math.round(1166 * widthFactor),
                    Math.round(150 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            constitutionTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            forceTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(222 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            forceTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            resistanceTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(294 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            resistanceTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            mentalTextField.setBounds(Math.round(1521 * widthFactor),
                    Math.round(150 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            mentalTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            intellectTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(222 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            intellectTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            eloquenceTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(294 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            eloquenceTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            dexteriteTextField.setBounds(Math.round(1166 * widthFactor),
                    Math.round(397 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            dexteriteTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            agiliteTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(469 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            agiliteTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            furtiviteTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(541 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            furtiviteTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            survieTextField.setBounds(Math.round(1521 * widthFactor),
                    Math.round(397 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            survieTextField.setFont(font);
        }

        {
            String text = "Survie";
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

        if (sizeChanged) {
            perceptionTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(469 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            perceptionTextField.setFont(font);
        }

        {
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

        if (sizeChanged) {
            savoirfaireTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(541 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            savoirfaireTextField.setFont(font);
        }

        {
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

        // Special Category 1
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1160 * widthFactor),
                Math.round(637 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1163 * widthFactor),
                Math.round(640 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory1LevelTextField.setBounds(Math.round(1166 * widthFactor),
                    Math.round(644 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory1LevelTextField.setFont(font);

            //   TextField
            specialCategory1TextField.setFont(font);
            specialCategory1TextField.setBounds(Math.round(1234 * widthFactor),
                    Math.round(640 * heightFactor),
                    Math.round(248 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1229 * widthFactor),
                Math.round(640 * heightFactor),
                Math.round(258 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1229 * widthFactor),
                Math.round(689 * heightFactor),
                Math.round(258 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 1 - ability 1
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(709 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(712 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory1Ability1LevelTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(716 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory1Ability1LevelTextField.setFont(font);

            //   TextField
            specialCategory1Ability1TextField.setFont(font);
            specialCategory1Ability1TextField.setBounds(Math.round(1262 * widthFactor),
                    Math.round(712 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(712 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(761 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 1 - ability 2
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(781 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(784 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory1Ability2LevelTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(788 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory1Ability2LevelTextField.setFont(font);

            //   TextField
            specialCategory1Ability2TextField.setFont(font);
            specialCategory1Ability2TextField.setBounds(Math.round(1262 * widthFactor),
                    Math.round(784 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(784 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(833 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 1 - ability 3
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1189 * widthFactor),
                Math.round(853 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1192 * widthFactor),
                Math.round(856 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory1Ability3LevelTextField.setBounds(Math.round(1195 * widthFactor),
                    Math.round(860 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory1Ability3LevelTextField.setFont(font);

            //   TextField
            specialCategory1Ability3TextField.setFont(font);
            specialCategory1Ability3TextField.setBounds(Math.round(1262 * widthFactor),
                    Math.round(856 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(856 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1257 * widthFactor),
                Math.round(905 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 2
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1515 * widthFactor),
                Math.round(637 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1518 * widthFactor),
                Math.round(640 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory2LevelTextField.setBounds(Math.round(1521 * widthFactor),
                    Math.round(644 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory2LevelTextField.setFont(font);

            //   TextField
            specialCategory2TextField.setFont(font);
            specialCategory2TextField.setBounds(Math.round(1589 * widthFactor),
                    Math.round(640 * heightFactor),
                    Math.round(248 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1584 * widthFactor),
                Math.round(640 * heightFactor),
                Math.round(258 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1584 * widthFactor),
                Math.round(689 * heightFactor),
                Math.round(258 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 2 - ability 1
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(709 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(712 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory2Ability1LevelTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(716 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory2Ability1LevelTextField.setFont(font);

            //   TextField
            specialCategory2Ability1TextField.setFont(font);
            specialCategory2Ability1TextField.setBounds(Math.round(1617 * widthFactor),
                    Math.round(712 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(712 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(761 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 2 - ability 2
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(781 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(784 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory2Ability2LevelTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(788 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory2Ability2LevelTextField.setFont(font);

            //   TextField
            specialCategory2Ability2TextField.setFont(font);
            specialCategory2Ability2TextField.setBounds(Math.round(1617 * widthFactor),
                    Math.round(784 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(784 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(833 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));

        // Special Category 2 - ability 3
        //   Level Oval
        g2d.setColor(new Color(52, 8, 8));
        g2d.fillOval(Math.round(1544 * widthFactor),
                Math.round(853 * heightFactor),
                Math.round(58 * widthFactor),
                Math.round(58 * heightFactor));

        g2d.setColor(new Color(80, 10, 10));
        g2d.fillOval(Math.round(1547 * widthFactor),
                Math.round(856 * heightFactor),
                Math.round(52 * widthFactor),
                Math.round(52 * heightFactor));

        if (sizeChanged) {
            specialCategory2Ability3LevelTextField.setBounds(Math.round(1550 * widthFactor),
                    Math.round(860 * heightFactor),
                    Math.round(48 * widthFactor),
                    Math.round(48 * heightFactor));
            specialCategory2Ability3LevelTextField.setFont(font);

            //   TextField
            specialCategory2Ability3TextField.setFont(font);
            specialCategory2Ability3TextField.setBounds(Math.round(1617 * widthFactor),
                    Math.round(856 * heightFactor),
                    Math.round(219 * widthFactor),
                    Math.round(52 * heightFactor));
        }
        g2d.setColor(new Color(80, 10, 10));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(856 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(52 * heightFactor));

        g2d.setColor(new Color(52, 8, 8));
        g2d.fillRect(Math.round(1612 * widthFactor),
                Math.round(905 * heightFactor),
                Math.round(229 * widthFactor),
                Math.round(3 * heightFactor));


        sizeChanged = true;
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

            Thread ifYes = new Thread(() -> ((CharacterCreator) this.getParent()).changeCharacterTo(this));

            PopUpMessages.yesNoMessage("Attention","Les modifications non sauvegard\u00e9es seront \u00e9cras\u00e9es", ifYes, new Thread());

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
