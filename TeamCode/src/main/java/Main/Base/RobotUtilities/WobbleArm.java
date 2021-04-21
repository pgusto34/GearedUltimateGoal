package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.Servo;

public class WobbleArm {

    private Servo wobbleArm;
    private Servo wobbleClaw;

    double wobbleClawPosition;
    double wobbleClawOpenPosition = 0;
    double wobbleClawClosedPosition = 0.58;

    double wobbleArmPosition;
    double wobbleArmOutPosition = 0.0;
    double wobbleArmUpPosition = 0.4;
    double wobbleArmInPosition = 0.7;


    public WobbleArm(Servo wA, Servo wC) {
        wobbleArm = wA;
        wobbleClaw = wC;
    }


    //Sets Wobble Claw position to either open or closed
    public void ControlWobbleClaw(boolean open) {
        if(open) wobbleClawPosition = wobbleClawOpenPosition;
        else wobbleClawPosition = wobbleClawClosedPosition;

        wobbleClaw.setPosition(wobbleClawPosition);
    }


    //Sets Wobble Arm position to either open or closed
    public void ControlWobbleArm(boolean out) {

        if(out) wobbleArmPosition = wobbleArmOutPosition;
        else wobbleArmPosition = wobbleArmUpPosition;

        wobbleArm.setPosition(wobbleArmPosition);
    }


    //Sets wobble arm position to 'in' (Our starting position for auto)
    public void setWobbleArmInPosition() {
        wobbleArm.setPosition(wobbleArmInPosition);
    }

}