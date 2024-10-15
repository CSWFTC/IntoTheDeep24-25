package org.firstinspires.ftc.teamcode.Helper;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/*
example use:

use EventBus.getInstance() to retrieve the singleton instance of the EventBus

WARNING: ALL Subscriber classes MUST BE PUBLIC

// you can edit return of onMessage when instantiating
public class TestSub implements Subscriber<Void> {
    @Override
    public Void onMessage() {
        // add ur onMessage code here

        return null;
    }

    @Override
    public Void onMessage(Object ... params) {
        // add onMessage code with params

        return null;
    }
}

// for parameters in the Subscriber extension:
public class SubWithParams implements Subscriber<String> {
    @Override
    public String onMessage() {
        // add ur onMessage code here

        return null;
    }

    @Override
    public String onMessage(Object ... params) {
        // add onMessage code with params

        return null;
    }
}

// add a subscription of id 'telemetry'
EventBus.getInstance().subscribe("telemetry", TestSub.class);

// emitting a new event
Set<String> targets = new HashSet<>();
targets.add("telemetry"); // add a new string to `targets` for each subId you want to activate
EventBus.getInstance().emit(targets);

 */

public class EventBus {
    private static final EventBus instance = new EventBus();

    private final Map<String, Class<? extends Subscriber<?>>> subscribers = new HashMap<>();

    public EventBus() {

    }

    public static EventBus getInstance() {
        return instance;
    }

    public void subscribe(String subId, Class<? extends Subscriber<?>> sub) {
        subscribers.computeIfAbsent(subId, k -> sub);
    }

    public void unsubscribe(String subId) {
        subscribers.remove(subId);
    }

    public void emit(Set<String> events, Object ... params) {
        for (Map.Entry<String, Class<? extends Subscriber<?>>> entry : this.subscribers.entrySet()) {
            String subId = entry.getKey();
            Class<? extends Subscriber<?>> subscriberClass = entry.getValue();

            if (events.contains(subId)) {
                try {
//                    Subscriber<?> subscriber = subscriberClass.getDeclaredConstructor().newInstance();
                    Subscriber<?> subscriber = subscriberClass.newInstance();

                    subscriber.onMessage(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
