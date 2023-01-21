// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  public final CANSparkMax motorTopRoller = new CANSparkMax(Constants.CAN.bRoller, MotorType.kBrushless);
  public final CANSparkMax motorBottomRoller = new CANSparkMax(Constants.CAN.tRoller, MotorType.kBrushless);
  public final CANSparkMax motorExtendBar = new CANSparkMax(Constants.CAN.extendBar, MotorType.kBrushless);
  public Intake(){
    //default settings here, right?
    motorTopRoller.setIdleMode(IdleMode.kBrake);
    motorBottomRoller.setIdleMode(IdleMode.kBrake);
    motorExtendBar.setIdleMode(IdleMode.kBrake);

    motorTopRoller.burnFlash();
    motorBottomRoller.burnFlash();
    motorExtendBar.burnFlash();
  }
  public void intakeWheels() {
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
  