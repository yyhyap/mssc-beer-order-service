package com.yyh.msscbeerorderservice.services.listeners;

import brewery.model.events.ValidateOrderResult;
import com.yyh.msscbeerorderservice.config.JmsConfig;
import com.yyh.msscbeerorderservice.services.BeerOrderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ValidationResultListener {

    @Autowired
    private BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult validateOrderResult) {
        final UUID beerOrderId = validateOrderResult.getOrderId();

        log.debug("Validation Result for order id: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, validateOrderResult.getIsValid());
    }
}
