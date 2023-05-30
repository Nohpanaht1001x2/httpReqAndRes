package org.example.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//import static sun.jvm.hotspot.runtime.PerfMemory.start;

public class chatServer {

    public void run() throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            Socket client = server.accept();
            System.out.println(client+" Accepting");
            new Thread(
                    () -> {
                        try {
                            BufferedReader readfromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            BufferedReader readfromTerminal = new BufferedReader(new InputStreamReader(System.in));
                            BufferedWriter writetoClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//                            writetoClient.write(readfromTerminal.readLine());
                            String recivefromClient = readfromClient.readLine();
                            System.out.println("Accepting  "+recivefromClient);
                            writetoClient.write("Hello " + recivefromClient+"\n");
                            writetoClient.newLine();
                            writetoClient.flush();
                            while(true)
                                chatSession(readfromClient,readfromTerminal,writetoClient);
//                            System.out.println(readfromClient.read());
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }).start();
        }
    }
    public static void chatSession(BufferedReader readfromClient,BufferedReader readfromTerminal,BufferedWriter writetoClient) throws IOException {
        System.out.println("Client Turn : ");
        System.out.print(readfromClient.readLine()+"\n");
        System.out.println("Your Turn : ");
        writetoClient.write(readfromTerminal.readLine());
        writetoClient.newLine();
        writetoClient.flush();
    }

}
