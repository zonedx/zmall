package com.zmall.common;

import com.zmall.util.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: RedisPool
 * @Date 2019-10-08 15:12
 * @Author duanxin
 **/
@Configuration
public class RedisPool {

    /**
     * jedis连接池
     */
    private static JedisPool pool;
    /**
     * 最大连接数
     */
    private static final String MAX_T0TAL = "redis.max.total";
    /**
     * 在jedispool中最大的idle状态（空闲）的jedis实例的个数
     */
    private static final String MAX_IDLE = "redis.max.idle";
    /**
     * 在jedispool中最小的idle状态（空闲）的jedis实例的个数
     */
    private static final String MIN_IDLE = "redis.min.idle";
    /**
     * 在borrow一个jedis实例的时候，是否要进行验证操作。如果赋值true，则得到的jedis实例肯定是可以用的。
     */
    private static final String TEST_ON_BORROW = "redis.test.borrow";
    /**
     * 在return一个jedis实例的时候，是否要进行验证操作。如果赋值true，则放回jedispool的jedis实例肯定是可以用的。
     */
    private static final String TEST_ON_RETURN = "redis.test.return";

    private static final String REDIS_IP = "redis.ip";
    private static final String REDIS_PORT = "redis.port";

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(PropertiesUtils.getIntegerProperty(MAX_T0TAL));
        config.setMaxIdle(PropertiesUtils.getIntegerProperty(MAX_IDLE));
        config.setMinIdle(PropertiesUtils.getIntegerProperty(MIN_IDLE));

        config.setTestOnBorrow(PropertiesUtils.getBooleanProperty(TEST_ON_BORROW));
        config.setTestOnReturn(PropertiesUtils.getBooleanProperty(TEST_ON_RETURN));

        config.setBlockWhenExhausted(true);

        return config;
    }

    @Bean
    public JedisPool jedisPool(){
        pool = new JedisPool(jedisPoolConfig(),PropertiesUtils.getProperty(REDIS_IP), PropertiesUtils.getIntegerProperty(REDIS_PORT),1000*2);
        return pool;
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    /**
     * todo 过时问题 暂未解决
     */
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnResource(jedis);
    }


}