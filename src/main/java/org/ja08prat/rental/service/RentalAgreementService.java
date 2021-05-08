package org.ja08prat.rental.service;

import org.ja08prat.rental.model.Tool;

import java.time.LocalDate;

/**
 * This interface consists of the different calculations needed
 * to generate a Rental Agreement
 */
public interface RentalAgreementService {
    /**
     * Calculates the due date by adding the number of rental days to the checkout date
     * @param checkoutDate - date tool was rented
     * @param rentalDays   - number of days to rent
     * @return             - rental days added to checkout date
     */
    LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDays);

    /**
     * Calculates count of chargeable days, from day after
     * checkout through and including due date, excluding
     * “no charge” days as specified by the tool type.
     * @param checkoutDate - date tool was checked out
     * @param dueDate      - tool due date
     * @param rentedTool   - the tool that was rented
     * @return             - count of chargeable days
     */
    int calculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool rentedTool);

    /**
     * Calculated as charge days times daily charge, Resulting total rounded half up to cents.
     * @param chargeDays  - chargeable days
     * @param dailyCharge - amount a given tool charges per day
     * @return            - charge before discount is considered
     */
    double calculatePreDiscountCharge(int chargeDays, double dailyCharge);

    /**
     * Calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
     * @param discountPercent   - discount percentage represented as a whole number
     * @param preDiscountCharge - charge before discount
     * @return                  - discount amount
     */
    double calculateDiscountAmount(int discountPercent, double preDiscountCharge);

    /**
     * Calculated as pre-discount charge minus the discount amount.
     * @param preDiscountCharge - the pre discount charge
     * @param discountAmount    - the discount amount
     * @return                  - the final charge
     */
    double calculateFinalCharge(double preDiscountCharge, double discountAmount);
}
