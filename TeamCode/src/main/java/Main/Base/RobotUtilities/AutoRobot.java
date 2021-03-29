package Main.Base.RobotUtilities;

import Main.Base.Robot;

public class AutoRobot extends Robot {

    public int rings;

    @Override
    public void init() {
        super.init();

        camera.startStreaming();
        rings = camera.detectRings();
        camera.displayRings();


    }


}
