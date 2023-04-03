package frc.robot.commands;

import java.util.function.BiConsumer;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Drivetrain;

public class FastAutonBalanceCommand extends CommandBase{
    
    private Drivetrain drivetrain;
    private BiConsumer<Double, Double> output;

    private enum FastBalanceStates {
        GROUNDSTATE,
        STEEPCLIMBSTATE,
        MIDDLECLIMBSTATE,
        REVERSECLIMBSTATE,
        SHALLOWCLIMBSTATE,
        LEVELSTATE,
    }

    private FastBalanceStates state; 
    private boolean reversed;

    // Slightly less than the steepest angle that the robot will reach while climbing
    private double onChargeStationDegree = 14;

    // Angle where the robot can climb at a verly low speed for precision
    private double shallowClimbDegree = 8;

    // The robot will attemt to get within positive or negative of this angle
    private double levelDegree = 4;

    // Intial speed and speed for steepest part of the climb
    private double robotSpeedFast = 4;

    // Speed for phaase of climb where all wheels are touching charge station
    private double robotSpeedMid = 2;

    // Speed for rinal part of balancing
    private double robotSpeedSlow = .6;
    
    public FastAutonBalanceCommand(Drivetrain drivetrain, BiConsumer<Double, Double> output, boolean reversed, Subsystem reqirements) {
        this.drivetrain = drivetrain;
        this.output = output;
        this.reversed = reversed;

        addRequirements(reqirements);
    }

    private double getPitch(){
        return drivetrain.getPitch();
    }

    public double FastAutonBalance() {
        switch (state) {
            case GROUNDSTATE:
                if (getPitch() > onChargeStationDegree){
                    state = FastBalanceStates.STEEPCLIMBSTATE;
                }
                break;
            
            case STEEPCLIMBSTATE: 
                if (getPitch() < onChargeStationDegree){
                    state = FastBalanceStates.MIDDLECLIMBSTATE;
                }
                break;
            
            case MIDDLECLIMBSTATE:
                if(getPitch() < shallowClimbDegree){
                    state = FastBalanceStates.REVERSECLIMBSTATE;
                }
                break;

            case REVERSECLIMBSTATE:
                if (getPitch() > -levelDegree){
                    state = FastBalanceStates.LEVELSTATE;
                }
                break;

            case SHALLOWCLIMBSTATE:
                if (getPitch() < levelDegree){
                    state = FastBalanceStates.LEVELSTATE;
                }
                break;

            case LEVELSTATE:
                if(getPitch() > levelDegree){
                    state = FastBalanceStates.SHALLOWCLIMBSTATE;
                }
                if (getPitch() < -levelDegree){
                    state = FastBalanceStates.REVERSECLIMBSTATE;
                }
                break;

        }

        switch(state) {
            case GROUNDSTATE:
                return robotSpeedFast;
            
            case STEEPCLIMBSTATE:
                return robotSpeedFast;
            
            case MIDDLECLIMBSTATE:
                return robotSpeedMid;
            
            case REVERSECLIMBSTATE:
                return -robotSpeedSlow;

            case SHALLOWCLIMBSTATE:
                return robotSpeedSlow;

            case LEVELSTATE:
                return 0;
        }
        
        return 0;
    }

    @Override
    public void initialize() {
      state = FastBalanceStates.GROUNDSTATE;
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      output.accept(FastAutonBalance(), FastAutonBalance());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

}
