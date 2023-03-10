// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Types.ElevatorPosition;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.subsystems.Transfer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveElevatorCommand extends PIDCommand {
  
  /** Creates a new MoveElevator. */
  public MoveElevatorCommand(Transfer transfer, ElevatorPosition targetPosition) {
  
    super(
      
        // The controller that the command will use
        new PIDController(ElevatorConstants.kp, ElevatorConstants.ki, ElevatorConstants.kd),
        // This should return the measurement
       transfer::getElevatorPosition,
        // This should return the setpoint (can also be a constant)
        transfer.convertEncoderPosition(targetPosition),
        // This uses the output
        output -> {
          transfer.setElevatorPower(output);
        });

        addRequirements(transfer);
        SmartDashboard.putData(this.getController());
       
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(getController().getPositionError())<.2;
    
  }
}
