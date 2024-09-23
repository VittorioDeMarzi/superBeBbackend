package de.supercode.superBnB.servicies;

import de.supercode.superBnB.repositories.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    BookingRepository bookRepository;

    public BookingService(BookingRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
