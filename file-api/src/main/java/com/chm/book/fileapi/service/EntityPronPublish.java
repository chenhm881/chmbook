package com.chm.book.fileapi.service;

import com.chm.book.fileapi.domain.BatchInfo;
import com.chm.book.fileapi.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.Optional;

@Component
public class EntityPronPublish {

    private static Logger logger = LoggerFactory.getLogger(EntityPronPublish.class);

    @Autowired
    private RedisMessageListenerContainer container;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String setMessage(BatchInfo batchInfo) {
        String batchId;
        synchronized (this) {
            batchId = (String) Optional.ofNullable(redisTemplate.opsForValue().get("batchId"))
                    .orElseGet( () -> {
                        String rawId = batchInfo.getLocale() + ":" + batchInfo.getRadarID() + ":" + DateTimeUtils.getCurrentDateTime();
                        return DigestUtils.md5DigestAsHex(rawId.getBytes());
                    });
            redisTemplate.opsForValue().set("batchId", batchId, Duration.ofMinutes(30));
            batchInfo.setBatchID(batchId);
            redisTemplate.opsForZSet().add("batchInfo", batchInfo, System.currentTimeMillis());
            if (!container.isRunning()) {
                container.start();
            }
        }
        redisTemplate.convertAndSend("newTopic", batchInfo);
        return batchId;
    }
}
