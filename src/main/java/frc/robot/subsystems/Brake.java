// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class Brake extends SubsystemBase {
  /** Creates a new Brake. */
  private Solenoid brake = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.brakeSolenoid/*right now is set to 36, where the elevatoor used to connect*/);
  public Brake() {
    
  }
  public void brakeDown(){
      brake.set(false);
      //may need to be switched to true
    }
  public void brakeUp(){
    brake.set(true);
    //may need to be switched to false
  }
  public boolean getBrake(){
    return brake.get();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
