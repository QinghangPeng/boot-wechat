package com.pqh.basic.wechat.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * @ClassName: ByteRedisSerializer
 * @Description:
 * @Author: jackson
 * @Date: 2020/1/14 下午9:35
 * @Version: v1.0
 */
public class ByteRedisSerializer implements RedisSerializer<byte[]> {
    private final Charset charset;

    public ByteRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public ByteRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(byte[] t) throws SerializationException {
        return t;
    }

    @Override
    public byte[] deserialize(byte[] bytes) throws SerializationException {
        return bytes;
    }

}
