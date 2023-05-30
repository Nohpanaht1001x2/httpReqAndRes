package org.example.servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Response {
    String metaContent;
    String header;
    OutputStream res;
    BufferedWriter resBuffer;
    List<String> msg = new ArrayList<String>();

    public Response(OutputStream res) {
        this.res = res;
        this.resBuffer = new BufferedWriter(new OutputStreamWriter(res));
    }

    public BufferedWriter getWriter() {
        return resBuffer;
    }

    public void setContentType(String s) throws IOException {
        this.metaContent = s;
    }

    public void flush() throws IOException {
        resBuffer.write("HTTP/1.1 200\n");
        resBuffer.write("Content-Type: " + metaContent + "\n");
        resBuffer.write("Access-Control-Allow-Origin: http://localhost:4200\n" +
                "Access-Control-Allow-Methods: POST, PUT, GET, DELETE, HEAD, OPTIONS");
        resBuffer.newLine();
        resBuffer.newLine();
//        renderResult(resBuffer, msg);
        renderParameter(resBuffer, msg);
        renderContent(resBuffer,msg);
        resBuffer.flush();
    }

    public void write(String msg) {
        this.msg.add(msg);
    }

    public void renderResult(BufferedWriter out, List<String> msg) throws IOException {
        out.write("<!DOCTYPE html>");
        out.write("<html>");
        out.write("<head>");
        out.write("<title>Servlet LoginServlet</title>");
        out.write("</head>");
        out.write("<body>");
        if (msg == null) {
            out.write("<h1>No Message</h1>");
        } else {
            for (String s : msg) {
                out.write("<h1>" + s + "</h1>");
            }
        }
        out.write("</body>");
        out.write("</html>");
    }
    private void renderParameter(BufferedWriter out, List<String> headers) throws IOException {
        Iterator<String> it = headers.iterator();
        out.write("your arguments is : ");
        while (it.hasNext()) {
            out.write(it.next());
            if (it.hasNext()) {
                out.write("&");
            }
        }
    }
    private void renderContent (BufferedWriter out,List<String> headers) throws IOException {
        Iterator<String> it = headers.iterator();
        out.write("\nyour content is : \n");
        while (it.hasNext()) {
            out.write(it.next()+"\n");
        }
    }

}
