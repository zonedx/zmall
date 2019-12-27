package com.zmall.util;

import com.zmall.common.RedisPool;
import com.zmall.common.RedisShardedPool;
import com.zmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @ClassName: RedisPoolUtil
 * @Date 2019-10-08 16:23
 * @Author duanxin
 **/

@Slf4j
public class RedisPoolUtil {

    /**
     * 设置key的有效期  单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
//        Jedis jedis = null;
//        Long result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.expire(key,exTime);
//        } catch (Exception e) {
//            log.error("expire key:{} error",key,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
        return 1L;
    }
//
//    //exTime单位是秒
    public static String setEx(String key,String value,int exTime){
//        Jedis jedis = null;
//        String result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.setex(key,exTime,value);
//        } catch (Exception e) {
//            log.error("setEx key:{} value:{} error",key,value,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
        return null;
    }

    public static String set(String key,String value){
//        Jedis jedis = null;
//        String result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.set(key,value);
//        } catch (Exception e) {
//            log.error("set key:{} value:{} error",key,value,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
        return null;
    }

    public static String get(String key){
//        Jedis jedis = null;
//        String result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.get(key);
//        } catch (Exception e) {
//            log.error("get key:{} error",key,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
        return null;
    }
//
    public static Long del(String key){
//        Jedis jedis = null;
//        Long result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.del(key);
//        } catch (Exception e) {
//            log.error("del key:{} error",key,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
        return null;
    }
//
//    public static Long setnx(String key,String value){
//        Jedis jedis = null;
//        Long result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.setnx(key,value);
//        } catch (Exception e) {
//            log.error("setnx key:{} value:{} error",key,value,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
//    }
//
//    public static String getSet(String key,String value){
//        Jedis jedis = null;
//        String result = null;
//
//        try {
//            jedis = RedisPool.getJedis();
//            result = jedis.getSet(key,value);
//        } catch (Exception e) {
//            log.error("getSet key:{} value:{} error",key,value,e);
//            RedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        RedisPool.returnResource(jedis);
//        return result;
//    }

//    public static void main(String[] args) {
//        Jedis jedis = RedisPool.getJedis();
//        RedisPoolUtil.set("keyTest","value");
//
//        String value = RedisPoolUtil.get("keyTest");
//
//        RedisPoolUtil.setEx("keyEx","valueEx",60*10);
//
//        RedisPoolUtil.expire("keyTest",60*20);
//
//        RedisPoolUtil.del("keyTest");
//
//        System.out.println("end");
//    }

}
