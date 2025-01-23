import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Inject;
import org.firstinspires.ftc.teamcode.Helper.DependencyInjection.Injectable;

public class BucketAction extends Injectable {
    public static class Params {
        public String bucketServoName = "bucketServo";
        public boolean bucketServoReverse = false;
        public double bucketStartPos = 0.12;   // Tucked in For Driving
        public double bucketCatchPos = 0.085;  // Catch from Beak
        public double bucketDumpPos = 0.01;    // Dump to Basket
    }

    public static Params PARAMS = new Params();

    private double targetServoPosition = 0;

    @Inject("hdwMap")
    private HardwareMap hardwareMap;

    private Servo bucketServo;

    public BucketAction() {
        super();
        bucketServo = hardwareMap.servo.get(PARAMS.bucketServoName);
        bucketServo.setDirection((PARAMS.bucketServoReverse) ? Servo.Direction.REVERSE : Servo.Direction.FORWARD);
    }

    private void MoveBucket(double position) {
        bucketServo.setPosition(position);
        targetServoPosition = position;
    }

    public void StartPosition() {
        MoveBucket(PARAMS.bucketStartPos);
    }

    public void DumpSample() {
        MoveBucket(PARAMS.bucketDumpPos);
    }

    public void PrepForCatch() {
        MoveBucket(PARAMS.bucketCatchPos);
    }
}
