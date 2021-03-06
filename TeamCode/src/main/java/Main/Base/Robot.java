package Main.Base;


import com.qualcomm.hardware.bosch.BNO055IMU;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;


import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.HashMap;


import Main.Base.HelperClasses.Button;
import Main.Base.RobotUtilities.Camera;
import Main.Base.RobotUtilities.Gyro;
import Main.Base.RobotUtilities.Intake;
import Main.Base.RobotUtilities.Shooter;
import Main.Base.RobotUtilities.Stopper;
import Main.Base.RobotUtilities.WheelBase;
import Main.Base.RobotUtilities.WobbleArm;




/** Base Class for TeleOp code
 * Initalizes Robot Utilities and initial values
 * that all TeleOp code uses. Use the hardware names from
 * the Hardware class in initialization. Just extend from
 * this class and override the 'loop()' function to have
 * a working teleOp OpMode
 */

public class Robot extends Hardware{

    //Defines Robot Utilities
    public static Gamepad gp;

    public Camera camera;
    public Gyro gyro;
    public WheelBase wheelBase;
    public Shooter shooter;
    public WobbleArm wobbleArm;
    public Intake intake;
    public Stopper stopper;

    public HashMap<Button, Boolean> buttonChecker = new HashMap<Button, Boolean>();


    @Override
    public void init() {

        gp = gamepad1;


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvInternalCamera phoneCamera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        camera = new Camera(phoneCamera);


        imu = hardwareMap.get(BNO055IMU.class, imuName);

        gyro = new Gyro(imu);


        leftFront = hardwareMap.dcMotor.get(leftFrontName);
        leftBack = hardwareMap.dcMotor.get(leftBackName);
        rightBack = hardwareMap.dcMotor.get(rightBackName);
        rightFront = hardwareMap.dcMotor.get(rightFrontName);

        left = hardwareMap.dcMotor.get(leftName);
        right = hardwareMap.dcMotor.get(rightName);
        mid = hardwareMap.dcMotor.get(midName);

        wheelBase = new WheelBase(leftFront, leftBack, rightFront, rightBack);


        leftIntake = hardwareMap.dcMotor.get(leftIntakeName);
        rightIntake = hardwareMap.dcMotor.get(rightIntakeName);

        intake = new Intake(leftIntake, rightIntake);


        flyWheel = hardwareMap.get(DcMotorEx.class, flyWheelName);
        feederServo = hardwareMap.servo.get(feederServoName);

        shooter = new Shooter(flyWheel, feederServo);


        wobbleArmServo = hardwareMap.servo.get(wobbleArmServoName);
        wobbleClaw = hardwareMap.servo.get(wobbleClawName);

        wobbleArm = new WobbleArm(wobbleArmServo, wobbleClaw);

        stopperServo = hardwareMap.servo.get(stopperServoName);
        stopper = new Stopper(stopperServo);


    }

}
