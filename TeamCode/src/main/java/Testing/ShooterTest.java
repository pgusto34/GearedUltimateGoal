package Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Main.Base.Robot;

@TeleOp(name = "Shooter Test", group = "test")
public class ShooterTest extends Robot {

    boolean lastA;

    @Override
    public void loop() {

        if (gp.a && !lastA) {
            try {
                shooter.shootHG();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (gp.a) lastA = true;
        else lastA = false;

    }
}
