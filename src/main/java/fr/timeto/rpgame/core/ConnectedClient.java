package fr.timeto.rpgame.core;

import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.character.Character;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class ConnectedClient implements Serializable {

    protected transient final Socket socket;
    public transient STexturedButton setGMButton = new STexturedButton(
            getResourceIgnorePath("/assets/rpgame/room/setGM-normal.png"),
            getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"),
            getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png")
        );

    protected String id;
    protected boolean idSet = false;
    protected boolean isGM = false;
    protected Character selectedChar = null;

    @Override
    public String toString() {
        return "ConnectedClient[" +

                id + "," +
                idSet + "," +
                isGM + "," +
                selectedChar.toString() +

                "]";
    }

    public static ConnectedClient getFromString(String str) {
        if (str.startsWith("ConnectedClient[")) {
            String[] list = str.split(Pattern.quote("ConnectedClient["));
            String[] elements = correctSplit(Client.removeLastChar(list[1]));

            ConnectedClient connectedClient = new ConnectedClient(
                    elements[0],
                    Boolean.parseBoolean(elements[1]),
                    Boolean.parseBoolean(elements[2]),
                    Character.getFromString(elements[3])
            );

            return connectedClient;
        }

        return null;
    }

    private ConnectedClient(String id, boolean idSet, boolean isGM, Character selectedChar) {
        this.id = id;
        this.idSet = idSet;
        this.isGM = isGM;
        this.selectedChar = selectedChar;
        this.socket = null;
    }

    public ConnectedClient(Socket socket) {
        this.socket = socket;
        this.id = null;
        this.selectedChar = new Character("Undefined", "NotChosen");
    }

    public ConnectedClient(Socket socket, String id) {
        this.socket = socket;
        this.id = id;
        idSet = true;
        this.selectedChar = new Character("Undefined", "NotChosen");
    }

    public boolean setId(String id) {
        if (!idSet) {
            this.id = id;
            idSet = true;
            return true;
        } else {
            return false;
        }
    }

    public String getId() {
        return id;
    }

    public void setGM(boolean GM) {
        isGM = GM;
    }

    public boolean isGM() {
        return isGM;
    }

    public void setCharacter(Character selectedChar) {
        this.selectedChar = selectedChar;
    }

    public Character getCharacter() {
        return selectedChar;
    }

    public void initSetGMButton() {
        setGMButton = new STexturedButton(
                getResourceIgnorePath("/assets/rpgame/room/setGM-normal.png"),
                getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png"),
                getResourceIgnorePath("/assets/rpgame/room/setGM-hover.png")
        );
    }

    public static String generateHash() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String connectedClientsToString(ArrayList<ConnectedClient> arrayList) {
        String str = "ArrayList<ConnectedClient>[";
        int i = 0;
        if (arrayList.size() != 0) {
            while (i != arrayList.size()) {
                str += arrayList.get(i).toString() + ",";
                i++;
            }
            str = Client.removeLastChar(str);
        }
        return str + "]";
    }

    public static ArrayList<ConnectedClient> connectedClientsFromString(String str) {
        ArrayList<ConnectedClient> arrayList = new ArrayList<>();
        if (str.startsWith("ArrayList<ConnectedClient>[")) {
            String[] list = str.split(Pattern.quote("ArrayList<ConnectedClient>["));
            String[] elements = correctSplit(Client.removeLastChar(list[1]));
            int i = 0;
            while (i != elements.length) {
                arrayList.add(ConnectedClient.getFromString(elements[i]));
                i++;
            }
        }

        return arrayList;
    }

    public static String[] correctSplit(String str) {
        final char regex = ',';
        char[] chars = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        int index = 0;
        int i = 0;
        boolean getChar = true;
        list.add("");

        while (i != chars.length) {
                if (chars[i] == regex) {
                    if (getChar) {
                        index++;
                        list.add("");
                    } else {
                        list.set(index, list.get(index) + String.valueOf(chars[i]));
                    }
                } else if (chars[i] == '[') {
                    getChar = false;
                    list.set(index, list.get(index) + String.valueOf(chars[i]));
                } else if (chars[i] == ']') {
                    getChar = true;
                    list.set(index, list.get(index) + String.valueOf(chars[i]));
                } else {
                    list.set(index, list.get(index) + String.valueOf(chars[i]));
                }
            i++;
        }

        return list.toArray(new String[0]);
    }
}
