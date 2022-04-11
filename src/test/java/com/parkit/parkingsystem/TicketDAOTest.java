package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketDAOTest {

    private static FareCalculatorService fareCalculatorService;
    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static Ticket ticket;
    private final FareCalculatorService fcs = new FareCalculatorService();

    @BeforeAll
    private static void setUp() throws Exception {
        fareCalculatorService = new FareCalculatorService();
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        ticket = new Ticket();
    }

    @BeforeEach
    public void setUpPerTest() {
        ticket = ticketDAO.getTicket("ABCDEF");
    }

    @Test
    public void saveTicketTest() {
        assertFalse(ticketDAO.saveTicket(ticket));
    }

    @Test
    public void updateTicketTest() {
        Date outTime = new Date();
        outTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));

        ticket.setOutTime(outTime);
        fareCalculatorService.calculateFare(ticket);
        assertTrue(ticketDAO.updateTicket(ticket));
    }

    @Test
    public void getTicketTest() {
        ticket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(ticket);
    }
}
