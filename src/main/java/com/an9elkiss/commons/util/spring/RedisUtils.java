package com.an9elkiss.commons.util.spring;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public final class RedisUtils {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, Object> valueOps;
	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;
	@Resource(name = "redisTemplate")
	private ZSetOperations<String, String> zsetOps;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOps;

	public void setValue(String key, Object value) {
		valueOps.set(key, value);
	}

	public void setString(String key, String value, Long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public String getString(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public Object getValue(String key) {
		return valueOps.get(key);
	}

	// 返回值为设置成功的value数
	public Long setSet(String key, String... value) {
		return setOps.add(key, value);
	}

	// 返回值为redis中键值为key的value的Set集合
	public Set<String> getSetMembers(String key) {
		return setOps.members(key);
	}

	public Boolean setZSet(String key, String value, double score) {
		return zsetOps.add(key, value, score);
	}

	public Double getZSetScore(String key, String value) {
		return zsetOps.score(key, value);
	}

	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

	public void deleteKeys(Collection<String> keys) {
		redisTemplate.delete(keys);
	}
}
