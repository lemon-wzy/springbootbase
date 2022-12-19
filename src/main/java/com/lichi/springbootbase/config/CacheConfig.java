package com.lichi.springbootbase.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @description: 缓存配置
 * @author: lichi
 * @date: 2022/12/17 21:07
 * @version: 1.0
 * @since: 2022/12/17
 */
@EnableCaching
@Configuration
public class CacheConfig {
    /**
     * 缓存过期时间
     */
    @Value("${cache.default-exp}")
    private long extension;

    /**
     * redis 主机地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * redis 端口
     */
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * redisTemplate配置
     * @param factory RedisConnectionFactory
     * @return RedisTemplate<?,?>
     */
    @Bean
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory) {
        // redisTemplate 对象
        RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<JSON> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(JSON.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 设置序列化的value值
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置序列化的hash值
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 设置序列化的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 缓存管理器
     * @param factory RedisConnectionFactory
     * @return CacheManager
     */
    @Bean
    CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //使用jackson 配置redis value 的序列化
        Jackson2JsonRedisSerializer<JSON> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(JSON.class);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                //value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                //缓存过期时间
                .entryTtl(Duration.ofSeconds(extension))
                //不缓存null值
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }


}
