package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import Main.Base.RobotUtilities.Odometry.Odometer;

import static java.lang.Math.PI;
import static java.lang.Math.abs;


public class WheelBase {

    //We are using four drive train motors and a mecanum drive

    /**  lF  |-----|  rF

         lB  |-----|  rB**/

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    Double lF, rF, lB, rB, maxVector;

    static final double SLOMO_DIVIDER = 2.5;

    static final double ROBOT_DIAMETER = 20;

    static final double TICKS_PER_REVOLUTION = 8192;
    static final double WHEEL_DIAMETER = 1.45;
    static final double TICKS_PER_INCH = (TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI));


    public WheelBase(DcMotor lF, DcMotor lB, DcMotor rF, DcMotor rB) {

        leftFront = lF;
        leftBack = lB;
        rightFront = rF;
        rightBack = rB;

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setModeAll(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setZeroPowerBehaviorAll(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    //Robot Centric Mecanum Drive
    public void mecanumDrive(double leftX, double leftY, double rightX, boolean slomo) {

        lF = -leftX - leftY - rightX;
        rF = -leftX + leftY - rightX;
        lB = -leftX + leftY + rightX;
        rB = -leftX - leftY + rightX;


        maxVector = Math.max(Math.max(Math.abs(lF), Math.abs(rF)),
                Math.max(Math.abs(lB), Math.abs(rB)));

        maxVector = maxVector > 1 ? maxVector : 1;

        lF /= maxVector;
        rF /= maxVector;
        lB /= maxVector;
        rB /= maxVector;

        if(slomo) setMotorPowers(lF/SLOMO_DIVIDER, lB/SLOMO_DIVIDER, rF/SLOMO_DIVIDER, rB/SLOMO_DIVIDER);
        else setMotorPowers(lF, lB, rF, rB);

    }


    //Field Centric Mecanum Drive
    public void fieldOrientatedDrive (Gamepad gamepad, Gyro gyro, boolean slomo, boolean reverse) {

        //Transforms inputs based on current robot heading
        double y = -gamepad.left_stick_y;
        double x = gamepad.left_stick_x;
        double turn = gamepad.right_stick_x;

        double gamepadHpot = Range.clip(Math.hypot(x, y), 0, 1);

        double gamepadDegree = gyro.angleWrapRadians(Math.atan2(y, x));

        double heading = gyro.angleWrapRadians(-gyro.getHeadingRadians());

        double movementDegree = gamepadDegree - heading;

        x = Math.cos(movementDegree) * gamepadHpot;

        y = Math.sin(movementDegree) * gamepadHpot;

        //Sets motor powers
        if(reverse) mecanumDrive(y, -x, turn, slomo);
        else mecanumDrive(x, y, turn, slomo);

    }


    //Field Centric Mecanum Drive for use in 'goToPosition()' function
    //All angles are between 180 and -180. + Values turn the robot Counter clockwise and vice versa
    public void odometryDrive (Gyro gyro, double x, double y, double turn, double angle, boolean slomo) {

        //Transforms inputs based on current robot heading
        double gamepadHpot = Range.clip(Math.hypot(x, y), 0, 1);

        double gamepadDegree = angleWrapRadians(Math.atan2(y, x));

        double heading = angleWrapRadians(-gyro.getHeadingRadians());

        double movementDegree = gamepadDegree - heading;

        x = Math.cos(movementDegree) * gamepadHpot;

        y = Math.sin(movementDegree) * gamepadHpot;

        lF = -x - y - turn;
        rF = -x + y - turn;
        lB = -x + y + turn;
        rB = -x - y + turn;


        maxVector = Math.max(Math.max(Math.abs(lF), Math.abs(rF)),
                Math.max(Math.abs(lB), Math.abs(rB)));

        maxVector = maxVector > 1 ? maxVector : 1;

        lF /= maxVector;
        rF /= maxVector;
        lB /= maxVector;
        rB /= maxVector;

        //Sets motor powers
        if(slomo) setMotorPowers(lF/SLOMO_DIVIDER, lB/SLOMO_DIVIDER, rF/SLOMO_DIVIDER, rB/SLOMO_DIVIDER);
        else setMotorPowers(lF, lB, rF, rB);

    }


    //Robot goes to a position and turns to a 'desiredRobotOrientation'
    public void goToPosition(Odometer odometry, Gyro gyro, double targetX, double targetY, double desiredRobotOrientation, double robotPower, double error){

        boolean Slomo = false;

        //Calculates encoder distance to target
        targetX = targetX * TICKS_PER_INCH;
        targetY = targetY * TICKS_PER_INCH;

        double distanceToXTarget = targetX - odometry.returnXCoordinate();
        double distanceToYTarget = targetY - odometry.returnYCoordinate();

        double distance = Math.hypot(distanceToXTarget, distanceToYTarget);

        error = error * TICKS_PER_INCH;

        //Travel to point while distance > error and the difference between the robot orientation and robot heading is greater than 3
        while(distance > error || abs(desiredRobotOrientation - gyro.getHeading()) > 3) {

            //Updates distance to point
            distance = Math.hypot(distanceToXTarget, distanceToYTarget);
            distanceToXTarget = targetX - odometry.returnXCoordinate();
            distanceToYTarget = targetY - odometry.returnYCoordinate();

            //Robot movement angle for calculating travel angle to point
            double robotMovementAngle = Math.toDegrees(Math.atan2(distanceToXTarget, distanceToYTarget));

            //Calculates X and Y for use in mecanum calculations
            double robotMovementXComponent = calculateX(robotMovementAngle, robotPower);
            double robotMovementYComponent = calculateY(robotMovementAngle, robotPower);

            //Calculate TurnPower/Angle based on current robot heading and desired heading
            double relativeAngleToTarget = angleWrapDegrees(180 - gyro.getHeading() + desiredRobotOrientation);
            double relativeTurnAngle = relativeAngleToTarget - 180;

            double turnPower = Range.clip(Math.toRadians(relativeTurnAngle), -1, 1) * 0.8;

            //Robot enters slomo if robot is close enough to point
            if(distance < 7*2*TICKS_PER_INCH) Slomo = true;

            //Drive Function
            odometryDrive(gyro, robotMovementXComponent, robotMovementYComponent, turnPower, robotMovementAngle, Slomo);

        }

        setMotorPowers(0, 0, 0, 0);

    }

    //Robot turns to a desired Orientation
    public void turn(Gyro gyro, double desiredRobotOrientation, double robotPower){

        boolean Slomo = false;

        //Travel to point while distance > error and the difference between the robot orientation and robot heading is greater than 3
        while(abs(desiredRobotOrientation - gyro.getHeading()) > 5) {


            //Calculate TurnPower/Angle based on current robot heading and desired heading
            double relativeAngleToTarget = angleWrapDegrees(180 - gyro.getHeading() + desiredRobotOrientation);
            double relativeTurnAngle = relativeAngleToTarget - 180;

            double turnPower = Range.clip(Math.toRadians(relativeTurnAngle), -1, 1) * robotPower;

            //Robot enters slomo if robot is close enough to point
//            if(abs(desiredRobotOrientation - gyro.getHeading()) < 10) Slomo = true;

            //Drive Function
            odometryDrive(gyro, 0, 0, turnPower, 0, Slomo);

        }

        setMotorPowers(0, 0, 0, 0);

    }



    //Sets the RunMode for all drive motors
    public void setModeAll(DcMotor.RunMode runMode) {

        leftFront.setMode(runMode);
        leftBack.setMode(runMode);
        rightFront.setMode(runMode);
        rightBack.setMode(runMode);

    }


    //sets the ZeroPowerBehavior for all drive motors
    public void setZeroPowerBehaviorAll(DcMotor.ZeroPowerBehavior zpb) {

        leftFront.setZeroPowerBehavior(zpb);
        leftBack.setZeroPowerBehavior(zpb);
        rightFront.setZeroPowerBehavior(zpb);
        rightBack.setZeroPowerBehavior(zpb);

    }


    //Sets power to all motors
    public void setMotorPowers(double leftFrontPower, double leftBackPower, double rightFrontPower, double rightBackPower) {

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);

    }


    //Calculates xComponent for 'goToPosition()' function
    private double calculateX(double desiredAngle, double speed) { return Math.sin(Math.toRadians(desiredAngle)) * speed; }


    //Calculates yComponent for 'goToPosition()' function
    private double calculateY(double desiredAngle, double speed) { return Math.cos(Math.toRadians(desiredAngle)) * speed; }


    //Transform angle to a value between 0 and 360
    public double angleWrapDegrees(double angle) {
        while (angle > 360) {
            angle -= 360;
        } while(angle < 0){
            angle += 360;
        }

        return angle;
    }


    //Transform angle to a value between 0 and 2PI
    public double angleWrapRadians(double angle) {
        while (angle > 2 * PI ) {
            angle -= 2 * PI;
        } while(angle < 0){
            angle += 2 * PI;
        }

        return angle;
    }

}
