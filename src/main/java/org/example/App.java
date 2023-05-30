package org.example;

import org.example.dbrepo.StudentRepo;
import org.example.server.RtServer;
import org.example.server.ServletServer;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
//        HttpServer A = new HttpServer();
//        RtServer A = new RtServer();
//        A.run();
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                ServletServer A = new ServletServer();
                try {
                    A.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread db = new Thread(new Runnable() {
            @Override
            public void run() {
                try (StudentRepo repo = new StudentRepo();) {
                    repo.connect();
                    System.out.println("Repo - Find all : " + repo.findAll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        server.start();
//        db.start();

    }
}
