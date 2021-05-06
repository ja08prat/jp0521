package org.ja08prat.rental.service.impl;

import org.ja08prat.rental.service.RentalAgreementService;

import java.time.LocalDate;

public class RentalAgreementServiceImpl implements RentalAgreementService {
    @Override
    public LocalDate calculateDueDate(LocalDate checkoutDate, Integer rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }
}
