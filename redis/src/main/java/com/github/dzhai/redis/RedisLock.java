package com.github.dzhai.redis;

import redis.clients.jedis.Jedis;

public class RedisLock {

	private Jedis jedis;

	public boolean lock(String name) {
		return lock(name, 0, 5000, 1000);
	}

	public boolean lock(String name, int expire) {
		return lock(name, 0, expire, 1000);
	}

	public boolean lock(String name, int timeout, int expire) {
		return lock(name, timeout, expire, 1000);
	}

	/**
	 * 加锁
	 * 
	 * @param name
	 *            锁的标识名
	 * @param timeout
	 *            循环获取锁的等待超时时间，在此时间内会一直尝试获取锁直到超时，为0表示失败后直接返回不等待
	 * @param expire
	 *            当前锁的最大生存时间(秒)，必须大于0，如果超过生存时间锁仍未被释放，则系统会自动强制释放
	 * @param waitIntervalUs
	 *            获取锁失败后挂起再试的时间间隔(秒)
	 * @return true 获得锁 false获得锁失败
	 */
	public boolean lock(String name, int timeout, int expire, int waitIntervalUs) {
		if (name == null || "".equals(name.trim())) {
			return false;
		}
		// 取得当前时间
		Long now = System.currentTimeMillis();
		// 获取锁失败时的等待超时时刻
		Long timeoutAt = now + timeout;
		// 锁的最大生存时刻
		Long expireAt = now + expire;

		String redisKey = getLockKey(name);

		while (true) {
			// 将rediskey的最大生存时刻存到redis里，过了这个时刻该锁会被自动释放
			Long result = jedis.setnx(redisKey, expireAt.toString());
			if (1 == result) {
				// 设置key的失效时间
				jedis.expireAt(redisKey, expireAt);
				// 将锁标志放到lockedNames数组里
				return true;
			}

			// 以秒为单位，返回给定key的剩余生存时间
			Long ttl = jedis.ttl(redisKey);

			// ttl小于0 表示key上没有设置生存时间（key是不会不存在的，因为前面setnx会自动创建）
			// 如果出现这种状况，那就是进程的某个实例setnx成功后 crash 导致紧跟着的expire没有被调用
			// 这时可以直接设置expire并把锁纳为己用
			if (ttl < 0) {
				jedis.set(redisKey, expireAt.toString());
				return true;
			}

			/***** 循环请求锁部分 *****/
			// 如果没设置锁失败的等待时间 或者 已超过最大等待时间了，那就退出
			if (timeout <= 0 || timeoutAt < System.currentTimeMillis()) {
				break;
			}

			// 隔 $waitIntervalUs 后继续 请求
			try {
				Thread.sleep(waitIntervalUs);
			} catch (InterruptedException e) {
				Thread.interrupted();
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * 解锁
	 * 
	 * @param name
	 * @return true 成功
	 */
	public boolean unlock(String name) {
		// 先判断是否存在此锁
		if (isLocking(name)) {
			// 删除锁
			if (jedis.del(getLockKey(name)) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 给当前所增加指定生存时间，必须大于0
	 * 
	 * @param name
	 * @return true 成功
	 */
	public boolean expire(String name, int expire) {
		// 所指定的生存时间必须大于0
		if (expire <= 0) {
			return false;
		}
		// 先判断是否存在该锁
		if (isLocking(name)) {
			// 增加锁生存时间
			if (jedis.expire(getLockKey(name), expire) == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断当前是否拥有指定名字的所
	 * 
	 * @param name
	 * @return boolean
	 */
	public boolean isLocking(String name) {
		Boolean result = jedis.exists(getLockKey(name));
		return result != null && result;
	}

	private String getLockKey(String name) {
		return "Lock:" + name;
	}
}
