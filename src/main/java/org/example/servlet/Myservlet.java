package org.example.servlet;


import jakarta.servlet.annotation.WebServlet;
import org.example.dbmodel.Student;
import org.example.dbrepo.StudentRepo;


import java.io.*;
import java.util.Optional;
import java.util.Scanner;

@WebServlet(name = "Myservlet", urlPatterns = {"/myservlet"})
public class Myservlet implements CustomServlet {


    @Override
    public void service(Request request, Response response) throws IOException {
        System.out.println("Myservlet : " + request.firstLine);
        switch (request.firstLine.split(" ")[0]) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
                break;
        }
    }

    @Override
    public void doGet(Request request, Response response) throws IOException {
        System.out.println("Myservlet.doGet");
        try (StudentRepo repo = new StudentRepo();) {
            repo.connect();
            String reqFile = request.firstLine.split(" ")[1];
            response.setContentType(reqFile.endsWith(".js")?"application/javascript;charset=UTF-8"
                    :reqFile.endsWith(".css")?"text/css;charset=UTF-8":"text/html;charset=UTF-8");
            for (String name : request.getparameterNames()) {
                response.write(name + "=" + request.getParameterValues(name));
            }

//            Optional<Student> call = repo.findbyId(Integer.parseInt(request.getParameterValues("id")));
//            response.write("id : "+String.valueOf(call.get().getId())+
//                                "\nusername : " + String.valueOf(call.get().getUsername())+
//                                "\npassword : " + String.valueOf(call.get().getPassword()));
            String indexPath = "src/main/java/org/example/hello-world";
            File file = new File(reqFile.equals("/")?indexPath+"/index.html":indexPath+reqFile);  //windows
            System.out.println(file.getAbsolutePath());
            Scanner htmlReader = new Scanner(file);
            while(htmlReader.hasNext()){
                response.write(htmlReader.nextLine());
            }
            response.flush();
        } catch (Exception e) {
            response.flush();
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) throws IOException {
        System.out.println("Myservlet.doPost");
        try (StudentRepo repo = new StudentRepo();) {
            repo.connect();
            repo.save(new Student(Integer.parseInt(request.params.get("id")), request.params.get("username"), request.params.get("password")));
            response.setContentType("text/html;charset=UTF-8");
//            OutputStreamWriter client = response.getWriter();
//            client.write("hello world");
//
//            client.flush();
//            Thread dummyReq = new Thread(() -> {
//                while (true) {
//                    try {
//                        String header =  request.reqBuffer.readLine();
//                        if (header!=null) {
//                            System.out.println(header);
//                        }
//                    } catch (IOException e) {
//                        System.out.println(Thread.currentThread());
//                        System.out.println("exception reading request"+"\n"+e.getMessage());
////                        break;
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//            dummyReq.start();
//            response.write("1");
            for (String name : request.getparameterNames()) {
                response.write(name + "=" + request.getParameterValues(name));
            }

            response.flush();
//            response.resBuffer.flush();
        } catch (Exception e) {
            System.out.println("Unique Index Violation: " + e.getMessage());
            response.setContentType("text/html;charset=UTF-8");
            response.write(e.getMessage());
            response.flush();
        }
//        catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void doPut(Request request, Response response) {

    }
}
