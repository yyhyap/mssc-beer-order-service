package com.yyh.msscbeerorderservice.web.mappers;

import com.yyh.msscbeerorderservice.domain.BeerOrderLine;
import com.yyh.msscbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
