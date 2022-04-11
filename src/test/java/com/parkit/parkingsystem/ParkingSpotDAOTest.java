package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingSpotDAOTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
    }

    @AfterAll
    private static void tearDown(){
    }

    @Test
    public void getNextAvailableSlotTest() {
        ParkingType parkingType = ParkingType.CAR;
        assertEquals(1, parkingSpotDAO.getNextAvailableSlot(parkingType));
    }

    @Test
    public void updateParkingTest() {
        ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
        assertTrue(parkingSpotDAO.updateParking(parkingSpot));
    }
}