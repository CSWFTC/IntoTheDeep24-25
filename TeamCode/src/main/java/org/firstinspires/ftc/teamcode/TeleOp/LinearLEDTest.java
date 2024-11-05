/*
This file is an LED TEST FILE - Which is used to test the TelemetryEvent class
 */


package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;
import org.firstinspires.ftc.teamcode.Helper.Telemetry.TelemetryEvent;

import java.util.HashSet;
import java.util.Set;

@TeleOp(name = "Concept: RevLED 02", group = "Test")

public class LinearLEDTest extends LinearOpMode {
    LED frontLED_red;
    LED frontLED_green;
    EventBus eventBus = EventBus.getInstance();
    Set<String> targets = new HashSet<>();

    public void runOpMode() {
        // Testing TelemetryEvent
        TelemetryEvent __ = TelemetryEvent.getInstance(); // sets up listener

        this.targets.add("push_telemetry");

        frontLED_green = hardwareMap.get(LED.class, "front_led_green");
        frontLED_red = hardwareMap.get(LED.class, "front_led_red");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                frontLED_red.on();
                this.eventBus.emit(this.targets, telemetry, "Red Light Status \n ON");

            } else {
                frontLED_red.off();
                this.eventBus.emit(this.targets, telemetry, "Red Light Status \n OFF");
            }
            if (gamepad1.b) {
                frontLED_green.on();
                this.eventBus.emit(this.targets, telemetry, "Green Light Status \n ON");
            } else {
                frontLED_green.off();
                this.eventBus.emit(this.targets, telemetry, "Green Light Status \n OFF");
            }
        }
    }
}
