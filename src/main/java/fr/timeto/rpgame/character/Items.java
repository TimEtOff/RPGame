package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Items implements Serializable {

    Item item;
    int number;
    int specialStat;
    transient Inventory linkedInventory = null;

    public Items(Item item) {
        this(item, 1, 1);
    }

    public Items(Item item, int number, int specialStat) {
        this.item = item;
        this.number = number;
        this.specialStat = specialStat;
    }

    public void consume() {
        consume(null);
    }

    public void consume(Character character) {
        if (this.number <= 0) {
            throw new RuntimeException("No more item to be consume");
        } else if (item.type == Item.TYPES.HEALTH) {
            if (character == null) {
                throw new NullPointerException("Character is null, cannot restore health");
            } else if (character.health < 4) {
              character.health += 1;
              this.number -= 1;
            }
        } else if (item.type == Item.TYPES.ENERGY) {
            if (character == null) {
                throw new NullPointerException("Character is null, cannot restore energy");
            } else if (character.energy < 4) {
                character.energy += 1;
                this.number -= 1;
            }
        } else if (item.type == Item.TYPES.NULL) {
            this.number -=1;
        }

        if (this.number <= 0 && this.linkedInventory != null) {
            this.linkedInventory.remove(this);
        }
    }

    @Override
    public String toString() {
        return "Items[" +

                item.toString() + "," +
                number + "," +
                specialStat +

                "]";
    }

    public static Items getFromString(String str) {
        if (str.startsWith("Items[")) {
            String[] list = str.split(Pattern.quote("Items["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            return new Items(
                    Item.getFromString(elements[0]),
                    Integer.parseInt(elements[1]),
                    Integer.parseInt(elements[2])
            );
        }

        return null;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }

    public Inventory getLinkedInventory() {
        return linkedInventory;
    }

    public void setNumber(int number) {
        this.number = number;

        if (this.number <= 0 && this.linkedInventory != null) {
            this.linkedInventory.remove(this);
        }
    }

    public void setLinkedInventory(Inventory linkedInventory) {
        this.linkedInventory = linkedInventory;
    }

    public void addNumber(int number) {
        this.number += number;
    }

    public void removeNumber(int number) {
        this.number -= number;

        if (this.number <= 0 && this.linkedInventory != null) {
            this.linkedInventory.remove(this);
        }
    }

    public void addOne() {
        addNumber(1);
    }

    public void removeOne() {
        removeNumber(1);

        if (this.number <= 0 && this.linkedInventory != null) {
            this.linkedInventory.remove(this);
        }
    }
}
