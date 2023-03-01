// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Types.FourbarPosition;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Fourbar extends SubsystemBase implements Loggable {
  // Creates new pneumatic fourbar
  DoubleSolenoid fourBar = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CAN.extendFourbar, Constants.CAN.retractFourbar);
  

  /** Creates a new Fourbar */
  public Fourbar() {
    
    //fourBar.set(Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
  public void fourbarDown(){
    // sets fourbar to false or down in low
    //if(fourBar.get() == Value.kForward){
      fourBar.set(Value.kForward);
    }
 // }
  public void fourbarUp(){
   // if(fourBar.get() == Value.kReverse){
      fourBar.set(Value.kReverse);
   // }
    
  }

  public Command fourbarToggle() {
    return new InstantCommand(()-> fourBar.toggle());
  }
  
  public FourbarPosition getFourBarPosition(){
      if (fourBar.get()==Value.kReverse){
        return FourbarPosition.EXTEND;
      }
      else if (fourBar.get()==Value.kForward){
        return FourbarPosition.RETRACT;
      }
      //I don't know if closed should be the default position, but it shuld work for not.
      return FourbarPosition.RETRACT;
    }
}
