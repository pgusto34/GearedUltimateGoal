package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;

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
                pause(1000);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                pause(250);


                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry,  gyro, 60, 13, 0, 0.45, 0.75);
                shooter.shootHG();


                //Grab and Drop off 2nd Wobble Goal
                wheelBase.goToPosition(odometry, gyro, 25,  10, 0, 0.6, 5);
                wheelBase.goToPosition(odometry, gyro, 20,  10, 0, 0.5, 2);
                wheelBase.turn(gyro, -80, 0.8);


                wobbleArm.ControlWobbleArm(true);
                pause(500);

                wheelBase.goToPosition(odometry, gyro, 14,  22.7, -90, 0.55, 1);
                wheelBase.goToPosition(odometry, gyro, 13,  23.1, -90, 0.5, 1);

                wobbleArm.ControlWobbleClaw(false);
                pause(1000);

                wheelBase.goToPosition(odometry,  gyro, 58, 11.25, 0, 0.5, 6);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);
                pause(250);
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
                wheelBase.goToPosition(odometry, gyro, 81, 19, 0, 0.8, 6);
                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry, gyro, 81, 21, 0, 0.5, 2.5);
                wobbleArm.ControlWobbleArm(true);
                pause(500);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);

                //Grab Ring
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry,  gyro, 40, 4, 0,  0.9, 4);
                wheelBase.goToPosition(odometry, gyro, 39.5, 14, 0, 0.8, 2);

;
//                //Grab   2nd Wobble Goal
                wheelBase.goToPosition(odometry, gyro, 25,  4, 0, 0.7, 5);
                wheelBase.goToPosition(odometry, gyro, 20,  4, 0, 0.7, 3);
                wheelBase.turn(gyro, -80, 0.8);


                wobbleArm.ControlWobbleArm(true);
                pause(250);

                wheelBase.goToPosition(odometry, gyro, 12.85,  17.6, -90, 0.55, 1);
                wheelBase.goToPosition(odometry, gyro, 10.5,  18.4, -90, 0.5, 0.75);


                wobbleArm.ControlWobbleClaw(false);
                pause(400);

                wheelBase.turn(gyro, 0, 0.75);
//                //Drop 2nd Wobble Goal
                wobbleArm.setWobbleArmInPosition();

                wheelBase.goToPosition(odometry, gyro, 52.5, 42, 0, 0.8, 6);
                wheelBase.goToPosition(odometry,  gyro, 53.5, 43, 0, 0.63, 2);
                shooter.shootPS();
                pause(100);

                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry,  gyro, 62, 30, 0, 1, 6);
                shooter.stopFlyWheel();
                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry,  gyro, 74.5, 30, 0, 0.73, 2);
                wobbleArm.ControlWobbleClaw(true);
                pause(150);
                wobbleArm.ControlWobbleArm(false);

                //Park
                wheelBase.goToPosition(odometry, gyro, 64, 38, 0, 1, 3);


            }

            //Path C
            else if(rings == 4) {


                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry, gyro, 44, 0, 0, 0.8, 6);
                shooter.runFlyWheelHigh();
                wheelBase.goToPosition(odometry,  gyro, 56.5, 13.5, 0, 0.45, 1);
                shooter.flickFeeder(4);
                shooter.stopFlyWheel();


                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry, gyro,92, 2, 0,1, 6);

                //stopper.controlStopper(false);
                wobbleArm.ControlWobbleArm(true);
                pause(500);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);


                //Pick Up Rings
                wheelBase.goToPosition(odometry,  gyro, 34, -1.5, 0,  0.6, 1);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, gyro, 34,  4.1, 0, 0.4, 0.5);
                pause(250);
                intake.controlLIntake(false, false);
                pause(250);

                intake.controlLIntake(true,false);
                wheelBase.goToPosition(odometry, gyro, 34,  5, 0, 0.4, 0.5);
                pause(250);
                intake.controlLIntake(true,false);


                wheelBase.goToPosition(odometry, gyro, 34,  -1, 0, 0.8, 4);
                intake.controlLIntake(false,false);
                wobbleArm.ControlWobbleArm(true);

                wheelBase.turn(gyro, -90, 0.8);

                wheelBase.goToPosition(odometry, gyro, 13.25,  15.4, -90, 0.55, 1);
                wheelBase.goToPosition(odometry, gyro, 10.2,  17.3, -90, 0.47, 0.75);


                wobbleArm.ControlWobbleClaw(false);
                pause(600);


                //Shoot Rings
                shooter.runFlyWheelHigh();
                wobbleArm.setWobbleArmInPosition();
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry,  gyro, 50, 10, 0, 0.8, 6);
                wheelBase.goToPosition(odometry,  gyro, 52.5, 18, 0, 0.55, 1);
                shooter.flickFeeder(4);
                shooter.stopFlyWheel();

                intake.controlLIntake(false,false);

                //Drop 2nd Wobble Goal
                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry,  gyro, 102, 10, 0, 1, 30);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                pause(250);





                //Park
               wheelBase.goToPosition(odometry,  gyro, 64, 20, 0, 1, 5);


            }
        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }

    public void aParker(){
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


        //Grab and Drop off 2nd Wobble Goal
        wheelBase.goToPosition(odometry, gyro, 20,  15, 0, 0.6, 2);
        wheelBase.turn(gyro, -90, 0.8);


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
}
