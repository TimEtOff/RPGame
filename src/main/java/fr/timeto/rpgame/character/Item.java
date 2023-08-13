package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public class Item implements Serializable {

    String name;
    TYPES type;

    public Item(String name, TYPES type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Item[" +

                name + "," +
                type.getName() +

                "]";
    }

    public static Item getFromString(String str) {
        if (str.startsWith("Item[")) {
            String[] list = str.split(Pattern.quote("Item["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            return new Item(
                    elements[0],
                    getTypeFromString(elements[1])
            );
        }

        return null;
    }

    public static TYPES getTypeFromString(String str) {
        int i = 1;
        TYPES[] types = TYPES.values();
        while (i != types.length -1) {
            if (Objects.equals(str, types[i].name)) {
                return types[i];
            }
            i++;
        }
        return TYPES.NULL;
    }

    public enum TYPES {
        NULL(""),
        HEALTH("Health"),
        ENERGY("Energy");

        final String name;

        TYPES(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
