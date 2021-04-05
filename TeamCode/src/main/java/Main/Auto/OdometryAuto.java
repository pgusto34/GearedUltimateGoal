//package Main.Auto;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import Main.Base.AutoRobot;
//import Main.Base.RobotUtilities.Odometry;
//
//
//@Autonomous(name = "Odometry Auto")
//public class OdometryAuto extends AutoRobot{
//
//
//    @Override
//    public void runOpMode() throws InterruptedException{
//        initialize();
//
//       // positionThread.start();
//
////        rings = camera.detectRings();
////        telemetry.addData("Rings: ", rings);
////        telemetry.update();
////
////        while(!isStarted()) {
////            rings = camera.detectRings();
////            telemetry.addData("Rings: ", rings);
////            telemetry.update();
////        }
//
//        waitForStart();
//
////        if (rings == 0) {}//do something
////        else if (rings == 1) {} //do something else
////        else if (rings == 4) {} //do another things
//        odometry = new Odometry(left, right, mid,  75);
//        positionThread = new Thread(odometry);
//
//        positionThread.start();
//
//        wheelBase.goToPosition(odometry,  52, 20, 0.5, 4);
//        wheelBase.turnRobot(odometry, 90, 0.5, false);
////        wheelBase.goToPosition(odometry, 5, 5, 0.5, 4);
//
//
//
//
//
//        while(opModeIsActive()) {
//
//            //Display Global (x, y, theta) coordinates
//            telemetry.addData("X Position", odometry.returnXCoordinateInches());
//            telemetry.addData("Y Position", odometry.returnYCoordinateInches());
//            telemetry.addData("Orientation (Degrees)", odometry.returnOrientation());
//            telemetry.update();
//        }
//
//        odometry.stop();
//
//    }
//
//
//}
