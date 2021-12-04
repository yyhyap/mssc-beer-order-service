package com.yyh.msscbeerorderservice.services;

import com.yyh.msscbeerorderservice.domain.BeerOrder;
import com.yyh.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.yyh.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.yyh.msscbeerorderservice.repositories.BeerOrderRepository;
import com.yyh.msscbeerorderservice.statemachine.BeerOrderStateChangeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BeerOrderManagerImpl implements BeerOrderManager {

    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    @Autowired
    private StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor;

    @Transactional
    @Override
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {
        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        sendBeerOrderEvent(savedBeerOrder, BeerOrderEventEnum.VALIDATE_ORDER);

        return savedBeerOrder;
    }

    private void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum beerOrderEventEnum) {

        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = build(beerOrder);

        Message message = MessageBuilder.withPayload(beerOrderEventEnum)
                .setHeader(ORDER_ID_HEADER, beerOrder.getId().toString())
                .build();

        sm.sendEvent(message);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder) {

        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = stateMachineFactory.getStateMachine(beerOrder.getId());

        sm.stop();

        // Set it to the status retrieved from database
        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null, null, null));
            // and save the status back to the database for preStateChange
            sma.addStateMachineInterceptor(beerOrderStateChangeInterceptor);
        });

        sm.start();

        return sm;
    }
}
