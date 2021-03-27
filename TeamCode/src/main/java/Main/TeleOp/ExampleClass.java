package Main.TeleOp;

import Main.Base.Robot;

public class ExampleClass extends Robot {

    boolean lastA = false;

    @Override
    public void loop( ){

        shooter.shoot(true, 3);

        wheelBase.mecanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, false);

        if(gp.a && !lastA) shooter.shoot(true, 1);

        if(gp.a) lastA = true;
        else lastA = false;

    }

}
