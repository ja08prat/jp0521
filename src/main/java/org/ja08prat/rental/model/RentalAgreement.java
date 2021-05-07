package org.ja08prat.rental.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    // initialized values
    private Tool rentedTool;
    private int rentalDays;
    private int discountPercent;
    private LocalDate checkoutDate;
    // calculated values
    private LocalDate dueDate;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalCharge;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    public RentalAgreement(Tool rentedTool, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        this.rentedTool = rentedTool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
    }

    public Tool getRentedTool() {
        return rentedTool;
    }

    public void setRentedTool(Tool rentedTool) {
        this.rentedTool = rentedTool;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(double preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

    @Override
    public String toString() {
        return
                "Tool code: " + rentedTool.getToolCode() + "\n" +
                "Tool type: " + rentedTool.getToolType() + "\n" +
                "Tool brand: " + rentedTool.getBrand() + "\n" +
                "Rental days: " + rentalDays + "\n" +
                "Checkout date: " + checkoutDate.format(formatter) + "\n" +
                "Due date: " + dueDate.format(formatter) + "\n" +
                "Daily rental charge: $" + String.format("%.2f", rentedTool.getDailyCharge()) + "\n" +
                "Charge days: " + chargeDays + "\n" +
                "Pre-discount charge $" + String.format("%.2f", preDiscountCharge) + "\n" +
                "Discount percent " + discountPercent + "%\n" +
                "Discount amount $" + String.format("%.2f", discountAmount) + "\n" +
                "Final charge $" + String.format("%.2f", finalCharge);
    }

    public void print() {
        System.out.println(toString());
    }
}
