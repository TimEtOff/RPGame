package fr.timeto.rpgame.character;

import fr.timeto.rpgame.core.Client;
import fr.timeto.rpgame.core.ConnectedClient;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public class Talent implements Serializable {

    int level;
    String name;
    TALENT_ABILITY ability;


    public Talent(int level, String name, TALENT_ABILITY ability) {
        this.level = level;
        this.name = name;
        this.ability = ability;
    }

    public String getAbilityShortName() {
        String str = "";
        if (ability != TALENT_ABILITY.NULL) {
            str += "(" + ability.name.toLowerCase().substring(0, 3) + ")";
        }
        return str;
    }

    public static String getAbilityShortName(TALENT_ABILITY ability) {
        String str = "";
        if (ability != TALENT_ABILITY.NULL) {
            str += "(" + ability.name.toLowerCase().substring(0, 3) + ")";
        }
        return str;
    }

    @Override
    public String toString() {
        return "Talent[" + level + "," +
                name + "," +
                ability.name +
                "]";
    }

    public static Talent getFromString(String str) {
        if (str.startsWith("Talent[")) {
            String[] list = str.split(Pattern.quote("Talent["));
            String[] elements = ConnectedClient.correctSplit(Client.removeLastChar(list[1]));

            return new Talent(
                    Integer.parseInt(elements[0]),
                    elements[1],
                    getAbilityFromString(elements[2])
            );
        }

        return null;
    }

    public static TALENT_ABILITY getAbilityFromString(String str) {
        int i = 1;
        TALENT_ABILITY[] abilities = TALENT_ABILITY.values();
        while (i != abilities.length -1) {
            if (Objects.equals(str, abilities[i].name)) {
                return abilities[i];
            }
            i++;
        }
        return TALENT_ABILITY.NULL;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbility(TALENT_ABILITY ability) {
        this.ability = ability;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public TALENT_ABILITY getAbility() {
        return ability;
    }

    public enum TALENT_ABILITY {
        NULL(""),
        FORCE("Force"),
        RESISTANCE("R\u00e9sistance"),
        INTELLECT("Intellect"),
        ELOQUENCE("Eloquence"),
        AGILITE("Agilit\u00e9"),
        FURTIVITE("Furtivit\u00e9"),
        PERCEPTION("Perception"),
        SAVOIR_FAIRE("Savoir-faire");

        final String name;

        TALENT_ABILITY(String name) {
            this.name = name;
        }
    }
}
