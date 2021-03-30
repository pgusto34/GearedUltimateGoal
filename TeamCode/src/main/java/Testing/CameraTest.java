package Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;

@Autonomous(name = "Camera Test", group = "testing")
public class CameraTest extends AutoRobot {

    @Override
    public void loop() {

        rings = camera.detectRings();
        telemetry.addData("Rings: ", rings);
        telemetry.update();

    }

}
