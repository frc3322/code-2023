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
  private final DigitalInput transferInProximitySensor = new DigitalInput(DIO.transferInProximitySensor);
  private final DigitalInput transferOutProximitySensor = new DigitalInput(DIO.transferOutProximitySensor);


  /** Creates a new Transfer. */
  public static final CANSparkMax beltMotor = new CANSparkMax(Constants.CAN.transfer, MotorType.kBrushless);
  public Transfer() {
    //Initialize Belt
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
    
    //Initialize Elevator
    elevatorMotor.setIdleMode(IdleMode.kBrake);
    elevatorMotor.burnFlash();
  }

  private boolean isTransferOccupied() {
    return !isTransferEmpty();
  }

  private boolean isTransferEmpty() {
    return transferInProximitySensor.get() && transferOutProximitySensor.get();
  }

  public boolean shouldRunBelt() {
    //If there is nothing in transfer, return false immediately
    if (isTransferEmpty()) {
      return false;
    }
    //If first sensor is empty and second sensor is not empty, return false immediately
    if (transferInProximitySensor.get() && !transferOutProximitySensor.get()) {
      return false;
    }
    //Assumption: If both sensors are occupied, run the belt
    return true;
  }

  //check if elevator is up or down. Could probably be a lambda in toElevator
  public boolean getElevator() {
    //Elevator.getPlace(); just a placholer for now
    return false;
  }
 
  public static void setBeltPower(double power){
    beltMotor.set(power);
  }


  public void stop() {
    beltMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
    //ELEVATOR CODE
  public final CANSparkMax elevatorMotor = new CANSparkMax(Constants.CAN.elevatorMotor, MotorType.kBrushless);
    //will need limit switch or encoder to stop elevator when it reaches top/bottom. Encoder may be easier because might need 2 limit switches?
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    //DigitalInput toplimitSwitch = new DigitalInput(0);
    //DigitalInput bottomlimitSwitch = new DigitalInput(1);
  
    public void setPower(double power)
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
        return elevatorEncoder.getPosition();
    }
}
