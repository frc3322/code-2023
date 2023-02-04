// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Types.ElevatorPosition;
import frc.robot.subsystems.Elevator;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveElevatorCommand extends PIDCommand {
  
  /** Creates a new MoveElevator. */
  public MoveElevatorCommand(Elevator elevator, ElevatorPosition position) {
  
    super(
      
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
       elevator::getElevatorPosition,
        // This should return the setpoint (can also be a constant)
        elevator.convertEncoderPosition(position),
        // This uses the output
        output -> {
          elevator.setPower(output);
        });

        addRequirements(elevator);
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
