package org.firstinspires.ftc.teamcode.Helper.Telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Subscriber;

public class TelemetrySubscriber implements Subscriber<TelemetrySubscriberResult, Void> {
    public TelemetrySubscriber() {}

    @Override
    public TelemetrySubscriberResult onMessage(Object ... params) {
        if (params.length > 0 && params[0] instanceof Telemetry)  {
            Telemetry telemetry = (Telemetry) params[0];
            if (params.length > 1 && params[1] instanceof String) {
                String[] logs = ((String) params[1]).split("\n");
                try {
                    for (String log : logs) {
                        telemetry.addLine(log);
                    }
                    telemetry.update();
                } catch(Exception e) {
                    return TelemetrySubscriberResult.ITERATION_ERROR;
                }
            } else {
                return TelemetrySubscriberResult.NO_STRING_PROVIDED;
            }
        } else {
            return TelemetrySubscriberResult.NO_TELEMETRY_OBJECT_PROVIDED;
        }
        return TelemetrySubscriberResult.SUCCESS;
    }

    @Override
    public Void onMessage() {
        return null;
    }
}
