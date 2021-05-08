package org.ja08prat.rental.service.impl;

import org.ja08prat.rental.model.Tool;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * This class tests the RentalAgreementService
 */
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
        int rentalDays = 7;
        LocalDate expectedResult = LocalDate.of(2020, Month.NOVEMBER, 15);

        LocalDate result = rentalAgreementService.calculateDueDate(checkoutDate, rentalDays);

        assertEquals(expectedResult, result);
    }

    /**
     * This method tests the functionality of calculateChargeDays() with the following case:
     * Time span is weekdays only, with no holidays.
     * Rented tool is a ladder, which charges on weekdays and not holidays.
     */
    @Test
    public void testCalculateChargeDaysWeekdaysNoHolidayLadder() {
        Tool rentedTool = new Tool("Ladder", "Werner",
                "LADW", 1.99, true, true, false);
        LocalDate checkoutDate = LocalDate.of(2020, Month.MAY, 3);
        LocalDate dueDate = LocalDate.of(2020, Month.MAY, 8);
        int expectedChargeDays = 5;

        int result = rentalAgreementService.calculateChargeDays(checkoutDate, dueDate, rentedTool);

        assertEquals(expectedChargeDays, result);
    }

    /**
     * This method tests the functionality of calculateChargeDays() with the following case:
     * Time span covers weekdays, weekends and labor day.
     * Rented tool is a chainsaw, which charges on weekdays, holidays but not weekends.
     */
    @Test
    public void testCalculateChargeDaysWeekdaysWeekendsLaborDayChainsaw() {
        Tool rentedTool = new Tool("Chainsaw", "Stihl",
                "CHNS", 1.49, true, false, true);
        LocalDate checkoutDate = LocalDate.of(2020, Month.SEPTEMBER, 1);
        LocalDate dueDate = LocalDate.of(2020, Month.SEPTEMBER, 9);
        int expectedChargeDays = 6;

        int result = rentalAgreementService.calculateChargeDays(checkoutDate, dueDate, rentedTool);

        assertEquals(expectedChargeDays, result);
    }

    /**
     * This method tests the functionality of calculateChargeDays() with the following case:
     * Time span covers weekdays, weekends and an observed July Fourth.
     * Rented tool is a chainsaw, which charges on weekdays, holidays but not weekends.
     */
    @Test
    public void testCalculateChargeDaysWeekdaysWeekendsJulyFourthChainsaw() {
        Tool rentedTool = new Tool("Chainsaw", "Stihl",
                "CHNS", 1.49, true, false, true);
        LocalDate checkoutDate = LocalDate.of(2020, Month.JULY, 1);
        LocalDate dueDate = LocalDate.of(2020, Month.JULY, 7);
        int expectedChargeDays = 4;

        int result = rentalAgreementService.calculateChargeDays(checkoutDate, dueDate, rentedTool);

        assertEquals(expectedChargeDays, result);
    }

    /**
     * This method tests the functionality of calculateChargeDays() with the following case:
     * Time span covers weekdays, weekends and an observed July Fourth.
     * Rented tool is a Ladder, which charges on weekdays, weekends, but not holidays.
     */
    @Test
    public void testCalculateChargeDaysWeekdaysWeekendsJulyFourthLadder() {
        Tool rentedTool = new Tool("Ladder", "Werner",
                "LADW", 1.99, true, true, false);
        LocalDate checkoutDate = LocalDate.of(2020, Month.JULY, 1);
        LocalDate dueDate = LocalDate.of(2020, Month.JULY, 7);
        int expectedChargeDays = 5;

        int result = rentalAgreementService.calculateChargeDays(checkoutDate, dueDate, rentedTool);

        assertEquals(expectedChargeDays, result);
    }

    /**
     * This method tests the functionality of calculatePreDiscountCharge(),
     * chargeDays and daily charge are multiplied and rounded
     */
    @Test
    public void testCalculatePreDiscountCharge() {
        int chargeDays = 5;
        double dailyCharge = 1.955;
        double expectedResult = 9.78;

        double result = rentalAgreementService.calculatePreDiscountCharge(chargeDays, dailyCharge);

        assertEquals(expectedResult, result, 0.00001);
    }

    /**
     * This method tests the functionality of calculateDiscountAmount(),
     * discount percentage and pre discount charge are multiplied and rounded
     */
    @Test
    public void testCalculateDiscountAmount() {
        int discountPercentage = 17;
        double preDiscountCharge = 45.67;
        double expectedResult = 7.76;

        double result = rentalAgreementService.calculateDiscountAmount(discountPercentage, preDiscountCharge);

        assertEquals(expectedResult, result, 0.00001);
    }

    /**
     * This method tests the functionality of calculateDueDate(),
     * rental days are added to a checkout date
     */
    @Test
    public void testCalculateFinalCharge() {
        double discountAmount = 7.76;
        double preDiscountCharge = 45.67;
        double expectedResult = 37.91;

        double result = rentalAgreementService.calculateFinalCharge(preDiscountCharge, discountAmount);

        assertEquals(expectedResult, result, 0.00001);
    }
}