package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.Servo;

/**TODO:
 * -Tune Stopper positions
 *      -Probably should write a tuner program
 * -Add functions as needed
 */

public class Stopper {

    private Servo stopper;

    double stopperPosition;
    double stopperUpPosition = 0.1;

    double stopperDownPosition = .65;


    public Stopper(Servo sServo) {
        stopper = sServo;
    }


    public void setStopperPosition(double position) {
        stopper.setPosition(position);
    }


    public void controlStopper(boolean up) {

        if(up) stopperPosition = stopperUpPosition;
        else stopperPosition = stopperDownPosition;
        //double stopperPosition = up ? stopperUpPosition : stopperDownPosition;

        stopper.setPosition(stopperPosition);

    }

}
