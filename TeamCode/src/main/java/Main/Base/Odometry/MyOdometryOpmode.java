package Main.Base.Odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Main.Base.Robot;

public class MyOdometryOpmode extends Robot {

    OdometryPositionUpdater globalPositionUpdate;
    Thread positionThread;

    final double TICKS_PER_REV = 8192;
    final double WHEEL_DIAMETER = 38/25.4;

    final double TICKS_PER_INCH = TICKS_PER_REV / (Math.PI * WHEEL_DIAMETER);

    @Override
    public void init() {



        //Create and start GlobalCoordinatePosition thread to constantly update the global coordinate positions
        globalPositionUpdate = new OdometryPositionUpdater(left, right, mid, gyro, 75);
        positionThread = new Thread(globalPositionUpdate);
        positionThread.start();

    }

    @Override
    public void start() {

        goToPosition(0, 24, 1, 0, 1);

    }


    @Override
    public void loop() {

        //Display Global (x, y, theta) coordinates
        telemetry.addData("X Position", globalPositionUpdate.returnXCoordinate());
        telemetry.addData("Y Position", globalPositionUpdate.returnYCoordinate());
        telemetry.addData("Orientation (Degrees)", globalPositionUpdate.returnOrientation());

        telemetry.addData("Vertical left encoder position", left.getCurrentPosition());
        telemetry.addData("Vertical right encoder position", right.getCurrentPosition());
        telemetry.addData("horizontal encoder position", mid.getCurrentPosition());

        telemetry.addData("Thread Active", positionThread.isAlive());
        telemetry.update();
    }

    @Override
    public void stop() {
        //Stop the thread
        globalPositionUpdate.stop();
    }


    public void goToPosition(double targetX, double targetY, double robotPower, double desiredRobotOrientation, double error){
        targetX = targetX * TICKS_PER_INCH;
        targetY = targetY * TICKS_PER_INCH;

        double distanceToXTarget = targetX - globalPositionUpdate.returnXCoordinate();
        double distanceToYTarget = targetY - globalPositionUpdate.returnYCoordinate();

        double distance = Math.hypot(distanceToXTarget, distanceToYTarget);

        while(distance < error) {
            distance = Math.hypot(distanceToXTarget, distanceToYTarget);
            distanceToXTarget = targetX - globalPositionUpdate.returnXCoordinate();
            distanceToYTarget = targetY - globalPositionUpdate.returnYCoordinate();

            double robotMovementAngle = Math.toDegrees(Math.atan2(distanceToXTarget, distanceToYTarget));

            double robotMovementXComponent = calculateX(robotMovementAngle, robotPower);
            double robotMovementYComponent = calculateY(robotMovementAngle, robotPower);
            double pivotCorrection = desiredRobotOrientation - globalPositionUpdate.returnOrientation();
        }
    }



    /**
     * Calculate the power in the x direction
     * @param desiredAngle angle on the x axis
     * @param speed robot's speed
     * @return the x vector
     */
    private double calculateX(double desiredAngle, double speed) {
        return Math.sin(Math.toRadians(desiredAngle)) * speed;
    }

    /**
     * Calculate the power in the y direction
     * @param desiredAngle angle on the y axis
     * @param speed robot's speed
     * @return the y vector
     */
    private double calculateY(double desiredAngle, double speed) {
        return Math.cos(Math.toRadians(desiredAngle)) * speed;
    }




}


