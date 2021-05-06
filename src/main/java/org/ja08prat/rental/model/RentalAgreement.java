package org.ja08prat.rental.model;

import java.time.LocalDate;

public class RentalAgreement {
    private Tool rentedTool;
    private Integer rentalDays;
    // this is a percentage
    private Integer discountPercent;
    private LocalDate checkoutDate;

    private LocalDate dueDate;
    private Integer chargeDays;
    private Double preDiscountCharge;
    private Double discountAmount;
    private Double finalCharge;

    public RentalAgreement(Tool rentedTool, Integer rentalDays, Integer discountPercent, LocalDate checkoutDate) {
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

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
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

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public Double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(Double preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(Double finalCharge) {
        this.finalCharge = finalCharge;
    }

    // fill this out later
    @Override
    public String toString() {
        return "";
    }

    public void print() {
        System.out.println(toString());
    }
}
