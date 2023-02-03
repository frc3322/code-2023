// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Intake extends SubsystemBase implements Loggable {
  /** Creates a new Intake. */
  public final CANSparkMax motorTopRoller = new CANSparkMax(Constants.CAN.tRoller, MotorType.kBrushless);
  public final CANSparkMax motorBottomRoller = new CANSparkMax(Constants.CAN.bRoller, MotorType.kBrushless);
  public final CANSparkMax motorArm = new CANSparkMax(Constants.CAN.arm, MotorType.kBrushless);
  //motor arm will need an encoder or limit switch to determine where to stop
  public final RelativeEncoder armEncder = motorArm.getEncoder();
  // intake will need a proximity sensor to tell if there is a game piece inside
  public final DigitalInput proximitySensor = new DigitalInput(0);

  @Log private boolean proximitySensorValue;
  
  public Intake(){
    //default settings here, right?
    motorTopRoller.setIdleMode(IdleMode.kBrake);
    motorBottomRoller.setIdleMode(IdleMode.kBrake);
    motorArm.setIdleMode(IdleMode.kBrake);

    motorTopRoller.burnFlash();
    motorBottomRoller.burnFlash();
    motorArm.burnFlash();
  }
  
  public void flipDown() {
    //move arm to down/intake position
    ;
  }
  public void flipUp() {
    //move arm to up/transfer/elevator position
    ;
  }
 
  public void spinIntake(double speed){
    motorTopRoller.set(speed);
    motorBottomRoller.set(-speed);
  }

  public void stopSpin() {
    //turns the rollers on and off
    motorTopRoller.stopMotor();
    motorBottomRoller.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    proximitySensorValue = proximitySensor.get();
  }
}
  