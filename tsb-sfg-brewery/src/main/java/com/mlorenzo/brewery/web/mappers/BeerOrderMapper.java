package com.mlorenzo.brewery.web.mappers;

import com.mlorenzo.brewery.domain.Beer;
import com.mlorenzo.brewery.domain.BeerOrder;
import com.mlorenzo.brewery.domain.BeerOrderLine;
import com.mlorenzo.brewery.web.model.BeerOrderDto;
import com.mlorenzo.brewery.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerOrderMapper {
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);
    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    default BeerOrderLine dtoToBeerOrder(BeerOrderLineDto dto){
        return BeerOrderLine.builder()
                .orderQuantity(dto.getOrderQuantity())
                .beer(Beer.builder().id(dto.getBeerId()).build())
                .build();
    }
}
