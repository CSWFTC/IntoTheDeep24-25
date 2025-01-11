package org.firstinspires.ftc.teamcode.TeleOp.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import org.firstinspires.ftc.teamcode.Helper.GamePad;



import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Intake.Pinch;
import org.firstinspires.ftc.teamcode.Helper.PinchingMech;


@Config
@TeleOp(name = "PinchingTest", group = "Helper")
public class PinchingMechTest extends LinearOpMode {

    public PinchingMech obj1;
  //public PinchingMech obj1 = new PinchingMech(hardwareMap);
    public void runOpMode() {
        if (isStopRequested()) return;

        obj1 = new PinchingMech(hardwareMap);
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        GamePad gpIn1 = new GamePad(gamepad1, false);
        //GamePad gpIn2 = new GamePad(gamepad1, false);
        telemetry.clear();

        obj1.setPosition();

        waitForStart();

        float ServoSensitivity = 0.010f;

        while(opModeIsActive()){
            update_telemetry();
            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
               case JOYSTICK:
                 obj1.oneHandMovement(gamepad1.left_stick_y + ServoSensitivity);
            }


          //  obj1.setArm(0.45 );
           // obj1.setElbow(0.630);
         //  obj1.setArm(0.4);
         //  obj1.setElbow(0.334);
            //obj1.setElbow(0.668);
            //obj1.setElbow(0.0);

            // obj1.getArm();
            update_telemetry();

           // obj1.startingPos(0.668);
        }


        //starting position arm set at 0.1 & elbow at 0.668
    }

    private void update_telemetry(){
        telemetry.addData("Arm position", obj1.getArm());
        telemetry.addLine().addData("Elbow Postion", obj1.getEl());
        telemetry.update();

    }


}

//from the starting postion, the elbow moves down until it comes down 0.58 & then the arm moves down to 0.29

//the elbow straight is 0.45
//the arm straight is 0.630
//then