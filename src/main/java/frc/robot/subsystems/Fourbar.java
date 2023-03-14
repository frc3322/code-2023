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
    // sets fourbar to down or down in low
      fourBar.set(Value.kForward);
    }
 // }
  public void fourbarUp(){
   // sets fourbar to top
      fourBar.set(Value.kReverse);
    
  }

  public Command fourbarToggle() {
    //toggles position.
    return new InstantCommand(()-> fourBar.toggle());
  }
  
  public FourbarPosition getFourBarPosition(){
      if (fourBar.get()==Value.kReverse){
        //if up return extended
        return FourbarPosition.EXTEND;
      }
      else if (fourBar.get()==Value.kForward){
        return FourbarPosition.RETRACT;
        //if down return down
      }
      //I don't know if closed should be the default position, but it should work for not.
      return FourbarPosition.RETRACT;
    }

  public Command createMoveCommand(FourbarPosition fourbarDestination){
    return new InstantCommand(()->{
    if (fourbarDestination == FourbarPosition.EXTEND) {
      fourbarUp();
    }
    else {
      fourbarDown();
    }
    }).until(()->(fourbarDestination==getFourBarPosition()));
  }
}
