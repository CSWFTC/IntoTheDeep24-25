package org.firstinspires.ftc.teamcode.Auton;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.ftc.Actions;

import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ClawAction;
import org.firstinspires.ftc.teamcode.Helper.ViperSlide.ViperAction;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "AutoBlueOB", group = "RoadRunner")
public class AutoBlueOB extends LinearOpMode {

    public static class Params {
        public boolean easy = false;
    }

    public static Params PARAMS = new Params();
    private MecanumDrive drive;
    private ClawAction Roar;
    private ViperAction Tiger;

    public void runOpMode(){
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        Roar = new ClawAction(hardwareMap);
        Tiger = new ViperAction(hardwareMap);

        waitForStart();

        telemetry.addData("okay", "so code needs to push6");
        telemetry.update();

        if(PARAMS.easy){
            forward();
            toPark();
        }
        else{
            Roar.CloseGrip();
            Tiger.moveForSub();
            toLine();
            markOne();
            humanPlayer();
            GoBack();
            toPark();
            GoBack();
            Reverse();
            backToLine();
            updateTelemetry(drive.pose.position);
        }
    }

    public void toLine(){
        //beginning position: ends at the sub
        Action movePos = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-25)
                .build();
        Actions.runBlocking(new SequentialAction(movePos,Tiger.clawDropOnSub(), Roar.placeOnSub()));

        //positioned back
        Action moveBack = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-20)
                .build();
        Actions.runBlocking(moveBack);
    }

    public void markOne(){
        Action lineM1 = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .splineTo(new Vector2d(-25, 38), Math.toRadians(180))
                .build();
        Actions.runBlocking(lineM1);
    }

    public void humanPlayer(){
        Action Player = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-20)
                .build();
        Actions.runBlocking(Player);
    }

    public void GoBack(){
        Action back = drive.actionBuilder(drive.pose)
                .setReversed(false)
                .lineToX(-20)
                .build();
        Actions.runBlocking(back);
    }
    public void Reverse(){
        Action turnAgain = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .turnTo(260)
                .build();
        Actions.runBlocking(turnAgain);
    }

    public void backToLine(){
        Action backAgain = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineTo(new Vector2d(-27, 0), Math.toRadians(180))
                .build();
        Actions.runBlocking(new SequentialAction(backAgain, Roar.placeOnSub()));
    }

    private void toPark(){
        //basket
        Action moveBasket= drive.actionBuilder(drive.pose)
                .setReversed(true)
                // .splineTo(new Vector2d(-12, -48), Math.toRadians(-20))
                .strafeTo(new Vector2d(-2,48))
                .build();
        Actions.runBlocking(new SequentialAction(moveBasket, Roar.grabFromHuman()));

    }

    private void forward(){
        Action moveOut = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .lineToX(-4)
                .build();
        Actions.runBlocking(moveOut);
    }

    private void updateTelemetry(Vector2d pos) {
        telemetry.addLine("RoadRunner Auto Drive BLUE");
        telemetry.addData("Current x Position", pos.x );
        telemetry.addData("Current y Postion", pos.y);
        telemetry.update();

    }



}
