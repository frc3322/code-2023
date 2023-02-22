// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import java.util.function.BooleanSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants.*;
import frc.robot.Types.ElevatorPosition;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Transfer extends SubsystemBase implements Loggable{
  private final DigitalInput transferInProximitySensor = new DigitalInput(DIO.transferInProximitySensor);
  private final DigitalInput transferOutProximitySensor = new DigitalInput(DIO.transferOutProximitySensor);
  private final DigitalInput elevatorTopProx = new DigitalInput(DIO.elevatorTop);
  private final DigitalInput elevatorBottomProx = new DigitalInput(DIO.elevatorBottom);
 

  /** Creates a new Transfer. */
  public static final CANSparkMax beltMotor = new CANSparkMax(CAN.transfer, MotorType.kBrushless);
  public static final CANSparkMax elevatorMotor = new CANSparkMax(CAN.elevatorMotor, MotorType.kBrushless);

  private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();

  @Log private double elevatorPos = elevatorEncoder.getPosition();

  @Log private boolean elevatorTop = elevatorAtTop().getAsBoolean();
  @Log private boolean elevatorBotom = elevatorAtBottom().getAsBoolean();

  @Log private boolean elevatorShouldntRunlogg = elevatorShouldntRun().getAsBoolean();

  @Log private double activeBeltSpeed = TransferConstants.coneTransferSpeed;




  public Transfer() {
    //Initialize Belt
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
    
    //Initialize Elevator
    elevatorMotor.setIdleMode(IdleMode.kBrake);
    elevatorMotor.burnFlash();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    elevatorPos = elevatorEncoder.getPosition();
    if(elevatorAtTop().getAsBoolean()){
      elevatorPos = 0;
    }
    elevatorTop = elevatorTopProx.get();
    elevatorBotom = elevatorBottomProx.get();
    elevatorShouldntRunlogg = elevatorShouldntRun().getAsBoolean();

  }

  @Log
  private boolean isFrontOccupied() {
    //checks that first is occupied
    return (!transferInProximitySensor.get());
  }

  @Log
  private boolean isBackOccupied(){
    //checks that back is not detecting
    return (!transferOutProximitySensor.get());
  }

  // private boolean isTransferEmpty() {
  //   //if both true, true
  //   return transferInProximitySensor.get() && transferOutProximitySensor.get();
  // }

  public boolean shouldRunBelt() {
    // //If there is nothing in transfer, return false immediately
    // if (isTransferEmpty()) {
    //   return false;
    // }
    // //If first sensor is empty and second sensor is not empty, return false immediately
    // if (transferInProximitySensor.get() && !transferOutProximitySensor.get()) {
    //   return false;
    // }
    // //Assumption: If both sensors are occupied, run the belt
    // return true;
    //if transfer is occupied, return true
    // if (isBackOccupied()){
    //   return false;
    // }
    if (isFrontOccupied()) {
      return true;
    }
    else {
      return false;
    }
  }

  public Command beltRunCommand(){
   return new RunCommand(() ->{
    if(isFrontOccupied()){
      setBeltPower(activeBeltSpeed);
    }
    if(isBackOccupied()){
      setBeltPower(0);
    }
   }, this);
  }

  public void setBeltPower(double power){
    beltMotor.set(power);
  }

  public void setActiveBeltSpeed(double speed){
    activeBeltSpeed = speed;
  }


  public BooleanSupplier elevatorAtTop(){
    return ()-> !elevatorTopProx.get();
  }

  
  public BooleanSupplier elevatorAtBottom(){
    return ()-> !elevatorBottomProx.get();
  }

  public BooleanSupplier elevatorShouldntRun(){
    return ()-> (elevatorAtTop().getAsBoolean()) || (elevatorAtBottom().getAsBoolean());
  }

  public Command elevatorToTop(){
    return new RunCommand(()-> setElevatorPower(-.5))
    .until(elevatorAtTop())
    .andThen(()-> setElevatorPower(0));
  }

  public Command elevatorToBottom(){
    return new RunCommand(()-> setElevatorPower(.5))
    .until(elevatorAtBottom())
    .andThen(()-> setElevatorPower(0));
  }

  
    public void setElevatorPower(double power)
    {
        elevatorMotor.set(power);
    }

    public double convertEncoderPosition(ElevatorPosition position) {
      double encoderPosition;
      if (position == ElevatorPosition.BOTTOM){
          encoderPosition = ElevatorConstants.kBottomEncoderPosition;
      }else if (position == ElevatorPosition.MID){
        encoderPosition = ElevatorConstants.kMidEncoderPosition;
      }
      else{
          encoderPosition = ElevatorConstants.kTopEncoderPosition;
      }
      return encoderPosition;
    }

    public double getElevatorPosition(){
        // gets the current elevator encoder position
        return elevatorPos;
    }
}
