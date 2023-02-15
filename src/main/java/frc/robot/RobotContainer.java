// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.*;
import frc.robot.motor.TitanSRX;
import frc.robot.subsystems.Barrel;
import frc.robot.subsystems.BarrelTilt;
import frc.robot.subsystems.JankDrive;
import frc.robot.utils.TitanButton;
import frc.robot.utils.TitanDS;

@SuppressWarnings("CommentedOutCode")
public class RobotContainer {
    //OI
    public OI oi;
    public TitanDS titanDS;

    //Motors
    public final TitanSRX leftFront, leftRear, rightFront, rightRear;
    public final TitanSRX barrelMotor, tiltMotor;

    //Relay
    public final Relay spike;

    //ColorSensor
    public final ColorSensorV3 colorSensor;

    //Value
    public static Relay.Value spikeMode = Relay.Value.kOff;

    //DigitalOutput
    public final DigitalOutput dout;

    //Subsystems
    public final JankDrive drive;
    public final Barrel barrel;
    public final BarrelTilt barrelTilt;

    //Buttons
    public TitanButton shootButton, indexButton, compressorButton;

    //Commands
    public DriveTeleop driveTeleop;
    public IndexTeleop indexTeleop;
    public TiltTeleop tiltTeleop;
    public ShootTeleop shootTeleop;
    public AutoShoot autoShoot;

    public RobotContainer() {
        if (Robot.isController) {
            oi = new OI();
        }

        //Drivetrain Motors
        leftFront = new TitanSRX(RobotMap.leftFront, RobotMap.leftFrontReverse);
        leftRear = new TitanSRX(RobotMap.leftRear, RobotMap.leftRearReverse);
        rightFront = new TitanSRX(RobotMap.rightFront, RobotMap.rightFrontReverse);
        rightRear = new TitanSRX(RobotMap.rightRear, RobotMap.rightRearReverse);

        leftRear.follow(leftFront);
        rightRear.follow(rightFront);

        //DriveTrain stuff
        drive = new JankDrive(leftFront, rightFront);
        if (Robot.isController) {
            driveTeleop = new DriveTeleop(drive, oi.getXbox());
        }

        //Turret
        //Makes it less jittery but at the same time less accurate. most accurate = k4X. least jitter = k1X
        barrelMotor = new TitanSRX(RobotMap.barrel, RobotMap.barrelReverse);
        barrelMotor.coast();

        //Tilt motor
        tiltMotor = new TitanSRX(RobotMap.tilt, RobotMap.tiltReverse);
        tiltMotor.coast();

        barrel = new Barrel(barrelMotor);
        barrelTilt = new BarrelTilt(tiltMotor);

        //Set channel for Spike (compressor toggle)
        spike = new Relay(0);

        //Create DigitalOutput (Shooting)
        dout = new DigitalOutput(2);
        //Set PWM rate or it won't pulse right length
        dout.setPWMRate(10000); //Random Value

        //ColorSensor
        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        colorSensor.configureColorSensor(ColorSensorV3.ColorSensorResolution.kColorSensorRes16bit, ColorSensorV3.ColorSensorMeasurementRate.kColorRate25ms, ColorSensorV3.GainFactor.kGain18x);

        //Teleop commands
        indexTeleop = new IndexTeleop(barrel, colorSensor);

        if (Robot.isController) {
            shootButton = new TitanButton(oi.getXbox(), OI.XBOX_BUMPER_RIGHT);
            compressorButton = new TitanButton(oi.getXbox(), OI.XBOX_A);
            indexButton = new TitanButton(oi.getXbox(), OI.XBOX_B);

            tiltTeleop = new TiltTeleop(barrelTilt, oi.getXbox());
            shootTeleop = new ShootTeleop(dout, indexTeleop, shootButton);
        } else {
            autoShoot = new AutoShoot(dout, indexTeleop);

            //TitanDS
            titanDS = new TitanDS(drive, barrelTilt, autoShoot, spike);
        }

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        if (Robot.isController) {
            compressorButton.whenPressed(new InstantCommand(() -> {
                spikeMode = spike.get() == Relay.Value.kOff ? Relay.Value.kForward : Relay.Value.kOff;
                spike.set(spikeMode);
            }));

            indexButton.whenPressed(indexTeleop);
        }
    }

    public Command getAutonomousCommand() {
        // Unless you're crazy and want the big robot with the big scary air tanks to drive itself don't use this.
        return null;
    }
}
