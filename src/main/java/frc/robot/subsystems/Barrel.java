package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;

public class Barrel extends SubsystemBase {

    private final TitanSRX barrel;

    public Barrel(TitanSRX barrel) {
        this.barrel = barrel;
    }

    public void set(double power) {
        barrel.set(power);
    }

    public TitanSRX getBarrel() {
        return barrel;
    }
}
