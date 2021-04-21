package Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.HashMap;

import Main.Base2.HelperClasses.BooleanUpdater;
import Main.Base2.HelperClasses.Button;
import Main.Base2.Robot;

import static Main.Base2.HelperClasses.BooleanUpdater.updateBooleans;

@Disabled
@TeleOp(name = "BooleanUpdater Test", group = "testing")
public class BooleanUpdaterTest extends OpMode {

    HashMap<Button, Boolean> buttonChecker;

    public void init() {
        buttonChecker = new HashMap<Button, Boolean>();
    }


    @Override
    public void loop() {
        buttonChecker = updateBooleans(gamepad1, buttonChecker);

        telemetry.addData("a: ", buttonChecker.get(Button.a));

        telemetry.addData("Gamepad a: ", gamepad1.a);
    }


}
