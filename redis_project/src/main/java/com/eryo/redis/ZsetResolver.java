package com.eryo.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * 向zset中添加元素，设置过期时间，创建zset的操作
 */
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
                    if(value != null) {     //向zset中添加新值
                        if(expireTime != 0) {       //向zset中添加新值，并更新过期时间
                            if (jedis.zrank(key, value) < 0){   //该元素还未存在
                                jedis.zadd(key, 1, value);
                                jedis.expire(key, expireTime);
                                res = "键：" + key + "中集合中添加新值：" + value + "，score为1" + "，key的过期时间为" + jedis.ttl(key);
                            }else{  //该元素已经在这个集合之中了
                                res = "键：" + key + "中集合中已经存在值：" + value + "，score为1" + "，key的过期时间为" + jedis.ttl(key);
                            }
                        } else {    //仅添加新值，不更新过期时间
                            if (jedis.zrank(key, value) < 0){   //已经存在该元素
                                jedis.zadd(key, 1, value);
                                jedis.expire(key, expireTime);
                                res = "键：" + key + "中集合中添加新值：" + value + "，score为1";
                            }else{  //该元素还未存在
                                res = "键：" + key + "中集合中已经存在值：" + value + "，score为1";
                            }
                        }
                    } else {        //展示该zset中现有的元素
                        if(expireTime != 0) {   //展示现有元素并更新过期时间
                            jedis.expire(key, expireTime);
                            Set<String> set = jedis.zrangeByScore(key, 1, 2);
                            res = "键：" + key + "中集合的值如下：";
                            Iterator<String> it = set.iterator();
                            while(it.hasNext()){
                                String str = it.next();
                                res += str + " ";
                            }
                        }else{  //仅展示现有元素
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
            } else {    //该key还不存在，创建zset
                if(value != null) {
                    if(expireTime != 0) {   //创建zset，设置默认的score为1，设置过期时间
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
