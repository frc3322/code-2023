// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  public final CANSparkMax motorTopRoller = new CANSparkMax(Constants.CAN.tRoller, MotorType.kBrushless);
  public final CANSparkMax motorBottomRoller = new CANSparkMax(Constants.CAN.bRoller, MotorType.kBrushless);
  public final CANSparkMax motorArm = new CANSparkMax(Constants.CAN.arm, MotorType.kBrushless);
  //motor arm will need an encoder or limit switch to determine where to stop
  public final RelativeEncoder armEncder = motorArm.getEncoder();
  
  public Intake(){
    //default settings here, right?
    motorTopRoller.setIdleMode(IdleMode.kBrake);
    motorBottomRoller.setIdleMode(IdleMode.kBrake);
    motorArm.setIdleMode(IdleMode.kBrake);

    motorTopRoller.burnFlash();
    motorBottomRoller.burnFlash();
    motorArm.burnFlash();
  }
  public void intakeSpin() {
    //the revrobotics website lists 12 as the optimal voltage for NEO brushless motors.
    //one will need to be setInverted
    //sets the voltage so it will spin to intake continuously
    motorTopRoller.setVoltage(12.0);
    motorBottomRoller.setVoltage(12.0);
  }
  public void flipDown() {
    //move arm to down/intake position
    ;
  }
  public void flipUp() {
    //move arm to up/transfer/elevator position
    ;
  }
  public void upSpin() {
    //the revrobotics website lists 12 as the optimal voltage for NEO brushless motors.
    //if setInverted is clockwise, we will need to set both to normal, otherwise invert
    //sets the voltage so it will spin continuously while up, which is apparently needed for transfer or elevator or something
    motorTopRoller.setVoltage(12.0);
    motorBottomRoller.setVoltage(12.0);
  }
  public void outSpin() {
    //the revrobotics website lists 12 as the optimal voltage for NEO brushless motors.
    //one will need to be setInverted, the opposite one from intakeSpin
    //sets the voltage so it will spin to intake continuously in reverse directions from normal
    motorTopRoller.setVoltage(12.0);
    motorBottomRoller.setVoltage(12.0);
  }
  public void spinToggle() {
    //turns the rollers on and off
    motorTopRoller.stopMotor();
    motorBottomRoller.stopMotor();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
  