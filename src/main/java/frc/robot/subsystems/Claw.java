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

public class Claw extends SubsystemBase {
  /** Creates a new Outtake. */
  public final Solenoid bigPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.BP);
  public final Solenoid smallPiston = new Solenoid(PneumaticsModuleType.REVPH, Constants.CAN.SP); 

  public Claw() {
    //create solenoids to grab cones and cubes
    //NOTE: this is based on the assumption that false is open and true is closed. may need to be switched when we know more.
    bigPiston.set(false);
    smallPiston.set(false);

    }

    public void setOpen() {
      bigPiston.set(false);
      smallPiston.set(false);
    }

    public void setClosed() {
      bigPiston.set(true);
      smallPiston.set(true);
    }

    public void toggleCone() {
      //grab or drop using both solenoids
      bigPiston.toggle();
      smallPiston.toggle();
    }
    
    public void toggleCube() {
      //grab or drop using one solenoid (cube will be crushed otherwise)
      bigPiston.set(false);
      smallPiston.toggle();
    }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}