package service;

import java.sql.*;

public class JDBCConnection {

        static Connection conn;

        public JDBCConnection(){

        }
        public static Connection connectToDatabase() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException exc) {
                throw new RuntimeException(exc);
            }
            String url1 = "jdbc:mysql://localhost:3306/senior_project";
            String user = "root";
            String password = "root";

            try {
                conn = DriverManager.getConnection(url1, user, password);
                if (conn != null) {
                    System.out.println("Connected to the database");
                    return conn;
                }
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
            return null;
        }

        public int getUserLogin(String username, String password){
            conn = connectToDatabase();
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                stmt=conn.createStatement();
                rs =stmt.executeQuery("select ID from user where username='"+username+"' and password='"+password+"'");
                return rs.findColumn("ID");
            } catch (SQLException e)
            {
                e.printStackTrace();
                return 0;
            }

        }

        public int getAccountBalance(int userId){
            conn = connectToDatabase();
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from account where userId=" + userId);
                return rs.findColumn("balance");
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

        }
        public boolean createDeposit(int value, int userId){
            conn = connectToDatabase();
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                stmt=conn.createStatement();
                int balanceValue = getAccountBalance(userId)+value;
                stmt.execute("update account set balance=" + balanceValue +
                       " where userId=" + userId);
                return true;
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }

        }

        public boolean createDispense(long value, int userId){
            conn = connectToDatabase();
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                int accountBalance = getAccountBalance(userId);
                if(accountBalance>value) {
                    stmt = conn.createStatement();
                    stmt.execute("update account set balance=" + accountBalance +
                            + userId + value);
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }

        }
        }
