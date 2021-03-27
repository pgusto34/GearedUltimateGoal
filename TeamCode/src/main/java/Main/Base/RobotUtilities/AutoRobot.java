package Main.Base.RobotUtilities;

import Main.Base.Robot;

public class AutoRobot extends Robot {

    @Override
    public void init() {
        super.init();

        camera.startStreaming();
        camera.detectRings();
        camera.displayRings();
    }


}
