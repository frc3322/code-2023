// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CanSparkMaxLowLevel
import frc.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Outtake extends SubsystemBase {
  /** Creates a new Outtake. */
  Solenoid bigPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.BP);
  Solenoid smallPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.SP); 

  public Outtake() {
    //create solenoids to grab cones and cubes
    
    bigPiston.set(false);
    smallPiston.set(false);

    }
    public void clampCone(){
      //grab or drop using both solenoids
      bigPiston.toggle();
      smallPiston.toggle();
    }
    public void clampCube(){
      //grab or drop using one solenoid
      smallPiston.toggle();
    }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}