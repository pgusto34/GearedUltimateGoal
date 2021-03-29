package Main.Base.HelperClasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import static Main.Base.HelperClasses.Button.*;

import java.util.HashMap;

public class BooleanUpdater {

    public static boolean lastA = false, lastB = false, lastX = false, lastY = false, lastRB = false, lastLB = false, lastRT = false, lastLT = false;
    public static boolean lastDLeft = false, lastDRight = false, lastDUp = false, lastDDown = false;
    public static boolean lastStart = false, lastBack = false;
    public static boolean lastLS = false, lastRS = false;

    public static boolean pressA = false, pressB = false, pressX = false, pressY = false, pressRB = false, pressLB = false, pressRT = false, pressLT = false;
    public static boolean pressDLeft = false, pressDRight = false, pressDUp = false, pressDDown = false;
    public static boolean pressStart = false, pressBack = false;
    public static boolean pressLS = false, pressRS = false;




    public static HashMap<Button, Boolean> updateBooleans(Gamepad gp, HashMap<Button, Boolean> hm) {
        hm.put(Button.a, gp.a);

        lastA = gp.a;

        lastB = gp.b;

        lastX = gp.x;

        lastY = gp.y;

        lastLT = gp.left_trigger > 0.1;

        lastRT = gp.right_trigger > 0.1;

        lastRB = gp.right_bumper;

        lastLB = gp.left_bumper;

        lastDUp = gp.dpad_up;

        lastDDown = gp.dpad_down;

        lastDRight = gp.dpad_right;

        lastDLeft = gp.dpad_left;

        lastStart = gp.start;

        lastBack = gp.back;

        lastLS = gp.left_stick_button;

        lastRS = gp.right_stick_button;



        //pressA = gp.a && !lastA;

        //buttons.put(a, gp.a);


        pressB = gp.b && !lastB;

        hm.put(b, pressB);


        pressX = gp.x && !lastX;

        hm.put(x, pressX);


        pressY = gp.y && !lastY;

        hm.put(y, pressY);


        pressLB = gp.left_bumper && !lastLB;

        hm.put(left_bumper, pressLB);


        pressRB = gp.right_bumper && !lastRB;

        hm.put(right_bumper, pressRB);


        pressLT = gp.left_trigger > 0.2 && !lastLT;

        hm.put(left_trigger, pressLT);


        pressRT = gp.right_trigger > 0.2 && !lastA;

        hm.put(right_trigger, pressRT);


        pressDUp = gp.dpad_up && !lastDUp;

        hm.put(dpad_up, pressDUp);


        pressDDown = gp.dpad_down && !lastDDown;

        hm.put(dpad_down, pressDDown);


        pressDLeft = gp.dpad_left && !lastDLeft;

        hm.put(dpad_left, pressDLeft);


        pressDRight = gp.dpad_right && !lastDRight;

        hm.put(dpad_right, pressDRight);


        pressA = gp.a && !lastA;

        hm.put(a, pressA);


        pressA = gp.a && !lastA;

        hm.put(a, pressA);


        pressLS = gp.left_stick_button && !lastLS;

        hm.put(left_stick_button, pressLS);


        pressRS = gp.right_stick_button && !lastRS;

        hm.put(right_stick_button, pressRS);



        return hm;
    }
}
