package com.xcs.server.opc.memory.impl;

import com.xcs.server.opc.data.Value;
import com.xcs.server.opc.memory.*;

import java.util.*;

public abstract class AbstractDataMemory implements DataMemory {

    protected Set<String> changedPoints = new HashSet<>();

    private Set<DataSubscriber> subscribers = new HashSet<>();
    private Map<DataSubscriber, PushTask> taskMap = new HashMap<>();

    private Timer timer = new Timer(true);

    private PushTask task;

    @Override
    public void addSubscriber(DataSubscriber subscriber, int pushInterval) {
        subscribers.add(subscriber);
        task = new PushTask(subscriber);
        taskMap.put(subscriber, task);
        timer.schedule(task, pushInterval, pushInterval);
    }

    @Override
    public void removeSubscriber(DataSubscriber subscriber) {
        subscribers.remove(subscriber);
        taskMap.get(subscriber).cancel();
        taskMap.remove(subscriber);
        timer.purge();
    }

    @Override
    public void put(String key, Value value) {
        if (!value.equals(get(key)))
            addChangedPoint(key);
    }

    private synchronized void addChangedPoint(String point) {
        changedPoints.add(point);
    }

//    private synchronized void clearChangedPoints() {
//        changedPoints.clear();
//    }


    protected abstract ValueMap getAllDatas();

    protected abstract ValueMap getChangedDatas();

    protected abstract ValueMap getRequiredDatas(Set<String> points);

    public class PushTask extends TimerTask {

        private DataSubscriber subscriber;

        public PushTask(DataSubscriber subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void run() {
            DataSubscriber.Strategy strategy = subscriber.getStrategy();
            if (strategy.equals(DataSubscriber.Strategy.ALL)) {
                subscriber.onReceived(getAllDatas());
            } else if (strategy.equals(DataSubscriber.Strategy.CHANGED)) {
                subscriber.onReceived(getChangedDatas());
            } else if (strategy.equals(DataSubscriber.Strategy.REQUIRED)) {
                RequiredDataSubscriber s = (RequiredDataSubscriber) subscriber;
                s.onReceived(getRequiredDatas(s.getRequiredPoints()));
            }
        }
    }
}
