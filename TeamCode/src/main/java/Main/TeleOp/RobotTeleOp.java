package Main.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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

/**TODO:
 * -Add Stopper Bar
 *      -Start off with using a button to lower and raise Bar (DPAD button or remove
 *      the reverse functionality and use A) or use a second controller
 *      -Automate raising/lowering the bar to raise the bar when the right trigger
 *      is pressed and the flyWheel is turned on.
 *          -Add a boolean that is toggled when right trigger is pressed
 *          and after you shoot
 * -Add Power Shots
 *      -Overlaps with needed changes to Shooter Class
 *      -Tune PID code for Power Shots in the'PIDTuningTeleOp' class in the Testing Package
 *          -Focus on ACCURACY
 *
 */


/**Our Competition TeleOp:
 * We use a hashMap called buttonChecker to update gamepad boolean values,
 * field orientated drive with slomo and reverse features,
 * x for toggling slomo,
 * a for toggling reverse (changing the forward direction for field orientated drive),
 * left Trigger for intaking in and back for spitting out,
 * right trigger for setting the flyWheel to the power for scoring high goals,
 * right bumper for shooting into the high goal,
 * right bumper for setting the flyWheel power and shooting for power shots,
 * b for toggling the wobble arm up and down
 * y for toggling the wobble claw open and closed
 */

@TeleOp(name = "TeleOp", group = "competition")
public class RobotTeleOp extends Robot {

    //Booleans for use in TeleOp
    boolean intakeLIn = false, intakeLOut = false;

    boolean wobbleArmDown = false, wobbleClawOpen = true;

    boolean stopperDown = true;

    boolean slomo = false, reverse = false;

    boolean FOD = true;

    @Override
    public void loop() {

        //Gamepad Button Updater
        buttonChecker = updateBooleans(gp, buttonChecker);


        //Drive
        if (buttonChecker.get(x)) slomo = !slomo;

        if(buttonChecker.get(a)) reverse = !reverse;

        if(buttonChecker.get(left_stick_button)) FOD = !FOD;

        wheelBase.fieldOrientatedDrive(gp, gyro, slomo, reverse);


        //Intake
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


        //Shoot
        if (buttonChecker.get(right_trigger)) {
            shooter.setHighGoalPID();
            shooter.runFlyWheelHigh();
        }

        if (buttonChecker.get(right_bumper)) {
            shooter.setHighGoalPID();

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

            shooter.shootPS();

        }


        //Wobble Arm
        if(buttonChecker.get(b)) wobbleArmDown = !wobbleArmDown;

        wobbleArm.ControlWobbleArm(wobbleArmDown);

        if(buttonChecker.get(y)) wobbleClawOpen = !wobbleClawOpen;

        wobbleArm.ControlWobbleClaw(wobbleClawOpen);


        //stopper
        if(buttonChecker.get(dpad_left)) stopperDown = !stopperDown;

        stopper.controlStopper(stopperDown);

        
        //tuner
        if(buttonChecker.get(dpad_right)){
            shooter.updateIndex(true);
        }

        if(buttonChecker.get(dpad_up)){
            shooter.updateValues(true);
        } else if(buttonChecker.get(dpad_down)){
            shooter.updateValues(false);
        }





        telemetry.addData("Mode: ", shooter.getMode(shooter.getIndex()));
        telemetry.addData("Value: ", shooter.getValue(shooter.getIndex()));
        telemetry.addData("StopperDown: ", stopperDown);
        telemetry.update();

    }


}
