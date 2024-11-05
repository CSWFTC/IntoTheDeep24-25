package org.firstinspires.ftc.teamcode.Tests.DITest;

/*
This file is an LED TEST FILE - Which is used to test the TelemetryEvent class
 */


import com.acmerobotics.roadrunner.Line;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.DependencyInjector;
import org.firstinspires.ftc.teamcode.Helper.EventBus.EventBus;
import org.firstinspires.ftc.teamcode.Helper.Telemetry.TelemetryEvent;

import java.util.HashSet;
import java.util.Set;

@TeleOp(name = "DI LED Test", group = "Test")

public class LEDTestDI extends LinearOpMode {
    LED frontLED_red;
    LED frontLED_green;
    TelemetryTest tester = new TelemetryTest();

    public void runOpMode() {
        DependencyInjector.register("telemetry", telemetry);

        frontLED_green = hardwareMap.get(LED.class, "front_led_green");
        frontLED_red = hardwareMap.get(LED.class, "front_led_red");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                frontLED_red.on();

                this.tester.logRedOn();
            } else {
                frontLED_red.off();

                this.tester.logRedOff();
            }
            if (gamepad1.b) {
                frontLED_green.on();
            } else {
                frontLED_green.off();
            }
        }
    }
}
