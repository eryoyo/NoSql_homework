package com.eryo.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class ZsetResolver implements TypeResolver{

    private CounterSpec counterSpec;
    private Jedis jedis;

    @Override
    public String resolve() {
        String res = "没有进行有效操作";
        String key = counterSpec.getKeyFields();
        String value = counterSpec.getValueFields();
        int expireTime = counterSpec.getExpireTime();
        
        if(key != null) {
            if(jedis.exists(key)) {
                if(jedis.type(key).equals("zset")) {
                    if(value != null) {
                        if(expireTime != 0) {
                            if (jedis.zrank(key, value) < 0){
                                jedis.zadd(key, 1, value);
                                jedis.expire(key, expireTime);
                                res = "键：" + key + "中集合中添加新值：" + value + "，score为1" + "，key的过期时间为" + jedis.ttl(key);
                            }else{
                                res = "键：" + key + "中集合中已经存在值：" + value + "，score为1" + "，key的过期时间为" + jedis.ttl(key);
                            }
                        } else {
                            if (jedis.zrank(key, value) < 0){
                                jedis.zadd(key, 1, value);
                                jedis.expire(key, expireTime);
                                res = "键：" + key + "中集合中添加新值：" + value + "，score为1";
                            }else{
                                res = "键：" + key + "中集合中已经存在值：" + value + "，score为1";
                            }
                        }
                    } else {
                        if(expireTime != 0) {
                            jedis.expire(key, expireTime);
                            Set<String> set = jedis.zrangeByScore(key, 1, 2);
                            res = "键：" + key + "中集合的值如下：";
                            Iterator<String> it = set.iterator();
                            while(it.hasNext()){
                                String str = it.next();
                                res += str + " ";
                            }
                        }else{
                            Set<String> set = jedis.zrangeByScore(key, 1, 2);
                            res = "键：" + key + "中集合的值如下：";
                            Iterator<String> it = set.iterator();
                            while(it.hasNext()){
                                String str = it.next();
                                res += str + " ";
                            }
                        }
                    }
                }
            } else {
                if(value != null) {
                    if(expireTime != 0) {
                        jedis.zadd(key, 1, value);
                        jedis.expire(key, expireTime);
                        res = "键：" + key + "中集合中添加新值：" + value + "，score为1" + "，key的过期时间为：" + expireTime;
                    } else {
                        jedis.zadd(key, 1, value);
                        res = "键：" + key + "中集合中添加新值：" + value + "，score为1";
                    }
                }
            }
        }
        return res;

    }

    @Override
    public void setData(CounterSpec counterSpec, Jedis jedis) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
    }
}
