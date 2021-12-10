package com.yyh.msscbeerorderservice.services;

import com.yyh.brewery.model.events.CustomerPagedList;
import org.springframework.data.domain.Pageable;


public interface CustomerService {
    CustomerPagedList listCustomers(Pageable pageable);
}
