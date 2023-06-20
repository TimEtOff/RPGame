package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Character implements Serializable {

    // TODO Doit avoir un max de 75 point de compétences
    protected String name;
    protected String firstname;

    @Override
    public String toString() {
        return "Character[" +

                name + "," +
                firstname +

                "]";
    }

    public static Character getFromString(String str) {
        if (str.startsWith("Character[")) {
            String[] list = str.split(Pattern.quote("Character["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            Character connectedClient = new Character(
                    elements[0],
                    elements[1]
            );
            return connectedClient;
        }

        return null;
    }

    public Character(String name, String firstname) {
        this.name = name;
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getFullName() {
        return name + " " + firstname;
    }
}
