package com.pearson.redis.restexpress.plugin;

import com.strategicgains.restexpress.plugin.AbstractPlugin;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class RedisPlugin extends AbstractPlugin 
{
    private Map<String,Cache> cacheDefinitions;
    
    public RedisPlugin( ) {
        
    }
    
    public void addCache( String cacheName, Cache cache ) {
        
        if( cacheDefinitions == null ) {
            cacheDefinitions = new HashMap<String,Cache>();
        }
        
        cacheDefinitions.put(cacheName, cache);
    }
    
    public Cache getCache( String cacheName ) throws CacheNotAvailableException  {
        
        if( cacheDefinitions == null ) {
            throw new CacheNotAvailableException( "No caches have been defined" );
        }
        
        Cache c = cacheDefinitions.get( cacheName );
        
        if( c == null ) {
            throw new CacheNotAvailableException( "No cache by the name " + cacheName + " has been defined" );
        }
        
        return c;
    }
}
