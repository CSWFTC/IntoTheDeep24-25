package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "Continuous Servo Test", group = "Test")
public class TestServoContinuous extends  LinearOpMode {
    public static class Params {
        public String servoName = "DroneServo";
        public double servoForward = 1;
        public double servoStartPos = 0d;
    }

    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private FtcDashboard dashboard;
    private Servo servo;

    private boolean tlmServoForward = false;
    private double tlmServoPosition = 0;
    private double newPosition = 0;

    private double turnValue = 0.6f;


    @Override
    public void runOpMode() {
        boolean init = initialize();
        waitForStart();
        if (isStopRequested() || !init) {
            return;
        }
        telemetry.clear();

        tlmServoForward = (PARAMS.servoForward != 0);
        servo.setDirection(tlmServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

        // Move Servo to initial Position
        newPosition = PARAMS.servoStartPos;
        servo.setPosition(newPosition);
        tlmServoPosition = newPosition;



        while (opModeIsActive()) {
            update_telemetry();

            // turnValue based conditional
            if (this.turnValue > 0.5) {
                // turns position based on the last setPosition command sent to servo
                servo.setPosition(servo.getPosition()+10);
            } else {
                servo.setPosition(servo.getPosition()-10);
            }

            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                        this.turnValue = 0.6f;
                    break;
                case BUTTON_Y:
                    this.turnValue = 0.4f;
                    break;
            }
        }
    }

    public boolean initialize() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Servo Test");
        telemetry.addLine();

        // Initialize Helpers
        try {
            servo = hardwareMap.servo.get(PARAMS.servoName);
            // gamepad disabled
            gpInput = new GamePad(gamepad1);
            dashboard = FtcDashboard.getInstance();
            dashboard.clearTelemetry();

            telemetry.addLine("All Sensors Initialized");
            telemetry.addLine("");
            telemetry.addData(">", "Press Play to Start");
            telemetry.update();
            return (true);
        } catch (Exception e) {
            telemetry.addLine("");
            telemetry.addLine("*** INITIALIZATION FAILED ***");
            telemetry.update();
            return (false);
        }
    }

    private void update_telemetry() {
        telemetry.addLine("Servo Test");
//        telemetry.addLine("Use Dpad to Set Position");
//        telemetry.addLine("  Up/Down    +/- 0.1");
//        telemetry.addLine("  Left/Right +/- 0.005");
//        telemetry.addLine("Button A --> GoTo New Position");
//        telemetry.addLine("Left Bumper --> Change Direction/n");
        telemetry.addLine().addData("Name     ", PARAMS.servoName );
        telemetry.addLine().addData("Direction", (tlmServoForward ? "Forward" : "Reverse") );
        telemetry.addLine().addData("Curr Pos ", tlmServoPosition );
        telemetry.addLine().addData("New Pos ", newPosition );
        telemetry.update();

        // FTC Dashboard Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.servoName );
        packet.put("Direction", (tlmServoForward ? 1 : 0));
        packet.put("Curr Position", tlmServoPosition);
        packet.put("New Position", newPosition);
        dashboard.sendTelemetryPacket(packet);
    }
}
