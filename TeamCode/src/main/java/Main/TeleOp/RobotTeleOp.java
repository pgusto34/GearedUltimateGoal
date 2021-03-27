package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.Robot;
import Main.Base.RobotUtilities.WobbleArm;


@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    boolean lastA = false, lastB = false, lastX = false, lastY = false, lastRight = false, lastLeft = false, lastRT = false, lastLT = false;

    boolean Ltpressed = false, Rtpressed = false;

    boolean intakeLIn, intakeLOut, intakeRIn, intakeROut;

    boolean wobbleArmUp = true, wobbleClawOpen = true;

    @Override
    public void loop() {

        wheelBase.mecanumDrive(-gp.left_stick_y, -gp.left_stick_x, gp.right_stick_x);

        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


        if (gp.right_trigger > 0.2 & !lastRT) {
            intakeRIn = !intakeRIn;
            intakeROut = false;
        }

        if (gp.left_trigger > 0.2 & !lastLT) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

        if(gp.back) {
            intakeRIn = false;
            intakeLIn = false;
            intakeROut = true;
            intakeLOut = true;
        }

        if (gp.right_bumper && !lastRight) shooter.shoot(true, 3);


        if (gp.left_bumper && !lastLeft) shooter.shoot(false, 1);


        if(gp.a && !lastA) wobbleArm.ControlWobbleArm(!wobbleArmUp);

        if(gp.y && !lastY) wobbleArm.ControlWobbleClaw(!wobbleClawOpen);


        if (gp.left_bumper) lastLeft = true;
        else lastLeft = false;

        if (gp.right_bumper) lastRight = true;
        else lastRight = false;

        if (gp.a) lastA = true;
        else lastA = false;

        if (gp.b) lastB = true;
        else lastB = false;

        if (gp.x) lastX = true;
        else lastX = false;

        if (gp.y) lastY = true;
        else lastY = false;

        if(gp.left_trigger > 0.1) lastLT = true;
        else lastLT = false;

        if(gp.right_trigger > 0.1) lastRT = true;
        else lastRT = false;

        //boolUpdater.updateBooleans(gp);

        telemetry.addData("Heading: ", gyro.getHeading());
    }


}
