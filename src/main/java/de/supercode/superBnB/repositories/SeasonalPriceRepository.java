package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.booking.SeasonalPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeasonalPriceRepository extends JpaRepository<SeasonalPrice, Long> {
    Optional<SeasonalPrice> findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long id, LocalDate startDate, LocalDate endDate);

    Optional<List<SeasonalPrice>> findByPropertyId(Long propertyId);
}
