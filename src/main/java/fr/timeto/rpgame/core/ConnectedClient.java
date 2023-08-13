package fr.timeto.rpgame.core;

import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.rpgame.character.Character;
import fr.timeto.rpgame.character.Inventory;

import java.io.IOException;
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

    private String id;
    private boolean idSet = false;
    private boolean isGM = false;
    private boolean ready = false;
    private Character selectedChar;
    private final Inventory inventory = new Inventory();

    @Override
    public String toString() {
        return "ConnectedClient[" +

                id + "," +
                idSet + "," +
                isGM + "," +
                ready + "," +
                selectedChar.toString() +

                "]";
    }

    public static ConnectedClient getFromString(String str) {
        if (str.startsWith("ConnectedClient[")) {
            String[] list = str.split(Pattern.quote("ConnectedClient["));
            String[] elements = correctSplit(Client.removeLastChar(list[1]));

            return new ConnectedClient(
                    elements[0],
                    Boolean.parseBoolean(elements[1]),
                    Boolean.parseBoolean(elements[2]),
                    Boolean.parseBoolean(elements[3]),
                    Character.getFromString(elements[4])
            );
        }

        return null;
    }

    private ConnectedClient(String id, boolean idSet, boolean isGM, boolean ready, Character selectedChar) {
        this.id = id;
        this.idSet = idSet;
        setGMButton.addEventListener(e -> {
            try {
                Client.sendToServer(Server.FROM_CLIENT.SET_GM.str + id + "] This client is now GM");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.isGM = isGM;
        this.ready = ready;
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
        setGMButton.addEventListener(e -> {
            try {
                Client.sendToServer(Server.FROM_CLIENT.SET_GM.str + id + "] This client is now GM");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        this.selectedChar = new Character("Undefined", "NotChosen");
    }

    public boolean setId(String id) {
        if (!idSet) {
            this.id = id;
            idSet = true;
            setGMButton.addEventListener(e -> {
                try {
                    Client.sendToServer(Server.FROM_CLIENT.SET_GM.str + id + "] This client is now GM");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
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

    public Inventory getInventory() {
        return inventory;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
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
        StringBuilder str = new StringBuilder("ArrayList<ConnectedClient>[");
        int i = 0;
        if (arrayList.size() != 0) {
            while (i != arrayList.size()) {
                str.append(arrayList.get(i).toString()).append(",");
                i++;
            }
            str = new StringBuilder(Client.removeLastChar(str.toString()));
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
        int classLevel = 0;
        list.add("");

        while (i != chars.length) {
                if (chars[i] == regex) {
                    if (getChar) {
                        index++;
                        list.add("");
                    } else {
                        list.set(index, list.get(index) + chars[i]);
                    }
                } else if (chars[i] == '[') {
                    classLevel++;
                    getChar = false;
                    list.set(index, list.get(index) + chars[i]);
                } else if (chars[i] == ']') {
                    classLevel--;
                    if (classLevel == 0) {
                        getChar = true;
                    }
                    list.set(index, list.get(index) + chars[i]);
                } else {
                    list.set(index, list.get(index) + chars[i]);
                }
            i++;
        }

        return list.toArray(new String[0]);
    }
}
