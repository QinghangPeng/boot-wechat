package com.pqh.basic.wechat.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置缓存并设置时效
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);

            } else {
                set(key, value);
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 设置缓存在某个时间点失效
     * @param key
     * @param value
     * @param date
     * @return
     */
    public boolean set(String key, Object value, Date date) {
        try {
            if (date != null) {
                redisTemplate.opsForValue().set(key, value);
                redisTemplate.expireAt(key,date);

            } else {
                set(key, value);
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *  设置hash值
     * @param key
     * @param chunks
     * @return
     */
    public boolean hashSetAll(String key,Map<String,byte[]> chunks) {
        try{
            redisTemplate.opsForHash().putAll(key,chunks);
            redisTemplate.expire(key,5,TimeUnit.MINUTES);
            return true;
        } catch(Exception e) {
            throw e;
        }
    }

    public int hashSize(String key) {
        try{
            return key == null ? 0 : redisTemplate.opsForHash().size(key).intValue();
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     *  设置hash缓存
     * @param key
     * @param valueKey
     * @param value
     * @return
     */
    public boolean hashSet(String key,Object valueKey,Object value) {
        try{
            redisTemplate.opsForHash().put(key,valueKey,value);
            return true;
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     *  设置hash缓存并设置时效
     * @param key
     * @param valueKey
     * @param value
     * @param time  单位为分钟
     * @return
     */
    public boolean hashSet(String key,Object valueKey,Object value,long time) {
        try{
            if (time > 0) {
                redisTemplate.opsForHash().put(key,valueKey,value);
                redisTemplate.expire(key,time,TimeUnit.MINUTES);
            } else {
                hashSet(key,valueKey,value);
            }
            return true;
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     *  获取hash某个valuekey缓存
     * @param key
     * @param valueKey
     * @return
     */
    public Object hashGet(String key,Object valueKey) {
        try{
            return redisTemplate.opsForHash().get(key,valueKey);
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     *  文件分块上传专用
     * @param key
     * @return
     */
    public Map<Object, Object> hashCollections(String key) {
        try{
            if (StringUtils.isBlank(key)) {
                return null;
            }
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            return entries;
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     *  获取hash所有缓存
     * @param key
     * @return
     */
    public List<Object> hashValues(String key) {
        try{
            return key == null ? null : redisTemplate.opsForHash().values(key);
        } catch(Exception e) {
            throw e;
        }
    }

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     *  校验是否存在这个key
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     *  删除key
     * @param key
     */
    public void delKey(String key) {
        redisTemplate.delete(key);
    }
}
