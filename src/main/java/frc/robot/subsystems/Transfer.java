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

//this entire file may become irrelevant.
public class Transfer extends SubsystemBase implements Loggable{
  private final DigitalInput transferInProximitySensor = new DigitalInput(DIO.transferInProximitySensor);
  private final DigitalInput transferOutProximitySensor = new DigitalInput(DIO.transferOutProximitySensor);
  private final DigitalInput elevatorTopProx = new DigitalInput(DIO.elevatorTop);
  private final DigitalInput elevatorBottomProx = new DigitalInput(DIO.elevatorBottom);
 

  /** Creates a new Transfer. */
  public static final CANSparkMax beltMotor = new CANSparkMax(CAN.transfer, MotorType.kBrushless);


  @Log public double activeBeltSpeed = TransferConstants.coneTransferSpeed;




  public Transfer() {
    //Initialize Belt
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
    
    //Initialize Elevator

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Log
  public boolean isFrontOccupied() {
    //checks that first is occupied
    return (!transferInProximitySensor.get());
  }

  @Log
  public boolean isBackOccupied(){
    //checks that back is not detecting
    return (!transferOutProximitySensor.get());
  }


  public Command beltRunCommand(Transfer transfery){
   return new RunCommand(() ->{
    if(isFrontOccupied()){
      setBeltPower(activeBeltSpeed);
    }
    if(isBackOccupied()){
      setBeltPower(0);
    }
   }, transfery);
  }

  public void setBeltPower(double power){
    beltMotor.set(power);
  }

  public boolean isBeltRunning(){
    return beltMotor.get() != 0;
  }

  public void setActiveBeltSpeed(double speed){
    activeBeltSpeed = speed;
  }
}