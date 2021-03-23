package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

@TeleOp(name = "FODEXAMPLE")
public class FODExample extends Robot {

    public void fieldOrientatedDrive() {
        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;

        double heading = gyro.getHeading();
        double headingRadians = Math.toRadians(heading);

        double temp = y * Math.cos(headingRadians) + x * Math.sin(headingRadians);
        x = (-y) * Math.sin(headingRadians) + x * Math.cos(headingRadians);
        y = temp;

        wheelBase.mecanumDrive(x, y , gamepad1.right_stick_x);
    }
}
