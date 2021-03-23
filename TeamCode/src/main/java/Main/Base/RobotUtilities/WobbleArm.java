package Main.Base.RobotUtilities;

import com.qualcomm.robotcore.hardware.Servo;

public class WobbleArm {

    Servo wobbleArm;
    Servo wobbleClaw;

    double wobbleClawPosition;
    double wobbleClawOpenPosition = 0.58;
    double wobbleClawClosedPosition = 0.0;

    double wobbleArmPosition;
    double wobbleArmOutPosition = 0.0;
    double wobbleArmUpPosition = 0.331;
    double wobbleArmInPosition = 0.653;


    public WobbleArm(Servo wA, Servo wC) {
        wobbleArm = wA;
        wobbleClaw = wC;
    }


    public void ControlWobbleClaw(boolean open) {
        if(open) wobbleClawPosition = wobbleClawOpenPosition;
        else wobbleClawPosition = wobbleClawClosedPosition;

        wobbleClaw.setPosition(wobbleClawPosition);
    }


    public void ControlWobbleArm(boolean armState) {

        if(armState) wobbleArmPosition = wobbleArmOutPosition;
        else wobbleArmPosition = wobbleArmUpPosition;

        wobbleArm.setPosition(wobbleArmPosition);
    }


    public void WobbleArmIn() {
        wobbleArm.setPosition(wobbleArmInPosition);
    }


}