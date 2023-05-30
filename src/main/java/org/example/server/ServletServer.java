package org.example.server;

import org.example.servlet.CustomServlet;
import org.example.servlet.Request;
import org.example.servlet.Response;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ServletServer {
    private Map<Class, CustomServlet> servletMap = new HashMap<>();

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    System.out.println("\naccept 1 client");
                    //if not already load this servlet
                    // load class from String
                    String classNameFromConfiguration = "org.example.servlet.Myservlet";
                    Class<?> aClass = Class.forName(classNameFromConfiguration);
                    if (!servletMap.containsKey(aClass)) {
                        // new MyServlet()
                        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
                        System.out.println("--------------------------New servlet------------------------");
                        CustomServlet servlet = (CustomServlet) declaredConstructor.newInstance();
                        servletMap.put(aClass, servlet);

                        Method service = aClass.getDeclaredMethod("service", Request.class, Response.class);
                        service.invoke(servlet, new Request(socket.getInputStream()), new Response(socket.getOutputStream()));

                        //call method goGet() of Servlet

//                        servlet.service(new Request(socket.getInputStream()), new Response(socket.getOutputStream()));
                    } else {
                        CustomServlet servlet = servletMap.get(aClass);
                        //call method goGet() of Servlet
                        System.out.println("--------------------------Standby servlet------------------------");
                        servlet.service(new Request(socket.getInputStream()), new Response(socket.getOutputStream()));
                    }


                    socket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}