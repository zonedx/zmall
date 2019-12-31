package com.zmall.common;

import com.zmall.util.PropertiesUtils;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RedisShardedPool
 * @Date 2019-10-11 09:56
 * @Author duanxin
 **/
public class RedisShardedPool {

    private static ShardedJedisPool pool;//jedis连接池
    private static Integer maxTotal = PropertiesUtils.getIntegerProperty("redis.max.total","20");//最大连接数
    private static Integer maxIdle = PropertiesUtils.getIntegerProperty("redis.max.idle","10"); //在jedispool中最大的idle状态（空闲）的jedis实例的个数
    private static Integer minIdle = PropertiesUtils.getIntegerProperty("redis.min.idle","2"); //在jedispool中最小的idle状态（空闲）的jedis实例的个数

    private static Boolean testOnBorrow = PropertiesUtils.getBooleanProperty("redis.test.borrow","true"); //在borrow一个jedis实例的时候，是否要进行验证操作。如果赋值true，则得到的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = PropertiesUtils.getBooleanProperty("redis.test.return","true"); //在return一个jedis实例的时候，是否要进行验证操作。如果赋值true，则放回jedispool的jedis实例肯定是可以用的。

    private static String redis1Ip = PropertiesUtils.getProperty("redis1.ip");
    private static Integer redis1Port = PropertiesUtils.getIntegerProperty("redis1.port");

    private static String redis2Ip = PropertiesUtils.getProperty("redis2.ip");
    private static Integer redis2Port = PropertiesUtils.getIntegerProperty("redis2.port");

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip,redis1Port,1000*2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port,1000*2);

        List<JedisShardInfo> jedisShardInfos = new ArrayList<>(2);
        jedisShardInfos.add(info1);
        jedisShardInfos.add(info2);

        //Hashing.MURMUR_HASH一致性hash算法
        pool = new ShardedJedisPool(config,jedisShardInfos, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    private static void returnResource(){
        pool.close();
    }

    public static void returnBrokenResource(){
        pool.close();
    }

    public static void main(String[] args) {
        ShardedJedis shardedJedis = pool.getResource();
        for (int i = 0; i <10;i++){
            shardedJedis.set("key"+i,"value"+i);
        }
        returnResource();
        System.out.println("program is end");
    }
}
