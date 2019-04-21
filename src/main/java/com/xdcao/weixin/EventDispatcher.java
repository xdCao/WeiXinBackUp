package com.xdcao.weixin;

import com.xdcao.weixin.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.xdcao.weixin.utils.RedisUtil.USER_KEY;

/**
 * @Author: buku.ch
 * @Date: 2019-04-17 22:34
 */

@Component
public class EventDispatcher {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    public void dispatchEvent(Map<String, String> eventMap) {
        // todo
        switch (eventMap.get(MessageUtil.EVENT_TYPE)) {
            case MessageUtil.EVENT_TYPE_SUBSCRIBE:
                redisTemplate.opsForSet().add(USER_KEY, eventMap.get(MessageUtil.FROM_USER_NAME));
                break;
            case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
                redisTemplate.opsForSet().remove(USER_KEY, eventMap.get(MessageUtil.FROM_USER_NAME));
                break;
        }
    }

}
