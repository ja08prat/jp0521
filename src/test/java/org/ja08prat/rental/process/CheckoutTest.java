package org.ja08prat.rental.process;

import org.ja08prat.rental.model.RentalAgreement;
import org.ja08prat.rental.service.impl.RentalAgreementServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class CheckoutTest {

    private static Checkout checkout;

    @Before
    public void setUpTest() {
        checkout = new Checkout(new RentalAgreementServiceImpl());
    }

    /**
     * This method tests the functionality of processCheckout(),
     * will throw an exception because discount percentage will be out of range
     */
    @Test
    public void testProcessCheckoutDiscountPercentageException() {
        String toolCode = "JAKR";
        int rentalDays = 5;
        int discountPercentage = 101;
        LocalDate checkoutDate = LocalDate.of(2015, Month.SEPTEMBER, 3);
        String expectedMessage = "Illegal Discount Rate. You cannot have a discount less than 0% or more than 100%, please try again.";

        String message = "";
        try {
            RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);
        } catch (Exception e) {
            message = e.getMessage();
        }

        assertEquals(expectedMessage, message);
    }

    /**
     * This method tests the functionality of processCheckout(),
     * will test with a Ladder, checkout date of 7/2/20,
     * 3 rental days and a discount of 10%
     */
    @Test
    public void testProcessCheckoutLadderJulyFourth() throws Exception {
        String toolCode = "LADW";
        int rentalDays = 3;
        int discountPercentage = 10;
        LocalDate checkoutDate = LocalDate.of(2020, Month.JULY, 2);

        RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);

        assertEquals("Ladder", result.getRentedTool().getToolType());
        assertEquals(LocalDate.of(2020, Month.JULY, 5), result.getDueDate());
        assertEquals(2, result.getChargeDays());
        assertEquals(3.98, result.getPreDiscountCharge(), 0.00001);
        assertEquals(0.40, result.getDiscountAmount(), 0.00001);
        assertEquals(3.58, result.getFinalCharge(), 0.00001);
    }

    /**
     * This method tests the functionality of processCheckout(),
     * will test with a Chainsaw, checkout date of 7/2/15,
     * 5 rental days and a discount of 25%
     */
    @Test
    public void testProcessCheckoutChainsawJulyFourth() throws Exception {
        String toolCode = "CHNS";
        int rentalDays = 5;
        int discountPercentage = 25;
        LocalDate checkoutDate = LocalDate.of(2015, Month.JULY, 2);

        RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);

        assertEquals("Chainsaw", result.getRentedTool().getToolType());
        assertEquals(LocalDate.of(2015, Month.JULY, 7), result.getDueDate());
        assertEquals(3, result.getChargeDays());
        assertEquals(4.47, result.getPreDiscountCharge(), 0.00001);
        assertEquals(1.12, result.getDiscountAmount(), 0.00001);
        assertEquals(3.35, result.getFinalCharge(), 0.00001);
    }

}