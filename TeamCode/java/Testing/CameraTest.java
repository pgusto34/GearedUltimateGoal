package Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base2.AutoRobot;
import Main.Base2.Robot;

@Autonomous(name = "Camera Test", group = "testing")
public class CameraTest extends AutoRobot {

    @Override
    public void loop() {

        int rings = camera.detectRings();
        telemetry.addData("Rings: ", rings);
        telemetry.update();

    }

    @Override
    public void runAutonomous() {

    }
}
