package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "AutoBlueOB", group = "RoadRunner")
public class AutoBlueOB extends LinearOpMode {

    public static class Params {

    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;

    public void runOpMode(){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        telemetry.addData("okay", "so code needs to push6");
        telemetry.update();

        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
                        .setReversed(true)
                        .lineToX(-44.5)
                        //hang a specimen
                        .setReversed(false)
                        .lineToX(-20)
                        .turnTo(Math.toRadians(140))
                        .setReversed(true)
                        .lineToY(16)
                        .splineTo(new Vector2d(-45, 41), Math.toRadians(0))
                        //grab a sample
                        .turnTo(140)
                        //.lineToX()
                        //.strafeTo(new Vector2d(51, 15))
                        .build()
        );
    }


}
