package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
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

        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                        beginPose
                )
                        .strafeTo(
                                new Vector2d(0, 100)
                        )
                        .build()
        );

        Actions.runBlocking(
                mecanumDrive.actionBuilder(
                                beginPose
                        )
                        .strafeTo(
                                new Vector2d(10, 100)
                        )
                        .build()
        );
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
