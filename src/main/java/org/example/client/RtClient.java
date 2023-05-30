package org.example.client;

import java.io.*;
import java.net.Socket;

public class RtClient {
//    public static void main(String[] args) throws IOException {
//        startChat();
//    }
    public static void main(String[] args) throws IOException {
        System.out.println("Chat Start");
        Socket socket = new Socket("192.168.1.36",8080);
        System.out.println("connected to " + socket);
        BufferedReader readfromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader readfromTerminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writetoServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        System.out.print("\nPlease Enter your username : ");
        Thread clientReader = new Thread(() -> {
            try {
                while (true) {
                    String line = readfromServer.readLine();
                    if (line.equals(""))
                        continue;
                    System.out.println(/*"Message from Server : "+*/line);

                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        Thread clientWritter = new Thread(() -> {
            try {
                while (true) {
                    String line = readfromTerminal.readLine();
                    if (line.equals(""))
                        continue;
                    writetoServer.write(line);
                    writetoServer.newLine();
                    writetoServer.flush();
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        while(true) {
            try{
                clientReader.start();
                clientWritter.start();
            }catch (IllegalThreadStateException e) {
                break;
            }
        }
    }
}
