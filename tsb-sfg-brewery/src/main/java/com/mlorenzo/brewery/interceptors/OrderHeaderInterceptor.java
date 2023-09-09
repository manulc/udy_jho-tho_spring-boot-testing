package com.mlorenzo.brewery.interceptors;

import com.mlorenzo.brewery.domain.BeerOrder;
import com.mlorenzo.brewery.domain.OrderStatusEnum;
import com.mlorenzo.brewery.events.BeerOrderStatusChangeEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Catch order updates
 */
@AllArgsConstructor
@Slf4j
@Component
public class OrderHeaderInterceptor extends EmptyInterceptor {
    private final ApplicationEventPublisher publisher;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof BeerOrder){
            for(Object curObj : currentState){
                if(curObj instanceof OrderStatusEnum){
                    for (Object prevObj : previousState){
                        if (prevObj instanceof OrderStatusEnum) {
                            OrderStatusEnum curStatus = (OrderStatusEnum) curObj;
                            OrderStatusEnum prevStatus = (OrderStatusEnum) prevObj;
                            if(curStatus != prevStatus){
                                log.debug("Order status change detected");
                                publisher.publishEvent(new BeerOrderStatusChangeEvent((BeerOrder) entity, prevStatus));
                            }
                        }
                    }
                }
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
