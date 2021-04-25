package Main.Base;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

import Main.Base.RobotUtilities.Camera;
import Main.Base.RobotUtilities.Gyro;
import Main.Base.RobotUtilities.Intake;
import Main.Base.RobotUtilities.Odometry.Odometer;
import Main.Base.RobotUtilities.Shooter;
import Main.Base.RobotUtilities.WheelBase;
import Main.Base.RobotUtilities.WobbleArm;


/** Base Class for Auto code
 * Initalizes Robot Utilities and initial values
 * that all Auto code uses. Use the hardware names from
 * the Hardware class in initialization. Defines threads that runs during auto.
 * Runs 'runAutonomous()' function on 'start()'. Simply extend 'AutoRobot"
 * and override the 'runAutonomous()' function to create an
 * Autonomous OpMode or just copy the 'TemplateAuto' class.
 */


public abstract class AutoRobot extends Hardware {

    //Defines Robot Utilities
    public Camera camera;
    public Gyro gyro;
    public WheelBase wheelBase;
    public Shooter shooter;
    public WobbleArm wobbleArm;
    public Intake intake;
    public Odometer odometry;

    //Position Thread for updating Robot Position through Odometry. Runs on Init.
    public Thread positionThread;

    //Position Thread for Running Auto. Runs on Start.
    protected Thread autoThread = new Thread(new Runnable() {
        @Override
        public void run() {
            runAutonomous();
        }
    });

    //Variable for determining # of Rings
    public int rings = -1;


    @Override
    public void init() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvInternalCamera phoneCamera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        camera = new Camera(phoneCamera);


        imu = hardwareMap.get(BNO055IMU.class, imuName);

        gyro = new Gyro(imu);


        leftFront = hardwareMap.dcMotor.get(leftFrontName);
        leftBack = hardwareMap.dcMotor.get(leftBackName);
        rightBack = hardwareMap.dcMotor.get(rightBackName);
        rightFront = hardwareMap.dcMotor.get(rightFrontName);

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


        left = hardwareMap.dcMotor.get(leftName);
        right = hardwareMap.dcMotor.get(rightName);
        mid = hardwareMap.dcMotor.get(midName);

        odometry = new Odometer(left, right, mid,  50);


        //Starts Position Thread
        positionThread = new Thread(odometry);
        positionThread.start();

        //Starts Camera Streaming
        camera.startStreaming();

    }


    //Displays # of Rings after Init
    @Override
    public void init_loop() {

        rings = camera.detectRings();
        telemetry.addData("Rings: ", rings);
        telemetry.update();

    }


    //Starts AutoThread
    @Override
    public void start() {
        autoThread.start();
    }


    //Displays Robot Position During Loop
    @Override
    public void loop() {

        telemetry.addData("X Position", odometry.returnXCoordinateInches());
        telemetry.addData("Y Position", odometry.returnYCoordinateInches());
        telemetry.addData("Orientation (Degrees)", gyro.getHeading());
        telemetry.update();

    }


    //Stops autoThread on Stop
    @Override
    public void stop() {

        try {
            autoThread.join();
            autoThread.interrupt();
        } catch (Exception e) {
            telemetry.addData("ENCOUNTERED AN EXCEPTION", e);
        }

    }


    //Pauses Robot during Autonomous (in milliseconds)
    protected void pause(long millis) {
        long maxTime;
        maxTime = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() < maxTime && !autoThread.isInterrupted()) {
        }
    }


    //Overridable Function to Write Auto in
    public abstract void runAutonomous();

}
