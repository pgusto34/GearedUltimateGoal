package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.Servo;

/**TODO:
 * -Tune Stopper positions
 *      -Probably should write a tuner program
 * -Add functions as needed
 */

public class Stopper {

    Servo stopper;

    double stopperUpPosition = 0.5;

    double stopperDownPosition = 0.5;


    public Stopper(Servo sServo) {
        stopper = sServo;
    }


    private void setStopperPosition(double position) {
        stopper.setPosition(position);
    }


    public void controlStopper(boolean up) {

        double stopperPosition = up ? stopperUpPosition : stopperDownPosition;

        stopper.setPosition(stopperPosition);

    }

}
