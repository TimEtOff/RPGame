package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.*;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Character implements Serializable {

    // TODO Doit avoir un max de 75 point de compétences
    protected String name;
    protected String lastname;
    protected AbilityCategory consitutionAbilities = new AbilityCategory(false, "Constitution", 0, new Ability("Force", 0), new Ability("Résistance", 0));
    protected AbilityCategory mentalAbilities = new AbilityCategory(false, "Mental", 0, new Ability("Intellect", 0), new Ability("Eloquence", 0));
    protected AbilityCategory dexteriteAbilities = new AbilityCategory(false, "Dextérité", 0, new Ability("Agilité", 0), new Ability("Furtivité", 0));
    protected AbilityCategory survieAbilities = new AbilityCategory(false, "Survie", 0, new Ability("Perception", 0), new Ability("Savoir-faire", 0));
    protected AbilityCategory specialAbilities1 = new AbilityCategory(true, "", 0, new Ability("", 0), new Ability("", 0), new Ability("", 0));
    protected AbilityCategory specialAbilities2 = new AbilityCategory(true, "", 0, new Ability("", 0), new Ability("", 0), new Ability("", 0));

    @Override
    public String toString() {
        return "Character[" +

                name + "," +
                lastname + "," +
                consitutionAbilities + "," +
                mentalAbilities + "," +
                dexteriteAbilities + "," +
                survieAbilities + "," +
                specialAbilities1 + "," +
                specialAbilities2 +

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
                    AbilityCategory.getFromString(elements[7])
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
                      AbilityCategory specialAbilities2) {
        this.name = name;
        this.lastname = lastname;
        this.consitutionAbilities = consitutionAbilities;
        this.mentalAbilities = mentalAbilities;
        this.dexteriteAbilities = dexteriteAbilities;
        this.survieAbilities = survieAbilities;
        this.specialAbilities1 = specialAbilities1;
        this.specialAbilities2 = specialAbilities2;
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
