package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;



@Autonomous(name = "Odometry Auto")
public class OdometryAuto extends AutoRobot {

//       rings = camera.detectRings();
//       telemetry.addData("Rings: ", rings);
//       telemetry.update();


    @Override
    public void runOpMode() {
        initialize();

        positionThread.start();

        rings = camera.detectRings();
        telemetry.addData("Rings: ", rings);
        telemetry.update();

        waitForStart();

        if (rings == 0) {}//do something
        else if (rings == 1) {} //do something else
        else if (rings == 4) {} //do another things

        wheelBase.goToPosition(odometry, 24, 0, 0.5, 0.3, 0, 4);


        while(!opModeIsActive()) {
            rings = camera.detectRings();
            telemetry.addData("Rings: ", rings);
            telemetry.update();
        }


        while(opModeIsActive()) {

            //Display Global (x, y, theta) coordinates
            telemetry.addData("X Position", odometry.returnXCoordinate());
            telemetry.addData("Y Position", odometry.returnYCoordinate());
            telemetry.addData("Orientation (Degrees)", odometry.returnOrientation());
            telemetry.update();
        }



    }
}
