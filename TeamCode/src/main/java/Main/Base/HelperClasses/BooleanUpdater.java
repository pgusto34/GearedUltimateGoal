package Main.Base.HelperClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

public class BooleanUpdater {

    Gamepad gamepad;

    public static boolean lastA = false, lastB = false, lastX = false, lastY = false, lastRight = false, lastLeft = false, lastRT = false, lastLT = false;
    public static boolean lastDLeft = false, lastDRight = false, lastDUp = false, lastDDown = false;


    public BooleanUpdater() {}

    public BooleanUpdater(Gamepad gpad) {
        this.gamepad = gpad;
    }


    public static void updateBooleans(Gamepad gp) {
        if (gp.a) lastA = true;
        else lastA = false;

        if (gp.b) lastB = true;
        else lastB = false;

        if (gp.x) lastX = true;
        else lastX = false;

        if (gp.y) lastY = true;
        else lastY = false;

        if(gp.left_trigger > 0.1) lastLT = true;
        else lastLT = false;

        if(gp.right_trigger > 0.1) lastRT = true;
        else lastRT = false;

        if(gp.right_bumper) lastRight = true;
        else lastRight = false;

        if(gp.left_bumper) lastLeft = true;
        else lastLeft = false;

        if(gp.dpad_up) lastDUp = true;
        else lastDUp = false;

        if(gp.dpad_down) lastDDown = true;
        else lastDDown = false;

        if(gp.dpad_right) lastDRight = true;
        else lastDRight = false;

        if(gp.dpad_left) lastDLeft = true;
        else lastDLeft = false;
    }
}
