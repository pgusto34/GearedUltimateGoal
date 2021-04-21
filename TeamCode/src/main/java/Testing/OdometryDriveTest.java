package Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;
import Main.Base.RobotUtilities.Odometry;

@Autonomous(name = "OdometryDrive Test", group = "testing")
public class OdometryDriveTest extends AutoRobot {

    @Override
    public void runAutonomous() {
        for (int i = 0; i < 10; i++) {
            wheelBase.goToPosition(odometry, gyro, telemetry, 24, 0, 90, 0.4, 3);
            wheelBase.goToPosition(odometry, gyro, telemetry, 24, 24, 180, 0.4, 3);
            wheelBase.goToPosition(odometry, gyro, telemetry, 0, 24, -90, 0.4, 3);
            wheelBase.goToPosition(odometry, gyro, telemetry, 0, 0, 0, 0.4, 3);
        }

    }
}
