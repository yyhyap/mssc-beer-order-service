package com.yyh.msscbeerorderservice.web.mappers;

import com.yyh.msscbeerorderservice.domain.BeerOrder;
import com.yyh.msscbeerorderservice.domain.Customer;
import com.yyh.msscbeerorderservice.repositories.CustomerRepository;
import com.yyh.brewery.model.BeerOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderMapperDecorator implements BeerOrderMapper{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    @Qualifier("delegate")
    private BeerOrderMapper mapper;

    @Override
    public BeerOrderDto beerOrderToDto(BeerOrder beerOrder) {
        return mapper.beerOrderToDto(beerOrder);
    }

    @Override
    public BeerOrder dtoToBeerOrder(BeerOrderDto dto) {
        BeerOrder beerOrder = mapper.dtoToBeerOrder(dto);

        Optional<Customer> customerOptional = customerRepository.findById(dto.getCustomerId());

        // customer -> beerOrder.setCustomer(customer)
        customerOptional.ifPresent(beerOrder::setCustomer);

//        // testing
//        customerOptional.ifPresent(customer -> System.out.println(customer.getId() + " " + customer.getCustomerName()));

        return beerOrder;
    }
}
