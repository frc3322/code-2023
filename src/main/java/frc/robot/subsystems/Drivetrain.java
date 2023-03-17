package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import frc.robot.Constants;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Drivetrain extends SubsystemBase implements Loggable {
  private final CANSparkMax motorFR = new CANSparkMax(Constants.CAN.FR, MotorType.kBrushless);
  private final CANSparkMax motorFL = new CANSparkMax(Constants.CAN.FL, MotorType.kBrushless);
  private final CANSparkMax motorBR = new CANSparkMax(Constants.CAN.BR, MotorType.kBrushless);
  private final CANSparkMax motorBL = new CANSparkMax(Constants.CAN.BL, MotorType.kBrushless);

  private final RelativeEncoder FLEncoder = motorFL.getEncoder();
  private final RelativeEncoder FREncoder = motorFR.getEncoder();
  private final RelativeEncoder BLEncoder = motorBL.getEncoder();
  private final RelativeEncoder BREncoder = motorBR.getEncoder();
  
  
  private final DifferentialDrive robotDrive = new DifferentialDrive(motorFL, motorFR);

  private final AHRS gyro = new AHRS();

  private final DifferentialDriveOdometry driveOdometry;
  
  private final SlewRateLimiter accelLimit = new SlewRateLimiter(1.2);
  private final SlewRateLimiter turnLimit = new SlewRateLimiter(2);

  //gets the default instance of NetworkTables that is automatically created
  NetworkTableInstance inst = NetworkTableInstance.getDefault();
  
  //gets the limelight table where data is stored from the limelight
  NetworkTable limelightTable = inst.getTable("limelight");

  // create double for logging the controller input
  @Log private double speed = -2;
  @Log private double turn = -2;

  //Variables to log voltage
  @Log double FLVoltageVal;
  @Log double FRVoltageVal;
  @Log double BLVoltageVal;
  @Log double BRVoltageVal;

  
   //Variables to log voltage
   @Log double FRVelocityVal;
   @Log double FLVelocityVal;
   @Log double BLVelocityVal;
   @Log double BRVelocityVal;

  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {
    motorFL.setInverted(true);
    motorFR.setInverted(false);
    motorBR.follow(motorFR);
    motorBL.follow(motorFL);

    FLEncoder.setPositionConversionFactor(0.4788/8.45);
    FREncoder.setPositionConversionFactor(0.4788/8.45);
    //meters per rotation, gear ratio
    
    FLEncoder.setVelocityConversionFactor(0.4788/8.45/60);
    FREncoder.setVelocityConversionFactor(0.4788/8.45/60);
    //meters per wheel rotation, gearing reduction, divide by 60 for per second


    motorFR.setIdleMode(IdleMode.kBrake);
    motorFL.setIdleMode(IdleMode.kBrake);
    motorBR.setIdleMode(IdleMode.kBrake);
    motorBL.setIdleMode(IdleMode.kBrake);

    motorFR.burnFlash();
    motorFL.burnFlash();
    motorBR.burnFlash();
    motorBL.burnFlash();

    resetEncoders();

    driveOdometry = new DifferentialDriveOdometry(gyro.getRotation2d(), FLEncoder.getPosition(), FREncoder.getPosition());
  }


  // Getters
  @Log
  public double getYaw() {
    return gyro.getRotation2d().getDegrees();
  }
  @Log
  public double getPitch() {
    return gyro.getPitch();
  }
  @Log
  public double getRoll() {
    //hopefully this should never change.
    return gyro.getRoll();
  }
  @Log
  public double getDistance() {
    return motorFR.getEncoder().getPosition();
  }
  //@Log
  public Pose2d getPose() {
    return driveOdometry.getPoseMeters();
  }

  public void resetOdometry(Pose2d pose){
    resetEncoders();
    driveOdometry.resetPosition(gyro.getRotation2d(), FLEncoder.getPosition(), FREncoder.getPosition(), pose);
  }

  // Setters
  public void resetGyro() {
    gyro.reset();
  }

  public void resetEncoders() {
    motorFL.getEncoder().setPosition(0);
    motorFR.getEncoder().setPosition(0);
    motorBL.getEncoder().setPosition(0);
    motorBR.getEncoder().setPosition(0);
  }

  public void setPID() {
    // TODO: compmlete this function
  }

  // Actions

  public void drive(double speed, double turn) {
    turn = 0.5 * turn + 0.5 * Math.pow(turn, 3);  // Weird math

    this.speed = speed;
    this.turn = turn;

   //robotDrive.arcadeDrive(accelLimit.calculate(speed), turnLimit.calculate(turn), false);
    robotDrive.arcadeDrive(speed, turn, false);

    robotDrive.feed();
  }

  public void tankDriveVolts(double leftVolts, double rightVolts){
    motorFL.setVoltage(leftVolts);
    motorFR.setVoltage(rightVolts);
    
    robotDrive.feed();
  }

  public void autonDrive(double speed, double turn) {
    
    // to compensate for the turning/binding on one side, add x to turn
    // turn = turn + x;

    this.speed = speed;
    this.turn = turn;

    // robotDrive.arcadeDrive(accelLimit.calculate(speed), turnLimit.calculate(turn), false);
    robotDrive.arcadeDrive(speed, turn, false);

    robotDrive.feed();
  }

  public void jankDrive(double speed, double turn) {

    turn = 0.5 * turn + 0.5 * Math.pow(turn, 3);  // Weird math

    this.speed = speed;
    this.turn = turn;

    HAL.report(
      31, 7, 2);
      speed = MathUtil.applyDeadband(speed, 0.02);
      turn = MathUtil.applyDeadband(turn, 0.02);
  
      var speeds = robotDrive.arcadeDriveIK(speed, turn, false);
  
      motorFL.set(speeds.left);
      motorFR.set(speeds.right * .90);
  
    

   // robotDrive.arcadeDrive(accelLimit.calculate(speed), turnLimit.calculate(turn), false);
   

    robotDrive.feed();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds(){
    return new DifferentialDriveWheelSpeeds(FLEncoder.getVelocity(), FREncoder.getVelocity());
  }

  

  // Limelight Functions Start

  public void setPipeline(int pipelineNum){
    limelightTable
      //gets the "pipeline" entry from the limelight table
      .getEntry("pipeline")
      //sets the value of the pipeline entry to the parameter of the function
      .setNumber(pipelineNum);
  }


  @Override
  public void periodic() {
    // heading = getHeading();

    FLVoltageVal = motorFL.getBusVoltage();
    FRVoltageVal = motorFR.getBusVoltage();
    BLVoltageVal = motorBL.getBusVoltage();
    BRVoltageVal = motorBR.getBusVoltage();

    FLVelocityVal = FLEncoder.getVelocity();
    FRVelocityVal = FREncoder.getVelocity();
    BLVelocityVal = BLEncoder.getVelocity();
    BRVelocityVal = BREncoder.getVelocity();

    robotDrive.feed();

    driveOdometry.update(gyro.getRotation2d(), FLEncoder.getPosition(), FREncoder.getPosition());

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


}