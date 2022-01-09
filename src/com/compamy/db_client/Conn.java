package com.compamy.db_client;

import java.sql.*;
import java.util.ArrayList;


public class Conn {
    private Connection conn;
    private Statement statement;

    public Conn(String path) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + path);
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getAllTables() {
        ArrayList<String> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            while (resultSet.next()) {
                String tableName = (String) resultSet.getObject("name");
                if (tableName.equals("sqlite_sequence") || tableName.equals("sqlite_stat1")) continue;
                result.add(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getColumnNamesByTable(String tableName) {
        ArrayList<String> result = new ArrayList<>();
        try {
            PreparedStatement prepareStatement = conn.prepareStatement("SELECT name FROM PRAGMA_TABLE_INFO(?);");
            prepareStatement.setString(1, tableName);
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                String columnName = (String) resultSet.getObject("name");
                result.add(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String[]> getData(String tableName) {
        ArrayList<String[]> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
            ResultSetMetaData md = resultSet.getMetaData();
            int colCount = md.getColumnCount();

            while (resultSet.next()) {
                String[] row = new String[colCount];
                for (int i = 1; i <= colCount; i++) {
                    row[i - 1] = resultSet.getString(i);
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
