package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.Servo;

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
