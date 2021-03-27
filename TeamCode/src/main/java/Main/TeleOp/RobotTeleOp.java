package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.HelperClasses.Button;
import Main.Base.HelperClasses.Button.*;
import Main.Base.Robot;
import Main.Base.RobotUtilities.WobbleArm;


@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {
//
//    boolean lastA = false, lastB = false, lastX = false, lastY = false, lastRight = false, lastLeft = false, lastRT = false, lastLT = false;
//
//    boolean Ltpressed = false, Rtpressed = false;

    boolean intakeLIn, intakeLOut, intakeRIn, intakeROut;

    boolean wobbleArmUp = true, wobbleClawOpen = true;

    boolean slomo = false;

    @Override
    public void loop() {

        if (gp.a & !buttonChecker.get(Button.a)) slomo = !slomo;

        wheelBase.mecanumDrive(-gp.left_stick_y, -gp.left_stick_x, gp.right_stick_x, slomo);


        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


        if (gp.right_trigger > 0.2 & !buttonChecker.get(Button.right_trigger)) {
            intakeRIn = !intakeRIn;
            intakeROut = false;
        }

        if (gp.left_trigger > 0.2 & !buttonChecker.get(Button.left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

        if(gp.back) {
            intakeRIn = false;
            intakeLIn = false;
            intakeROut = true;
            intakeLOut = true;
        }


        if (gp.right_bumper && !buttonChecker.get(Button.right_bumper)) shooter.shoot(true, 3);

        if (gp.left_bumper && !buttonChecker.get(Button.left_bumper)) shooter.shoot(false, 1);


        if(gp.x && !buttonChecker.get(Button.x)) wobbleArm.ControlWobbleArm(!wobbleArmUp);

        if(gp.y && !buttonChecker.get(Button.y)) wobbleArm.ControlWobbleClaw(!wobbleClawOpen);


        boolUpdater.updateBooleans(gp);


        telemetry.addData("Heading: ", gyro.getHeading());

//        if (gp.left_bumper) lastLeft = true;
//        else lastLeft = false;
//
//        if (gp.right_bumper) lastRight = true;
//        else lastRight = false;
//
//        if (gp.a) lastA = true;
//        else lastA = false;
//
//        if (gp.b) lastB = true;
//        else lastB = false;
//
//        if (gp.x) lastX = true;
//        else lastX = false;
//
//        if (gp.y) lastY = true;
//        else lastY = false;
//
//        if(gp.left_trigger > 0.1) lastLT = true;
//        else lastLT = false;
//
//        if(gp.right_trigger > 0.1) lastRT = true;
//        else lastRT = false;

    }


}
