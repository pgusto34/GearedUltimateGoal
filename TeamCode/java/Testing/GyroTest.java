package Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base2.Robot;

@TeleOp(name = "Gyro Test", group = "test")
public class GyroTest extends Robot {

    @Override
    public void loop() {

        telemetry.addData("Heading: ", gyro.getHeading());
    }
}
