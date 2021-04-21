package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;

public class Shooter {

    /** Reverse flyWheel motor so positive powers will shoot out **/

    public DcMotorEx flyWheel;

    private Servo feederServo;

    double feederServoFeedPosition = 0.0;
    double feederServoPrepPosition = 0.25;
    double feederServoPosition = feederServoPrepPosition;

    public static double highGoalVelocity = 1000;
    public static double powerShotVelocity = 80;

//    public static double highGoalVelocity = 2000;
//    public static double powerShotVelocity = 250;

    double TICKS_PER_REV = 28;

    private ElapsedTime runTime = new ElapsedTime();


    public Shooter(DcMotorEx fwMotor, Servo fServo) {

        flyWheel = fwMotor;
        feederServo = fServo;

        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    public void firstSetPID(){

        /** Real **/
       flyWheel.setVelocityPIDFCoefficients(14,3,2.5,5.7);

        /** Practice **/
//        highGoalVelocity = 3700;
//        flyWheel.setVelocityPIDFCoefficients(18,13,3.6,6.4);

    }


    public void shoot(boolean highGoal, int times){

        double velocity;
        if(highGoal) velocity = highGoalVelocity;
        else velocity = powerShotVelocity;

        resetShooter();
        flyWheel.setMode(RUN_USING_ENCODER);
        runTime.reset();
        flyWheel.setVelocity(velocity);

        runTime.reset();
        while(runTime.milliseconds() < 1400) { }

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();
        }
        resetShooter();
        flyWheel.setVelocity(0);
        flyWheel.setPower(0);

    }


    public void shoot(double velocity, int times){

        resetShooter();
        flyWheel.setMode(RUN_USING_ENCODER);
        runTime.reset();
        runFlyWheel(velocity);

        runTime.reset();
        while(runTime.milliseconds() < 1000) { }

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();
        }
        resetShooter();
        flyWheel.setVelocity(0);
        flyWheel.setPower(0);
        firstSetPID();

    }


    public void feedShooter() {

        runTime.reset();

        while (runTime.milliseconds() > 0 && runTime.milliseconds() < 250 ) {
            feederServo.setPosition(feederServoFeedPosition);
        }

    }


    public void resetShooter() {

        runTime.reset();

        while (runTime.milliseconds() > 0 && runTime.milliseconds() < 250 ) {
            feederServo.setPosition(feederServoPrepPosition);
        }

    }


    public void flickFeeder() {

        feedShooter();
        resetShooter();

    }


    public void flickFeeder(int times) {

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();

        }
    }


    public void runFlyWheel(double v) {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setVelocity(v);

    }


    public void runFlyWheelHigh() {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setVelocity(highGoalVelocity);

    }


    public void stopFlyWheel() {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setPower(0);

    }


    public void setPID(PIDFCoefficients coEs){ flyWheel.setPIDFCoefficients(RUN_USING_ENCODER, coEs); }


    public PIDFCoefficients getPID(){ return flyWheel.getPIDFCoefficients(RUN_USING_ENCODER); }


    public double getVelocity(){ return flyWheel.getVelocity(); }


    public void changePSV(double num){ powerShotVelocity += num; }


    public double getPSV(){ return powerShotVelocity; }


    public void changeHGV(double num){ highGoalVelocity += num; }


    public double getHGV(){ return highGoalVelocity; }

}