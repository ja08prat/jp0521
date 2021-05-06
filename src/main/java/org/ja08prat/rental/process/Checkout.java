package org.ja08prat.rental.process;

import org.ja08prat.rental.model.RentalAgreement;
import org.ja08prat.rental.model.Tool;
import org.ja08prat.rental.service.RentalAgreementService;

import java.time.LocalDate;

public class Checkout {
    private final RentalAgreementService rentalAgreementService;

    public Checkout(RentalAgreementService rentalAgreementService) {
        this.rentalAgreementService = rentalAgreementService;
    }

    public void checkout(String toolCode, Integer rentalDays, Integer discountPercent, LocalDate checkoutDate) throws Exception {
        // data validation
        if (rentalDays < 1) {
            throw new Exception("Illegal Rental Day Count. You must rent a tool for 1 or more days, please try again.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Illegal Discount Rate. You cannot have a discount less than 0% or more than 100%, please try again.");
        }

        // build tool object based on toolCode
        Tool rentedTool = buildRentedTool(toolCode);

        // check that rented tool was initialized
        if (rentedTool == null) {
            throw new Exception("Illegal tool code. LADW, CHNS, JAKR and JAKD are the valid tool codes, please enter a valid tool code.");
        }

        // Initialize rentalAgreement with fields that don't need to be calculated
        RentalAgreement rentalAgreement = new RentalAgreement(rentedTool, rentalDays, discountPercent, checkoutDate);

        // calculate due date
        rentalAgreement.setDueDate(rentalAgreementService.calculateDueDate(checkoutDate, rentalDays));

        // calculate charge days
        rentalAgreement.setChargeDays(rentalAgreementService.calculateChargeDays(checkoutDate, rentalAgreement.getDueDate(), rentedTool));

        // calculate pre-discount charge
        rentalAgreement.setPreDiscountCharge(rentalAgreementService.calculatePreDiscountCharge(rentalAgreement.getChargeDays(), rentedTool.getDailyCharge()));

        // calculate discount amount
        rentalAgreement.setDiscountAmount(rentalAgreementService.calculateDiscountAmount(discountPercent, rentalAgreement.getPreDiscountCharge()));
    }

    private Tool buildRentedTool(String toolCode) {
        Tool tool = null;
        if ("LADW".equals(toolCode)) {
            tool = new Tool("Ladder", "Werner",
                    toolCode, 1.99, true, true, false);
        } else if ("CHNS".equals(toolCode)) {
            tool = new Tool("Chainsaw", "Stihl",
                    toolCode, 1.49, true, false, true);
        } else if ("JAKR".equals(toolCode) || "JAKD".equals(toolCode)) {
            tool = new Tool("Jackhammer", "JAKR".equals(toolCode) ? "Ridgid" : "DeWalt",
                    toolCode, 2.99, true, false, false);
        }

        return tool;
    }
}
