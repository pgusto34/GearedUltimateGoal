package Main.Auto;

import Main.Base.AutoRobot;

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
                wheelBase.goToPosition(odometry,  58, 10, speed, 4);
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
                wheelBase.goToPosition(odometry,  57, 14, speed, error);
                shooter.shoot(true, 3);
                pause(1000);
                wheelBase.goToPosition(odometry,  40, 0, speed, error);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 40, 10, speed, error);
                wheelBase.goToPosition(odometry,  58, 10, speed, error);
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
                wheelBase.goToPosition(odometry,  57, 14, speed, error);
                shooter.shoot(true, 3);
                pause(500);
                wheelBase.goToPosition(odometry,  38, 0, speed, error);
                //wheelBase.straighten(odometry, 0.1);
                wheelBase.goToPosition(odometry, 38, 19, speed, error);
                pause(500);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 38, 28, 0.2, error);
                pause(500);
                wheelBase.goToPosition(odometry,  56, 18, speed, error);
                shooter.shoot(true, 3);
                intake.controlLIntake(false, true);
                wheelBase.goToPosition(odometry,  45, 0, speed, error);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, 48, 24, speed, error);
                wheelBase.goToPosition(odometry, 48, 44, 0.2, error);
                wheelBase.goToPosition(odometry,  56, 18, speed, error);
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
