/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.util;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author pvargas
 */

@Component
@Slf4j
public class TlContextHystrixConcurrencyStrategy extends HystrixConcurrencyStrategy {

    public TlContextHystrixConcurrencyStrategy() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
    }
    
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable){
        return new TlCallable(callable, TlThreadLocalHolder.getCorrelationId());
    }
    
    
    public static class TlCallable<T> implements Callable<T>{
        
        private final Callable<T> actual;
        private final String correlationId;

        public TlCallable(Callable<T> actual, String correlationId) {
            this.actual = actual;
            this.correlationId = correlationId;
        }
        
        

        @Override
        public T call() throws Exception {
            TlThreadLocalHolder.setCorrelationId(correlationId);
            
            try{
                return actual.call();
            }finally {
            
                    TlThreadLocalHolder.setCorrelationId(null);
            }
        }
    
    }
    
    
    
}
