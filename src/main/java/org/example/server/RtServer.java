package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RtServer {
    public void run() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Socket client = server.accept();
        System.out.println(client + " Accepting");
        BufferedReader readfromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedReader readfromTerminal = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writetoClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        Thread reader = new Thread(() -> {
            try {
                while (true) {
                    String line = readfromClient.readLine();
                    if (line.equals(""))
                        continue;
                    System.out.println(/*"Message from Client : "+*/line);

                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        Thread writter = new Thread(() -> {
            try {
                while (true) {
                    String line = readfromTerminal.readLine();
                    if (line.equals(""))
                        continue;
                    writetoClient.write(line);
                    writetoClient.newLine();
                    writetoClient.flush();
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
        while (true) {
            try{
                reader.start();
                writter.start();
            }catch (IllegalThreadStateException e){
                break;
            }
        }
    }

}
