/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.tiendaliz.catalogo.util;

/**
 *
 * @author pvargas
 */
public class TlThreadLocalHolder {
    
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal();

    public static void setCorrelationId(String correlationId){
        CORRELATION_ID.set(correlationId);
    }
    
    public static String getCorrelationId() {
        return CORRELATION_ID.get();
    }
    
    
    
    
    
}
