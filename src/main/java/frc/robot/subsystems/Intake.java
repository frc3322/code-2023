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
  private final CANSparkMax motorTopRoller = new CANSparkMax(Constants.CAN.tRoller, MotorType.kBrushless);
  private final CANSparkMax motorBottomRoller = new CANSparkMax(Constants.CAN.bRoller, MotorType.kBrushless);
  private final CANSparkMax motorArm = new CANSparkMax(Constants.CAN.pivotIntake, MotorType.kBrushless);
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
  
  public void flipDown() {
    //move arm to down/intake position
    while (proximitySensor.get()==true){
      setFlipperSpeed(Constants.IntakeConstants.armDownSpeed);
    }
    motorArm.stopMotor();
  }


  public void flipUp() {
    //move arm to up/transfer/elevator position. encoders don't do get. Switching this to while proximity is false, may need to be changed later.
    /*while (armEncoder.get()==true){
      setFlipperSpeed(Constants.IntakeConstants.armUpSpeed);
    }
    motorArm.stopMotor();*/
    while (proximitySensor.get()==true){
      setFlipperSpeed(Constants.IntakeConstants.armUpSpeed);
    }

    }

    public void encoderFlipUp(){
      while(armEncoder.getPosition() > 0.1){
        motorArm.set(calculateIntakeFlipUp());
      }
    }


  public void setFlipperSpeed(double speed){
    motorArm.set(speed);
  }

  public double calculateIntakeFlipUp(){
    if ((armEncoder.getPosition() < Constants.IntakeZoneLimits.topLimitOff)|| (armEncoder.getPosition() > Constants.IntakeZoneLimits.bottomLimitOff)){
      return 0;
    }else if(armEncoder.getPosition() < Constants.IntakeZoneLimits.slowZoneStart){
      return Constants.IntakeConstants.armUpSlowSpeed;
    }else{
      return Constants.IntakeConstants.armUpSpeed;
    }
  }
 
  public void spinIntake(double speed){
    motorTopRoller.set(speed);
    motorBottomRoller.set(speed);
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
  