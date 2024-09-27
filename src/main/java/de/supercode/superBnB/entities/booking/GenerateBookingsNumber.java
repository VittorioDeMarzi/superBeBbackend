package de.supercode.superBnB.entities.booking;

import de.supercode.superBnB.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class GenerateBookingsNumber {
    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int CODE_LENGTH = 10;
    private final SecureRandom random = new SecureRandom();
    private BookingRepository bookingRepository;

    public GenerateBookingsNumber(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String generateUniqueRandomCode(){
        String code;
        do {
            code = generateRandomCode();
        } while (bookingRepository.existsByBookingCode(code));
        return code;
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            code.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return code.toString();
    }
}
