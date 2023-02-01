package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {
    public final CANSparkMax elevatorMotor = new CANSparkMax(Constants.CAN.E, MotorType.kBrushless);
    //will need limit switch or encoder to stop elevator when it reaches top/bottom. Encoder may be easier because might need 2 limit switches?
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    
    public Elevator(){
        elevatorMotor.setIdleMode(IdleMode.kBrake);
        elevatorMotor.burnFlash();
    }

    //either up or down will need to be reversed
    public void up() {
        elevatorMotor.setInverted(false);;
        elevatorMotor.setVoltage(12.0);
        //something about the encoder/limit switch to stop it
    }
    public void down() {
        elevatorMotor.setInverted(true);
        elevatorMotor.setVoltage(12.0);
        //something about the encoder/limit switch to stop it
    }
    public void getPlace(){
        elevatorEncoder.getPosition();
    }




    @Override
    public void periodic() {   
      // This method will be called once per scheduler run
    }
}