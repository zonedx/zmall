package com.zmall.task;

import com.zmall.common.Const;
import com.zmall.service.IOrderService;
import com.zmall.util.PropertiesUtil;
import com.zmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @ClassName: CloseOrderTask
 * @Date 2019-10-14 13:37
 * @Author duanxin
 **/
@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private IOrderService iOrderService;

    @PreDestroy
    public void delLock() {
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }

    //    @Scheduled(cron = "0 */1 * * * ?")//每一分钟执行一次
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动");
        int hour = PropertiesUtil.getIntegerProperty("close.order.task.time.hour", "2");
        //iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    //@Scheduled(cron = "0 */1 * * * ?")//每一分钟执行一次
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "50000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));

        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回值是1，代表设置成功，获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("没有获得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")//每一分钟执行一次
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));

        if (setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {

                String getSetResult = RedisShardedPoolUtil.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                //再次用当前时间戳getSet
                //返回给定的key的旧值，->旧值判断，是否可以获取锁
                //当key没有旧值时，即key不存在时，返回nil ->获取锁
                //这里设置了一个新的value值，返回旧的值。
                if (getSetResult == null || (getSetResult != null && StringUtils.equals(lockValueStr, getSetResult))) {
                    //真正获取到锁
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取到分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            }
        }

        log.info("关闭订单定时任务结束");
    }


    private void closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, 5);//设置有效期50s，防止死锁
        log.info("获取：{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = PropertiesUtil.getIntegerProperty("close.order.task.time.hour", "2");
        //iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放：{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("=================================");
    }

}
