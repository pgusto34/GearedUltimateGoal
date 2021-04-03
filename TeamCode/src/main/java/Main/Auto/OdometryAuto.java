package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;
import Main.Base.RobotUtilities.Odometry;


@Autonomous(name = "Odometry Auto")
public class OdometryAuto extends AutoRobot {

    static final double TICKS_PER_REVOLUTION = 8192;
    static final double WHEEL_DIAMETER = 1.45;
    static final double TICKS_PER_INCH = (TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI));


    @Override
    public void runOpMode() {
        initialize();

       // positionThread.start();

//        rings = camera.detectRings();
//        telemetry.addData("Rings: ", rings);
//        telemetry.update();
//
//        while(!isStarted()) {
//            rings = camera.detectRings();
//            telemetry.addData("Rings: ", rings);
//            telemetry.update();
//        }

        waitForStart();

//        if (rings == 0) {}//do something
//        else if (rings == 1) {} //do something else
//        else if (rings == 4) {} //do another things
        odometry = new Odometry(left, right, mid,  75);
        positionThread = new Thread(odometry);

        positionThread.start();

        wheelBase.goToPosition(odometry,  52, 6, 0.5, 0, 4);





        while(opModeIsActive()) {

            //Display Global (x, y, theta) coordinates
            telemetry.addData("X Position", odometry.returnXCoordinate());
            telemetry.addData("Y Position", odometry.returnYCoordinate());
            telemetry.addData("Orientation (Degrees)", odometry.returnOrientation());
            telemetry.update();
        }

        positionThread.stop();

    }


}
