package com.mlorenzo.sfgrestdocsexample.web.mappers;

import org.mapstruct.Mapper;

import com.mlorenzo.sfgrestdocsexample.domain.Beer;
import com.mlorenzo.sfgrestdocsexample.web.models.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
	BeerDto beerToBeerDto(Beer beer);
	Beer beerDtoToBeer(BeerDto beerDto);
}
