package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;
import Main.Base.RobotUtilities.WheelBase;

/** TODO:
 * -Fine Tune Path A
 *      -Focus on Getting the 2nd Wobble and Accuracy
 *      -Making sure the robot never crosses over the launch line in auto for any reason whatsoever
 * -Add 2nd Wobble Goal to B and C Paths
 *      -Focus on ACCURACY and limit jiggling (by finding best combination of error and speed)
 * -Optimize Time for B and C Paths
 *      -Increase Error when robot doesn't have to be exact (Robot is jiggling)
 *      -Decrease Error when robot has to be in exact position
 *      -Take out Pauses when not needed
 *      -Replace 'shoot()' function with runFlyWheelHigh()/runFlyWheelPower, flickFeeder(), and stopFlyWheel() (See RobotTeleOp for an example)
 *       should save plenty of time
 *      -Increase Speed when Accuracy isn't needed and see how far you can push it
 * -Path C
 *      -Optimize Time for Optimimal Score
 *      -Tune Ramming Rings in 1st Ring Pick up to prevent jamming and picking up as many rings as possible
 *      -Add 2nd Passover of Ring pile after optimizing time if possible, but would be awesome
 *          -Would probably be most effective if you did you first pass through, shot the rings you had, then did another pass through
 *          shot those rings, then went for the 2nd wobble goal
 * -Powershots
 *      -Work with Colton to try and hit powershots in auto
 *      -Only attempt this if everything else works and you have time
 *
 */


/** Our Competition Auto:
 *  Overrides the runAutonomous function that's defined in the AutoRobot Class, which runs when
 *  the autoThread is started in the 'start()' function. We created and run our auto on a separate
 *  thread to have the ability to utilize the useful loop features of an iterative opMode while being
 *  able to code auto like we're using a linear opMode. This also solves the problem of coding auto in
 *  the 'start()' function, which causes the program to crash and we don't like that.
 */

@Autonomous(name = "Competition Auto", group = "competition")
public class UGAuto extends AutoRobot {

    double speed = 0.6;

    double error = 4;

    public void runAutonomous(){

        try {

            wobbleArm.setWobbleArmInPosition();
            wobbleArm.ControlWobbleClaw(false);
            stopper.controlStopper(true);


            //Path A
            if(rings == 0) {

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry,  gyro, 54, -1, 0, speed, 2);
                wobbleArm.ControlWobbleArm(true);
                pause(1500);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                pause(1000);


                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry,  gyro, 60, 13, 0, 0.4, 1);
                shooter.shootHG();


                //Grab and Drop off 2nd Wobble Goal
                //
                //wheelBase.goToPosition(odometry, gyro, 34, 30, 180, speed, error);
             //   wheelBase.goToPosition(odometry, gyro, 15.05,  23.25, -90, 0.4, 2);
                wheelBase.goToPosition(odometry, gyro, 20,  15, 0, 0.6, 2);
                wheelBase.turn(odometry, gyro, -90, 0.8);


                wobbleArm.ControlWobbleArm(true);
                pause(500);

                wheelBase.goToPosition(odometry, gyro, 14,  23, -90, 0.55, 1);
                wheelBase.goToPosition(odometry, gyro, 13,  23.5, -90, 0.55, 1);

                wobbleArm.ControlWobbleClaw(false);
                pause(1000);

                wheelBase.goToPosition(odometry,  gyro, 56.5, 10, 0, 0.5, error);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);
                pause(500);
                wheelBase.goToPosition(odometry,  gyro, 45, 31, 0, speed, error);



                //Park
                wheelBase.goToPosition(odometry,  gyro, 68, 55, 0, 0.5, error);

            }

            //Path B
            else if(rings == 1) {

                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry, gyro, 44, 0, 0, 0.8, 6);
                shooter.runFlyWheelHigh();
                wheelBase.goToPosition(odometry,  gyro, 56.5, 15, 0, 0.45, 1);
                shooter.flickFeeder(4);
                shooter.stopFlyWheel();

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry, gyro, 78, 23.5, 0, 0.8, 6);
                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry, gyro, 80, 23, 0, 0.5, 1.5);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);

                //Grab Ring
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry,  gyro, 35, 0, 0,  speed, 3);
                wheelBase.goToPosition(odometry, gyro, 33, 14, 0, 0.8, 2);

;
//                //Grab   2nd Wobble Goal
//
//                wheelBase.goToPosition(odometry, gyro, 35, 3, -45, 0.55, 5);
//                wheelBase.goToPosition(odometry, gyro, 35,21,-90,0.55, 5);

                //wheelBase.goToPosition(odometry, gyro, 35,  12, -90, 0.5, 5);
                wheelBase.turn(odometry, gyro, -90, 0.75);


                wobbleArm.ControlWobbleArm(true);
                pause(500);

                wheelBase.goToPosition(odometry, gyro, 34,  9, -90, 0.7, 2);
                wheelBase.goToPosition(odometry, gyro, 12,  10, -90, 0.6, 2);
                wheelBase.goToPosition(odometry, gyro, 10.5,  21, -90, 0.6, 2);


                wobbleArm.ControlWobbleClaw(false);
                pause(750);
//
                wheelBase.turn(odometry, gyro, 0, 0.75);
//                //Drop 2nd Wobble Goal
                wobbleArm.setWobbleArmInPosition();
                //shooter.runFlyWheelHigh();
                wheelBase.goToPosition(odometry, gyro, 51, 26, 0, 0.8, 5);
                wheelBase.goToPosition(odometry,  gyro, 53, 34, 0, 0.5, 3);;
                shooter.shootPS();
                //shooter.flickFeeder(2);
                //shooter.stopFlyWheel();

