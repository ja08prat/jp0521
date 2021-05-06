package org.ja08prat.rental.service;

import java.time.LocalDate;

public interface RentalAgreementService {
    LocalDate calculateDueDate(LocalDate checkoutDate, Integer rentalDays);
}
