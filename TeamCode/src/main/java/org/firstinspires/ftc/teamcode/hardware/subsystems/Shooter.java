package org.firstinspires.ftc.teamcode.hardware.subsystems;

import java.util.List;

import org.firstinspires.ftc.teamcode.hardware.devices.Motor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Shooter {
  private final List<Motor> motors;

  public Shooter(Motor... motors) {
    this.motors = List.of(motors);
  }

  public Shooter(double ppr, DcMotorEx... motors) {
    this.motors = List.of();
    for (DcMotorEx motor : motors)
      this.motors.add(new Motor(motor, ppr));
  }

  // Motor-like helpers (delegate to both motors)

  public void setPower(double power) {
    for (Motor m : motors)
      m.setPower(power);
  }

  public double getPower() {
    double sum = 0.0;
    for (Motor m : motors)
      sum += m.getPower();
    return sum / motors.size();
  }

  public void setMode(DcMotor.RunMode mode) {
    for (Motor m : motors)
      m.setMode(mode);
  }

  public DcMotor.RunMode getMode() {
    return motors.get(0).getMode();
  }

  public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
    for (Motor m : motors)
      m.setZeroPowerBehavior(behavior);
  }

  public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
    return motors.get(0).getZeroPowerBehavior();
  }

  public void setVelocity(double angularRate) {
    for (Motor m : motors)
      m.setVelocity(angularRate);
  }

  public double getVelocity() {
    double sum = 0.0;
    for (Motor m : motors)
      sum += m.getVelocity();
    return sum / motors.size();
  }

  public void setRPM(double rpm) {
    for (Motor m : motors)
      m.setRPM(rpm);
  }

  public double getRPM() {
    double sum = 0.0;
    for (Motor m : motors)
      sum += m.getRPM();
    return sum / motors.size();
  }

  public void setTargetPosition(int position) {
    for (Motor m : motors)
      m.setTargetPosition(position);
  }

  public int getCurrentPosition() {
    double sum = 0.0;
    for (Motor m : motors)
      sum += m.getCurrentPosition();
    return (int) (sum / motors.size());
  }

  public boolean atSpeed(double targetVelocity) {
    for (Motor m : motors)
      if (!m.atSpeed(targetVelocity))
        return false;
    return true;
  }

  public boolean atSpeedRPM(double targetRPM) {
    for (Motor m : motors)
      if (!m.atSpeedRPM(targetRPM))
        return false;
    return true;
  }

  /**
   * TODO: Update this calculation based on actual shooter performance.
   * Calculates the required RPM for the shooter to hit a target at the given distance.
   * @param distance The distance to the target in inches.
   * @return The calculated RPM.
   * @apiNote See the desmos graph here: https://www.desmos.com/calculator/xu4hd5q78y
   */
  public double calculateRPM(double distance) {
    return 0.0000038436 * Math.pow(distance, 5) + -0.00148844 * Math.pow(distance, 4) +
        0.220217 * Math.pow(distance, 3) + -15.32687 * Math.pow(distance, 2) +
        495.37699 * distance + -3472.17911;
  }

}