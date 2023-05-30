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
import java.util.Objects;
import java.util.regex.Pattern;


public class Server {

    public enum FROM_CLIENT {
        SENDING_ID("[MyID=", 1),
        UPLOAD_FILE("[UploadFile]", 2),
        DOWNLOAD_FILE("[DownloadFile]", 3),
        DISCONNECTING("[Disconnecting]", 4),

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

    protected static ArrayList<ConnectedClient> connectedSockets = new ArrayList<>();

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
            connectedSockets.add(connectedClient);
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
                    }
                }
                throw new SocketException("Disconnected ?");
            } catch (SocketException ex) {
                removeFromSocketList(connectedClient);
                try {
                    printToAllSockets(getIPFromSocket(socket) + " (" + connectedClient.id + ") disconnected", true);
                } catch (SocketException ignored) {}
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        printToSocket(client, true, str);
    }

    protected static void printToSocket(ConnectedClient client, boolean appearLikePrivate, String str) throws IOException {
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
            connectedSockets.remove(socket);
        }

        System.out.println(newStr);
    }

    protected static void printToAllSockets(String str) throws IOException {
        printToAllSockets(str, false);
    }

    protected static void printToAllSockets(String str, boolean serverConsoleIncluded) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String newStr = "[" + dtf.format(now) + "] [Server] " + str;
        if (!connectedSockets.isEmpty()) {
            int i = 0;
            while (i != connectedSockets.size()) {
                Socket socket = connectedSockets.get(i).socket;
                BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                try
                {
                    writerChannel.write(newStr + "\n");
                    writerChannel.flush();

                } catch (SocketException ex) {
                    connectedSockets.remove(socket);
                }
                i++;
            }
        }

        if (serverConsoleIncluded) {
            System.out.println(newStr);
        }
    }

    protected static void sendConnectedClientsToSocket(ConnectedClient client) {
        try {

            printToSocket(client, Client.FROM_SERVER.SENDING_CONNECTED_CLIENTS.str + " Sending actual connected clients");
            ObjectOutputStream oos = new ObjectOutputStream(client.socket.getOutputStream());
            oos.writeUnshared(connectedSockets);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected static void sendConnectedClientsToAll() {

        if (!connectedSockets.isEmpty()) {
            int i = 0;
            while (i != connectedSockets.size()) {
                ConnectedClient client = connectedSockets.get(i);
                try {

                    printToSocket(client, false, Client.FROM_SERVER.SENDING_CONNECTED_CLIENTS.str + " Sending actual connected clients");
                    ObjectOutputStream oos = new ObjectOutputStream(client.socket.getOutputStream());
                    oos.writeUnshared(connectedSockets);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
        }
    }

    public static void removeFromSocketList(ConnectedClient client) {
        if (!connectedSockets.isEmpty()) {
            int i = 0;
            while (i != connectedSockets.size()) {
                if (Objects.equals(connectedSockets.get(i).id, client.id)) {
                    connectedSockets.remove(i);
                    break;
                }
            }
        }
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
