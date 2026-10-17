package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.constants.DeviceNames;
import org.firstinspires.ftc.teamcode.hardware.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.subsystems.Intake;
import org.firstinspires.ftc.teamcode.hardware.subsystems.Shooter;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Represents the robot's hardware and subsystems, including the drivetrain.
 * Provides methods to initialize and control the robot's motors and drivetrain.
 */
public class Robot {
  /* ------------------ Devices ------------------ */
  private final DcMotorEx frontLeft;
  private final DcMotorEx frontRight;
  private final DcMotorEx rearLeft;
  private final DcMotorEx rearRight;

  //private final DcMotorEx leftShooter;
  //private final DcMotorEx rightShooter;

  //private final CRServo leftIntake;
  //private final CRServo rightIntake;

  /* ------------------ Subsystems ------------------ */
  public final Drivetrain drivetrain;
  //public final Intake intake;
  //public final Shooter shooter;

  /** Initialize the robot's hardware components */
  public Robot(HardwareMap hardwareMap) {
    // Calculate drive motor PPR (Pulses Per Rotation) based on gear ratio
    // Formula: ((1 + (stage1_ratio)) * (1 + (stage2_ratio))) * base_motor_PPR
    // Gear ratios: 46:17 (stage 1) and 46:11 (stage
    // Base motor: 28 PPR (likely a bare motor encoder co
    double drivePPR = ((((1 + (46.0 / 17.0))) * (1 + (46.0 / 11.0))) * 28.0);

    //! Changes the `DeviceNames` below to match the actual motor assignments on the Control Hub.
    this.rearLeft = hardwareMap.get(DcMotorEx.class, DeviceNames.CH_MOTOR_0.getDeviceName());
    this.rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    this.rearRight = hardwareMap.get(DcMotorEx.class, DeviceNames.CH_MOTOR_1.getDeviceName());
    this.frontLeft = hardwareMap.get(DcMotorEx.class, DeviceNames.CH_MOTOR_2.getDeviceName());
    this.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    this.frontRight = hardwareMap.get(DcMotorEx.class, DeviceNames.CH_MOTOR_3.getDeviceName());

    this.drivetrain = new Drivetrain(this.frontLeft, this.frontRight, this.rearLeft, this.rearRight, drivePPR);

    // Calculate drive motor PPR (Pulses Per Rotation) based on gear ratio
    // Formula: ((1 + (stage1_ratio)) * (1 + (stage2_ratio))) * base_motor_PPR
    // Gear ratios: 46:17 (stage 1) and 46:11 (stage
    // Base motor: 28 PPR (likely a bare motor encoder co
    double shooterPPR = ((((1 + (46.0 / 17.0))) * (1 + (46.0 / 11.0))) * 28.0);

    //this.leftShooter = hardwareMap.get(DcMotorEx.class, DeviceNames.EH_MOTOR_0.getDeviceName());
    //this.rightShooter = hardwareMap.get(DcMotorEx.class, DeviceNames.EH_MOTOR_1.getDeviceName());
    //this.shooter = new Shooter(shooterPPR, this.leftShooter, this.rightShooter);

    //this.leftIntake = hardwareMap.get(CRServo.class, DeviceNames.EH_SERVO_0.getDeviceName());
    //this.rightIntake = hardwareMap.get(CRServo.class, DeviceNames.EH_SERVO_1.getDeviceName());
    //this.intake = new Intake(this.leftIntake, this.rightIntake);
  }
}