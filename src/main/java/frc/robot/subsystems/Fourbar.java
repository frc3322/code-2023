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
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Fourbar extends SubsystemBase implements Loggable {
  // Creates new pneumatic fourbar
  Solenoid fourBarPH1 = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.P1); 
  Solenoid fourBarPH2 = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.P2);

  /** Creates a new Fourbar */
  public Fourbar() {
    // sets the fourbar to false or down by default
    fourBarPH1.set(false);
    fourBarPH2.set(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
//this function will not work because we are using solenoids.
  //public void moveFourbar(double distance){
    //;
  //}
  public void fourbarDown(){
    // sets fourbar to false or down in low
    fourBarPH1.set(false);
    fourBarPH2.set(false);
  }
  public void fourbarUp(){
    fourBarPh1.set(true);
    fourBarPH2.set(true);
    ;
  }
  //this function will not work as there are only two settings.
  //public void fourbarHigh(){
    //fourBarPh1.set();
    //fourBarPH2.set();
    ;
  //}
}
