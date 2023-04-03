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
        EXITSTATE
    }

    private FastBalanceStates state; 
    private double time;

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

    //time the robot needs to be level for to exit the command
    private double endPhaseTime = .5;
    
    public FastAutonBalanceCommand(Drivetrain drivetrain, BiConsumer<Double, Double> output, boolean reversed, Subsystem reqirements) {
        this.drivetrain = drivetrain;
        this.output = output;

        addRequirements(reqirements);

        if(reversed){
            onChargeStationDegree *= -1;
            shallowClimbDegree *= -1;
            levelDegree *= -1;
            robotSpeedFast *= -1;
            robotSpeedMid *= -1;
            robotSpeedSlow *= -1;
        }
        
    }

    private double getPitch(){
        return drivetrain.getPitch();
    }

    // runs 50 times a second, so one second time is 50 loops of code
    private int secondsToTicks(double time) {
        return (int) (time * 50);
    }

    public double FastAutonBalance() {
        
        switch (state) {
            
            // switch to steep climb state when on charge station
            case GROUNDSTATE:
                if (getPitch() > onChargeStationDegree){
                    state = FastBalanceStates.STEEPCLIMBSTATE;
                }
                break;
            
            // switch to middle climb state when the pitch is less steep
            case STEEPCLIMBSTATE: 
                if (getPitch() < onChargeStationDegree){
                    state = FastBalanceStates.MIDDLECLIMBSTATE;
                }
                break;
            
            // switch to reverse slow when charge station starts to flip
            case MIDDLECLIMBSTATE:
                if(getPitch() < shallowClimbDegree){
                    state = FastBalanceStates.REVERSECLIMBSTATE;
                }
                break;

            // switch to stop when pitch is greater than the negative level degree
            case REVERSECLIMBSTATE:
                if (getPitch() > -levelDegree){
                    state = FastBalanceStates.LEVELSTATE;
                }
                break;

            // switch to stop when the pitch is less than the level degree
            case SHALLOWCLIMBSTATE:
                if (getPitch() < levelDegree){
                    state = FastBalanceStates.LEVELSTATE;
                }
                break;

            // swich to slow climb based on the side the charge station is tipping
            case LEVELSTATE:
                if(getPitch() > levelDegree){
                    state = FastBalanceStates.SHALLOWCLIMBSTATE;
                    time = 0;
                }
                else if (getPitch() < -levelDegree){
                    state = FastBalanceStates.REVERSECLIMBSTATE;
                    time = 0;
                }
                else {
                    time++;
                }

                if (time == secondsToTicks(endPhaseTime)){
                    state = FastBalanceStates.EXITSTATE;
                }
                
                break;
            
            //state to exit the command
            case EXITSTATE:
                break;

        }

        // output state machine
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

            case EXITSTATE:
                return 0;

        }
        
        return 0;
    }

    @Override
    public void initialize() {
      state = FastBalanceStates.GROUNDSTATE;

      output.accept(0.0, 0.0);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      output.accept(FastAutonBalance(), FastAutonBalance());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      output.accept(0.0, 0.0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return state == FastBalanceStates.EXITSTATE;
    }

}
