package com.yyh.msscbeerorderservice.statemachine.actions;

import com.yyh.brewery.model.events.AllocationFailureEvent;
import com.yyh.brewery.model.events.ValidateOrderRequest;
import com.yyh.msscbeerorderservice.config.JmsConfig;
import com.yyh.msscbeerorderservice.domain.BeerOrder;
import com.yyh.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.yyh.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.yyh.msscbeerorderservice.repositories.BeerOrderRepository;
import com.yyh.msscbeerorderservice.services.BeerOrderManagerImpl;
import com.yyh.msscbeerorderservice.web.mappers.BeerOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_FAILURE_QUEUE, AllocationFailureEvent.builder()
                        .orderId(UUID.fromString(beerOrderId))
                .build());

        log.debug("Sent Allocation Failure Message to queue for order id " + beerOrderId);

    }
}
