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
        PAUSESTATE,
        REVERSECLIMBSTATE,
        SHALLOWCLIMBSTATE,
        LEVELSTATE,
        EXITSTATE
    }

    private FastBalanceStates state; 
    private double time;
    private boolean reversed;

    // Slightly less than the steepest angle that the robot will reach while climbing
    private double onChargeStationDegree = 13;

    // Angle where the robot can climb at a verly low speed for precision
    private double shallowClimbDegree = 10; //12

    //Angle at which the robot has fully flipped
    private double flipDegree = -10;
    
    //Angle that the PAUSESTATE ends at. Only used in non timed pauseState
    private double pauseEndDegree = -4;

    // The robot will attemt to get within positive or negative of this angle
    private double levelDegree = 4.5;

    // Intial speed and speed for steepest part of the climb
    private double robotSpeedFast = 4;

    //Speed the robot climbs at
    private double robotSpeedMid = 2;

    //Speed during PAUSESTATE
    private double pauseSpeed = -1;

    // Speed for final part of balancing
    private double robotSpeedSlow = .7;

    //time the robot pauses when the charge station flips
    private double pauseWaitTime = 1.8;

    //time the robot needs to be level for to exit the command
    private double endPhaseTime = 1.5;

    private boolean hasFlipped = false;
    
    public FastAutonBalanceCommand(Drivetrain drivetrain, boolean reversed, Subsystem reqirements) {
        this.drivetrain = drivetrain;
        this.output = drivetrain::tankDriveVolts; //modified
        this.reversed = reversed;

        addRequirements(reqirements);

        if(reversed){
            robotSpeedFast *= -1;
            robotSpeedMid *= -1;
            robotSpeedSlow *= -1;
            pauseSpeed *= -1;
        }
        
    }

    private double getPitch(){
        // if(reversed){
        //     return -drivetrain.getPitch();
        // }
        // return drivetrain.getPitch();

        return drivetrain.getPitch() * (reversed ? -1.0 : 1.0);
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
                if (getPitch() < shallowClimbDegree){
                    state = FastBalanceStates.PAUSESTATE;
                }
                break;
            
            // switch to reverse slow when charge station starts to flip
            case PAUSESTATE:
                time++;
                if(time >= secondsToTicks(pauseWaitTime)){
                    state = FastBalanceStates.LEVELSTATE;
                    time = 0;
                }
                break;

            //New PAUSESTATE may increase reliability.
            // case PAUSESTATE:
            //  if(getPitch() < flipDegree){
            //      hasFlipped = false;
            //  }
            
            //  if(getPitch() > pauseEndDegree){
            //      state = FastBalanceStates.LEVELSTATE;
            //  }
            //  break;

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

                if (time >= secondsToTicks(endPhaseTime)){ //modified
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
                return robotSpeedMid;
            
            case PAUSESTATE:
                return pauseSpeed;
            
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
        hasFlipped = false;
        output.accept(0.0, 0.0);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
       // double speed = FastAutonBalance();
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
