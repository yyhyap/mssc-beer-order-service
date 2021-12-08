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

import java.util.Objects;

@Slf4j
@Component
public class BeerOrderValidationListener {

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message message) {
        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();

        System.out.println("##### Validation Listener RAN #####");

//        // condition to fail validation
//        if(request.getBeerOrderDto().getCustomerRef() != null && request.getBeerOrderDto().getCustomerRef().equals("fail-validation")) {
//            isValid = false;
//        }

        if(request.getBeerOrderDto().getCustomerRef() != null) {
            // condition to fail validation
            if(request.getBeerOrderDto().getCustomerRef().equals("fail-validation")) {
                isValid = false;
            } else if (request.getBeerOrderDto().getCustomerRef().equals("dont-validate")){
                sendResponse = false;
            }
        }

        if(sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                    .isValid(isValid)
                    .orderId(request.getBeerOrderDto().getId())
                    .build());
        }

    }

}
