package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Config
@Autonomous
public class TestRRNavigation extends LinearOpMode {
    private Pose2d beginPose = new Pose2d(0, 0, 0);

    @Override
    public void runOpMode() {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        Actions.runBlocking(
            new SequentialAction(
                    mecanumDrive.actionBuilder(beginPose)
                            .lineToX(80d)
                            .build(),
                    mecanumDrive.actionBuilder(new Pose2d(80d, 0d, 0d))
                        .turnTo(Math.toRadians(90d))
                        .build()
            )
        );

//        Actions.runBlocking(
//                mecanumDrive.actionBuilder(beginPose)
//                        .strafeTo(
//                                new Vector2d(50d, 0d)
//                        )
//                        .build()
//        );
    }
}
