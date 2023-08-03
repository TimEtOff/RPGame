package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.*;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Character implements Serializable {

    // TODO Doit avoir un max de 75 point de compétences -- 70 coompétences + 5 talents
    protected String name;
    protected String lastname;
    public AbilityCategory consitutionAbilities = new AbilityCategory(false, "Constitution", 0, new Ability("Force", 0), new Ability("R\u00e9sistance", 0));
    public AbilityCategory mentalAbilities = new AbilityCategory(false, "Mental", 0, new Ability("Intellect", 0), new Ability("Eloquence", 0));
    public AbilityCategory dexteriteAbilities = new AbilityCategory(false, "Dext\u00e9rit\u00e9", 0, new Ability("Agilit\u00e9", 0), new Ability("Furtivit\u00e9", 0));
    public AbilityCategory survieAbilities = new AbilityCategory(false, "Survie", 0, new Ability("Perception", 0), new Ability("Savoir-faire", 0));
    public AbilityCategory specialAbilities1 = new AbilityCategory(true, "", 0, new Ability("", 0), new Ability("", 0), new Ability("", 0));
    public AbilityCategory specialAbilities2 = new AbilityCategory(true, "", 0, new Ability("", 0), new Ability("", 0), new Ability("", 0));
    public Talent talent1 = new Talent(0, "", Talent.TALENT_ABILITY.NULL);
    public Talent talent2 = new Talent(0, "", Talent.TALENT_ABILITY.NULL);

    @Override
    public String toString() {
        return "Character[" +

                name + "," +
                lastname + "," +
                consitutionAbilities.toString() + "," +
                mentalAbilities.toString() + "," +
                dexteriteAbilities.toString() + "," +
                survieAbilities.toString() + "," +
                specialAbilities1.toString() + "," +
                specialAbilities2.toString() + "," +
                talent1.toString() + "," +
                talent2.toString() +

                "]";
    }

    public static Character getFromString(String str) {
        if (str.startsWith("Character[")) {
            String[] list = str.split(Pattern.quote("Character["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            return new Character(
                    elements[0],
                    elements[1],
                    AbilityCategory.getFromString(elements[2]),
                    AbilityCategory.getFromString(elements[3]),
                    AbilityCategory.getFromString(elements[4]),
                    AbilityCategory.getFromString(elements[5]),
                    AbilityCategory.getFromString(elements[6]),
                    AbilityCategory.getFromString(elements[7]),
                    Talent.getFromString(elements[8]),
                    Talent.getFromString(elements[9])
            );
        }

        return null;
    }

    public Character(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    private Character(String name, String lastname,
                      AbilityCategory consitutionAbilities,
                      AbilityCategory mentalAbilities,
                      AbilityCategory dexteriteAbilities,
                      AbilityCategory survieAbilities,
                      AbilityCategory specialAbilities1,
                      AbilityCategory specialAbilities2,
                      Talent talent1,
                      Talent talent2) {
        this.name = name;
        this.lastname = lastname;
        this.consitutionAbilities = consitutionAbilities;
        this.mentalAbilities = mentalAbilities;
        this.dexteriteAbilities = dexteriteAbilities;
        this.survieAbilities = survieAbilities;
        this.specialAbilities1 = specialAbilities1;
        this.specialAbilities2 = specialAbilities2;
        this.talent1 = talent1;
        this.talent2 = talent2;
    }

    public int getTotalLevel() {
        return consitutionAbilities.getTotalCategoryLevel() +
                mentalAbilities.getTotalCategoryLevel() +
                dexteriteAbilities.getTotalCategoryLevel() +
                survieAbilities.getTotalCategoryLevel() +
                specialAbilities1.getTotalCategoryLevel() +
                specialAbilities2.getTotalCategoryLevel();
    }

    public int verifyAbilityChange(int oldAbilityLevel, int newAbilityLevel) {
        if (newAbilityLevel > 13) {
            newAbilityLevel = 13;
        }

        if (getTotalLevel() - oldAbilityLevel + newAbilityLevel > 70) {
            newAbilityLevel = 70 - getTotalLevel() + oldAbilityLevel;
        }

        return newAbilityLevel;

    }

    public int verifyTalentChange(int oldTalentLevel, int newTalentLevel) {
        if (talent1.getLevel() + talent2.getLevel() - oldTalentLevel + newTalentLevel > 5) {
            newTalentLevel = 5 - talent1.getLevel() + talent2.getLevel() + oldTalentLevel;
        }

        return newTalentLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        return name + " " + lastname;
    }

    public void writeToFile(Path out) throws IOException {
        writeToFile(out.toFile());
    }

    public void writeToFile(File out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        writer.write(this.toString());
        writer.close();
    }
}