//                wheelBase.goToPosition(odometry, gyro, 74, 23, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, 60, 35, 0, 1, 6);
                wheelBase.goToPosition(odometry,  gyro, 71, 34, 0, 1, 4);
                wobbleArm.ControlWobbleArm(true);
                pause(100);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
//
                //fire ring

                wheelBase.goToPosition(odometry, gyro, 64, 40, 0, 1, 3);
//                intake.controlLIntake(false, false);
//                shooter.shootPS();
                //wheelBase.goToPosition(odometry, gyro, 60, 42, 0 ,1, error );
                //wheelBase.goToPosition(odometry, gyro, 55, 14, 0, 1, error);


//                shooter.runFlyWheelHigh();
//                wheelBase.goToPosition(odometry,  gyro, 56, 15, 0, 0.8, 1);
//
//                shooter.flickFeeder();
//                shooter.stopFlyWheel();
//                intake.controlLIntake(false, false);
//
//
//                //Park
//                wheelBase.goToPosition(odometry,  gyro, 66, 14, 0, 0.5, error);

            }

            //Path C
            else if(rings == 4) {

//                //Shoot 3 Rings in High Goal
//                wheelBase.goToPosition(odometry, gyro, 44, 0, 0, 0.8, 6);
//                shooter.runFlyWheelHigh();
//                wheelBase.goToPosition(odometry,  gyro, 56.5, 15, 0, 0.45, 1);
//                shooter.flickFeeder(4);
//                shooter.stopFlyWheel();
//
//                //Drop Off Wobble Goal
//                wheelBase.goToPosition(odometry, gyro, 90, 4, 0, 0.8, 6);
//                wheelBase.goToPosition(odometry, gyro, 99, 4, 0, 0.5, 1.5);
//                wobbleArm.ControlWobbleArm(true);
//                pause(500);
//                wobbleArm.ControlWobbleClaw(true);
//                pause(250);
//                wobbleArm.ControlWobbleArm(false);
//
//                wheelBase.goToPosition(odometry, gyro, 34,  9, -90, 0.7, 2);
//
//
//                wheelBase.turn(odometry, gyro, -90, 0.75);
//
//                wobbleArm.ControlWobbleArm(true);
//
//                wheelBase.goToPosition(odometry, gyro, 12,  10, -90, 0.6, 2);
//                wheelBase.goToPosition(odometry, gyro, 10.5,  20.5, -90, 0.6, 2);
//
//
//                wobbleArm.ControlWobbleClaw(false);
//                pause(500);
//
//                wheelBase.turn(odometry, gyro, 0, 0.75);
//
//
//                wheelBase.goToPosition(odometry, gyro, 89, 50, 0, 0.8, 6);
//                wheelBase.goToPosition(odometry, gyro, 98, 50, 0, 0.5, 1.5);
//                wobbleArm.ControlWobbleArm(true);
//                pause(500);
//                wobbleArm.ControlWobbleClaw(true);
//                pause(250);
//                wobbleArm.ControlWobbleArm(false);
//
//                wheelBase.goToPosition(odometry, gyro, 58, 27, 0, 0.7, 3);


                //Shoot
                //stopper.controlStopper(false);
                wheelBase.goToPosition(odometry, gyro, 48, 8, 0, 1, error);
                shooter.runFlyWheelHigh();
                wheelBase.goToPosition(odometry,  gyro, 57, 13, 0, 0.5, 1);
                wheelBase.goToPosition(odometry,  gyro, 56.5, 15, 0, 0.45, 1);
                shooter.flickFeeder(4);
                shooter.stopFlyWheel();

                pause(250);

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry, gyro,87, 10, 0,1, 6);
                wheelBase.goToPosition(odometry, gyro,96, 10, 0,0.5, 1);

                //stopper.controlStopper(false);
                wobbleArm.ControlWobbleArm(true);
                pause(250);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);


                //Pick Up Rings
                wheelBase.goToPosition(odometry,  gyro, 37, 7, 0,  0.8, 2);
                wheelBase.goToPosition(odometry, gyro, 37,  18, 0, 1, 2);
                wheelBase.goToPosition(odometry, gyro, 37,  7, 0, 0.7, 2);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, gyro, 38,  24, 0, 0.4, 2);
                wheelBase.goToPosition(odometry, gyro, 37,  9.5, 0, 0.7, 2);

                //Grab   2nd Wobble Goal
                //wheelBase.goToPosition(odometry, gyro, 37, 3, 0, 0.6, 2);
                //wheelBase.goToPosition(odometry, gyro, 29, 23, 180, 1, 10);
//                wheelBase.goToPosition(odometry, gyro, 12,  17, -90, 0.6, 2);
//
//                wobbleArm.ControlWobbleArm(true);
//                pause(500);
//                wobbleArm.ControlWobbleClaw(false);
//                pause(250);

                //Shoot Rings
                wheelBase.goToPosition(odometry, gyro, 54,  20, 0, 1, error);
                shooter.runFlyWheelHigh();
                wheelBase.goToPosition(odometry,  gyro, 58,  18, 0, 0.55, 1);
                shooter.flickFeeder();
                shooter.flickFeeder();
                shooter.flickFeeder();
                shooter.stopFlyWheel();
                intake.controlLIntake(false, true);

//                //Drop 2nd Wobble Goal
//                wheelBase.goToPosition(odometry,  gyro, 110, 8, 0, 1, 30);
//                //wheelBase.goToPosition(odometry,  gyro, 78, 27, 0, 0.5, 2);
//                wobbleArm.ControlWobbleClaw(true);
//                pause(500);
//                wobbleArm.ControlWobbleArm(false);
//                pause(500);





                //Park
                wheelBase.goToPosition(odometry,  gyro, 66, 14, 0, 1, error);
                intake.controlLIntake(false, false);

            }
        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
