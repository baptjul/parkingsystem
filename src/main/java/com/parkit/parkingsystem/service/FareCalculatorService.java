package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.dao.TicketDAO;

import java.util.concurrent.TimeUnit;

public class FareCalculatorService {

    TicketDAO ticketDAO = new TicketDAO();

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            if (ticket.getOutTime() != null) {
                throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
            }
            throw new IllegalArgumentException("Out time provided not found");
        }

        TimeUnit minutes = TimeUnit.MINUTES;
        long inTime = ticket.getInTime().getTime();
        long outTime = ticket.getOutTime().getTime();

        long duration = outTime - inTime;
        float hourDifference = (float)minutes.convert(duration, TimeUnit.MILLISECONDS) / 60;

        if (hourDifference <= 0.5) {
            ticket.setPrice(0);
        } else {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(hourDifference * Fare.CAR_RATE_PER_HOUR);
                    if(ticketDAO.recurrentUser(ticket)) {
                        ticket.setPrice((ticket.getPrice() - ((ticket.getPrice() * 5) / 100)));
                    }
                    break;
                }
                case BIKE: {
                    ticket.setPrice(hourDifference * Fare.BIKE_RATE_PER_HOUR);
                    if(ticketDAO.recurrentUser(ticket)) {
                        ticket.setPrice((ticket.getPrice() - ((ticket.getPrice() * 5) / 100)));
                    }
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unknown Parking Type");
            }
        }
    }
}