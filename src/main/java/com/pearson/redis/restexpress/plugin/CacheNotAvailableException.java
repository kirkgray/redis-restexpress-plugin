/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pearson.redis.restexpress.plugin;

/**
 *
 * @author kirkg
 */
public class CacheNotAvailableException extends Exception {
    
    public CacheNotAvailableException() {
        super();
    }
    
    public CacheNotAvailableException( String message ) {
        super( message );
    }
}
