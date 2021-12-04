package com.yyh.msscbeerorderservice.services;

import com.yyh.msscbeerorderservice.domain.BeerOrder;

public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
