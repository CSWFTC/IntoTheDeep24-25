package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;


import org.firstinspires.ftc.teamcode.Helper.Intake.Pinch;
import org.firstinspires.ftc.teamcode.Helper.PinchingMech;


@Config
@TeleOp(name = "PinchingTest", group = "Helper")
public class PinchingMechTest extends LinearOpMode {

    public PinchingMech obj1;
  //public PinchingMech obj1 = new PinchingMech(hardwareMap);
    public void runOpMode() {
        waitForStart();

        obj1 = new PinchingMech(hardwareMap);
        telemetry.clear();

      //  intialize();
        while(opModeIsActive()){
            telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
            update_telemetry();

           obj1.setArm(0);
            //obj1.setElbow(0.668);
          //  obj1.setElbow(0.0);

          //  obj1.getArm();
            update_telemetry();

           // obj1.startingPos(0.668);
        }
    }

    private void update_telemetry(){
        telemetry.addData("Arm position", obj1.getArm());
        telemetry.addLine().addData("Elbow Postion", obj1.getEl());

        telemetry.update();

    }


}
