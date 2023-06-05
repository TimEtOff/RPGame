package fr.timeto.rpgame.core;

import fr.timeto.rpgame.display.GameFrame;
import fr.timeto.rpgame.display.Room;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


public class Client {

    public enum FROM_SERVER {
        SENDING_CONNECTED_CLIENTS("[SendingConnectedClients]", 1),

        TEXT("", 0);

        public final String str;
        public final int i;

        FROM_SERVER(String str, int i) {
            this.str = str;
            this.i = i;
        }
    }

    public static ArrayList<ConnectedClient> connectedClients = null;

    protected static Socket socket;
    public static GameFrame gameFrame;
    public static String publicIP;
    public static String id;

    public static boolean isConnectedToServer = false;

    public static void main(String[] args) throws Exception {
        GameFrame.RP_GAMEFOLDER.mkdir();
        GameFrame.RP_CHARACTERSFOLDER.mkdir();
        GameFrame.RP_MAPSFOLDER.mkdir();
        GameFrame.RP_CONFIGFILE.createNewFile();

        if (GameFrame.RP_CONFIGFILE_SAVER.get("id") == null) {
            GameFrame.RP_CONFIGFILE_SAVER.set("id", ConnectedClient.generateHash());
        }

        if (args.length != 0) {
            if (Objects.equals(args[0], "--randomId")) {
                id = ConnectedClient.generateHash();
            } else {
                id = GameFrame.RP_CONFIGFILE_SAVER.get("id");
            }
        } else {
            id = GameFrame.RP_CONFIGFILE_SAVER.get("id");
        }

        gameFrame = new GameFrame();
        publicIP = Objects.requireNonNull(getMyPublicIP());
    }

    public static void println(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("[" + dtf.format(now) + "] [" + publicIP + " (Me)/Private] " + str);
    }

    public static void connectToServer() {
        Thread t = new Thread(() -> {
            println("Connecting to server...");
            try {
                String ip = SecretInfos.SERVER_IP;
                if (Objects.equals(getMyPublicIP(), SecretInfos.SERVER_IP)) {
                    ip = "127.0.0.1";
                }
                socket = new Socket(ip, SecretInfos.SERVER_PORT);
            } catch (ConnectException e) {
                JOptionPane.showMessageDialog(null, "Echec de la connexion", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isConnectedToServer = true;
            println("You are now connected to the server");
            gameFrame.setContentPane(new Room());

            BufferedWriter writerChannel = null;
            BufferedReader readerChannel = null;
            try {
                writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                sendToServer(Server.FROM_CLIENT.SENDING_ID.str + id + "]");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String line;
            while (true && isConnectedToServer) {
                try {
                    if (!((line = readerChannel.readLine()) != null)) break;
                } catch (SocketException e) {
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                int info = verifyInfosFromServer(line);

                if (info == FROM_SERVER.SENDING_CONNECTED_CLIENTS.i) {
                    if (gameFrame.getContentPane() instanceof Room) {
                        ((Room) gameFrame.getContentPane()).spinner.setVisible(true);
                    }
                    getConnectedClients(line);
                //    System.out.println(line);
                } else if (info == FROM_SERVER.TEXT.i) {
                    System.out.println(line);
                }
            }
            });
            t.start();
        }

        public static void sendToServer(String str) throws IOException {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;

            writerChannel.write(str + "\n");
            writerChannel.flush();

            System.out.println("[" + dtf.format(now) + "] [" + publicIP + " (Me)] " + str);
        }

        private static int nbTryGetConnectedClients = 1;
        protected static void getConnectedClients(String txtFromServer) {
            System.out.println(txtFromServer);

            try {
                println("Get connected clients - try " + nbTryGetConnectedClients);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object object = ois.readUnshared();
                connectedClients = (ArrayList<ConnectedClient>) object;
                nbTryGetConnectedClients = 1;
                println("Got connected clients list");
                if (gameFrame.getContentPane() instanceof Room) {
                    ((Room) gameFrame.getContentPane()).spinner.setVisible(false);
                }

            } catch (Exception e) {
                try {
                    nbTryGetConnectedClients++;
                    sendToServer(Server.FROM_CLIENT.ASK_FOR_CONNECTED_CLIENTS.str + " StreamCorruptedException, retry");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (gameFrame.getContentPane() instanceof Room) {
                ((Room) gameFrame.getContentPane()).repaint();
            }
        }

        public static void disconnect() {
            try {
                if (isConnectedToServer) {
                    Client.sendToServer(Server.FROM_CLIENT.DISCONNECTING.str + " Client disconnecting...");
                socket.close();
                connectedClients = null;
                isConnectedToServer = false;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected static int verifyInfosFromServer(String str) {
        int i = 0;
        FROM_SERVER[] values = FROM_SERVER.values();
        while (i != values.length) {
            if (str.contains(values[i].str)) {
                return values[i].i;
            }
            i++;
        }

        return FROM_SERVER.TEXT.i;
    }

    public static String getMyPublicIP() {
        try
        {
            URL ip = new URL("https://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(ip.openStream()));
            return br.readLine();
        }
        catch(Exception e)
        {
            System.out.println("Exception: " +e);
            return null;
        }
    }
}
