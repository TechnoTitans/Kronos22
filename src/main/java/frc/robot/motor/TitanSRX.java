package frc.robot.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class TitanSRX extends WPI_TalonSRX implements MotorController {

    private Encoder encoder;

    public TitanSRX(int channel, boolean reverse) {
        super(channel);
        super.setInverted(reverse);
    }

    public TitanSRX(int channel, boolean reverse, Encoder encoder) {
        super(channel);
        super.setInverted(reverse);
        this.encoder = encoder;
    }

    public void set(double power) {
        // That weird thing is so that it clamps the speed to 1 and -1, it prob doesn't need it but whatever
        super.set(ControlMode.PercentOutput, power > 1 ? 1 : power < -1 ? -1 : power);
    }

    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    public Encoder getEncoder() {
        return this.encoder;
    }

    public void resetEncoder() {
        if (this.encoder != null) {
            this.encoder.reset();
        }
    }

    public void setReversed(boolean reverse) {
        super.setInverted(reverse);
    }

    public boolean isReversed() {
        return super.getInverted();
    }

    public void clearErrors() {
        super.clearStickyFaults();
    }

    public void brake() {
        super.setNeutralMode(NeutralMode.Brake);
    }

    public void coast() {
        super.setNeutralMode(NeutralMode.Coast);
    }

    public void stop() {
        super.stopMotor();
    }

    public void follow(TitanSRX master) {
        super.set(ControlMode.Follower, master.getDeviceID());
    }
}
