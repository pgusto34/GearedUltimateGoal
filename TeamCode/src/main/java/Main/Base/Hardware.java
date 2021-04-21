package Main.Base;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvInternalCamera;

/**TODO:
 * -Add Stopper
 *      -Add Stopper motor
 *      -Add Stopper name ('stopper')
 */


/**
 * BASE base class for all code. Add any hardware on the robot here
 * as well as the config name.
 */

public class Hardware extends OpMode {

    //Drive Motors
    public DcMotor leftFront, rightFront, leftBack, rightBack;
    String leftFrontName = "leftFront", rightFrontName = "rightFront", leftBackName = "leftBack", rightBackName = "rightBack";


    //Odometry Encoders
    public DcMotor left;
    public DcMotor right;
    public DcMotor mid ;

    String leftName = leftFrontName, rightName = leftBackName, midName = rightBackName;


    //Intake Motors
    DcMotor leftIntake, rightIntake;
    String leftIntakeName = "leftIntake", rightIntakeName = "rightIntake";


    //Motor and Servo for Shooter
    public DcMotorEx flyWheel;
    String flyWheelName = "flyWheel";

    Servo feederServo;
    String feederServoName = "feeder";


    //Wobble Arm Servos
    Servo wobbleArmServo, wobbleClaw;
    String wobbleArmServoName = "wobbleArm", wobbleClawName = "wobbleClaw";


    //IMU for Gyro
    BNO055IMU imu;
    String imuName = "imu";


    //Define OpMode functions for later use since Hardware extends from OpMode
    public void init(){}

    public void start(){}

    public void loop(){}

    public void stop(){}

    public void init_loop(){}

}
