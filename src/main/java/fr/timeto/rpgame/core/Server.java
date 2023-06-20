package fr.timeto.rpgame.core;

import fr.theshark34.openlauncherlib.util.Saver;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Server {

    public enum FROM_CLIENT {
        SENDING_ID("[MyID=", 1),
        UPLOAD_FILE("[UploadFile]", 2),
        DOWNLOAD_FILE("[DownloadFile]", 3),
        DISCONNECTING("[Disconnecting]", 4),
        ASK_FOR_CONNECTED_CLIENTS("[AskConnectedClients]", 5),
        SET_GM("[NewGM=", 6),

        TEXT("", 0);

        public final String str;
        public final int i;

        FROM_CLIENT(String str, int i) {
            this.str = str;
            this.i = i;
        }
    }

    public enum FILE_TYPES {

    }

    protected static File SERVER_MAINFOLDER;
    protected static File SERVER_MAPFOLDER;
    protected static File SERVER_INFOSFILE;
    protected static Saver infosSaver;

    protected static ArrayList<ConnectedClient> connectedClients = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // ./build/classes/java/main/server when run in ItelliJ project
        SERVER_MAINFOLDER = new File(new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI()), "server");
        SERVER_MAPFOLDER = new File(SERVER_MAINFOLDER, "map");
        SERVER_INFOSFILE = new File(SERVER_MAINFOLDER, "game.properties");

        SERVER_MAINFOLDER.mkdir();
        SERVER_MAPFOLDER.mkdir();
        SERVER_INFOSFILE.createNewFile();
        infosSaver = new Saver(SERVER_INFOSFILE.toPath());

        ServerSocket listener = new ServerSocket(SecretInfos.SERVER_PORT);
        println("Server is now online");
        try
        {
            while (true)
            {
                Socket socket = listener.accept();
                newClient(socket);
            }
        }
        finally
        {
            listener.close();
        }
    }

    protected static void newClient(Socket socket) {
        Thread t = new Thread(() -> {
            BufferedReader readerChannel = null;
            BufferedWriter writerChannel = null;
            try {
                readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ConnectedClient connectedClient = new ConnectedClient(socket);
            connectedClients.add(connectedClient);
            if (connectedClients.size() == 1) {
                connectedClient.setGM(true);
            }
            String line;
            try
            {

                while ((line = readerChannel.readLine()) != null)
                {
                    int info = verifyInfosFromClient(line);

                    if (info == FROM_CLIENT.TEXT.i) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String newLine = "[" + dtf.format(now) + "] [" + getIPFromSocket(socket) + " (" + connectedClient.id + ")] " + line;
                        System.out.println(newLine);

                    } else if (info == FROM_CLIENT.SENDING_ID.i) {
                        String[] split = line.split(Pattern.quote(FROM_CLIENT.SENDING_ID.str));
                        String id = split[1].substring(0, 10).replaceAll(" ", "");
                        if (connectedClient.setId(id)) {
                            printToAllSockets(getIPFromSocket(socket) + " (" + connectedClient.id + ") connected", true);
                            sendConnectedClientsToAll();
                        }

                    } else if (info == FROM_CLIENT.DISCONNECTING.i) {
                        socket.close();
                        throw new SocketException("Disconnected ?");

                    } else if (info == FROM_CLIENT.ASK_FOR_CONNECTED_CLIENTS.i) {
                        sendConnectedClientsToSocket(connectedClient, false);
                    }
                }
                throw new SocketException("Disconnected ?");
            } catch (SocketException ex) {
                disconnectClient(connectedClient);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally
            {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    protected static int verifyInfosFromClient(String str) {
        int i = 0;
        FROM_CLIENT[] values = FROM_CLIENT.values();
        while (i != values.length) {
            if (str.contains(values[i].str)) {
                return values[i].i;
            }
            i++;
        }

        return FROM_CLIENT.TEXT.i;
    }

    protected static void printToSocket(ConnectedClient client, String str) throws IOException {
        printToSocket(client, true, true, str);
    }

    protected static void printToSocket(ConnectedClient client, boolean appearLikePrivate, String str) throws IOException {
        printToSocket(client, appearLikePrivate, true, str);
    }

    protected static void printToSocket(ConnectedClient client, boolean appearLikePrivate, boolean serverConsoleIncluded, String str) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String newStr;
        if (appearLikePrivate) {
            newStr = "[" + dtf.format(now) + "] [Server/To " + getIPFromSocket(client.socket) + " (" + client.id + ") only] " + str;
        } else {
            newStr = "[" + dtf.format(now) + "] [Server] " + str;
        }
        Socket socket = client.socket;
        BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        try {
            writerChannel.write(newStr + "\n");
            writerChannel.flush();
        } catch (SocketException ex) {
            connectedClients.remove(socket);
        }

        if (serverConsoleIncluded) {
            System.out.println(newStr);
        }
    }

    protected static void printToAllSockets(String str) throws IOException {
        printToAllSockets(str, false);
    }

    protected static void printToAllSockets(String str, boolean serverConsoleIncluded) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String newStr = "[" + dtf.format(now) + "] [Server] " + str;
        if (!connectedClients.isEmpty()) {
            int i = 0;
            while (i != connectedClients.size()) {
                Socket socket = connectedClients.get(i).socket;
                BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                try
                {
                    writerChannel.write(newStr + "\n");
                    writerChannel.flush();

                } catch (SocketException ex) {
                    connectedClients.remove(socket);
                }
                i++;
            }
        }

        if (serverConsoleIncluded) {
            System.out.println(newStr);
        }
    }

    protected static void sendConnectedClientsToSocket(ConnectedClient client) {
        sendConnectedClientsToSocket(client, true);
    }

    protected static void sendConnectedClientsToSocket(ConnectedClient client, boolean print) {
        try {

            if (print) {
                printToSocket(client, Client.FROM_SERVER.SENDING_CONNECTED_CLIENTS.str + " Sending actual connected clients");
            }

            /*    ObjectOutputStream oos = new ObjectOutputStream(client.socket.getOutputStream());
            oos.flush();
            oos.writeUnshared(connectedClients);    // Premiere version testée qui marchait pas à 100%
            oos.flush(); */

            Socket socket = client.socket;
            BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            try {
                writerChannel.write(ConnectedClient.connectedClientsToString(connectedClients) + "\n");
                writerChannel.flush();
            } catch (SocketException ex) {
                connectedClients.remove(socket);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void sendConnectedClientsToAll() {

        for (ConnectedClient connectedClient : connectedClients) {
            System.out.println(connectedClient.id);
        }

        if (!connectedClients.isEmpty()) {
            int i = 0;
            println(Client.FROM_SERVER.SENDING_CONNECTED_CLIENTS.str + " Sending actual connected clients");
            while (i != connectedClients.size()) {
                ConnectedClient client = connectedClients.get(i);
                try {

                    printToSocket(client, false, false, Client.FROM_SERVER.SENDING_CONNECTED_CLIENTS.str + " Sending actual connected clients");

                    /*    ObjectOutputStream oos = new ObjectOutputStream(client.socket.getOutputStream());
                    oos.flush();
                    oos.writeUnshared(connectedClients);    // Premiere version testée qui marchait pas à 100%
                    oos.flush(); */

                    Socket socket = client.socket;
                    BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    try {
                        String line = ConnectedClient.connectedClientsToString(connectedClients);
                        System.out.println(line);
                        writerChannel.write(line + "\n");
                        writerChannel.flush();
                    } catch (SocketException ex) {
                        connectedClients.remove(socket);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
        }
    }

    protected static void disconnectClient(ConnectedClient client) {
        connectedClients.remove(client);
        try {
            printToAllSockets(getIPFromSocket(client.socket) + " (" + client.id + ") disconnected", true);
        } catch (SocketException ignored) {}
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (client.isGM() && !connectedClients.isEmpty()) {
            ConnectedClient newGM = connectedClients.get(0);
            println("New GM is " + getIPFromSocket(newGM.socket) + " (" + newGM.getId() + ")");
            newGM.setGM(true);
        }

        sendConnectedClientsToAll();
    }

    public static void println(String str) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("[" + dtf.format(now) + "] [Server/Private] " + str);
    }

    public static String getIPFromSocket(Socket socket) {
        String ip = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
        if (ip.equals("127.0.0.1") || ip.equals("0.0.0.0")) {
            ip = Client.getMyPublicIP();
        }
        return ip;
    }
}
