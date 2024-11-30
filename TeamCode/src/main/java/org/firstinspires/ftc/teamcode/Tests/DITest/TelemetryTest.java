package org.firstinspires.ftc.teamcode.Tests.DITest;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class TelemetryTest extends Injectable {
    @Inject("telemetry")
    Telemetry telemetry = null;

    public void logRedOn() {
        if (areDepsNull(telemetry))
            return;
        this.telemetry.addLine("Red Light On");
        this.telemetry.update();
    }

    public void logRedOff() {
        if (areDepsNull(telemetry))
            return;
        this.telemetry.addLine("Red Light Off");
        this.telemetry.update();
    }
}
