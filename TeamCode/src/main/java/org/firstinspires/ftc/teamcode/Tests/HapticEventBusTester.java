/*
THIS IS SOLELY A TEST CLASS FOR EVENTBUS
 */

package org.firstinspires.ftc.teamcode.Tests;

import org.firstinspires.ftc.teamcode.Helper.EventBus;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.HapticLogs;
import org.firstinspires.ftc.teamcode.Helper.Subscriber;

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
}
