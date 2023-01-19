// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Elevator extends SubsystemBase implements Loggable {
  public final CANSparkMax elevatorL = new CANSparkMax(Constants.CAN.LElevator, MotorType.kBrushless);
  public final CANSparkMax elevatorR = new CANSparkMax(Constants.CAN.RElevator, MotorType.kBrushless);

  /** Creates a new Elevator. */
  public Elevator() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void moveElevator(double distance){
    ;
  }
  public void elevatorLow(){
    ;
  }
  public void elevatorMid(){
    ;
  }
  public void elevatorHigh(){
    ;
  }
}
