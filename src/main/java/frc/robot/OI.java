package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    public static final int XBOX_A = 1;
    public static final int XBOX_B = 2;
    public static final int XBOX_X = 3;
    public static final int XBOX_Y = 4;
    public static final int XBOX_BUMPER_RIGHT = 6;
    public static final int XBOX_BUMPER_LEFT = 5;
    public static final int XBOX_BTN_SELECT = 7;
    public static final int XBOX_BTN_START = 8;

    public static double getXboxLeftY(XboxController xbox) {
        return -xbox.getLeftY();
    }

    public static double getXboxLeftX(XboxController xbox) {
        return -xbox.getLeftX();
    }

    public static double getXboxRightY(XboxController xbox) {
        return xbox.getRightY();
    }

    public static double getXboxRightX(XboxController xbox) {
        return xbox.getRightX();
    }

    public static double getXboxLeftTrigger(XboxController xbox) {
        return xbox.getLeftTriggerAxis();
    }

    public static double getXboxRightTrigger(XboxController xbox) {
        return xbox.getRightTriggerAxis();
    }

    public static int getXboxPOV(XboxController xbox) {
        return xbox.getPOV();
    }
}
