package com.eryo.redis;

/**
 * @author 18301092-陈佳林
 *  计数器的实体类，保存着操作的具体信息
 */
public class CounterSpec {

    //计数器的名称
    private String counterName;
    private String counterIndex;
    //操作的数据的类型
    private String type;
    //redis中键值对的key
    private String keyFields;
    private String fields;
    //数值型增加的幅度，对于列表和集合是加入到列表或集合之中的值
    private String valueFields;
    private int maxSize;
    //过期时间
    private int expireTime;

    public String getCounterName() {
        return counterName;
    }

    public String getCounterIndex() {
        return counterIndex;
    }

    public String getType() {
        return type;
    }

    public String getKeyFields() {
        return keyFields;
    }

    public String getValueFields() {
        return valueFields;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public void setCounterIndex(String counterIndex) {
        this.counterIndex = counterIndex;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKeyFields(String keyFields) {
        this.keyFields = keyFields;
    }

    public void setValueFields(String valueFields) {
        this.valueFields = valueFields;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "CounterSpec{" +
                "counterName='" + counterName + '\'' +
                ", counterIndex='" + counterIndex + '\'' +
                ", type='" + type + '\'' +
                ", keyFields='" + keyFields + '\'' +
                ", fields='" + fields + '\'' +
                ", valueFields='" + valueFields + '\'' +
                ", maxSize=" + maxSize +
                ", expireTime=" + expireTime +
                '}';
    }
}
