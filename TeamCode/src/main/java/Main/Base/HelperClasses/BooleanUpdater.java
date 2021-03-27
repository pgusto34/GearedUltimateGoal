package Main.Base.HelperClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import static Main.Base.HelperClasses.Button.*;

import java.util.HashMap;

public class BooleanUpdater {

    Gamepad gamepad;

    static HashMap<Button, Boolean> buttons = new HashMap<Button, Boolean>();

    public static boolean lastA = false, lastB = false, lastX = false, lastY = false, lastRB = false, lastLB = false, lastRT = false, lastLT = false;
    public static boolean lastDLeft = false, lastDRight = false, lastDUp = false, lastDDown = false;
    public static boolean lastStart = false, lastBack = false;


    public BooleanUpdater() {}


    public static HashMap<Button, Boolean> updateBooleans(Gamepad gp) {
        if (gp.a) lastA = true;
        else lastA = false;

        buttons.put(a, lastA);


        if (gp.b) lastB = true;
        else lastB = false;

        buttons.put(b, lastB);


        if (gp.x) lastX = true;
        else lastX = false;

        buttons.put(x, lastX);


        if (gp.y) lastY = true;
        else lastY = false;

        buttons.put(y, lastY);


        if(gp.left_trigger > 0.1) lastLT = true;
        else lastLT = false;

        buttons.put(left_trigger, lastLT);


        if(gp.right_trigger > 0.1) lastRT = true;
        else lastRT = false;

        buttons.put(right_trigger, lastRT);


        if(gp.right_bumper) lastRB = true;
        else lastRB = false;

        buttons.put(right_bumper, lastRB);


        if(gp.left_bumper) lastLB = true;
        else lastLB = false;

        buttons.put(left_bumper, lastLB);


        if(gp.dpad_up) lastDUp = true;
        else lastDUp = false;

        buttons.put(dpad_up, lastDUp);


        if(gp.dpad_down) lastDDown = true;
        else lastDDown = false;

        buttons.put(dpad_down, lastDDown);


        if(gp.dpad_right) lastDRight = true;
        else lastDRight = false;

        buttons.put(dpad_right, lastDRight);


        if(gp.dpad_left) lastDLeft = true;
        else lastDLeft = false;

        buttons.put(dpad_left, lastDLeft);


        if(gp.start) lastStart = true;
        else lastStart = false;

        buttons.put(start, lastStart);


        if(gp.back) lastBack = true;
        else lastBack = false;

        buttons.put(back, lastBack);


        return buttons;
    }
}
