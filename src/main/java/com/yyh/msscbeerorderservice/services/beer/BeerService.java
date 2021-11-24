package com.yyh.msscbeerorderservice.services.beer;

import com.yyh.msscbeerorderservice.web.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID beerId);

    Optional<BeerDto> getBeerByUpc(String upc);
}
