package com.yyh.msscbeerorderservice.statemachine.actions;

import brewery.model.events.AllocateOrderRequest;
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

import java.util.UUID;

@Slf4j
@Component
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private BeerOrderMapper beerOrderMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE, AllocateOrderRequest.builder()
                        .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                        .build());

        log.debug("Sent allocate order request to queue for order id " + beerOrderId);
    }
}
