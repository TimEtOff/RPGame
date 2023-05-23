package fr.timeto.rpgame.core;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Client
{
    protected static Socket socket;

    public static void main(String[] args) throws Exception
    {
        socket = new Socket("127.0.0.1", 4000);
        BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;

        sendToServer(new Date().toString());

        while ((line = readerChannel.readLine()) != null)
        {
            System.out.println(line);
        }
    }

    protected static void sendToServer(String str) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;

        writerChannel.write(str + "\n");
        writerChannel.flush();

        System.out.println("[" + dtf.format(now) + "] [" + socket.getLocalAddress().getHostAddress() + " (Me)] " + str);
    }
}
