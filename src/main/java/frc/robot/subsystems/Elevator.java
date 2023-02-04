package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Types.ElevatorPosition;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class Elevator extends SubsystemBase implements Loggable{
    public final CANSparkMax elevatorMotor = new CANSparkMax(CAN.elevatorMotor, MotorType.kBrushless);
    //will need limit switch or encoder to stop elevator when it reaches top/bottom. Encoder may be easier because might need 2 limit switches?
    private final RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    //DigitalInput toplimitSwitch = new DigitalInput(0);
    //DigitalInput bottomlimitSwitch = new DigitalInput(1);
    
    public Elevator(){
        elevatorMotor.setIdleMode(IdleMode.kBrake);
        elevatorMotor.burnFlash();
    }

    public void setPower(double power)
    {
        elevatorMotor.set(power);
    }

    public double convertEncoderPosition(ElevatorPosition position){
        double encoderPosition;
        if (position == ElevatorPosition.BOTTOM){
            encoderPosition = ElevatorConstants.bottomEncoderPosition;
        }
        else{
            encoderPosition = ElevatorConstants.topEncoderPosition;
        }
        return encoderPosition;
    }

    @Config
     public double getElevatorPosition(){
        // gets the current elevator encoder position
        return elevatorEncoder.getPosition();
    }

    //pub

    @Override
    public void periodic() {   
      // This method will be called once per scheduler run
    }
}