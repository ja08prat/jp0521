package org.ja08prat.rental.model;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * This classes tests the RentalAgreement class
 */
public class RentalAgreementTest {

    /**
     * This method will validate the formatting of the Rental Agreement toString() method
     */
    @Test
    public void testToString() {
        Tool testTool =  new Tool("Ladder", "Werner",
                "LADW", 1.99, true, true, false);

        RentalAgreement rentalAgreement = new RentalAgreement(testTool, 5, 5, LocalDate.of(1997, Month.NOVEMBER, 8));
        rentalAgreement.setDueDate(LocalDate.of(1997, Month.NOVEMBER, 13));
        rentalAgreement.setChargeDays(5);
        rentalAgreement.setPreDiscountCharge(3.50);
        rentalAgreement.setDiscountAmount(1.47);
        rentalAgreement.setFinalCharge(1.52);

        String expectedResult = "Tool code: LADW\n" +
                "Tool type: Ladder\n" +
                "Tool brand: Werner\n" +
                "Rental days: 5\n" +
                "Checkout date: 11/08/97\n" +
                "Due date: 11/13/97\n" +
                "Daily rental charge: $1.99\n" +
                "Charge days: 5\n" +
                "Pre-discount charge $3.50\n" +
                "Discount percent 5%\n" +
                "Discount amount $1.47\n" +
                "Final charge $1.52";

        String result = rentalAgreement.toString();

        assertEquals(expectedResult, result);
    }
}