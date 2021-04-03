package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.HelperClasses.BooleanUpdater;
import Main.Base.HelperClasses.Button;
import Main.Base.HelperClasses.Button.*;
import Main.Base.Robot;
import Main.Base.RobotUtilities.WobbleArm;

import static Main.Base.HelperClasses.BooleanUpdater.*;
import static Main.Base.HelperClasses.Button.*;


@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    boolean intakeLIn = false, intakeLOut = false, intakeRIn = false, intakeROut = false;

    boolean wobbleArmDown = false, wobbleClawClosed = false;

    boolean slomo = false, reverse = false;

    boolean FOD = true;

    @Override
    public void loop() {

        buttonChecker = updateBooleans(gp, buttonChecker);



        if (buttonChecker.get(x)) slomo = !slomo;

        if(buttonChecker.get(a)) reverse = !reverse;

        if(buttonChecker.get(left_stick_button)) FOD = !FOD;

        if(FOD) wheelBase.fieldOrientatedDrive(gp, gyro, slomo, reverse);
        else if(reverse) wheelBase.mecanumDrive(gp.left_stick_x, -gp.left_stick_y, gp.right_stick_x, slomo);
        else wheelBase.mecanumDrive(-gp.left_stick_y, -gp.left_stick_x, gp.right_stick_x, slomo);

        if(FOD) telemetry.addData("Drive: ", "FOD");
        else telemetry.addData("Drive: ", "ROD");



        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


        if (buttonChecker.get(right_trigger)) {
            intakeRIn = !intakeRIn;
            intakeROut = false;
        }

        if (buttonChecker.get(left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

        if(gp.back) {
            intakeLOut = true;
            intakeROut = true;
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
            shooter.shoot(true, 3);
        }

        if (buttonChecker.get(left_bumper)) {
            intake.controlLIntake(false, false);
            intake.controlRIntake(false, false);
            intakeLIn = false;
            intakeRIn = false;
            shooter.shoot(false, 1);
        }

        if(gp.right_stick_button) shooter.setFlywheelPower(true, true);



        if(buttonChecker.get(b)) wobbleArmDown = !wobbleArmDown;

        wobbleArm.ControlWobbleArm(wobbleArmDown);


        if(buttonChecker.get(y)) wobbleClawClosed = !wobbleClawClosed;

        wobbleArm.ControlWobbleClaw(wobbleClawClosed);



        telemetry.addData("Heading: ", gyro.getHeading());

    }


}
