package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;

public class Barrel extends SubsystemBase {

    private final TitanSRX barrel;
    private final Encoder barrelEncoder;

    // travel distance
    public static final int DISTANCE = 420;

    public Barrel(TitanSRX barrel) {
        this.barrel = barrel;
        this.barrelEncoder = barrel.getEncoder();
    }

    public void set(double power) {
        barrel.set(power);
    }

    public Encoder getBarrelEncoder() {
        return barrelEncoder;
    }

    public TitanSRX getBarrel() {
        return barrel;
    }
}
