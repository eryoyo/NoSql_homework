package com.eryo.redis;

import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 按小时为单位计算流量,在计算某时间段总流量的时候将不满足整点的时间段直接默认为整点！！！
 */
public class FreqResolver implements TypeResolver{

    private CounterSpec counterSpec;
    private Jedis jedis;

    @Override
    public String resolve() throws ParseException {
        String res = "没有进行有效操作";
        //获取key
        String key = counterSpec.getKeyFields();
        //获取计数的时间段，此时以小时为单位计算流量
        String field = counterSpec.getFields();
        //流量增加的速度
        String value = counterSpec.getValueFields();
        // 有keyFields字段时
        if(key != null) {
            if(jedis.exists(key)) {
                if(field != null) {
                    String[] t = DateUtil.StringFormat(field);
                    if(t.length == 1) { //更新某时段的流量值（增加或降低）
                        if(jedis.hexists(key, t[0])) { //已经存在该时段，直接增加
                            if(value != null) {
                                long val = Long.parseLong(value);
                                jedis.hincrBy(key, t[0], val);
                                res = "键:" + key + "，时段：" + t[0] + "，变化了" + val + "，现在为：" + jedis.hget(key, t[0]);
                            } else {
                                res = "键:" + key + "，时段：" + t[0] + "值为：" + jedis.hget(key, t[0]);
                            }
                        } else {    //该时段还未开始计算流量，创建该时段
                            if(value != null) {
                                jedis.hset(key, t[0], value);
                                res = "设置键" + key + "，时段：" + t[0] + "，值为：" + value;
                            } else {
                                res = "没有找到当前时段数据";
                            }
                        }
                    } else if (t.length == 2) { //获取某时间段的总流量
                        String startStr = t[0];
                        String endStr = t[1];
                        SimpleDateFormat strToDate = new SimpleDateFormat("yyyyMMddHHmm");
                        Date start = strToDate.parse(startStr);
                        Date end = strToDate.parse(endStr);
                        List<DateSplitUtils.DateSplit> dateSplits = DateSplitUtils.splitDate(start, end, DateSplitUtils.IntervalType.HOUR, 1);
                        List<String> timeKeys = new ArrayList<>();
                        for(int i = 0; i < dateSplits.toArray().length; i++)
                        {
                            timeKeys.add(DateUtil.StringFormat(dateSplits.get(i).getStartDateTimeStr())[0]);
                        }
                        long total = 0;
                        for(int i = 0; i < timeKeys.size(); i++)
                        {
                            if(jedis.hexists(key, timeKeys.get(i))) {
                                total += Long.parseLong(jedis.hget(key, timeKeys.get(i)));
                            }
                        }
                        res = "键:" + key + "，时段" + startStr + "-->" + endStr + "，总和为：" + total;
                    }
                } else {    //如果时间域即不为一个具体的值也不是一个区间，就展示所有的区间和值
                    jedis.hgetAll(key).forEach((k, v) -> {
                        System.out.println("field:" + k + "，value:" + v);
                    });
                    res = "以上为该key中所有field和field的值";
                }
            } else {
                //该时段还未开始计数，此时开始计数
                if(field != null) {
                    //获取时间所在的小时时间区间
                    String[] t = DateUtil.StringFormat(field);
                    if(t.length == 1) {
                        if(value != null) {
                            //增加该区间并增加流量计数
                            jedis.hset(key, t[0], value);
                            res = "键:" + key + "，时段：" + t[0] + "，设置值为：" + value;
                        }
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
