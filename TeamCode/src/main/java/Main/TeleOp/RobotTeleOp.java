package Main.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.Robot;

import static Main.Base.HelperClasses.BooleanUpdater.updateBooleans;
import static Main.Base.HelperClasses.Button.a;
import static Main.Base.HelperClasses.Button.b;
import static Main.Base.HelperClasses.Button.dpad_down;
import static Main.Base.HelperClasses.Button.dpad_left;
import static Main.Base.HelperClasses.Button.dpad_right;
import static Main.Base.HelperClasses.Button.dpad_up;
import static Main.Base.HelperClasses.Button.left_bumper;
import static Main.Base.HelperClasses.Button.left_stick_button;
import static Main.Base.HelperClasses.Button.left_trigger;
import static Main.Base.HelperClasses.Button.right_bumper;
import static Main.Base.HelperClasses.Button.right_trigger;
import static Main.Base.HelperClasses.Button.x;
import static Main.Base.HelperClasses.Button.y;


@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    boolean intakeLIn = false, intakeLOut = false;

    boolean wobbleArmDown = false, wobbleClawOpen = true;

    boolean slomo = false, reverse = false;

    boolean FOD = true;


    @Override
    public void loop() {

        buttonChecker = updateBooleans(gp, buttonChecker);


        if (buttonChecker.get(x)) slomo = !slomo;

        if(buttonChecker.get(a)) reverse = !reverse;

        if(buttonChecker.get(left_stick_button)) FOD = !FOD;

        wheelBase.fieldOrientatedDrive(gp, gyro, slomo, reverse);


        if (buttonChecker.get(left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }

        if(gp.back) {
            intakeLOut = true;
            intakeLIn = false;
        }
        else {
            intakeLOut = false;
        }

        intake.controlLIntake(intakeLIn, intakeLOut);


        if (buttonChecker.get(right_trigger)) { shooter.runFlyWheelHigh(); }

        if (buttonChecker.get(right_bumper)) {

            intake.controlLIntake(false, false);
            intakeLIn = false;

            shooter.flickFeeder();
            shooter.flickFeeder();
            shooter.flickFeeder();
            shooter.stopFlyWheel();

        }

        if (buttonChecker.get(left_bumper)) {

            intake.controlLIntake(false, false);
            intake.controlRIntake(false, false);
            intakeLIn = false;

            shooter.shoot(false, 1);

        }


        if(buttonChecker.get(b)) wobbleArmDown = !wobbleArmDown;

        wobbleArm.ControlWobbleArm(wobbleArmDown);

        if(buttonChecker.get(y)) wobbleClawOpen = !wobbleClawOpen;

        wobbleArm.ControlWobbleClaw(wobbleClawOpen);

    }


}
