package com.yyh.msscbeerorderservice.statemachine.actions;

import com.yyh.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.yyh.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.yyh.msscbeerorderservice.services.BeerOrderManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        log.debug("Compensating Transaction... Validation Failed: " + beerOrderId);
    }
}
