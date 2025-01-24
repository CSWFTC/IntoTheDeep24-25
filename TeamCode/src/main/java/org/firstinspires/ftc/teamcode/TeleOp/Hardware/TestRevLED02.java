/*
This file is an LED TEST FILE - Which is used to test the TelemetryEvent class
 */


package org.firstinspires.ftc.teamcode.TeleOp.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;
import org.firstinspires.ftc.teamcode.Helper.Telemetry.TelemetryEvent;

import java.util.HashSet;
import java.util.Set;

@Disabled
@TeleOp(name = "Test Rev LED 02", group = "Hardware")
public class TestRevLED02 extends LinearOpMode {
    LED frontLED_red;
    LED frontLED_green;
    EventBus eventBus = EventBus.getInstance();
    Set<String> targets = new HashSet<>();

    @Override
    public void runOpMode() {
        // Testing TelemetryEvent
        TelemetryEvent __ = TelemetryEvent.getInstance(); // sets up listener

        frontLED_green = hardwareMap.get(LED.class, "front_led_green");
        frontLED_red = hardwareMap.get(LED.class, "front_led_red");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                frontLED_red.on()
            } else {
                frontLED_red.off();
            }
            if (gamepad1.b) {
                frontLED_green.on();
            } else {
                frontLED_green.off();
            }
        }
    }
}
