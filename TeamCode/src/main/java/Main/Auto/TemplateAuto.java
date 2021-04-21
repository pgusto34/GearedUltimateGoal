package Main.Auto;

import Main.Base.AutoRobot;

/** Template Class for Creating Auto Programs **/

public class TemplateAuto extends AutoRobot {

    double speed = 0;

    double error = 0;

    public void runAutonomous(){

        try {

            //Auto Code Here

        }catch(Exception e){
            autoThread.interrupt();
            telemetry.addData("AUTONOMOUS WAS INTERRUPTED", e);
        }
    }
}
