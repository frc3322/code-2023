// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Intake extends SubsystemBase implements Loggable {
  /** Creates a new Intake. */
  private final CANSparkMax motorTopRoller = new CANSparkMax(CAN.tRoller, MotorType.kBrushless);
  private final CANSparkMax motorBottomRoller = new CANSparkMax(CAN.bRoller, MotorType.kBrushless);
  private final CANSparkMax motorArm = new CANSparkMax(CAN.pivotIntake, MotorType.kBrushless);
  //motor arm will need an encoder or limit switch to determine where to stop
  private final RelativeEncoder armEncoder = motorArm.getEncoder();
  // intake will need a proximity sensor to tell if there is a game piece inside
  private final DigitalInput proximitySensor = new DigitalInput(0);

  @Log private boolean proximitySensorValue;
  @Log private double armpos;
  
  public Intake(){
    //default settings here, right?
    motorTopRoller.setIdleMode(IdleMode.kBrake);
    motorBottomRoller.setIdleMode(IdleMode.kBrake);
    motorArm.setIdleMode(IdleMode.kBrake);

    motorTopRoller.burnFlash();
    motorBottomRoller.burnFlash();
    motorArm.burnFlash();
  }

  public Boolean atTop(){
    return armEncoder.getPosition() < 0.1;
  }    
  
  public Boolean atBottom(){
    return armEncoder.getPosition() > IntakeZoneLimits.bottomLimitOff;
  }  
  

    public Command flipUp(){
      return new RunCommand(
        () -> setFlipperSpeed(calculateIntakeFlipUp())
      )
      .until(()->atTop());
      
    }

    
    public Command flipDown(){
      return new RunCommand(
        () -> setFlipperSpeed(calculateIntakeFlipDown())
      )
      .until(()->atBottom());
      
    }


  public void setFlipperSpeed(double speed){
    motorArm.set(speed);
  }

  public double calculateIntakeFlipUp(){
    if ((armEncoder.getPosition() < IntakeZoneLimits.topLimitOff)){
      return 0;
    }else if(armEncoder.getPosition() < IntakeZoneLimits.slowZoneStart){
      return IntakeConstants.armUpSlowSpeed;
    }else{
      return IntakeConstants.armUpSpeed;
    }
  }

  public double calculateIntakeFlipDown(){
    if ((armEncoder.getPosition() > IntakeZoneLimits.bottomLimitOff)){
      return 0;  
    }else{
      return IntakeConstants.armDownSpeed;
    }
  }
 
  public void spinIntake(double speed){
    motorTopRoller.set(speed);
    motorBottomRoller.set(speed * 0.8);
  }

  public void stopSpin() {
    //turns rollers on and off
    motorTopRoller.stopMotor();
    motorBottomRoller.stopMotor();
  }
  public void resetArmEncoder(){
    armEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    proximitySensorValue = proximitySensor.get();
    armpos = armEncoder.getPosition();
  }
}
  