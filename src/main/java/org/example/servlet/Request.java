package org.example.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Request {
    InputStreamReader req;
    BufferedReader reqBuffer;
    Map<String, String> params = new HashMap<String, String>();
    String firstLine;

    public Request(InputStream req) throws IOException {
        this.req = new InputStreamReader(req);
        this.reqBuffer = new BufferedReader(this.req);
        this.params = new HashMap<String, String>();
        try {
            this.firstLine = this.reqBuffer.readLine();
            getParameter();
        } catch (NullPointerException e) {
            System.out.println("there is no parameter");
        }
    }

    private void getParameter() throws IOException {
        String request = this.firstLine;
        int queryIndex=0;
        String url;
        String paraString="";
//        System.out.println("this is request text :"+request);
        if (request.startsWith("POST")) {
            // Read the request body
            System.out.println("Request:POST request");
            StringBuilder requestBody = new StringBuilder();
            while (reqBuffer.ready()) {
                requestBody.append((char) reqBuffer.read());
            }
            int lastindex = requestBody.toString().split("\n").length;
            paraString = requestBody.toString().split("\n")[lastindex-1];
//            System.out.println("this is " + paraString);
        }
        if (request.startsWith("GET")) {
            System.out.println("Request:GET request");
            String[] requestParts = request.split(" ");
            url = requestParts[1];
            queryIndex = url.indexOf('?');
            paraString = url.substring(queryIndex + 1);

        }
        if (queryIndex >= 0 || !paraString.isEmpty()) {
            // Extract the query string
            System.out.println("Request:get parameters");
            // Split the query string into individual parameters
            String[] paramPairs = paraString.split("&");
            System.out.println(paraString);
            // Extract the key-value pairs and add them to the map
            for (String paramPair : paramPairs) {
                String[] keyValue = paramPair.split("=");

                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    this.params.put(key, value);
                }
            }
        }
        System.out.println("Request:parameter is : "+params);
    }

    public String getParameterValues(String para) throws IOException {
        return params.get(para);
    }
    public Set<String> getparameterNames(){
        return params.keySet();
    }
}
