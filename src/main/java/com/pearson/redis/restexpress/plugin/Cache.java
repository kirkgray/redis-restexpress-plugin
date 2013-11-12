package com.pearson.redis.restexpress.plugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import org.apache.log4j.Logger;

public final class Cache {
    
    private JedisPool pool;
    private static final Logger log = Logger.getLogger(Cache.class);

    public Cache(String host){
        this.pool = new JedisPool(new JedisPoolConfig(), host);
    }
    
    public Cache( JedisPoolConfig config, String host ) {
        this.pool = new JedisPool( config, host );
    }

    public Object getFromCache(String key){
        BinaryJedis jedis = null;
        Object result = null;
        byte[] byte_key = key.getBytes();
        try{
            jedis = pool.getResource();
            if(jedis != null && jedis.exists(byte_key)){
                byte[] cached = jedis.get(byte_key);
                ByteArrayInputStream b = new ByteArrayInputStream(cached);
                ObjectInputStream o = new ObjectInputStream(b);
                result = o.readObject();
            }
        }
        catch(JedisConnectionException e){
            //Don't want to tank the whole service just because Redis went down
            log.error("Could not connect to Redis");
        }
        catch( IOException e){
            log.error(e);
        }
        catch( ClassNotFoundException e){
            log.error(e);
        }
        finally{
            pool.returnResource((Jedis)jedis);
        }
        return result;
    }

    public void setInCache(String key, Object obj, int ttl){
        BinaryJedis jedis = null;
        try{
            jedis = pool.getResource();
            byte[] byte_key = key.getBytes();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(obj);
            jedis.set(byte_key,b.toByteArray());
            jedis.expire(byte_key,ttl);
        }
        catch(JedisConnectionException e){
            //Don't want to tank the whole service just because Redis went down
            log.error("Could not connect to Redis");
        }
        catch( IOException e){
            log.error(e);
        }
        finally{
            pool.returnResource((Jedis)jedis);
        }
    }
}
