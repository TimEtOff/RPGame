package fr.timeto.rpgame.character;

import java.io.Serializable;

public class Character implements Serializable {

    // TODO Doit avoir un max de 75 point de compétences
    protected String name;
    protected String firstname;

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
