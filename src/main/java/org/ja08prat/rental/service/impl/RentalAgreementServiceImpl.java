package org.ja08prat.rental.service.impl;

import org.ja08prat.rental.model.Tool;
import org.ja08prat.rental.service.RentalAgreementService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.firstInMonth;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreementServiceImpl implements RentalAgreementService {
    @Override
    public LocalDate calculateDueDate(LocalDate checkoutDate, Integer rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    @Override
    public Integer calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool rentedTool) {
        // calculate correct inclusive date span first, which is day after checkout date up to
        // and including due date
        LocalDate beginDate = checkoutDate.plusDays(1);
        Integer chargeDays = 0;
        List<LocalDate> daysThatTriggeredAnIncrement = new ArrayList<>();

        // iterate over date range to test each individual date in that range
        for (LocalDate date = beginDate; date.isBefore(dueDate); date = date.plusDays(1)) {
            // date is a weekend
            if (isWeekend(date)) {
                // tool has a weekend charge and this day hasn't already been counted
                if (rentedTool.isWeekendCharge() && !daysThatTriggeredAnIncrement.contains(date)) {
                    chargeDays++;
                    daysThatTriggeredAnIncrement.add(date);
                }
            } else { // date is a weekday
                boolean isDateAnObservedJulyFourth = isDateAnObservedJulyFourth(date);
                boolean isLaborDay = isLaborDay(date);

                // weekdays are split into two buckets: holidays and not holidays

                // if date is an observed holiday and tool charges holidays and this day hasn't already been counted
                if ((isLaborDay || isDateAnObservedJulyFourth) && rentedTool.isHolidayCharge() && !daysThatTriggeredAnIncrement.contains(date)) {
                    chargeDays++;
                    daysThatTriggeredAnIncrement.add(date);
                }

                // if it is not an observed holiday and this day hasn't already been counted
                if (!(isLaborDay || isDateAnObservedJulyFourth) && !daysThatTriggeredAnIncrement.contains(date)) {
                    chargeDays++;
                    daysThatTriggeredAnIncrement.add(date);
                }
            }

        }

        return chargeDays;
    }

    @Override
    public Double calculatePreDiscountCharge(Integer chargeDays, Double dailyCharge) {
        double rawValue = (double) chargeDays * dailyCharge;

        return Math.round(rawValue * 100.0) / 100.0;
    }

    @Override
    public Double calculateDiscountAmount(Integer discountPercent, Double preDiscountCharge) {
        double discountPercentage = (double) discountPercent / .01;
        double rawValue = discountPercentage * preDiscountCharge;

        return Math.round(rawValue * 100.0) / 100.0;
    }

    @Override
    public Double calculateFinalCharge(Double preDiscountCharge, Double discountAmount) {
        return preDiscountCharge - discountAmount;
    }

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

    private boolean isWeekend(LocalDate date) {
        return DayOfWeek.SATURDAY == date.getDayOfWeek() || DayOfWeek.SUNDAY == date.getDayOfWeek();
    }

    // calculating if a given date is labor day
    private boolean isLaborDay(LocalDate date) {
        if (Month.SEPTEMBER == date.getMonth()) {
            LocalDate firstMonday = date.with(firstInMonth(DayOfWeek.MONDAY));
            return firstMonday.isEqual(date);
        }

        return false;
    }
}
