package com.mlorenzo.brewery.web.mappers;

import com.mlorenzo.brewery.domain.Beer;
import com.mlorenzo.brewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDto beerDto);
}
