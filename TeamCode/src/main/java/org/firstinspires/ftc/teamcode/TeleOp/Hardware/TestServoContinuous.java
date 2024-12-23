package org.firstinspires.ftc.teamcode.TeleOp.Hardware;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp(name = "Test Continuous Servo", group = "Hardware")
public class TestServoContinuous extends  LinearOpMode {
    public static class Params {
        public String servoName = "roti";
        public double servoForward = 1;
        public double servoStartPos = 0.5d;
    }

    // Enum for telemetry which shows what type of button press was detected
    private enum ServoUpdateEnum {
        BUTTON_A,
        BUTTON_B,
        DPAD_UP,
        DPAD_DOWN
    }

    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private FtcDashboard dashboard;
    private CRServo servo;

    // button press count
    private int iter = 0;

    // initial turn value (neutral)
    private double turnValue = 0.0f;

    @Override
    public void runOpMode() {
        // clear telemetry
        FtcDashboard.getInstance().clearTelemetry();

        // init servo
        boolean init = initialize();
        waitForStart();
        if (isStopRequested() || !init) {
            return;
        }
        telemetry.clear();

        // main loop
        while (opModeIsActive()) {
            GamePad.GameplayInputType inputType = gpInput.WaitForGamepadInput(100);
            switch (inputType) {
                case BUTTON_A:
                    this.turnValue = 1.0f;
                    updateServo(ServoUpdateEnum.BUTTON_A);
                    break;
                case BUTTON_Y:
                    this.turnValue = -1.0f;
                    updateServo(ServoUpdateEnum.BUTTON_B);
                    break;
                case DPAD_DOWN:
                    if (this.turnValue > -1.0f) {
                        this.turnValue -= 0.1f;
                    }
                    updateServo(ServoUpdateEnum.DPAD_DOWN);
                    break;
                case DPAD_UP:
                    if (this.turnValue < 1.0f) {
                        this.turnValue += 0.1f;
                    }
                    updateServo(ServoUpdateEnum.DPAD_UP);
                    break;
            }
        }
    }

    // update continuous servo based on turnValue
    private void updateServo(ServoUpdateEnum servoUpdateEnum) {
        this.iter += 1;
        servo.setPower(this.turnValue);
        telemetry.addLine("Button Pressed:"+" "+servoUpdateEnum.toString());
        telemetry.addLine("Control Press Count: "+Integer.toString(this.iter));
//        telemetry.addLine("\n\n");
        telemetry.addLine();
        // output additional telemetry
        update_telemetry();
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
        telemetry.addLine("Use Dpad to Change Rotational Speed");
        telemetry.addLine("  Up/Down    +/- 0.1");
        telemetry.addLine("Button A --> Spin Right");
        telemetry.addLine("Button B --> Spin Left");
        telemetry.addLine().addData("Name     ", PARAMS.servoName );
        telemetry.update();

        // FTC Dashboard Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.servoName );
        packet.put("TurnValue", this.turnValue);
        dashboard.sendTelemetryPacket(packet);
    }
}
