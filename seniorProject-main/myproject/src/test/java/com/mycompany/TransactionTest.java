package com.mycompany;

import org.junit.jupiter.api.Test;
import service.JDBCConnection;

public class TransactionTest {

    @Test
    public void deposit()
    {
        JDBCConnection jdbc = new JDBCConnection();
        System.out.println("Account balance before: " + jdbc.getAccountBalance(1));
        jdbc.createDeposit(1051, 1);
        System.out.println("Account balance after: " + jdbc.getAccountBalance(1));
    }
    @Test
    public void dispense()
    {
        JDBCConnection jdbc = new JDBCConnection();
        jdbc.getUserLogin("lehua", "root");
        System.out.println("Account balance: " + jdbc.getAccountBalance(1));
        jdbc.createDispense(55, 1);
    }
    @Test
    public void login()
    {
        JDBCConnection jdbc = new JDBCConnection();
        jdbc.getUserLogin("lehua", "root");
        System.out.println("Successful login");
    }
}
