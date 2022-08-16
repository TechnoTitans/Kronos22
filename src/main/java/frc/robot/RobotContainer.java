// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.IndexTeleop;
import frc.robot.commands.ShootTeleop;
import frc.robot.commands.TiltTeleop;
import frc.robot.motor.TitanSRX;
import frc.robot.subsystems.Barrel;
import frc.robot.subsystems.BarrelTilt;
import frc.robot.subsystems.JankDrive;
import frc.robot.utils.TitanButton;

public class RobotContainer {
    //OI
    public OI oi;

    //Motors
    public TitanSRX leftFront, leftRear, rightFront, rightRear;
    public TitanSRX barrel, tiltMotor;

    //Relay
    public Relay spike;

    //Value
    Relay.Value spikeMode = Relay.Value.kOff;

    //Encoders
    public Encoder barrelEncoder;

    //DigitalOutput
    public DigitalOutput dout;

    //Subsystems
    public JankDrive drive;
    public Barrel gun;
    public BarrelTilt gunAim;

    //Buttons
    public TitanButton indexButton, shootButton, compressorButton;

    //Commands
    public DriveTeleop driveTeleop;
    public IndexTeleop indexTeleop;
    public TiltTeleop tiltTeleop;
    public ShootTeleop shootTeleop;

    public RobotContainer() {
        oi = new OI();

        //Drivetrain Motors
        leftFront = new TitanSRX(RobotMap.leftFront, RobotMap.leftFrontReverse);
        leftRear = new TitanSRX(RobotMap.leftRear, RobotMap.leftRearReverse);
        rightFront = new TitanSRX(RobotMap.rightFront, RobotMap.rightFrontReverse);
        rightRear = new TitanSRX(RobotMap.rightRear, RobotMap.rightRearReverse);

        leftRear.follow(leftFront);
        rightRear.follow(rightFront);

        //DriveTrain stuff
        drive = new JankDrive(leftFront, rightFront);
        driveTeleop = new DriveTeleop(drive, oi::getXboxLeftTrigger, oi::getXboxRightTrigger, oi::getXboxRightX);

        //Turret
        // Makes it less jittery but at the same time less accurate. most accurate = k4X. least jitter = k1X
        barrelEncoder = new Encoder(RobotMap.barrelEncoderA, RobotMap.barrelEncoderB, RobotMap.barrelRevered, Encoder.EncodingType.k2X);
        barrel = new TitanSRX(RobotMap.barrel, RobotMap.barrelReverse, barrelEncoder);
        barrel.brake();
        barrelEncoder.reset();

        //Tilt motor
        tiltMotor = new TitanSRX(RobotMap.tilt, RobotMap.tiltReverse);
        tiltMotor.coast();

        gun = new Barrel(barrel);
        gunAim = new BarrelTilt(tiltMotor);

        //Set channel for Spike (compressor toggle)
        spike = new Relay(0);

        //Create DigitalOutput (Shooting)
        dout = new DigitalOutput(2);
        //Set PWM rate or it won't pulse right length
        dout.setPWMRate(10000);

        //Index Barrel
        indexButton = new TitanButton(oi.getXbox(), OI.XBOX_B);
        //Shoot
        shootButton = new TitanButton(oi.getXbox(), OI.XBOX_A);
        //Toggle Compressor
        compressorButton = new TitanButton(oi.getXbox(), OI.XBOX_Y);

        //Teleop commands
        indexTeleop = new IndexTeleop(gun);
        tiltTeleop = new TiltTeleop(gunAim, oi::getXboxPOV);
        shootTeleop = new ShootTeleop(dout, indexTeleop, shootButton);

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        indexButton.whenPressed(indexTeleop);
        shootButton.whenPressed(shootTeleop);
        compressorButton.whenPressed(new InstantCommand(() -> {
            spikeMode = spikeMode == Relay.Value.kOff ? Relay.Value.kForward : Relay.Value.kOff;
            spike.set(spikeMode);
        }));

    }

    public Command getAutonomousCommand() {
        // Unless you're crazy and want the big robot with the big scary air tanks to drive itself don't use this.
        return null;
    }
}
