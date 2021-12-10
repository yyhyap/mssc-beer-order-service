package com.yyh.msscbeerorderservice.services;

import com.yyh.brewery.model.events.CustomerPagedList;
import com.yyh.msscbeerorderservice.domain.Customer;
import com.yyh.msscbeerorderservice.repositories.CustomerRepository;
import com.yyh.msscbeerorderservice.web.mappers.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public CustomerPagedList listCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return new CustomerPagedList(customerPage
                .stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList()), PageRequest.of(
                        customerPage.getPageable().getPageNumber(),
                        customerPage.getPageable().getPageSize()),
                        customerPage.getTotalElements());
    }
}
