// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import java.util.function.BooleanSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.Constants.DIO;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Types.ElevatorPosition;
import io.github.oblarg.oblog.annotations.Log;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//mport frc.robot.Elevator;

public class Transfer extends SubsystemBase {
  // private final DigitalInput transferInProximitySensor = new DigitalInput(DIO.transferInProximitySensor);
  // private final DigitalInput transferOutProximitySensor = new DigitalInput(DIO.transferOutProximitySensor);
  //private final DigitalInput elevatorTopProx = new DigitalInput(DIO.elevatorTop);
 

  /** Creates a new Transfer. */
  public static final CANSparkMax beltMotor = new CANSparkMax(Constants.CAN.transfer, MotorType.kBrushless);
  public final CANSparkMax elevatorMotor = new CANSparkMax(Constants.CAN.elevatorMotor, MotorType.kBrushless);

  private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();

  @Log
  private double elevatorPos = elevatorEncoder.getPosition();


  public Transfer() {
    //Initialize Belt
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
    
    //Initialize Elevator
    elevatorMotor.setIdleMode(IdleMode.kBrake);
    elevatorMotor.burnFlash();
  }

  // private boolean isTransferOccupied() {
  //   return !isTransferEmpty();
  // }

  // private boolean isTransferEmpty() {
  //   return transferInProximitySensor.get() && transferOutProximitySensor.get();
  // }

  // public boolean shouldRunBelt() {
  //   //If there is nothing in transfer, return false immediately
  //   if (isTransferEmpty()) {
  //     return false;
  //   }
  //   //If first sensor is empty and second sensor is not empty, return false immediately
  //   if (transferInProximitySensor.get() && !transferOutProximitySensor.get()) {
  //     return false;
  //   }
  //   //Assumption: If both sensors are occupied, run the belt
  //   return true;
  // }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    elevatorPos = elevatorEncoder.getPosition();
    if(elevatorAtTop().getAsBoolean()){
      elevatorPos = 0;
    }

  }

  public void setBeltPower(double power){
    beltMotor.set(power);
  }

  public BooleanSupplier elevatorAtTop(){
    return ()->false;
  }

  public BooleanSupplier elevatorShouldRun(){
    return ()-> (!elevatorAtTop().getAsBoolean()) && (!(elevatorPos > ElevatorConstants.kBottomEncoderPosition));
  }
  
    public void setElevatorPower(double power)
    {
        elevatorMotor.set(power);
    }

    public double convertEncoderPosition(ElevatorPosition position) {
      double encoderPosition;
      if (position == ElevatorPosition.BOTTOM){
          encoderPosition = ElevatorConstants.kBottomEncoderPosition;
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
