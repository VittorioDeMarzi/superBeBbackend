package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.SeasonalPriceRequestDto;
import de.supercode.superBnB.dtos.SeasonalPriceResponseDto;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.entities.booking.SeasonalPrice;
import de.supercode.superBnB.mappers.SeasonalPriceDtoMapper;
import de.supercode.superBnB.repositories.SeasonalPriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SeasonalPriceService {

    SeasonalPriceRepository seasonalPriceRepository;
    PropertyService propertyService;
    SeasonalPriceDtoMapper seasonalPriceDtoMapper;

    public SeasonalPriceService(SeasonalPriceRepository seasonalPriceRepository, PropertyService propertyService, SeasonalPriceDtoMapper seasonalPriceDtoMapper) {
        this.seasonalPriceRepository = seasonalPriceRepository;
        this.propertyService = propertyService;
        this.seasonalPriceDtoMapper = seasonalPriceDtoMapper;
    }

    public SeasonalPriceResponseDto addSeasonalPrice(Long propertyId, SeasonalPriceRequestDto dto) {
        Property property = propertyService.findPropertyById(propertyId);
        checkNewSeasonalPriceDoesNotOverlap(property, dto);
        SeasonalPrice seasonalPrice  = new SeasonalPrice();
        seasonalPrice.setProperty(property);
        seasonalPrice.setStartDate(dto.startDate());
        seasonalPrice.setEndDate(dto.endDate());
        seasonalPrice.setPricePerNight(dto.pricePerNight());
        seasonalPriceRepository.save(seasonalPrice);
        return seasonalPriceDtoMapper.apply(seasonalPrice);
    }

    private void checkNewSeasonalPriceDoesNotOverlap(Property property, SeasonalPriceRequestDto dto) {
        LocalDate currentDate = dto.startDate();
        while(!currentDate.isAfter(dto.endDate())) {
            Optional<SeasonalPrice> currentPrice = seasonalPriceRepository.findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(property.getId(), currentDate, currentDate);
            if (currentPrice.isPresent()) throw new IllegalArgumentException("The provided seasonal price overlaps with an existing one.");
            currentDate = currentDate.plusDays(1);
        }
    }

    public Optional<SeasonalPrice> getSeasonPriceForCurrentDate(Long propertyId, LocalDate currentDate) {
        return seasonalPriceRepository.findByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(propertyId, currentDate, currentDate);
    }

    public List<SeasonalPriceResponseDto> getAllSeasonalPricesByProperty(Long id) {
        List<SeasonalPrice> seasonalPrices = seasonalPriceRepository.findByPropertyId(id).orElseThrow(() -> new NoSuchElementException("Property Id " + id + " does not have a seasonal price table"));
        return seasonalPrices.stream().map(seasonalPriceDtoMapper).toList();
    }
}
