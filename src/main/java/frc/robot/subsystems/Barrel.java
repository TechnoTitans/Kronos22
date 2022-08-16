package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;

public class Barrel extends SubsystemBase {

    private final TitanSRX barrel;
    private final Encoder barrelEncoder;

    // travel distance
    public static final double gear1 = 18;
    public static final double gear2 = 23;
    public static final double pulseperrev = 420;
    public static final double DISTANCE = (pulseperrev * (gear2/gear1)) / 6;

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
