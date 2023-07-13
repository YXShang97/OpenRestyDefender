package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Service
public class IPFrequencyRule implements Rule {
    private Duration duration;
    private int maxRequests;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    Map<String, Queue<Long>> map = new HashMap<>();

    public IPFrequencyRule(Duration duration, int maxRequests) {
        this.duration = duration;
        this.maxRequests = maxRequests;
    }

    @Override
    public boolean check(HttpRequest httpRequest) {
        // use Redis List to store the timestamps of requests
        String key = httpRequest.getIp();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForList().rightPush(key, new Date().getTime());
        }
        Long now = new Date().getTime();
        redisTemplate.opsForList().rightPush(key, now);
        while ((Long) redisTemplate.opsForList().index(key, 0) < now - duration.toMillis()) {
            redisTemplate.opsForList().leftPop(key);
        }
        while (redisTemplate.opsForList().size(key) > maxRequests + 1) {
            redisTemplate.opsForList().leftPop(key);
        }
        return redisTemplate.opsForList().size(key) <= maxRequests;

        // use HashMap to store the timestamps of requests
//        if (!map.containsKey(httpRequest.getIp())) {
//            map.put(httpRequest.getIp(), new LinkedList<>());
//        }
//        Queue<Long> queue = map.get(httpRequest.getIp());
//        Long now = new Date().getTime();
//        queue.offer(now);
//
//        while (!queue.isEmpty() && queue.peek() < now - duration.toMillis()) {
//            queue.poll();
//        }
//        while (queue.size() > maxRequests + 1) {
//            queue.poll();
//        }
//
//        return queue.size() <= maxRequests;
    }
}
