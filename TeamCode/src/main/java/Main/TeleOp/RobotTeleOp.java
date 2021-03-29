package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.HelperClasses.BooleanUpdater;
import Main.Base.HelperClasses.Button;
import Main.Base.HelperClasses.Button.*;
import Main.Base.Robot;
import Main.Base.RobotUtilities.WobbleArm;

import static Main.Base.HelperClasses.BooleanUpdater.*;


@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    boolean intakeLIn, intakeLOut, intakeRIn, intakeROut;

    boolean wobbleArmUp = true, wobbleClawOpen = true;

    boolean slomo = false;

    @Override
    public void loop() {

        buttonChecker = updateBooleans(gamepad1, buttonChecker);

        if (buttonChecker.get(Button.a)) slomo = !slomo;

        wheelBase.mecanumDrive(-gp.left_stick_y, -gp.left_stick_x, gp.right_stick_x, slomo);


        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


        if (buttonChecker.get(Button.right_trigger)) {
            intakeRIn = !intakeRIn;
            intakeROut = false;
        }

        if (buttonChecker.get(Button.left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

        if(gp.back) {
            intakeRIn = false;
            intakeLIn = false;
            intakeROut = true;
            intakeLOut = true;
        }


        if (buttonChecker.get(Button.right_bumper)) shooter.shoot(true, 3);

        if (buttonChecker.get(Button.left_bumper)) shooter.shoot(false, 1);


        if(buttonChecker.get(Button.b)) wobbleArm.ControlWobbleArm(!wobbleArmUp);

        if(buttonChecker.get(Button.y)) wobbleArm.ControlWobbleClaw(!wobbleClawOpen);


        telemetry.addData("Heading: ", gyro.getHeading());

        telemetry.addData("a: ", buttonChecker.get(Button.a));

        telemetry.addData("Gamepad a: ", gp.a);
    }


}
