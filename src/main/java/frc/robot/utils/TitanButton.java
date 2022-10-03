package frc.robot.utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

@SuppressWarnings("unused")
public class TitanButton extends JoystickButton {
    private final GenericHID hid;
    private final int buttonNumber;

    public TitanButton(GenericHID hid, int buttonNumber) {
        super(hid, buttonNumber);
        this.hid = hid;
        this.buttonNumber = buttonNumber;
    }

    public boolean isPressed() {
        return hid.getRawButtonPressed(buttonNumber);
    }

    public boolean isReleased() {
        return hid.getRawButtonReleased(buttonNumber);
    }

    public boolean isHeld() {
        return this.get();
    }

    public void rumble(double intensity) {
        hid.setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
        hid.setRumble(GenericHID.RumbleType.kRightRumble, intensity);
    }

    public void stopRumble() {
        hid.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        hid.setRumble(GenericHID.RumbleType.kRightRumble, 0);
    }

}