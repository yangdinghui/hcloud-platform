package hcloud.platform.modules.lock.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class RedissonLock {

    private RedissonClient redissonClient;

    public RedissonLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     *
     * @param key
     * @param expire
     * @return
     */
    public boolean lock(String key, int expire) {
        RLock lock = redissonClient.getLock(key);
        lock.lock((long) expire, TimeUnit.SECONDS);
        try {
            System.out.println(expire);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }


}
