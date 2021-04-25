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

            //Path A
            if(rings == 0) {

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry,  gyro, 59, -2, 0, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(true);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                pause(2000);


                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry,  gyro, 60, 13, 0, 0.3, 2);
                shooter.shootHG();
                wobbleArm.ControlWobbleArm(true);
                wheelBase.goToPosition(odometry,  gyro, 35, 30, 180, 0.5, 2);
                pause(1000);

                //Grab and Drop off 2nd Wobble Goal
                //
                wheelBase.goToPosition(odometry, gyro, 32, 30, 180, speed, error);
                wobbleArm.ControlWobbleClaw(false);
                pause(2000);
                wheelBase.goToPosition(odometry,  gyro, 58, 3, 0, speed, error);
                wobbleArm.ControlWobbleClaw(true);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                pause(1000);
                wheelBase.goToPosition(odometry,  gyro, 53, 10, 0, speed, error);



                //Park
                wheelBase.goToPosition(odometry,  gyro, 66, 14, 0, speed, error);

            }

            //Path B
            else if(rings == 1) {

                //Shoot 3 Rings in High Goal
                wheelBase.goToPosition(odometry, gyro, 56, 0, 0, 0.8, error);
                wheelBase.goToPosition(odometry,  gyro, 60, 13, 0, 0.5, 1);
                shooter.shootHG();
                pause(500);

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry, gyro, 86, 18, 0, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(800);
                wobbleArm.ControlWobbleClaw(true);
                pause(250);
                wobbleArm.ControlWobbleArm(false);

                //Grab Ring
                wheelBase.goToPosition(odometry,  gyro, 37, 0, 0,  speed, 3);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, gyro, 37, 14, 0, 0.8, 2);


                //Grab   2nd Wobble Goal
                wheelBase.goToPosition(odometry, gyro, 37, 3, 0, 0.6, 2);
                wheelBase.goToPosition(odometry, gyro, 31, 25, 180, 0.8, 4);
                wobbleArm.ControlWobbleArm(true);
                pause(500);
                wobbleArm.ControlWobbleClaw(false);
                pause(250);

                //Drop 2nd Wobble Goal
                wheelBase.goToPosition(odometry,  gyro, 78, 27, 0, 1, 36);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                pause(500);

                //fire ring
                //wheelBase.goToPosition(odometry, gyro, 55, 14, 0, 1, error);
                wheelBase.goToPosition(odometry,  gyro, 59, 13, 0, 0.7, 1);
                shooter.shootHG();
                intake.controlLIntake(false, false);




                //Park
                wheelBase.goToPosition(odometry,  gyro, 66, 14, 0, 0.5, error);

            }

            //Path C
            else if(rings == 4) {

                //Shoot
                wheelBase.goToPosition(odometry, gyro, 56, 0, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, 59.5, 13, 0, 0.5, 1);
                shooter.shootHG();
                pause(1000);

                //Pick Up Rings
                wheelBase.goToPosition(odometry,  gyro, 37, 0, 0,  speed, 3);
                wheelBase.goToPosition(odometry, gyro, 37,  16, 0, 0.7, 2);
                wheelBase.goToPosition(odometry, gyro, 37,  10, 0, 0.5, 2);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, gyro, 37,  22, 0, 0.5, 2);

                //Shoot Rings
                wheelBase.goToPosition(odometry, gyro, 55,  14, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, 59.5,  14, 0, 0.5, 1);
                shooter.shootHG();
                pause(500);
                intake.controlLIntake(false, true);

                //Drop Off Wobble Goal
                wheelBase.goToPosition(odometry, gyro,98, 4, 0,1, error);
                wobbleArm.ControlWobbleArm(true);
                pause(250);
                wobbleArm.ControlWobbleClaw(true);
                pause(500);
                wobbleArm.ControlWobbleArm(false);

                //Park
                wheelBase.goToPosition(odometry,  gyro, 66, 14, 0, 1, error);

            }
        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
