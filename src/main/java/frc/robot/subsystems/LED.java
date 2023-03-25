// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Types;
import frc.robot.Constants.LEDConstants;

public class LED extends SubsystemBase {
  private Spark blinkin = new Spark(LEDConstants.blinkinPort);
  
  /** Creates a new LED. */
  public LED() {



  }

  public void setLed(double pattern){
    blinkin.set(pattern);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run



  }
}
