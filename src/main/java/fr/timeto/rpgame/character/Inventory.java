package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Inventory extends ArrayList<Items> implements Serializable {

    public Inventory(Items... items) { // TODO Tester quand ce sera visible

        if (items != null) {
            int i = 0;
            while (i != items.length) {
                this.add(items[i]);
                i++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Inventory[" + this.size());

        int i = 0;
        while (i != this.size()) {
            str.append(",").append(this.get(i).toString());
            i++;
        }

        str.append("]");

        return str.toString();
    }

    public static Inventory getFromString(String str) {
        if (str.startsWith("Inventory[")) {
            String[] list = str.split(Pattern.quote("Inventory["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            ArrayList<Items> items = new ArrayList<>();
            int i = 0;
            while (i != Integer.parseInt(elements[0])) {
                items.add(Items.getFromString(elements[i + 1]));
                i++;
            }

            return new Inventory(
                    items.toArray(new Items[0])
            );
        }

        return null;
    }

    @Override
    public boolean add(Items e) {
        e.setLinkedInventory(this);
        return super.add(e);
    }
}
