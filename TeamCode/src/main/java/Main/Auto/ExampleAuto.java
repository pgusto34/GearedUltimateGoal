package Main.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Main.Base.AutoRobot;


@Autonomous(name = "Auto Test", group = "testing")
public class ExampleAuto extends AutoRobot {


    @Override
    public void runAutonomous(){
        wheelBase.mecanumEMove(0.5, 24, true);
//        wheelBase.moveBot("b", 24, 0.5);
//        wheelBase.moveBot("r", 24, 0.5);
//        wheelBase.moveBot("l", 24, 0.5);
    }


}
