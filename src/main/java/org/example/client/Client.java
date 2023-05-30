package org.example.client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        startChat();
    }
    public static void startChat() throws IOException {
        System.out.println("Chat Start");
        Socket socket = new Socket("192.168.1.36",8080);
        System.out.println("connected to " + socket);
        BufferedReader readfromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader readfromTerminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writetoServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.print("\nPlease Enter your username : ");
        String username = readfromTerminal.readLine();
        writetoServer.write(username);
        writetoServer.newLine();
        writetoServer.flush();
        System.out.println(readfromServer.readLine()+"\n");
        while (true) {
            chatSession(readfromServer, readfromTerminal, writetoServer);
        }
//        socket.close();
    }
    public static void chatSession(BufferedReader readfromServer,BufferedReader readfromTerminal,BufferedWriter writetoServer) throws IOException {
        System.out.println("Your Turn:");
        writetoServer.write(readfromTerminal.readLine());
        writetoServer.newLine();
        writetoServer.flush();
        System.out.println("Server Turn : ");
        String msg = null;
        while ((msg = readfromServer.readLine()) == null ||(msg = readfromServer.readLine()).equals("")) {
            // Waiting for response from the server
            System.out.println("waiting for response from the server");
        }
        System.out.print(msg+"\n");
    }
}
