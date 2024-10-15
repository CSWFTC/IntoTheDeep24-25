package org.firstinspires.ftc.teamcode.Tests;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Config
@Autonomous
public class TestRoadRunner extends LinearOpMode {
//    private MecanumDrive mecanumDrive = new MecanumDrive();
    private Pose2d beginPose = new Pose2d(0, 0, 0);


    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, beginPose);


        waitForStart();

        telemetry.addLine("v0.1");
        telemetry.update();

        telemetry.addLine(mecanumDrive.pose.heading.toString());
        telemetry.update();

        Action moveRb3 = mecanumDrive.actionBuilder(mecanumDrive.pose)
                .splineTo(new Vector2d(33, 40.0), Math.toRadians(-89), new VelConstraint() {
                    @Override
                    public double maxRobotVel(@NonNull Pose2dDual<Arclength> pose2dDual, @NonNull PosePath posePath, double v) {
                        return 1;
                    }
                })
                .build();
        Actions.runBlocking(moveRb3);


        telemetry.addLine(mecanumDrive.pose.heading.toString());
        telemetry.update();

        /*
        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                        beginPose
                )
                        .turnTo(90d)
                        .build()
        );
        */

        try {
            wait(1000);
        } catch(Exception e) {
        //
        }

        /*
        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                                beginPose
                        )
                        .turnTo(360d)
                        .build()
        );
        */

//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(
//                        beginPose
//                )
//                        .strafeTo(
//                                new Vector2d(0, 100)
//                        )
//                        .build()
//        );
//
//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(
//                                beginPose
//                        )
//                        .strafeTo(
//                                new Vector2d(10, 100)
//                        )
//                        .build()
//        );
//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(
//                                beginPose
//                        )
//                        .lineToY(
//                                200
//                        )
//                        .build()
//        );
    }
}
