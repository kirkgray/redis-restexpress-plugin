package com.pearson.redis.restexpress.plugin;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test for @RedisPlugin class
 */
public class RedisPluginTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RedisPluginTest( String testName )
    {
        super( testName );
    }

    public void testAddAndGetCache()
    {
        RedisPlugin plugin = new RedisPlugin();
        
        Cache cache1 = new Cache( "cache1host" );
        plugin.addCache( "cache1", cache1 );
        
        try {
            assertNotNull( plugin.getCache( "cache1" ) );
        }
        catch( CacheNotAvailableException e ) {}
        
        
    }
    
    public void testGetMissingCache() {
        
        RedisPlugin plugin = new RedisPlugin();
        
        try {
            plugin.getCache( "cache2" );
            fail();
        }
        catch( Exception e ) {
            Assert.assertTrue( e instanceof CacheNotAvailableException );
        }
    }
}
