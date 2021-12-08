package com.yyh.msscbeerorderservice.services.testcomponents;

import com.yyh.brewery.model.events.ValidateOrderRequest;
import com.yyh.brewery.model.events.ValidateOrderResult;
import com.yyh.msscbeerorderservice.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeerOrderValidationListener {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message message) {

        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();

        System.out.println("#####RAN#####");

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                        .isValid(true)
                        .orderId(request.getBeerOrderDto().getId())
                .build());

    }

}
