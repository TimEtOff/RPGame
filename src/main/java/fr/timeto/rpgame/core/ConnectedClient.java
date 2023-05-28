package fr.timeto.rpgame.core;

import java.net.Socket;
import java.util.Random;

public class ConnectedClient {

    protected final Socket socket;
    protected String id;
    protected boolean idSet = false;

    public ConnectedClient(Socket socket) {
        this.socket = socket;
        this.id = null;
    }

    public ConnectedClient(Socket socket, String id) {
        this.socket = socket;
        this.id = id;
        idSet = true;
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
}
