package com.devops.groupb.harbourmaster.model;
import java.sql.*;


public class exampleDB {
    Connection conn;

    public exampleDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "newuser", "password");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("IF NOT EXISTS CREATE TABLE EXAMPLE(ID = PRIMARY KEY AUTO INCREMENT, NAME1 VARCHAR(256), NAME2 VARCHAR(256), NAME3 VARCHAR(256)); ");
            createExample("test", "test", "test");
        } catch (
                Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        exampleDB test = new exampleDB();
    }

    public void createExample(String x, String y, String z) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO EXAMPLE VALUES (?, ?, ?);");
            preparedStatement.setString(1, x);
            preparedStatement.setString(2, y);
            preparedStatement.setString(3, z);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void readExample(int x) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT *, FROM EXAMPLE WHERE ID =  ?;");
            preparedStatement.setInt(1, x);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateExample(int id, String x, String y, String z) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE table_name SET NAME1 = ?, NAME2 = ? , NAME3 = ?, WHERE ID=?; ");
            preparedStatement.setString(1, x);
            preparedStatement.setString(2, y);
            preparedStatement.setString(3, z);
            preparedStatement.setInt(4, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteExample(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM EXAMPLE WHERE ID =  ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);
            System.out.println(resultSet);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeConn() {
        conn = null;
    }
}

