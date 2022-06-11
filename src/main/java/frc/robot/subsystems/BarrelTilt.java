package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.motor.TitanSRX;

public class BarrelTilt extends SubsystemBase {

    private final TitanSRX tilt;

    public BarrelTilt(TitanSRX tilt) {
        this.tilt = tilt;
    }

    public void set(double power) {
        tilt.set(power);
    }

    public TitanSRX getTilt() {
        return tilt;
    }
}
