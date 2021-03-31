package Main.Base;

import Main.Base.Robot;

public class AutoRobot extends Robot {

    public int rings = -1;

    @Override
    public void init() {
        super.init();

        camera.startStreaming();

    }


    @Override
    public void init_loop() {

       rings = camera.detectRings();
       telemetry.addData("Rings: ", rings);
       telemetry.update();

    }

    @Override
    public void start(){
        autoThread.start();
    }


    protected Thread autoThread = new Thread(new Runnable() {
        @Override
        public void run() {
            runAutonomous();
        }
    });

    private long maxTime;

    protected void pause(long millis){
        maxTime = System.currentTimeMillis() + millis;
        while(System.currentTimeMillis() < maxTime && !autoThread.isInterrupted()) {}
    }


    @Override
    public void stop(){
        try{
            autoThread.interrupt();

        }catch(Exception e){
            telemetry.addData("ENCOUNTERED AN EXCEPTION", e);
        }

    }

    public void runAutonomous(){};

}
