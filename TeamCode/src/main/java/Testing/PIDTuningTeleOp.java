package Testing;



import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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


@TeleOp(name = "PID Tuning", group = "testing")
public class PIDTuningTeleOp extends Robot {

    boolean intakeLIn = false, intakeLOut = false, intakeRIn = false, intakeROut = false;

    boolean wobbleArmDown = false, wobbleClawClosed = false;

    boolean slomo = false, reverse = true;

    boolean FOD = true;

    boolean HIGH = false;

    public static double highGoalVelocity = 1900;
    public static double powerShotVelocity = 300;
    public static double currentVelocity = 0;


    @Override
    public void init() {

        super.init();
        shooter.firstSetPID();

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


        intake.controlLIntake(intakeLIn, intakeLOut);
        intake.controlRIntake(intakeRIn, intakeROut);


        if (buttonChecker.get(left_trigger)) {
            intakeLIn = !intakeLIn;
            intakeLOut = false;
        }


        if(gp.back) {
            intakeLOut = true;
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


        if(buttonChecker.get(b)) wobbleArmDown = !wobbleArmDown;

        wobbleArm.ControlWobbleArm(wobbleArmDown);


        if(buttonChecker.get(y)) wobbleClawClosed = !wobbleClawClosed;

        wobbleArm.ControlWobbleClaw(wobbleClawClosed);


        if(buttonChecker.get(dpad_down)) shooter.changeHGV(10);

        if(buttonChecker.get(dpad_up)) shooter.changeHGV(-10);

        if(buttonChecker.get(dpad_left)) shooter.changePSV(-10);

        if(buttonChecker.get(dpad_right)) shooter.changePSV(10);


        if(buttonChecker.get(dpad_down)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p , shooter.getPID().i,shooter.getPID().d-.1,shooter.getPID().f);
        if(buttonChecker.get(dpad_up)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d+.1,shooter.getPID().f);

        if(buttonChecker.get(dpad_left)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d,shooter.getPID().f-.1);
        if(buttonChecker.get(dpad_right)) flyWheel.setVelocityPIDFCoefficients(shooter.getPID().p, shooter.getPID().i,shooter.getPID().d,shooter.getPID().f+.1);


        if(buttonChecker.get(dpad_left)) powerShotVelocity -= 10;

        if(buttonChecker.get(dpad_right)) powerShotVelocity += 10;


        telemetry.addData("Velocity: ", shooter.getVelocity());

        telemetry.addData("HGV: ", highGoalVelocity);
        telemetry.addData("PSV: ", powerShotVelocity);

        telemetry.addData("P: ", shooter.getPID().p);
        telemetry.addData("I: ", shooter.getPID().i);
        telemetry.addData("D: ", shooter.getPID().d);
        telemetry.addData("F: ", shooter.getPID().f);

        telemetry.update();

    }


}
