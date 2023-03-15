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
  private final DigitalInput intakeTopSensor = new DigitalInput(DIO.intakeTopSensor);
  // intake will need a proximity sensor to tell if there is a game piece inside
 
  @Log private double armpos;
  @Log private double armPower;
  @Log private double intakeAtTop;
  
  public Intake(){
    //default settings here, right?
    motorTopRoller.setIdleMode(IdleMode.kBrake);
    motorBottomRoller.setIdleMode(IdleMode.kBrake);
    motorArm.setIdleMode(IdleMode.kBrake);

    motorTopRoller.burnFlash();
    motorBottomRoller.burnFlash();
    motorArm.burnFlash();
  }

  @Log
  public Boolean atTop(){
    //return true if encoder is at 0 position/below 1 position.
    return armEncoder.getPosition() < 1;
  }    
  
  @Log
  public Boolean atBottom(){
    //return true if encoder is at bottom.
    return armEncoder.getPosition() > IntakeZoneLimits.bottomLimitOff;
  }  
  
/*
Not in use
    public Command spinIntakeWhileUp(boolean transferRunning){

      return new RunCommand(() ->{
        if(transferRunning){
          spinIntakeSameWay(.1);
        }
        else{
          spinIntakeSameWay(0);
        }
       }, this);
    }
*/
    public Command flipUp(){
      return new RunCommand(
        //set flipper to correct setting until it is true that the flipper is at the top.
        () -> setFlipperSpeed(calculateIntakeFlipUp())
      )
      .until(()->atTop());
      
    }

    public Command flipDownSpin(){
      return new RunCommand(
        //flip down and spin intake in with the top wheels moving faster
        () -> {setFlipperSpeed(calculateIntakeFlipDown());
        spinIntakeTopFaster(IntakeConstants.coneIntakeInSpeed);}, this
      )
      .until(()->atBottom()
    )
    .withTimeout(2);
      
    }

    public Command cubeFlipDownSpin(){
      return new RunCommand(
        () -> {setFlipperSpeed(calculateIntakeFlipDown());
        spinIntakeTopFaster(IntakeConstants.cubeIntakeInSpeed);}, this
      )
      .until(()->atBottom()
    )
    .withTimeout(2);
      
    }
    
    public Command flipUpStop(){
      //stop spinning, then move until at top is true. 
      return new RunCommand(
        () -> {setFlipperSpeed(calculateIntakeFlipUp());
        spinIntakeTopFaster(0);}, this
      )
      .until(()->atTop()
      ).withTimeout(2);
      
    }

    
    public Command flipDown(){
      //flips to bottom, does not spin. may be able to delete
      return new RunCommand(
        () -> setFlipperSpeed(calculateIntakeFlipDown())
      )
      .until(()->atBottom());
      
    }
/*
not in use
    public Command flipDownEject(){
      return new RunCommand(
        () -> {setFlipperSpeed(calculateIntakeFlipDown());
        spinIntakeTopFaster(-IntakeConstants.coneIntakeInSpeed);}
      )
      .until(()->atBottom()
      ).withTimeout(2);
      
    */


  public void setFlipperSpeed(double speed){
    //moves the entire arm
    motorArm.set(speed);
  }

  @Log
  public double calculateIntakeFlipUp(){
    if ((armEncoder.getPosition() < IntakeZoneLimits.topLimitOff)){
      return 0;
    }else if(armEncoder.getPosition() < IntakeZoneLimits.slowZoneStart){
      return IntakeConstants.armUpSlowSpeed;
    }else{
      return IntakeConstants.armUpSpeed;
    }
  }

  @Log
  public double calculateIntakeFlipDown(){
    if ((armEncoder.getPosition() > IntakeZoneLimits.bottomLimitOff)){
      return 0;  
    }else{
      return IntakeConstants.armDownSpeed;
    }
  }
 
  public void spinIntakeTopFaster(double speed){
    motorTopRoller.set(speed);
    motorBottomRoller.set(-speed * IntakeConstants.bottomRollerSpeedMultiplier);
    //multiply by .8
  }
  public void spinIntake(double speed){
    //move both same speed
    motorTopRoller.set(speed);
    motorBottomRoller.set(-speed);
    //multiply by .8
  }


  public void spinIntakeBottomFaster(double speed){
    //move bottom faster
    motorTopRoller.set(speed);
    motorBottomRoller.set(speed * 2);
    //multiply by .8
  }
/*
Not in use
  public void spinIntakeSameWay(double speed){
    //spin them in same direction
    motorTopRoller.set(speed);
    motorBottomRoller.set(-speed);
  }
  */

  public void resetArmEncoder(){
    //set current position to 0.
    armEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    armpos = armEncoder.getPosition();
    armPower = motorArm.getAppliedOutput();

    // If top sensor detects intake up, zeros encoder
    // if (!intakeTopSensor.get()) {
    //   armEncoder.setPosition(0.0);
    // }
  }
}
  