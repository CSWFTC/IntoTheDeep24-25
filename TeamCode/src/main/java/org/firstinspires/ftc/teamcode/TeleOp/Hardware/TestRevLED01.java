package org.firstinspires.ftc.teamcode.TeleOp.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;

/*This OpMode assumes that the REV Digital Indicator is setup as 2 Digital Channels named
 * front_led_green and front_led_red. (the green should be the lower of the 2 channels it is plugged
 into and the red should be the higher)
*/

@Disabled
@TeleOp(name = "Test LED REV 01", group = "Test")
public class TestRevLED01 extends OpMode {
    LED frontLED_red;
    LED frontLED_green;

    public void init() {
        frontLED_green = hardwareMap.get(LED.class, "front_led_green");
        frontLED_red = hardwareMap.get(LED.class, "front_led_red");
    }

    public void loop() {
        if (gamepad1.a) {
            frontLED_red.on();
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