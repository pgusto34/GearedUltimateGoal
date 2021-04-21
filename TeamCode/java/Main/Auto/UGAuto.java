package Main.Auto2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base2.AutoRobot;

@Autonomous(name = "Competition Auto", group = "competition")
public class UGAuto extends AutoRobot {

    double speed = 0.6;

    double error = 4;

    public void runAutonomous(){

        try {

            wobbleArm.setWobbleArmInPosition();
            wobbleArm.ControlWobbleClaw(true);

            if(rings == 0) {

                wheelBase.goToPosition(odometry,  gyro, telemetry, 56, 0, 0, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                pause(2000);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 60, 13, 0, 0.3, 2);
                shooter.shoot(true, 3);
                wobbleArm.ControlWobbleArm(false);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 35, 28, 180, 0.5, 2);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wheelBase.goToPosition(odometry, gyro, telemetry, 24, 28, 180, speed, error);
                wobbleArm.ControlWobbleClaw(true);
                pause(2000);
                wheelBase.turnBot(odometry,  gyro,  33, 24, 0);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 56, 8, 0, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 66, 14, 0, speed, error);

            }

            else if(rings == 1) {

                wheelBase.goToPosition(odometry, gyro, telemetry, 56, 0, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 60, 13, 0, 0.5, 1);
                shooter.shoot(true, 3);
                pause(1000);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 37, 0, 0,  speed, 3);
                intake.controlLIntake(true, false);
                wheelBase.goToPosition(odometry, gyro, telemetry, 37, 14, 0, 0.7, 2);
                wheelBase.goToPosition(odometry, gyro, telemetry, 55, 14, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 60, 13, 0, 0.5, 1);
                shooter.shoot(true, 3);
                intake.controlLIntake(false, false);
                wheelBase.goToPosition(odometry, gyro, telemetry, 80, 22, 0, speed, error);
                wobbleArm.ControlWobbleArm(true);
                pause(1000);
                wobbleArm.ControlWobbleClaw(false);
                pause(1000);
                wobbleArm.ControlWobbleArm(false);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 66, 14, 0, speed, error);

//                wheelBase.goToPosition(odometry,  gyro, telemetry, 24, 24, 90, speed, error);
//                wobbleArm.ControlWobbleArm(true);
//                pause(1000);
//                wobbleArm.ControlWobbleClaw(false);
//                pause(1000);
//                wobbleArm.ControlWobbleArm(false);
//                pause(2000);
//                wheelBase.goToPosition(odometry,  gyro, telemetry, 78, 18, 0, speed, error);
//                wobbleArm.ControlWobbleArm(true);
//                pause(1000);
//                wobbleArm.ControlWobbleClaw(false);
//                pause(1000);
//                wobbleArm.ControlWobbleArm(false);
//                pause(1000);
            }

            else if(rings == 4) {


                wheelBase.goToPosition(odometry, gyro, telemetry, 56, 0, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 59.5, 13, 0, 0.5, 1);
                shooter.shoot(true, 3);
                pause(1000);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 37, 0, 0,  speed, 3);
                wheelBase.goToPosition(odometry, gyro, telemetry, 37,  16, 0, 0.7, 2);
                wheelBase.goToPosition(odometry, gyro, telemetry, 37,  10, 0, 0.5, 2);
                intake.controlLIntake(true, false);

                wheelBase.goToPosition(odometry, gyro, telemetry, 37,  22, 0, 0.5, 2);
                wheelBase.goToPosition(odometry, gyro, telemetry, 55,  14, 0, speed, error);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 59.5,  14, 0, 0.5, 1);
                shooter.shoot(true, 3);
                pause(500);
                intake.controlLIntake(false, true);
//                wheelBase.goToPosition(odometry,  gyro, telemetry, 37, 0, 0,  speed, 3);
//                intake.controlLIntake(true, false);
//                wheelBase.goToPosition(odometry, gyro, telemetry, 37, 14, 0, speed, 2);
//                wheelBase.goToPosition(odometry, gyro, telemetry, 37, 28, 0, 0.5, 2);
//                wheelBase.goToPosition(odometry, gyro, telemetry, 55, 14, 0, speed, error);
//                wheelBase.goToPosition(odometry,  gyro, telemetry, 60, 13, 0, 0.4, 1);
//                shooter.shoot(true, 3);
//                intake.controlLIntake(false, false);
                wheelBase.goToPosition(odometry, 98, 4, 1, error);
                wobbleArm.ControlWobbleArm(true);
                pause(250);
                wobbleArm.ControlWobbleClaw(false);
                pause(500);
                wobbleArm.ControlWobbleArm(false);
                //wheelBase.goToPosition(odometry,  gyro, telemetry, 24, 24, 90, speed, error);
                wheelBase.goToPosition(odometry,  gyro, telemetry, 66, 14, 0, 1, error);

//                wobbleArm.ControlWobbleArm(true);
//                pause(1000);
//                wobbleArm.ControlWobbleClaw(false);
//                pause(1000);
//                wobbleArm.ControlWobbleArm(false);
//                pause(2000);
//                wheelBase.goToPosition(odometry,  gyro, telemetry, 120, 36, -90, speed, error);
//                wobbleArm.ControlWobbleArm(true);
//                pause(1000);
//                wobbleArm.ControlWobbleClaw(false);
//                pause(1000);
//                wobbleArm.ControlWobbleArm(false);
//                pause(2000);
//                wheelBase.goToPosition(odometry,  gyro, telemetry, 72, 36, 0, speed, error);
//                pause(2000);

            }


        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
