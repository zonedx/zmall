package com.zmall.common;

import com.zmall.util.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName: RedissonManager
 * @Date 2019-10-14 19:30
 * @Author duanxin
 **/

@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    @Value("${redis.ip}")
    private String redisIp;
    @Value("${redis.port}")
    private Integer redisPort;

    @PostConstruct
    private void init(){
        try {
            config.useSingleServer().setAddress(redisIp + ":" + redisPort);

            redisson = (Redisson) Redisson.create(config);
            log.info("初始化Redisson结束");
        } catch (Exception e) {
            log.error("init Redisson error:{}",e.toString());
        }
    }
}
