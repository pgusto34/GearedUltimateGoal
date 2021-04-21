package Main.Auto;

import Main.Base.AutoRobot;

public class TemplateAuto extends AutoRobot {

    double speed = 0;

    double error = 0;

    public void runAutonomous(){

        try {



        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
