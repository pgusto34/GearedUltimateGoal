package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Main.Base.Robot;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class WheelBase {

    //We are using four drive train motors and a mecanum drive

    /**  lF  |-----|  rF

         lB  |-----|  rB**/

    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    Double lF, rF, lB, rB, maxVector;

    static final double SLOMO_DIVIDER = 2;

    static final double ROBOT_DIAMETER = 20;

    static final double TICKS_PER_REVOLUTION = 8192;
    static final double WHEEL_DIAMETER = 1.45;
    static final double TICKS_PER_INCH = (TICKS_PER_REVOLUTION / (WHEEL_DIAMETER * Math.PI));


    public WheelBase(DcMotor lF, DcMotor lB, DcMotor rF, DcMotor rB) {
        leftFront = lF;
        leftBack = lB;
        rightFront = rF;
        rightBack = rB;

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setModeAll(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setZeroPowerBehaviorAll(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public void setModeAll(DcMotor.RunMode runMode) {
        leftFront.setMode(runMode);
        leftBack.setMode(runMode);
        rightFront.setMode(runMode);
        rightBack.setMode(runMode);
    }


    public void setZeroPowerBehaviorAll(DcMotor.ZeroPowerBehavior zpb) {
        leftFront.setZeroPowerBehavior(zpb);
        leftBack.setZeroPowerBehavior(zpb);
        rightFront.setZeroPowerBehavior(zpb);
        rightBack.setZeroPowerBehavior(zpb);
    }


    public void setMotorPowers(double leftFrontPower, double leftBackPower, double rightFrontPower, double rightBackPower) {
        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
    }


    public void mecanumDrive(double leftX, double leftY, double rightX, boolean slomo) {
        lF = -leftX - leftY - rightX;
        rF = -leftX + leftY - rightX;
        lB = -leftX + leftY + rightX;
        rB = -leftX - leftY + rightX;


        maxVector = Math.max(Math.max(Math.abs(lF), Math.abs(rF)),
                Math.max(Math.abs(lB), Math.abs(rB)));

        maxVector = maxVector > 1 ? maxVector : 1;

        lF /= maxVector;
        rF /= maxVector;
        lB /= maxVector;
        rB /= maxVector;

        if(slomo) setMotorPowers(lF/SLOMO_DIVIDER, lB/SLOMO_DIVIDER, rF/SLOMO_DIVIDER, rB/SLOMO_DIVIDER);
        else setMotorPowers(lF, lB, rF, rB);

    }


    public void moveBot(String direction, double distance, double speed) {


        int newTargetLF;
        int newTargetLB;
        int newTargetRF;
        int newTargetRB;

        speed = abs(speed) >= 1 ? 1 : speed;

        double travelTicks = distance * TICKS_PER_INCH;

        switch (direction) {
            case "f":
                newTargetLF = leftFront.getCurrentPosition() - (int) (travelTicks);
                newTargetLB = leftBack.getCurrentPosition() + (int) (travelTicks);
                newTargetRF = rightFront.getCurrentPosition() + (int) (travelTicks);
                newTargetRB = rightBack.getCurrentPosition() - (int) (travelTicks);
                break;
            case "r":
                newTargetLF = leftFront.getCurrentPosition() - (int) (travelTicks);
                newTargetLB = leftBack.getCurrentPosition() - (int) (travelTicks);
                newTargetRF = rightFront.getCurrentPosition() - (int) (travelTicks);
                newTargetRB = rightBack.getCurrentPosition() - (int) (travelTicks);
                break;
            case "l":
                newTargetLF = leftFront.getCurrentPosition() + (int) (travelTicks);
                newTargetLB = leftBack.getCurrentPosition() + (int) (travelTicks);
                newTargetRF = rightFront.getCurrentPosition() + (int) (travelTicks);
                newTargetRB = rightBack.getCurrentPosition() + (int) (travelTicks);
                break;
            case "b":
                newTargetLF = leftFront.getCurrentPosition() + (int) (travelTicks);
                newTargetLB = leftBack.getCurrentPosition() - (int) (travelTicks);
                newTargetRF = rightFront.getCurrentPosition() - (int) (travelTicks);
                newTargetRB = rightBack.getCurrentPosition() + (int) (travelTicks);
                break;
            default:
                newTargetLF = leftFront.getCurrentPosition();
                newTargetLB = leftBack.getCurrentPosition();
                newTargetRF = rightFront.getCurrentPosition();
                newTargetRB = rightBack.getCurrentPosition();
        }


        rightFront.setTargetPosition(newTargetRF);
        rightBack.setTargetPosition(newTargetRB);
        leftFront.setTargetPosition(newTargetLF);
        leftBack.setTargetPosition(newTargetLB);

        setModeAll(RUN_TO_POSITION);

        setMotorPowers(speed, speed, speed, speed);

        try {
            while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()) {

            }
        } catch (Exception e) {
            telemetry.addData("Error", e);
        }

        setMotorPowers(0, 0, 0, 0);

        setModeAll(RUN_WITHOUT_ENCODER);
    }


    public void turnBot(double speed, double degrees, boolean clockwise) {


        degrees = ((ROBOT_DIAMETER * Math.PI) / 360) * degrees;

        int newTargetLF;
        int newTargetLB;
        int newTargetRF;
        int newTargetRB;

        double travelTicks = degrees * TICKS_PER_INCH;

        if (clockwise) {
            newTargetLF = leftFront.getCurrentPosition() - (int) (travelTicks);
            newTargetLB = leftBack.getCurrentPosition() + (int) (travelTicks);
            newTargetRF = rightFront.getCurrentPosition() - (int) (travelTicks);
            newTargetRB = rightBack.getCurrentPosition() + (int) (travelTicks);
        } else {
            newTargetLF = leftFront.getCurrentPosition() + (int) (travelTicks);
            newTargetLB = leftBack.getCurrentPosition() - (int) (travelTicks);
            newTargetRF = rightFront.getCurrentPosition() + (int) (travelTicks);
            newTargetRB = rightBack.getCurrentPosition() - (int) (travelTicks);
        }

        leftFront.setTargetPosition(newTargetLF);
        leftBack.setTargetPosition(newTargetLB);
        rightFront.setTargetPosition(newTargetRF);
        rightBack.setTargetPosition(newTargetRB);

        setModeAll(RUN_TO_POSITION);

        setMotorPowers(speed, speed, speed, speed);


        try {
            while (leftFront.isBusy() && leftBack.isBusy() && rightFront.isBusy() && rightBack.isBusy()) {

            }
        } catch (Exception e) {
            telemetry.addData("Error", e);
        }

        setMotorPowers(0, 0, 0, 0);

        setModeAll(RUN_WITHOUT_ENCODER);
    }

    
}
