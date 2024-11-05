package org.firstinspires.ftc.teamcode.Helper.Telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Parser.ParamsParser;
import org.firstinspires.ftc.teamcode.Helper.EventBus.Subscriber;

public class TelemetrySubscriber implements Subscriber<TelemetrySubscriberResult, Void> {
    public TelemetrySubscriber() {}

    @Override
    public TelemetrySubscriberResult onMessage(Object ... params) {
        DependencyInjector.register("parser_params", params);
        ParamsParser parser = new ParamsParser();
        DependencyInjector.unregister("parser_params");

        Telemetry telemetry = parser.getParam(0, Telemetry.class);
        String logString = parser.getParam(1, String.class);

        if (telemetry == null || logString == null) {
            System.out.println("Error: Missing required parameters.");
            return TelemetrySubscriberResult.NO_TELEMETRY_OBJECT_PROVIDED;
        }

        String[] logs = (logString).split("\n");
        try {
            for (String log : logs) {
                telemetry.addLine(log);
            }
            telemetry.update();
        } catch(Exception e) {
            return TelemetrySubscriberResult.ITERATION_ERROR;
        }

        return TelemetrySubscriberResult.SUCCESS;
    }

    @Override
    public Void onMessage() {
        return null;
    }
}
