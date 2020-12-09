package com.eryo.redis;

import java.util.List;

/**
 * @author 18301092-陈佳林
 */
public class ActionSpec {

    //在执行动作前的展示对应数据的内容
    private List<CounterConfig> retrieve;
    //执行操作
    private List<CounterConfig> save;

    public List<CounterConfig> getRetrieve() {
        return retrieve;
    }

    public List<CounterConfig> getSave() {
        return save;
    }

    public void setRetrieve(List<CounterConfig> retrieve) {
        this.retrieve = retrieve;
    }

    public void setSave(List<CounterConfig> save) {
        this.save = save;
    }

    public ActionSpec(List<CounterConfig> retrieve, List<CounterConfig> save) {
        this.retrieve = retrieve;
        this.save = save;
    }

    @Override
    public String toString() {
        return "ActionSpec{" +
                "retrieve=" + retrieve +
                ", save=" + save +
                '}';
    }
}
