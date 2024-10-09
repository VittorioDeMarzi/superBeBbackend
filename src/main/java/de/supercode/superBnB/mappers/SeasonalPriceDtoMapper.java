package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.SeasonalPriceResponseDto;
import de.supercode.superBnB.entities.booking.SeasonalPrice;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SeasonalPriceDtoMapper implements Function<SeasonalPrice, SeasonalPriceResponseDto> {
    @Override
    public SeasonalPriceResponseDto apply(SeasonalPrice seasonalPrice) {
        return new SeasonalPriceResponseDto(
                seasonalPrice.getId(),
                seasonalPrice.getProperty().getId(),
                seasonalPrice.getStartDate(),
                seasonalPrice.getEndDate(),
                seasonalPrice.getPricePerNight()
        );
    }
}