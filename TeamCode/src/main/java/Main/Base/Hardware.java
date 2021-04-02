package Main.Base;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvInternalCamera;

public class Hardware extends OpMode {

    //Define all motors, servos, and sensors in this class as well as their names in the config
    public DcMotor leftFront, rightFront, leftBack, rightBack;
    String leftFrontName = "leftFront", rightFrontName = "rightFront", leftBackName = "leftBack", rightBackName = "rightBack";

    public DcMotor left = rightFront;
    public DcMotor right = rightBack;
    public DcMotor mid = leftBack;

    DcMotor leftIntake, rightIntake;
    String leftIntakeName = "leftIntake", rightIntakeName = "rightIntake";

    DcMotor flyWheel;
    String flyWheelName = "flyWheel";

    Servo wobbleArmServo, wobbleClaw;
    String wobbleArmServoName = "wobbleArm", wobbleClawName = "wobbleClaw";

    Servo feederServo;
    String feederServoName = "feeder";

    BNO055IMU imu;
    String imuName = "imu";

    OpenCvInternalCamera phoneCamera;

    public void init(){}

    public void start(){}

    public void loop(){}

    public void stop(){}

    public void init_loop(){}

}
