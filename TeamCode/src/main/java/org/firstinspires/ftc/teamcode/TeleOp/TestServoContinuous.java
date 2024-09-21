package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "Continuous Servo Test", group = "Test")
public class TestServoContinuous extends  LinearOpMode {
    public static class Params {
        public String servoName = "roti";
        public double servoForward = 1;
        public double servoStartPos = 0.5d;
    }

    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private FtcDashboard dashboard;
    private CRServo servo;

    private int iter = 0;

    private boolean tlmServoForward = false;
//    private double newPosition = 0;

    private double turnValue = 0.0f;


    @Override
    public void runOpMode() {
        FtcDashboard.getInstance().clearTelemetry();
        boolean init = initialize();
        waitForStart();
        if (isStopRequested() || !init) {
            return;
        }
        telemetry.clear();

        tlmServoForward = (PARAMS.servoForward != 0);
//        servo.setDirection(tlmServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

        while (opModeIsActive()) {
//            update_telemetry();

            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    this.turnValue = 1.0f;
                    updateServo();
                    break;
                case BUTTON_Y:
                    this.turnValue = -1.0f;
                    updateServo();
                    break;
                case DPAD_DOWN:
                    if (this.turnValue > -1.0f) {
                        this.turnValue -= 0.1f;
                    }
                    updateServo();
                    break;
                case DPAD_UP:
                    if (this.turnValue < 1.0f) {
                        this.turnValue += 0.1f;
                    }
                    updateServo();
                    break;
            }
        }
    }

    // update continuous servo based on turnValue
    private void updateServo() {
        this.iter += 1;
        servo.setPower(this.turnValue);
        telemetry.addLine("`*** SERVO UPDATED ***"+" ITERATION: "+" "+Integer.toString(this.iter));
        telemetry.addLine(Double.toString(this.turnValue));
        telemetry.update();
//        update_telemetry();
    }

    public boolean initialize() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Servo Test");
        telemetry.addLine();
        telemetry.addLine("*** Test Telemetry v0.1 ***");
        telemetry.addLine();
        telemetry.addLine(PARAMS.servoName);
        telemetry.addLine();
        telemetry.update();

        // Initialize Helpers
        try {
            servo = hardwareMap.get(CRServo.class, PARAMS.servoName);
            gpInput = new GamePad(gamepad1);
            dashboard = FtcDashboard.getInstance();
//            dashboard.clearTelemetry();
            telemetry.addLine("All Sensors Initialized");
            telemetry.addLine("");
            telemetry.addData(">", "Press Play to Start");
            telemetry.update();
            return (true);
        } catch (Exception e) {
            telemetry.addLine("");
            telemetry.addLine("*** INITIALIZATION FAILED ***");
            telemetry.addLine("*** ERROR LOGS: ***");
            telemetry.addLine(e.toString());
            telemetry.update();
            return (false);
        }
    }

    private void update_telemetry() {
        telemetry.addLine("Servo Test");
        telemetry.addLine("*** SERVO UPDATED ***");
        telemetry.addLine(Double.toString(this.turnValue));
        telemetry.addLine("Use Dpad to Change Rotational Speed");
        telemetry.addLine("  Up/Down    +/- 0.1");
        telemetry.addLine("Button A --> Spin Right");
        telemetry.addLine("Button B --> Spin Left");
        telemetry.addLine().addData("Name     ", PARAMS.servoName );
        telemetry.addLine().addData("Direction", (tlmServoForward ? "Forward" : "Reverse") );
        telemetry.update();

        // FTC Dashboard Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.servoName );
        packet.put("Direction", (tlmServoForward ? 1 : 0));
        packet.put("TurnValue", this.turnValue);
        dashboard.sendTelemetryPacket(packet);
    }
}
