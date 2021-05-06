package org.ja08prat.rental.service;

import org.ja08prat.rental.model.Tool;

import java.time.LocalDate;

public interface RentalAgreementService {
    LocalDate calculateDueDate(LocalDate checkoutDate, Integer rentalDays);

    Integer calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool rentedTool);

    Double calculatePreDiscountCharge(Integer chargeDays, Double dailyCharge);

    Double calculateDiscountAmount(Integer discountPercent, Double preDiscountCharge);

    Double calculateFinalCharge(Double preDiscountCharge, Double discountAmount);
}
