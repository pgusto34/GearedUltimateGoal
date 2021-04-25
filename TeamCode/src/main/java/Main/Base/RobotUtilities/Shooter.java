package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Main.Base.Robot;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;

public class Shooter extends Robot {

    int currentIndex = 0;
    public String[] modes = {"highGoalV", "PowerShotV", "p", "i", "d", "f", "PSp", "PSi", "PSd", "PSf"};
    public double[] values = {1000,            700,       14,  3,  2.5, 5.7,  1.5,     1.2,     0,     0};
    public double[] changers = {25,            5,       0.1, 0.1, 0.1, 0.1,  0.1,   0.1,   0.1,  0.1};

    //Mode Variables
//        public double highGoalV = shooter.getHGV();
//        public double powerShotV = shooter.getPSV();
//        public double p = shooter.getPIDCoefients().p;
//        public double i = shooter.getPIDCoefients().i;
//        public double d = shooter.getPIDCoefients().d;
//        public double f = shooter.getPIDCoefients().f;


    /** Reverse flyWheel motor so positive powers will shoot out **/

    public DcMotorEx flyWheel;

    private Servo feederServo;

    double feederServoFeedPosition = 0.0;
    double feederServoPrepPosition = 0.25;
    double feederServoPosition = feederServoPrepPosition;

    public double highGoalVelocity = values[0];
    public double powerShotVelocity = values[1];

//    public static double highGoalVelocity = 2000;
//    public static double powerShotVelocity = 250;

    double TICKS_PER_REV = 28;

    private ElapsedTime runTime = new ElapsedTime();


    public Shooter(DcMotorEx fwMotor, Servo fServo) {

        flyWheel = fwMotor;
        feederServo = fServo;

        flyWheel.setDirection(DcMotorSimple.Direction.REVERSE);

    }



    //Sets Motor either high goal or power shot velocity and shoots a given number of times

    public void shootPS(){
        //Tuning and Variables
        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flyWheel.setVelocityPIDFCoefficients(values[6],values[7],values[8],values[9]);

        resetShooter();
        flyWheel.setVelocity(values[1]);

        runTime.reset();
        while(runTime.milliseconds() < 1400) { }
        feedShooter();

        resetShooter();
        flyWheel.setPower(0);
        flyWheel.setVelocity(0);

    }


    public void shootHG(){
        //Tuning and Variables
        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flyWheel.setVelocityPIDFCoefficients(values[2],values[3],values[4],values[5]);

        resetShooter();
        flyWheel.setVelocity(values[0]);

        runTime.reset();
        while(runTime.milliseconds() < 1400) { }

        for(int i = 0; i < 3; i++) {
            feedShooter();
            resetShooter();
        }
        resetShooter();
        flyWheel.setPower(0);
        flyWheel.setVelocity(0);

    }

    public void setHighGoalPID(){
        flyWheel.setVelocityPIDFCoefficients(values[2],values[3],values[4],values[5]);
    }


//    //Sets motor velocity based on input and shoots a given number of times
//    public void shoot(double velocity, int times){
//
//        resetShooter();
//        flyWheel.setMode(RUN_USING_ENCODER);
//        runTime.reset();
//        runFlyWheel(velocity);
//
//        runTime.reset();
//        while(runTime.milliseconds() < 1000) { }
//
//        for(int i = 0; i < times; i++) {
//            feedShooter();
//            resetShooter();
//        }
//        resetShooter();
//        flyWheel.setPower(0);
//        flyWheel.setVelocity(0);
//
//        firstSetPID();
//
//    }


    //Sets servo to feed position
    public void feedShooter() {

        runTime.reset();

        while (runTime.milliseconds() > 0 && runTime.milliseconds() < 250 ) {
            feederServo.setPosition(feederServoFeedPosition);
        }

    }


    //Sets servo to reset position
    public void resetShooter() {

        runTime.reset();

        while (runTime.milliseconds() > 0 && runTime.milliseconds() < 250 ) {
            feederServo.setPosition(feederServoPrepPosition);
        }

    }


    //Sets servo to feed position then to reset position
    public void flickFeeder() {

        feedShooter();
        resetShooter();

    }


    //Sets servo to feed position then to reset position a given number of times
    public void flickFeeder(int times) {

        for(int i = 0; i < times; i++) {
            feedShooter();
            resetShooter();

        }
    }


    //Sets flywheel to a velocity
    public void runFlyWheel(double v) {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setVelocity(v);

    }


    //Sets flywheel to the velocity for a high goal
    public void runFlyWheelHigh() {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setVelocity(highGoalVelocity);

    }


    //Sets flywheel to the velocity for a power shot
    public void runFlyWheelPower() {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setVelocity(powerShotVelocity);

    }


    //Stops flywheel
    public void stopFlyWheel() {

        flyWheel.setMode(RUN_USING_ENCODER);
        flyWheel.setPower(0);

    }


    //Colton PID Stuff that I'm not going to touch
    public void setPID(PIDFCoefficients coEs){ flyWheel.setPIDFCoefficients(RUN_USING_ENCODER, coEs); }


    public PIDFCoefficients getPID(){ return flyWheel.getPIDFCoefficients(RUN_USING_ENCODER); }


    public double getVelocity(){ return flyWheel.getVelocity(); }


    public void changePSV(double num){ powerShotVelocity += num; }


    public double getPSV(){ return powerShotVelocity; }


    public void changeHGV(double num){ highGoalVelocity += num; }


    public double getHGV(){ return highGoalVelocity; }



        public void updateIndex(boolean up){
            if(up){
                if(currentIndex == modes.length-1){
                    currentIndex = 0;
                } else {
                    currentIndex++;
                }
            } else {
                if(currentIndex == 0){
                    currentIndex = modes.length-1;
                }
                else{
                    currentIndex--;
                }
            }
        }




        public void updateValues(boolean increase){
            if(increase){
                values[currentIndex] += changers[currentIndex];
            } else {
                values[currentIndex] -= changers[currentIndex];
            }
        }

        public int getIndex(){
            return  currentIndex;
        }

        public double getValue(int index){
            return values[index];
        }

        public String getMode(int index){
            return modes[index];
        }
    }


