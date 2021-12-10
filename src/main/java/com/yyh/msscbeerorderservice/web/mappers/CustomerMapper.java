package com.yyh.msscbeerorderservice.web.mappers;

import com.yyh.brewery.model.CustomerDto;
import com.yyh.msscbeerorderservice.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { DateMapper.class })
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
