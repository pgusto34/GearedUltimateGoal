package Testing;


import Main.Base.Odometry.OdometryPositionUpdater;
import Main.Base.Robot;

public class OdometryTest extends Robot {


    OdometryPositionUpdater globalPositionUpdate;
    Thread positionThread;


    @Override
    public void init() {
        super.init();

        OdometryPositionUpdater globalPositionUpdate = new OdometryPositionUpdater(left, right, mid, gyro, 75);
        Thread positionThread = new Thread(globalPositionUpdate);
        positionThread.start();

    }


    @Override
    public void loop(){
        telemetry.addData("X Position", globalPositionUpdate.returnXCoordinate());
        telemetry.addData("Y Position", globalPositionUpdate.returnYCoordinate());
        telemetry.addData("Orientation (Degrees)", globalPositionUpdate.returnOrientation());
        telemetry.addData("Thread Active", positionThread.isAlive());
        telemetry.update();
    }


    @Override
    public void stop(){globalPositionUpdate.stop();}

}
