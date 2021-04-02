package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import Main.Base.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

@TeleOp(name = "FODEXAMPLE")
public class FODExample extends Robot {


    @Override
    public void loop() {

        fieldOrientatedDrive();
    }




    public void fieldOrientatedDrive () {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        double gamepadHpot = Range.clip(Math.hypot(x, y), 0, 1);

        double gamepadDegree = Math.toRadians(Math.atan2(y, x));

        double heading = gyro.getHeadingRadians();

        double movementDegree = gamepadDegree - heading;

        x = Math.cos(movementDegree) * gamepadHpot;

        y = Math.sin(movementDegree) * gamepadHpot;

        wheelBase.mecanumDrive(x, y, turn, false);
    }


}
