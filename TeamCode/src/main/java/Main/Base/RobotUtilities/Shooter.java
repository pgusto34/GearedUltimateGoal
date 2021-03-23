package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {

    DcMotor flyWheel;

    Servo feederServo;

    double feederServoFeedPosition = 0.25;
    double feederServoPrepPosition = 0.0;
    double feederServoPosition = feederServoPrepPosition;

    double highGoalVelocity = 0.7;
    double powerShotVelocity = 0.6;

    private ElapsedTime runTime = new ElapsedTime();

    public Shooter(DcMotor fwMotor, Servo fServo) {
        flyWheel = fwMotor;
        feederServo = fServo;
    }

    public void shoot(boolean highGoal, int times){
        flyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(highGoal) flyWheel.setPower(highGoalVelocity);
        else flyWheel.setPower(powerShotVelocity);

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();
        }
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

}