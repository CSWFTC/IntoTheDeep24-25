package org.firstinspires.ftc.teamcode.Helper.Telemetry;

import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;

public class TelemetryEvent {
    private final static TelemetryEvent instance = new TelemetryEvent();
    private final EventBus eventBus = EventBus.getInstance();

    public TelemetryEvent() {
        this.subscribe();
    }

    public static TelemetryEvent getInstance() { return instance; }

    private void subscribe() {
        TelemetrySubscriber subscriber = new TelemetrySubscriber();
        this.eventBus.subscribe("push_telemetry", subscriber.getClass());
    }
}
