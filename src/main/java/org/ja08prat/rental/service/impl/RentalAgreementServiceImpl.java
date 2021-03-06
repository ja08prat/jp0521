package org.ja08prat.rental.service.impl;

import org.ja08prat.rental.model.Tool;
import org.ja08prat.rental.service.RentalAgreementService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

import java.time.Month;

/**
 * Implementation of the Rental Agreement interface
 */
public class RentalAgreementServiceImpl implements RentalAgreementService {

    @Override
    public LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    @Override
    public int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool rentedTool) {
        // calculate correct inclusive date span first, which is day after checkout date up to
        // and including due date
        LocalDate beginDate = checkoutDate.plusDays(1);
        int chargeDays = 0;

        // iterate over date range to test each individual date in that range
        for (LocalDate date = beginDate; date.isBefore(dueDate) || date.isEqual(dueDate); date = date.plusDays(1)) {
            if (isWeekend(date)) { // date is a weekend
                // tool has a weekend charge
                if (rentedTool.isWeekendCharge()) {
                    chargeDays++;
                }
            } else { // date is a weekday
                boolean isDateAnObservedJulyFourth = isDateAnObservedJulyFourth(date);
                boolean isLaborDay = isLaborDay(date);

                // weekdays are split into two buckets: holidays and not holidays
                // if date is an observed holiday and tool charges holidays
                if ((isLaborDay || isDateAnObservedJulyFourth) && rentedTool.isHolidayCharge()) {
                    chargeDays++;
                }

                // if it is not an observed holiday
                if (!(isLaborDay || isDateAnObservedJulyFourth)) {
                    chargeDays++;
                }
            }

        }

        return chargeDays;
    }

    @Override
    public double calculatePreDiscountCharge(int chargeDays, double dailyCharge) {
        return multiplyAndRoundDoubleValues(chargeDays, dailyCharge);
    }

    @Override
    public double calculateDiscountAmount(int discountPercent, double preDiscountCharge) {
        double discountPercentage = (double) discountPercent * .01;
        return multiplyAndRoundDoubleValues(discountPercentage, preDiscountCharge);
    }

    @Override
    public double calculateFinalCharge(double preDiscountCharge, double discountAmount) {
        return preDiscountCharge - discountAmount;
    }

    /**
     * Helper method that determines if a day is an observed July 4th holiday.
     * If July 4th falls on weekend, it is observed on the closest weekday
     * (if Sat, then Friday the 3rd, if Sunday, then Monday the 5th)
     * @param date - a given date
     * @return     - is this date an observed July 4th
     */
    private boolean isDateAnObservedJulyFourth(LocalDate date) {
        // if July 3rd is a friday
        if (Month.JULY == date.getMonth() && DayOfWeek.FRIDAY == date.getDayOfWeek() && 3 == date.getDayOfMonth()) {
            return true;
        }

        // if july 5th is a monday
        if (Month.JULY == date.getMonth() && DayOfWeek.MONDAY == date.getDayOfWeek() && 5 == date.getDayOfMonth()) {
            return true;
        }

        // if it is july 4th
        return Month.JULY == date.getMonth() && 4 == date.getDayOfMonth();
    }

    /**
     * Helper method that determines if a given date is a weekend
     * @param date - a given date
     * @return     - is this date a weekend
     */
    private boolean isWeekend(LocalDate date) {
        return DayOfWeek.SATURDAY == date.getDayOfWeek() || DayOfWeek.SUNDAY == date.getDayOfWeek();
    }

    /**
     * Helper method that calculates if a given date is labor day
     * @param date - a given date
     * @return     - is this date labor day
     */
    private boolean isLaborDay(LocalDate date) {
        if (Month.SEPTEMBER == date.getMonth()) {
            LocalDate firstMonday = date.with(firstInMonth(DayOfWeek.MONDAY));
            return firstMonday.isEqual(date);
        }

        return false;
    }

    /**
     * Helper method that multiplies and rounds two given double values
     * @param double1 - first number to be multiplied
     * @param double2 - second number to be multiplied
     * @return        - multiplied and rounded value
     */
    private double multiplyAndRoundDoubleValues(double double1, double double2) {
        double rawValue = double1 * double2;

        return Math.round(rawValue * 100.0) / 100.0;
    }
}
