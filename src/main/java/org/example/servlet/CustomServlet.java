package org.example.servlet;


import java.io.IOException;

public interface CustomServlet {
    public void service(Request request, Response response) throws IOException;
    public void doGet(Request request, Response response) throws IOException;
    public void doPost(Request request, Response response) throws IOException;
    public void doPut(Request request, Response response);
//    public void doDelete(Request request, Response response);
//    public void doOptions(Request request, Response response);
//    public void doHead(Request request, Response response);
//    public void doTrace(Request request, Response response);
//    public void doConnect(Request request, Response response);
//    public void doPatch(Request request, Response response);
}

