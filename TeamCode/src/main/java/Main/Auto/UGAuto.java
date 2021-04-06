package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;

@Autonomous(name = "Competition Auto", group = "competition")
public class UGAuto extends AutoRobot {

    double speed = 0.6;

    double error = 4;

    public void runAutonomous(){

        try {

            wobbleArm.setWobbleArmInPosition();
            wobbleArm.ControlWobbleClaw(true);

            if(rings == 0) {

                wheelBase.goToPosition(odometry,  56, 0, speed, 4);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                pause(2000);
                wheelBase.goToPosition(odometry,  62, 11, 0.4, 4);
                shooter.shoot(true, 3);
                pause(1000);
                wheelBase.goToPosition(odometry,  66, 14, speed, 4);

            }

            else if(rings == 1) {

                wheelBase.goToPosition(odometry, 56, 0, speed, error);
                wheelBase.goToPosition(odometry,  80, 21, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                wheelBase.goToPosition(odometry,  61, 9.5, speed, error);
                //wheelBase.straighten(odometry, 0.2);
                shooter.shoot(true, 3);
                pause(1000);
                wheelBase.goToPosition(odometry,  40, 0, speed, error);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 40, 14, speed, error);
                wheelBase.goToPosition(odometry,  59, 14, speed, error);
                //wheelBase.straighten(odometry, 0.15);
                shooter.shoot(true, 3);
                intake.controlLIntake(false, false);
                wheelBase.goToPosition(odometry,  67, 14, speed, error);

            }

            else if(rings == 4) {

                wheelBase.goToPosition(odometry, 98, 0, 0.8, error);
                wobbleArm.ControlWobbleArm(true);
                pause(250);
                wobbleArm.ControlWobbleClaw(false);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                wheelBase.straighten(odometry, 0.15);
                wheelBase.goToPosition(odometry,  60, 13, speed, error);
                shooter.shoot(true, 3);
                pause(500);
                wheelBase.goToPosition(odometry,  38, 0, speed, error);
                //wheelBase.straighten(odometry, 0.1);
                wheelBase.goToPosition(odometry, 38, 19, speed, error);
                pause(500);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 38, 31, 0.2, error);
                pause(500);
                wheelBase.goToPosition(odometry,  58.5, 14, speed, error);
                shooter.shoot(true, 3);
                intake.controlLIntake(false, true);
                wheelBase.goToPosition(odometry,  42, 0, speed, error);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 42, 24, speed, error);
                wheelBase.goToPosition(odometry, 42, 47, 0.2, error);
                wheelBase.goToPosition(odometry,  58, 14, speed, error);
                shooter.shoot(true, 3);
                intake.controlLIntake(false, false);
                wheelBase.goToPosition(odometry,  67, 14, speed, error);

            }


        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
