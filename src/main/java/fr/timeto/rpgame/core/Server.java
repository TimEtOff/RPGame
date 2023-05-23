package fr.timeto.rpgame.core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Server
{
    protected static ArrayList<Socket> connectedSockets = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        ServerSocket listener = new ServerSocket(4000);
        String line;
        try
        {
            while (true)
            {
                Socket socket = listener.accept();
                BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                connectedSockets.add(socket);
                try
                {
                    printToAllSockets(socket.getLocalAddress().getHostAddress() + " c'est connecté", true);

                    while ((line = readerChannel.readLine()) != null)
                    {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        String newLine = "[" + dtf.format(now) + "] [" + socket.getLocalAddress().getHostAddress() + "] " + line;
                        System.out.println(newLine);
                    }
                } catch (SocketException ex) {
                    connectedSockets.remove(socket);
                    printToAllSockets(socket.getLocalAddress().getHostAddress() + " c'est déconnecté", true);
                    return;
                }
                finally
                {
                    socket.close();
                }
            }
        }
        finally
        {
            listener.close();
        }
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
                Socket socket = connectedSockets.get(i);
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
}
