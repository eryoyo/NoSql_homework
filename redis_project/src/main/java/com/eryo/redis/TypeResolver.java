package com.eryo.redis;

import redis.clients.jedis.Jedis;
import java.text.ParseException;

public interface TypeResolver {
    String resolve() throws ParseException;

    void setData(CounterSpec counterSpec, Jedis jedis);
}
