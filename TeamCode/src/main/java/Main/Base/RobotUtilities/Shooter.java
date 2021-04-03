package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {

    /** Reverse flyWheel motor so positive powers will shoot out **/

    private DcMotor flyWheel;

    private Servo feederServo;

    double feederServoFeedPosition = 0.0;
    double feederServoPrepPosition = 0.25;
    double feederServoPosition = feederServoPrepPosition;

    double highGoalVelocity = 0.05;
    double powerShotVelocity = 0.0055;

    private ElapsedTime runTime = new ElapsedTime();

    public Shooter(DcMotor fwMotor, Servo fServo) {
        flyWheel = fwMotor;
        feederServo = fServo;

        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void shoot(boolean highGoal, int times){
        resetShooter();
        flyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(highGoal) flyWheel.setPower(highGoalVelocity);
        else flyWheel.setPower(powerShotVelocity);

        runTime.reset();
        while(runTime.milliseconds() < 2500) { }

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();
        }
        resetShooter();
        flyWheel.setPower(0);
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


    public void setFeederServoPosition(boolean feed) {
        if(feed) feederServoPosition = feederServoFeedPosition;
        else feederServoPosition = feederServoPrepPosition;

        feederServo.setPosition(feederServoPosition);
    }


    public void runFlywheels(boolean spinning) {
        if(spinning) {
            flyWheel.setPower(1);
        }
        else {
            flyWheel.setPower(0);
        }
    }

    public void setFlywheelPower(boolean HighGoal, boolean on) {

        if (on) {
            if (HighGoal) flyWheel.setPower(highGoalVelocity);
            else flyWheel.setPower(powerShotVelocity);
        }
        else flyWheel.setPower(0);

    }


    public void changePSV(double num){
        powerShotVelocity += num;
    }

    public double getPSV(){
        return powerShotVelocity;
    }

    public void changeHGV(double num){
        highGoalVelocity += num;
    }

    public double getHGV(){
        return highGoalVelocity;
    }

}