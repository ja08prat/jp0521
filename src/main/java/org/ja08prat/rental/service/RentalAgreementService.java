package org.ja08prat.rental.service;

import org.ja08prat.rental.model.Tool;

import java.time.LocalDate;

public interface RentalAgreementService {
    LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays);

    int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool rentedTool);

    double calculatePreDiscountCharge(int chargeDays, double dailyCharge);

    double calculateDiscountAmount(int discountPercent, double preDiscountCharge);

    double calculateFinalCharge(double preDiscountCharge, double discountAmount);
}
