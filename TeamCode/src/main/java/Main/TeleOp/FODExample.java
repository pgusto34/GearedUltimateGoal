package Main.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import Main.Base.Robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

@Disabled
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

        double gamepadDegree = gyro.angleWrapRadians(Math.atan2(y, x));

        double heading = gyro.angleWrapRadians(gyro.getHeadingRadians());

        double movementDegree = gamepadDegree - heading;

        x = Math.cos(movementDegree) * gamepadHpot;

        y = Math.sin(movementDegree) * gamepadHpot;

        telemetry.addData("Heading: ", heading);
        telemetry.addData("Stick Degree: ", gamepadDegree);
        telemetry.addData("X: ", x);
        telemetry.addData("Y: ", y);
        telemetry.update();

        wheelBase.mecanumDrive(y, -x, turn, false);
    }


}
