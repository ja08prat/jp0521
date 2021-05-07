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

}