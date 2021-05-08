package org.ja08prat.rental.process;

import org.ja08prat.rental.model.RentalAgreement;
import org.ja08prat.rental.service.impl.RentalAgreementServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * This class tests the Checkout class
 */
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
     * will throw an exception because rental days will be less than 1
     */
    @Test
    public void testProcessCheckoutRentalDaysLessThanOne() {
        String toolCode = "JAKR";
        int rentalDays = 0;
        int discountPercentage = 10;
        LocalDate checkoutDate = LocalDate.of(2015, Month.SEPTEMBER, 3);
        String expectedMessage = "Illegal Rental Day Count. You must rent a tool for 1 or more days, please try again.";

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
     * will throw an exception because an illegal toolCode will be provided
     */
    @Test
    public void testProcessCheckoutIllegalToolCode() {
        String toolCode = "Not A Tool Code";
        int rentalDays = 3;
        int discountPercentage = 10;
        LocalDate checkoutDate = LocalDate.of(2015, Month.SEPTEMBER, 3);
        String expectedMessage = "Illegal tool code. LADW, CHNS, JAKR and JAKD are the valid tool codes, please enter a valid tool code.";

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

    /**
     * This method tests the functionality of processCheckout(),
     * will test with a DeWalt Jackhammer, checkout date of 9/3/15,
     * 6 rental days and a discount of 0%
     */
    @Test
    public void testProcessCheckoutJackhammerLaborDay() throws Exception {
        String toolCode = "JAKD";
        int rentalDays = 6;
        int discountPercentage = 0;
        LocalDate checkoutDate = LocalDate.of(2015, Month.SEPTEMBER, 3);

        RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);

        assertEquals("Jackhammer", result.getRentedTool().getToolType());
        assertEquals("DeWalt", result.getRentedTool().getBrand());
        assertEquals(LocalDate.of(2015, Month.SEPTEMBER, 9), result.getDueDate());
        assertEquals(3, result.getChargeDays());
        assertEquals(8.97, result.getPreDiscountCharge(), 0.00001);
        assertEquals(0.00, result.getDiscountAmount(), 0.00001);
        assertEquals(8.97, result.getFinalCharge(), 0.00001);
    }

    /**
     * This method tests the functionality of processCheckout(),
     * will test with a Ridgid Jackhammer, checkout date of 7/2/15,
     * 9 rental days and a discount of 0%
     */
    @Test
    public void testProcessCheckoutJackhammerJulyFourthNoDiscount() throws Exception {
        String toolCode = "JAKR";
        int rentalDays = 9;
        int discountPercentage = 0;
        LocalDate checkoutDate = LocalDate.of(2015, Month.JULY, 2);

        RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);

        assertEquals("Jackhammer", result.getRentedTool().getToolType());
        assertEquals("Ridgid", result.getRentedTool().getBrand());
        assertEquals(LocalDate.of(2015, Month.JULY, 11), result.getDueDate());
        assertEquals(5, result.getChargeDays());
        assertEquals(14.95, result.getPreDiscountCharge(), 0.00001);
        assertEquals(0.00, result.getDiscountAmount(), 0.00001);
        assertEquals(14.95, result.getFinalCharge(), 0.00001);
    }

    /**
     * This method tests the functionality of processCheckout(),
     * will test with a Ridgid Jackhammer, checkout date of 7/2/20,
     * 4 rental days and a discount of 50%
     */
    @Test
    public void testProcessCheckoutJackhammerJulyFourthDiscount() throws Exception {
        String toolCode = "JAKR";
        int rentalDays = 4;
        int discountPercentage = 50;
        LocalDate checkoutDate = LocalDate.of(2020, Month.JULY, 2);

        RentalAgreement result = checkout.processCheckout(toolCode, rentalDays, discountPercentage, checkoutDate);

        assertEquals("Jackhammer", result.getRentedTool().getToolType());
        assertEquals("Ridgid", result.getRentedTool().getBrand());
        assertEquals(LocalDate.of(2020, Month.JULY, 6), result.getDueDate());
        assertEquals(1, result.getChargeDays());
        assertEquals(2.99, result.getPreDiscountCharge(), 0.00001);
        assertEquals(1.50, result.getDiscountAmount(), 0.00001);
        assertEquals(1.49, result.getFinalCharge(), 0.00001);
    }


}