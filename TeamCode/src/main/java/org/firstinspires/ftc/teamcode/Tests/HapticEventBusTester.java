/*
THIS IS SOLELY A TEST CLASS FOR EVENTBUS
 */

package org.firstinspires.ftc.teamcode.Tests;


import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Subscriber;
import org.firstinspires.ftc.teamcode.Helper.EventBus.SubscriberUtils;

public class HapticEventBusTester {

    EventBus eventBus = EventBus.getInstance();

    public HapticEventBusTester() {
        this.subscribe();
    }

    private static final HapticEventBusTester instance = new HapticEventBusTester();

    public static HapticEventBusTester getInstance() {
        return instance;
    }

    private void subscribe() {
        HapticEvent hapticEvent = new HapticEvent();
        this.eventBus.subscribe("haptic", hapticEvent.getClass());
    }

    public void genTest() {
        Subscriber<Void, String> generated = SubscriberUtils.generateSubscriberClass(
                params -> {
                    return null;
                }
                ,
                () -> {
                    return "Hello";
                }
        );
        generated.getClass();

        this.eventBus.subscribe("gen", (Class<? extends Subscriber<?, ?>>) generated.getClass());
    }
}