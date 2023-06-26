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

    @Override
    public String toString() {
        return "Character[" +

                name + "," +
                lastname +

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

    public Character(String name, String lastname) {
        this.name = name;
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
