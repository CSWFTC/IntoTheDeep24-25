package org.firstinspires.ftc.teamcode.Helper;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Helper.PinchingMech;


@TeleOp(name = "PinchingTest", group = "Helper")
public class PinchingMechTest extends LinearOpMode {

    private PinchingMech obj1;
    public void runOpMode() {
        obj1.setArm(0);
        //obj1.setElbow(0);


    }

}
