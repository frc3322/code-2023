// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

public class Brake extends SubsystemBase {
  /** Creates a new Brake. */
  private DoubleSolenoid brake = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CAN.brakeForward, Constants.CAN.brakeReverse);
  public Brake() {
    
  }
  public void brakeDown(){
      brake.set(Value.kForward);
      
    }

  public void brakeUp(){
    brake.set(Value.kReverse);
    
  }

  // doesnt work
  public void toggleBrake(){
    brake.toggle();
  }
  public Value getBrake(){
    return brake.get();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
