package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseConfigTest {
    private final DataBaseConfig dataBaseConfig = new DataBaseConfig();
    @Test
    public void getConnectionTest() {
        Connection con;
        boolean success = false;
        try {
            con = dataBaseConfig.getConnection();
            if(con != null){success = true;}
            dataBaseConfig.closeConnection(con);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        assertTrue(success);
    }
}