package com.yyh.msscbeerorderservice.web.mappers;

import com.yyh.msscbeerorderservice.domain.BeerOrderLine;
import com.yyh.msscbeerorderservice.services.beer.BeerService;
import brewery.model.BeerDto;
import brewery.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper{

    @Autowired
    private BeerService beerService;

    @Autowired
    // generated implementation of the original mapper by MapStruct is annotated with the Spring annotation @Qualifier("delegate")
    @Qualifier("delegate")
    private BeerOrderLineMapper mapper;

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {
        BeerOrderLineDto beerOrderLineDto = mapper.beerOrderLineToDto(line);

        Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(line.getUpc());

        beerDtoOptional.ifPresent(beerDto -> {
            beerOrderLineDto.setBeerName(beerDto.getBeerName());
            beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());
            beerOrderLineDto.setBeerId(beerDto.getId());
            beerOrderLineDto.setPrice(beerDto.getPrice());
        });

        return beerOrderLineDto;
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {
        return mapper.dtoToBeerOrderLine(dto);
    }
}
