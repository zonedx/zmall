package com.zmall.task;

import com.zmall.common.Const;
import com.zmall.common.RedissonManager;
import com.zmall.service.IOrderService;
import com.zmall.util.PropertiesUtils;
import com.zmall.util.RedisPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CloseOrderTask
 * @Date 2019-10-14 13:37
 * @Author duanxin
 **/
@Component
@Slf4j
public class CloseOrderTask {

    private IOrderService iOrderService;
    private RedissonManager redissonManager;

    @Autowired
    public CloseOrderTask(IOrderService iOrderService, RedissonManager redissonManager) {
        this.iOrderService = iOrderService;
        this.redissonManager = redissonManager;
    }

    @PreDestroy
    public void delLock() {
        RedisPoolUtils.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
    }



    //@Scheduled(cron = "0 */1 * * * ?")//每一分钟执行一次
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(PropertiesUtils.getProperty("lock.timeout", "5000"));
        Long setnxResult = RedisPoolUtils.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));

        if (setnxResult != null && setnxResult.intValue() == 1) {
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = RedisPoolUtils.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {

                String getSetResult = RedisPoolUtils.getSet(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                //再次用当前时间戳getSet
                //返回给定的key的旧值，->旧值判断，是否可以获取锁
                //当key没有旧值时，即key不存在时，返回nil ->获取锁
                //这里设置了一个新的value值，返回旧的值。
                if (getSetResult == null || StringUtils.equals(lockValueStr, getSetResult)) {
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

    @Scheduled(cron = "0 */1 * * * ?") //每分钟执行一次
    public void closeOrderTaskV4() {
        RLock lock = redissonManager.getRedisson().getLock(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;

        try {
            if (getLock = lock.tryLock(0,5, TimeUnit.SECONDS)){
                log.info("Redisson获取到分布式锁：{}，ThreadName：{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
                int hour = PropertiesUtils.getIntegerProperty("close.order.task.time.hour", "1");
                iOrderService.closeOrder(hour);
            }else {
                log.info("Redisson没有获取到分布式锁：{}，ThreadName：{}",Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK,Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.info("Redisson分布式锁获取异常");
        }finally {
            if (!getLock){
                return;
            }
            lock.unlock();
            log.info("Redisson分布式锁释放");
        }
    }

    private void closeOrder(String lockName) {
        RedisPoolUtils.expire(lockName, 5);//设置有效期50s，防止死锁
        log.info("获取：{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = PropertiesUtils.getIntegerProperty("close.order.task.time.hour", "2");
        //iOrderService.closeOrder(hour);
        RedisPoolUtils.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放：{},ThreadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("=================================");
    }

}
