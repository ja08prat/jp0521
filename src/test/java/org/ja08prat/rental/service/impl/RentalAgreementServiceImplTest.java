package org.ja08prat.rental.service.impl;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class RentalAgreementServiceImplTest {

    private static RentalAgreementServiceImpl rentalAgreementService;

    @Before
    public void setUpTest() {
        rentalAgreementService = new RentalAgreementServiceImpl();
    }

    /**
     * This method tests the functionality of calculateDueDate(),
     * rental days are added to a checkout date
     */
    @Test
    public void testCalculateDueDate() {
        LocalDate checkoutDate = LocalDate.of(2020, Month.NOVEMBER, 8);
        Integer rentalDays = 7;
        LocalDate expectedResult = LocalDate.of(2020, Month.NOVEMBER, 15);

        LocalDate result = rentalAgreementService.calculateDueDate(checkoutDate, rentalDays);

        assertEquals(expectedResult, result);
    }
}