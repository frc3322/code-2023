// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.Constants.DIO;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Types.ElevatorPosition;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//mport frc.robot.Elevator;

public class Transfer extends SubsystemBase {
  //private final DigitalInput transferInProximitySensor = new DigitalInput(DIO.transferInProximitySensor);
  //private final DigitalInput transferOutProximitySensor = new DigitalInput(DIO.transferOutProximitySensor);
  
  
  //elevator stuff
  public final CANSparkMax elevatorMotor = new CANSparkMax(Constants.CAN.elevatorMotor, MotorType.kBrushless);
  
  private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
  private final DigitalInput toplimitSwitch = new DigitalInput(0);
  private final DigitalInput bottomlimitSwitch = new DigitalInput(1);

  


  /** Creates a new Transfer. */
  public static final CANSparkMax beltMotor = new CANSparkMax(Constants.CAN.transfer, MotorType.kBrushless);
  public Transfer() {
    //Initialize Belt
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
    
    //Initialize Elevator
    elevatorMotor.setIdleMode(IdleMode.kBrake);
    elevatorMotor.burnFlash();

    //ELEVATOR CODE
    
  }

  // private boolean isTransferOccupied() {
  //   return !isTransferEmpty();
  // }

  // // private boolean isTransferEmpty() {
  // //   return transferInProximitySensor.get() && transferOutProximitySensor.get();
  // // }

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



  //ELEVATOR CODE STARTS HERE
  

  //check if elevator is up or down. Could probably be a lambda in toElevator
  public boolean getElevator() {
    //Elevator.getPlace(); just a placholder for now
    return false;
  }
 
  public void setBeltPower(double power){
    beltMotor.set(power);
  }


  public void stop() {
    beltMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
    
  
    public void setElevatorPower(double power)
    {
        elevatorMotor.set(power);
    }

    public ElevatorPosition getElevatorPosition(){
      // gets the current elevator encoder position
      double position=elevatorEncoder.getPosition();
      if (position==ElevatorConstants.kBottomEncoderPosition){
        return ElevatorPosition.BOTTOM;
      }
      else if (position==ElevatorConstants.kTopEncoderPosition){
        return ElevatorPosition.TOP;
      }
      else{
        return ElevatorPosition.MIDDLE;
      }
    }

    public double convertEncoderPosition(ElevatorPosition position) {
      //creates empty variable
      double encoderPosition;
      /*
      //checks if position is equal to bottom position, and sets it to the bottom constant
      if (position == ElevatorPosition.EDGE){
        encoderPosition=ElevatorConstants.kEdgeEncoderPosition;
      }
      else {
        //not sure what this is supposed to be set to, but it must not be equal to EDGE (need to understand enum)
        encoderPosition = position;
      }
      */

      if (position == ElevatorPosition.BOTTOM/*ElevatorPosition.BOTTOM*/){
          encoderPosition = ElevatorConstants.kBottomEncoderPosition;
      }
      //does same for top
      else if (position == ElevatorPosition.TOP){ 
          encoderPosition = ElevatorConstants.kTopEncoderPosition;
      }
      //just returns encoder position 
      else{
        //this probably is not supposed to return the value, seeing as the other conditions don't
        encoderPosition = elevatorEncoder.getPosition();
      }
      return encoderPosition;
      


    }
    public void elevatorStop(){
      if ((toplimitSwitch.get()==true) || (bottomlimitSwitch.get()==true) || (convertEncoderPosition(getElevatorPosition()) == ElevatorConstants.kBottomEncoderPosition)){
        elevatorMotor.stopMotor();
        /*elevatorEncoder.reset();
        elevatorEncoder.setReverseDirection(true);*/

      }


    }
    public ElevatorPosition getDestination(){
      ElevatorPosition currentPosition=getElevatorPosition();
      if (currentPosition==ElevatorPosition.BOTTOM){
        return ElevatorPosition.TOP;
      }


      //this is the code that should be used but I don't know how to make it return its current direction while moving.
      /*else if (currentPosition==ElevatorPosition.TOP){
        return ElevatorPostition.BOTTOM;
      }*/

      else {
        return ElevatorPosition.BOTTOM;
      }


    }

   


}
