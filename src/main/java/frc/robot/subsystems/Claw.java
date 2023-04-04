// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
//import com.revrobotics.CANSparkMax;
//import com.revrobotics.CanSparkMaxLowLevel
import frc.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Types.ClawPosition;

public class Claw extends SubsystemBase {
  /** Creates a new Outtake. */
  private DoubleSolenoid claw = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.CAN.clawExtend, Constants.CAN.clawRetract);
  private Servo clawServo = new Servo(Constants.PWM.S1);
  

  public Claw() {
    //create solenoids to grab cones and cubes
    //NOTE: this is based on the assumption that false is open and true is closed. may need to be switched when we know more.
 

    }

    public void setOpen() {
      //opens claw
     claw.set(Value.kReverse);
    }

    public void setClosed() {
      //closes claw
      claw.set(Value.kForward);
    }
    public void runServo() {
      //this is at half speed, and only goes in one direction
      clawServo.set(.75);
    }
    public void stopServo(){
      //stops servo
      clawServo.set(.5);
    }
    
    
    public ClawPosition getClawPosition(){
      if (claw.get()==Value.kReverse){
        //if claw is open return open
        return ClawPosition.OPEN;
      }
      else if (claw.get()==Value.kForward){
        //if claw is closed return closed
        return ClawPosition.CLOSED;
      }
      //I don't know if closed should be the default position, but it shuld work for not.
      return ClawPosition.CLOSED;
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}