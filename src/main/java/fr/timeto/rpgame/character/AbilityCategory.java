package fr.timeto.rpgame.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AbilityCategory extends ArrayList<Ability> implements Serializable {

    private final String name;

    public AbilityCategory(String name, Ability... abilities) {
        this.name = name;
        int i = 0;

        while (i != abilities.length) {
            this.add(abilities[i]);
            i++;
        }
    }

    public void set(Ability ability) {
        int i = 0;
        while (i != this.size()) {
            if (Objects.equals(this.get(i).getName(), ability.getName())) {
                this.set(i, ability);
                return;
            }
            i++;
        }
        this.add(ability);
    }
}
