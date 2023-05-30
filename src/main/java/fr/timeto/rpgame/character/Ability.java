package fr.timeto.rpgame.character;

import java.io.Serializable;

public class Ability implements Serializable {

    private final String name;
    private int level;

    public Ability(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
