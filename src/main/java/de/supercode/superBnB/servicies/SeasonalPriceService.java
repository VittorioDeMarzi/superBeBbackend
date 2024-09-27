package de.supercode.superBnB.servicies;

import de.supercode.superBnB.entities.Property;
import de.supercode.superBnB.entities.SeasonalPrice;
import de.supercode.superBnB.repositories.SeasonalPriceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SeasonalPriceService {

    SeasonalPriceRepository seasonalPriceRepository;
    PropertyService propertyService;

    public SeasonalPriceService(SeasonalPriceRepository seasonalPriceRepository, PropertyService propertyService) {
        this.seasonalPriceRepository = seasonalPriceRepository;
        this.propertyService = propertyService;
    }

    public SeasonalPrice addSeasonalPrice(long propertyId, LocalDate startDate, LocalDate endDate, BigDecimal price) {
        Property property = propertyService.findPropertyById(propertyId);
        SeasonalPrice seasonalPrice  = new SeasonalPrice();
        seasonalPrice.setProperty(property);
        seasonalPrice.setStartDate(startDate);
        seasonalPrice.setEndDate(endDate);
        seasonalPrice.setPricePerNight(price);
        return seasonalPriceRepository.save(seasonalPrice);
    }

    public List<SeasonalPrice> getSeasonalPricesForProperty(Long propertyId) {
        return seasonalPriceRepository.findByPropertyId(propertyId).orElseThrow(() -> new NoSuchElementException("No such property with id " + propertyId));
    }

    public Optional<SeasonalPrice> getSeasonPriceForCurrentDate(Long propertyId, LocalDate currentDate) {
        return seasonalPriceRepository.findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(propertyId, currentDate, currentDate);
    }
}
