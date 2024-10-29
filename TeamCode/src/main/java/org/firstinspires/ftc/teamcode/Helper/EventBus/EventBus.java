package org.firstinspires.ftc.teamcode.Helper.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
Refer to MD file for instructions on how to use this
 */

public class EventBus {
    private static final EventBus instance = new EventBus();

    private final Map<String, Class<? extends Subscriber<?,?>>> subscribers = new HashMap<>();

    public EventBus() {

    }

    public static EventBus getInstance() {
        return instance;
    }

    public void subscribe(String subId, Class<? extends Subscriber<?, ?>> sub) {
        subscribers.computeIfAbsent(subId, k -> sub);
    }


    public void unsubscribe(String subId) {
        subscribers.remove(subId);
    }

    public void emit(Set<String> events, Object ... params) {
        for (Map.Entry<String, Class<? extends Subscriber<?, ?>>> entry : this.subscribers.entrySet()) {
            String subId = entry.getKey();
            Class<? extends Subscriber<?, ?>> subscriberClass = entry.getValue();

            if (events.contains(subId)) {
                try {
//                    Subscriber<?> subscriber = subscriberClass.getDeclaredConstructor().newInstance();
                    Subscriber<?,?> subscriber = subscriberClass.newInstance();

                    subscriber.onMessage(params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
