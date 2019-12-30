package com.zmall.common;

import com.zmall.config.MixPropertySourceFactory;
import com.zmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: RedisPool
 * @Date 2019-10-08 15:12
 * @Author duanxin
 **/
@Configuration
@PropertySource(value = "classpath:application.yml")
public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static String maxTotal = "redis.max.total";//最大连接数
    private static String maxIdle = "redis.max.idle"; //在jedispool中最大的idle状态（空闲）的jedis实例的个数
    private static String minIdle = "redis.min.idle"; //在jedispool中最小的idle状态（空闲）的jedis实例的个数

    private static String testOnBorrow = "redis.test.borrow"; //在borrow一个jedis实例的时候，是否要进行验证操作。如果赋值true，则得到的jedis实例肯定是可以用的。
    private static String testOnReturn = "redis.test.return";; //在return一个jedis实例的时候，是否要进行验证操作。如果赋值true，则放回jedispool的jedis实例肯定是可以用的。

    private static String redisIp = "redis.ip";
    private static String redisPort = "redis.port";

    @Autowired
    private Environment env;


    public  void getIp(){

        String str = env.getRequiredProperty(testOnBorrow);
        String str1 = env.getRequiredProperty(maxTotal);
        System.out.println("+++++++++"+str);
        System.out.println("+++++++++"+str1);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(env.getRequiredProperty(maxTotal)));
        config.setMaxIdle(Integer.parseInt(env.getRequiredProperty(maxIdle)));
        config.setMinIdle(Integer.parseInt(env.getRequiredProperty(minIdle)));

        config.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty(testOnBorrow)));
        config.setTestOnReturn(Boolean.parseBoolean(env.getRequiredProperty(testOnReturn)));

        config.setBlockWhenExhausted(true);

        return config;
    }

    @Bean
    public JedisPool jedisPool(){
        pool = new JedisPool(jedisPoolConfig(),env.getRequiredProperty(redisIp), Integer.parseInt(env.getRequiredProperty(redisPort)),1000*2);
        return pool;
    }
//    private static void initPool(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        System.out.println("==========");
//        config.setMaxTotal(Integer.parseInt(maxTotal));
//        config.setMaxIdle(Integer.parseInt(maxIdle));
//        config.setMinIdle(Integer.parseInt(minIdle));
//
//        config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
//        config.setTestOnReturn(Boolean.parseBoolean(testOnReturn));
//
//        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true
//
//        pool = new JedisPool(config,redisIp, Integer.parseInt(redisPort),1000*2);
//    }
//
//    static {
//        initPool();
//    }
//
    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }


}