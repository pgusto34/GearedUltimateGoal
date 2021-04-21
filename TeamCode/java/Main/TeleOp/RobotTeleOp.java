package Main.TeleOp2;

//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Main.Base2.HelperClasses.BooleanUpdater;
import Main.Base2.HelperClasses.Button;
import Main.Base2.HelperClasses.Button.*;
import Main.Base2.Robot;
import Main.Base2.RobotUtilities.WobbleArm;

import static Main.Base2.HelperClasses.BooleanUpdater.*;
import static Main.Base2.HelperClasses.Button.*;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;

//@Config
@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    boolean intakeLIn = false, intakeLOut = false, intakeRIn = false, intakeROut = false;

    boolean wobbleArmDown = false, wobbleClawClosed = false;

    boolean slomo = false, reverse = true;

    boolean FOD = true;

    boolean HIGH = false;

    //FtcDashboard dashboard;
    public static double highGoalVelocity = 1900;
    public static double powerShotVelocity = 300;
    public static double currentVelocity = 0;

    //public PIDFCoefficients pidCoefficients = shooter.getPID();

    @Override
    public void init() {
        super.init();
        shooter.firstSetPID();
        //dashboard = FtcDashboard.getInstance();
    }

    @Override
    public void loop() {

        buttonChecker = updateBooleans(gp, buttonChecker);
        currentVelocity = shooter.getVelocity();


        if (buttonChecker.get(x)) slomo = !slomo;

        if(buttonChecker.get(a)) reverse = !reverse;

        if(buttonChecker.get(left_stick_button)) FOD = !FOD;

        if(FOD) wheelBase.fieldOrientatedDrive(gp, gyro, slomo, reverse);
        else if(reverse) wheelBase.mecanumDrive(gp.left_stick_x, -gp.left_stick_y, Math.pow(gp.right_stick_x, 3)/1.5, slomo);
        else wheelBase.mecanumDrive(-gp.left_stick_y, -gp.left_stick_x, Math.pow(gp.right_stick_x, 3)/1.5 , slomo);

//        if(FOD) telemetry.addData("Drive: ", "FOD");
//        else telemetry.addData("Drive: ", "ROD");



        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


//        if (buttonChecker.get(right_trigger)) {
//            intakeRIn = !intakeRIn;
//            intakeROut = false;
//        }

        if (buttonChecker.get(left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

//        if (buttonChecker.get(right_trigger)) {
//            intakeLIn = !intakeLIn;
//            intakeLOut = false;
//        }

        if(gp.back) {
            intakeLOut = true;
            //intakeROut = true;
            intakeLIn = false;
            intakeRIn = false;
        }
        else {
            intakeLOut = false;
            intakeROut = false;
        }


        if (buttonChecker.get(right_bumper)) {
            intake.controlLIntake(false, false);
            intake.controlRIntake(false, false);
            intakeLIn = false;
            intakeRIn = false;
            //shooter.shoot(true, 3);
            shooter.feedShooter();
            shooter.resetShooter();
            shooter.feedShooter();
            shooter.resetShooter();
            shooter.feedShooter();
            shooter.resetShooter();
            shooter.resetShooter();
            shooter.stopFlyWheel();
        }


        if (buttonChecker.get(right_trigger)) {
            shooter.runFlyWheelHigh();
        }


        if (buttonChecker.get(left_bumper)) {
            intake.controlLIntake(false, false);
            intake.controlRIntake(false, false);
            intakeLIn = false;
            intakeRIn = false;
            shooter.shoot(false, 1);
        }

//        if (buttonChecker.get(right_trigger)) HIGH = !HIGH;
//
//        if(HIGH) shooter.runFlyWheel(highGoalVelocity);
//        else shooter.stopFlyWheel();



        if(buttonChecker.get(b)) wobbleArmDown = !wobbleArmDown;

        wobbleArm.ControlWobbleArm(wobbleArmDown);


        if(buttonChecker.get(y)) wobbleClawClosed = !wobbleClawClosed;

        wobbleArm.ControlWobbleClaw(wobbleClawClosed);



        //telemetry.addData("Heading: ", gyro.getHeading());

        if(buttonChecker.get(dpad_down)) shooter.changeHGV(10);

        if(buttonChecker.get(dpad_up)) shooter.changeHGV(-10);

        if(buttonChecker.get(dpad_left)) shooter.changePSV(-10);

        if(buttonChecker.get(dpad_right)) shooter.changePSV(10);



//        if(buttonChecker.get(dpad_down)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p , shooter.getPID().i,shooter.getPID().d-.1,shooter.getPID().f);
//        if(buttonChecker.get(dpad_up)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d+.1,shooter.getPID().f);
//
//        if(buttonChecker.get(dpad_left)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d,shooter.getPID().f-.1);
//        if(buttonChecker.get(dpad_right)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d,shooter.getPID().f+.1);


//        if(buttonChecker.get(dpad_left)) powerShotVelocity -= 10;
//
//        if(buttonChecker.get(dpad_right)) powerShotVelocity += 10;

//        telemetry.addData("Velocity: ", shooter.getVelocity());
//
        telemetry.addData("HGV: ", highGoalVelocity);
        telemetry.addData("PSV: ", powerShotVelocity);

//        TelemetryPacket packet = new TelemetryPacket();
//        packet.put("Velocity", currentVelocity);
//        packet.put("HighGoalVelocity", highGoalVelocity);
//        dashboard.sendTelemetryPacket(packet);

//        telemetry.addData("P: ", shooter.getPID().p);
//        telemetry.addData("I: ", shooter.getPID().i);
//        telemetry.addData("D: ", shooter.getPID().d);
//        telemetry.addData("F: ", shooter.getPID().f);
//
//
//
//
//        telemetry.update();


    }


}
