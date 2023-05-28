package service;

public class Service {

    public boolean deposit(int value, int userId){
        JDBCConnection connection = new JDBCConnection();
        return connection.createDeposit(value, userId);
    }
    public boolean dispense(long value, int userId){
        JDBCConnection connection = new JDBCConnection();
        return connection.createDispense(value, userId);
    }
    public int getUserLogin(String username, String password){
        JDBCConnection connection = new JDBCConnection();
        return connection.getUserLogin(username,password);
    }
}
