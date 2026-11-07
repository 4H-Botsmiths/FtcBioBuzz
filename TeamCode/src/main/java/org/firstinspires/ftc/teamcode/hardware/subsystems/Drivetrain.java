package org.firstinspires.ftc.teamcode.hardware.subsystems;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.devices.Motor;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

/**
 * Controls a four-wheel mecanum drivetrain using motor RPM commands.
 *
 * <p>The drivetrain supports both robot-centric and field-centric driving.
 * Field-centric driving uses the robot heading to transform field-relative
 * inputs into robot-relative commands.</p>
 */
public class Drivetrain {

  public Motor frontLeft;
  public Motor frontRight;
  public Motor rearLeft;
  public Motor rearRight;

  /**
   * Creates a drivetrain using the supplied motor wrappers.
   *
   * @param frontLeft front-left drivetrain motor
   * @param frontRight front-right drivetrain motor
   * @param rearLeft rear-left drivetrain motor
   * @param rearRight rear-right drivetrain motor
   */
  public Driveztrain(Motor frontLeft, Motor frontRight, Motor rearLeft, Motor rearRight) {
    this.frontLeft = frontLeft;
    this.frontRight = frontRight;
    this.rearLeft = rearLeft;
    this.rearRight = rearRight;
  }

  /**
   * Creates a drivetrain using unwrapped motor objects.
   *
   * @param frontLeft front-left drivetrain motor config entry
   * @param frontRight front-right drivetrain motor config entry
   * @param rearLeft rear-left drivetrain motor config entry
   * @param rearRight rear-right drivetrain motor config entry
   * @param ppr pulses per rotation for the drive motors
   */
  public Drivetrain(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx rearLeft, DcMotorEx rearRight, double ppr) {
    this.frontLeft = new Motor(frontLeft, ppr);
    this.frontRight = new Motor(frontRight, ppr);
    this.rearLeft = new Motor(rearLeft, ppr);
    this.rearRight = new Motor(rearRight, ppr);
  }

  /** Derived from the 2025-26 season. This is the recommended RPM for the drive motors. */
  public static final int DRIVE_MAX_RPM = 300;

  /**
   * Drives the robot using robot-centric mecanum-drive inputs.
   *
   * <p>The inputs are combined to calculate the requested power for each
   * wheel. Each wheel power is clipped to {@code [-1, 1]} and scaled to
   * {@link #DRIVE_MAX_RPM} before being sent to the motor.</p>
   *
   * @param x lateral strafe input; positive values request movement to the right
   * @param y forward or backward input; positive values request forward movement
   * @param rotate rotational input, using the drivetrain's rotation convention
   */
  public void drive(double x, double y, double rotate) {
    double frontLeftPower = y + x + rotate;
    double frontRightPower = y - x - rotate;
    double rearLeftPower = y - x + rotate;
    double rearRightPower = y + x - rotate;

    frontLeft.setRPM(Range.clip(frontLeftPower, -1, 1) * DRIVE_MAX_RPM);
    frontRight.setRPM(Range.clip(frontRightPower, -1, 1) * DRIVE_MAX_RPM);
    rearLeft.setRPM(Range.clip(rearLeftPower, -1, 1) * DRIVE_MAX_RPM);
    rearRight.setRPM(Range.clip(rearRightPower, -1, 1) * DRIVE_MAX_RPM);
  }
  public void drive(double x, double y, double rotate, Telemetry telemetry) {
    double frontLeftPower = y + x + rotate;
    double frontRightPower = y - x - rotate;
    double rearLeftPower = y - x + rotate;
    double rearRightPower = y + x - rotate;
    telemetry.addData("frontLeftPower", frontLeftPower);
    telemetry.update();
    telemetry.addData("frontRightPower", frontRightPower);
    telemetry.update();

    frontLeft.setRPM(Range.clip(frontLeftPower, -1, 1) * DRIVE_MAX_RPM);
    frontRight.setRPM(Range.clip(frontRightPower, -1, 1) * DRIVE_MAX_RPM);
    rearLeft.setRPM(Range.clip(rearLeftPower, -1, 1) * DRIVE_MAX_RPM);
    rearRight.setRPM(Range.clip(rearRightPower, -1, 1) * DRIVE_MAX_RPM);
    telemetry.addData("frontLeftPower", frontLeft.getPower());
    telemetry.update();
    telemetry.addData("frontRightPower", frontRight.getPower());
    telemetry.update();
  }

  /**
   * Drives the robot using field-centric inputs by compensating for the robot's current heading.
   *
   * <p>This method converts a desired motion vector provided in field coordinates (x, y)
   * into robot-relative coordinates by rotating the vector by -gyro (i.e. it applies a rotation
   * that compensates for the robot's current heading). The transformed robot-relative commands
   * are then passed to {@link #drive(double, double, double)} which computes individual wheel
   * powers and sets motor RPMs (clipped and scaled by {@link #DRIVE_MAX_RPM}).</p>
   *
   * <p>Coordinate/convention notes:
   * <ul>
   *   <li>{@code x} is the lateral (strafe) command; positive values request motion to the right.</li>
   *   <li>{@code y} is the longitudinal (forward/backward) command; positive values request forward motion.</li>
   *   <li>{@code rotate} is the rotation command (a signed rotational rate); its sign follows the
   *       drivetrain's internal convention used by {@link #drive(double, double, double)}.</li>
   *   <li>{@code gyro} is the robot heading used to convert field-relative inputs to robot-relative;
   *       it is interpreted as an angle in radians.</li>
   * </ul>
   * </p>
   *
   * @param x lateral (strafe) input in field coordinates, typically in the range [-1, 1]
   * @param y forward/backward input in field coordinates, typically in the range [-1, 1]
   * @param rotate rotational input (signed), typically in the range [-1, 1]
   * @param gyro robot heading in radians used to transform field-centric inputs into robot-centric ones
   */
  public void drive(double x, double y, double rotate, double gyro) {
    double tempX = x * Math.cos(gyro) + y * Math.sin(gyro);
    double tempY = -x * Math.sin(gyro) + y * Math.cos(gyro);

    this.drive(tempX, tempY, rotate);
  }
}