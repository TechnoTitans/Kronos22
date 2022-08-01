// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.*;
import frc.robot.motor.TitanSRX;
import frc.robot.subsystems.Barrel;
import frc.robot.subsystems.BarrelTilt;
import frc.robot.subsystems.JankDrive;
import frc.robot.utils.TitanButton;

public class RobotContainer {
    //Motors
    public TitanSRX leftFront, leftRear, rightFront, rightRear;
    public TitanSRX barrel, tilt;

    //Encoders
    public Encoder barrelEncoder;

    //Solenoids
//    public Solenoid tshirtSolenoid;

    public DigitalOutput dout;

    //Subsystems
    public JankDrive drive;
    public Barrel gun;
    public BarrelTilt gunAim;

    //Buttons
    public TitanButton indexButton, shootButton;

    //Commands
    public IndexTeleop indexTeleop;
    public ShootTeleop shootTeleop;
    public TitanDriveParser titanDriveParser;

    public RobotContainer() {
        //Drivetrain
        leftFront = new TitanSRX(RobotMap.leftFront, RobotMap.leftFrontReverse);
        leftRear = new TitanSRX(RobotMap.leftRear, RobotMap.leftRearReverse);
        rightFront = new TitanSRX(RobotMap.rightFront, RobotMap.rightFrontReverse);
        rightRear = new TitanSRX(RobotMap.rightRear, RobotMap.rightRearReverse);

        leftRear.follow(leftFront);
        rightRear.follow(rightFront);

        drive = new JankDrive(leftFront, rightFront);

        //Turret
        // Makes it less jittery but at the same time less accurate. most accurate = k4X. least jitter = k1X
        barrelEncoder = new Encoder(RobotMap.barrelEncoderA, RobotMap.barrelEncoderB, RobotMap.barrelRevered, Encoder.EncodingType.k2X);
        barrel = new TitanSRX(RobotMap.barrel, RobotMap.barrelReverse, barrelEncoder);
        tilt = new TitanSRX(RobotMap.tilt, RobotMap.tiltReverse);

//        tshirtSolenoid = new Solenoid(RobotMap.PCM, PneumaticsModuleType.CTREPCM, RobotMap.tshirtSolenoid);

        gun = new Barrel(barrel);
        gunAim = new BarrelTilt(tilt);

        dout = new DigitalOutput(RobotMap.dout);
        dout.setPWMRate(10000);

        // commands
        indexTeleop = new IndexTeleop(gun);
        shootTeleop = new ShootTeleop(indexTeleop, dout);
        titanDriveParser = new TitanDriveParser(drive, shootTeleop, gunAim);

        drive.setDefaultCommand(titanDriveParser);
        gunAim.setDefaultCommand(titanDriveParser);

        configureButtonBindings();
    }

    private void configureButtonBindings() {

    }

    public Command getAutonomousCommand() {
        // Unless you're crazy and want the big robot with the big scary air tanks to drive itself don't use this.
        return null;
    }
}
