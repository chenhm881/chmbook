package com.chm.book.fileapi.service;

import com.chm.book.fileapi.domain.BatchInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityPronSubscribe {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    @SneakyThrows
    public void getMessage(String batchInfoJson) {
        synchronized (this) {
            BatchInfo batchInfo = (BatchInfo) (new GenericJackson2JsonRedisSerializer()).deserialize(batchInfoJson.getBytes("UTF-8"));
            System.out.println("batchId：" + batchInfo.getRadarID());



            Optional<Object> infoOptional = redisTemplate.opsForZSet().range("batchInfo", 0, -1).stream()
                    .filter(info -> ((BatchInfo) info).getRadarID().equals(batchInfo.getRadarID())).findFirst();
            //infoOptional.ifPresent(o -> redisTemplate.opsForZSet().remove("batchInfo", o));

            Long size = redisTemplate.opsForZSet().zCard("batchInfo");
            if (size > 0) {
                Object obj = redisTemplate.opsForZSet().range("batchInfo", size - 1, size - 1).stream().findFirst().get();
                redisTemplate.opsForZSet().add("batchInfo", obj, System.currentTimeMillis());
            }
        }
    }
}
