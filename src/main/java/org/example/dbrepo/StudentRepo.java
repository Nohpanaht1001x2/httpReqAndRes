package org.example.dbrepo;

import org.example.dbmodel.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepo implements AutoCloseable{
    private Connection connection;
    @Override
    public void close() throws Exception {
        System.out.println("closing connection");
        if (connection != null){
            connection.close();
            System.out.println("connection close");
        }
    }
    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:./target/db;AUTO_SERVER=TRUE","sa","sa");
    }

    public void save(Student st) throws SQLException {
        Statement statement = connection.createStatement();
//        statement.executeUpdate("insert into student(id,name) values( " +st.getId()+ " '"+st.getName()+"')");
        String sql = String.format("insert into student(id,username,password) values(%d,'%s','%s')",st.getId(),st.getUsername(),st.getPassword());
        System.out.println(sql);
        statement.executeUpdate(sql);
    }
    public Optional<Student> findbyId(Integer id) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("select  ID,USERNAME,PASSWORD from STUDENT where ID=?")){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("USERNAME");
                String pass = resultSet.getString("PASSWORD");
                Student student = new Student(id1,name,pass);
                return Optional.of(student);
            }else {
                return Optional.empty();
            }
        }
    }
    public List<Student> findAll() throws SQLException{
        List<Student> students = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("select  ID,USERNAME,PASSWORD from STUDENT")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("USERNAME");
                String pass = resultSet.getString("PASSWORD");
                Student student = new Student(id1, name,pass);
                students.add(student);
            }
            return students;
        }
    }
}
