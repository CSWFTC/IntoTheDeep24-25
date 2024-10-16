package org.firstinspires.ftc.teamcode.Tests.DITest;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;

public class TelemetryTest {
    @Inject("telemetry")
    Telemetry telemetry = null;

    public TelemetryTest() throws Exception {
        DependencyInjector.inject(this);
    }

    public void logRedOn() {
        if (this.telemetry != null) {
            this.telemetry.addLine("Red Light On");
            this.telemetry.update();
        }
    }

    public void logRedOff() {
        if (this.telemetry != null) {
            this.telemetry.addLine("Red Light Off");
            this.telemetry.update();
        }
    }
}
