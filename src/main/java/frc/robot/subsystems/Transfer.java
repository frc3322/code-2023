// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//mport frc.robot.Elevator;
public class Transfer extends SubsystemBase {
  /** Creates a new Transfer. */
  public final CANSparkMax beltMotor = new CANSparkMax(Constants.CAN.T, MotorType.kBrushless);
  public Transfer() {
    beltMotor.setIdleMode(IdleMode.kBrake);
    beltMotor.burnFlash();
  }
  //check if elevator is up or down. Could probably be a lambda in toElevator
  public boolean getElevator() {
    //Elevator.getPlace(); just a placholer for now
    return false;
  }
  public void beltForward() {
    //bring cubes/cones to elevator
    //add check for getElevator
    beltMotor.setVoltage(12.0);
  } 
  public void beltReverse() {
    //eject cubes/cones
    beltMotor.setVoltage(-12.0);
  }
  public void stop() {
    beltMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
