package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class AbilityCategory extends ArrayList<Ability> implements Serializable {

    private final boolean special;
    private String name;
    private int categoryLevel;
    private final int numberOfAbilities;

    public AbilityCategory(boolean special, String name, int categoryLevel, Ability... abilities) {
        this.special = special;
        this.name = name;
        this.categoryLevel = categoryLevel;

        int i = 0;

        while (i != abilities.length) {
            this.add(abilities[i]);
            i++;
        }
        numberOfAbilities = i;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("AbilityCategory[" +
                special + "," +
                name + "," +
                categoryLevel + "," +
                numberOfAbilities);

        int i = 0;
        while (i != numberOfAbilities) {
            str.append(",").append(this.get(i).toString());
            i++;
        }

        str.append("]");

        return str.toString();

    }

    public static AbilityCategory getFromString(String str) {
        if (str.startsWith("AbilityCategory[")) {
            String[] list = str.split(Pattern.quote("AbilityCategory["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            ArrayList<Ability> abilities = new ArrayList<>();
            int i = 0;
            while (i != Integer.parseInt(elements[3])) {
                abilities.add(Ability.getFromString(elements[i + 4]));
                i++;
            }

            return new AbilityCategory(
                    Boolean.parseBoolean(elements[0]),
                    elements[1],
                    Integer.parseInt(elements[2]),
                    abilities.toArray(new Ability[0])

            );
        }

        return null;
    }

    public int getTotalCategoryLevel() {
        int i = 0;
        int level = categoryLevel;
        while (i != numberOfAbilities) {
            level += this.get(i).getLevel();
            i++;
        }
        return level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryLevel(int categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getName() {
        return name;
    }

    public int getCategoryLevel() {
        return categoryLevel;
    }
}
