package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Ability implements Serializable {

    private String name;
    private int level;

    public Ability(String name, int level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Ability[" +

                name + "," +
                level +

                "]";
    }

    public static Ability getFromString(String str) {
        if (str.startsWith("Ability[")) {
            String[] list = str.split(Pattern.quote("Ability["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));
        //    String[] elements = ConnectedClient.correctSplit(list[1]);

            return new Ability(
                    elements[0],
                    Integer.parseInt(elements[1])
            );
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
