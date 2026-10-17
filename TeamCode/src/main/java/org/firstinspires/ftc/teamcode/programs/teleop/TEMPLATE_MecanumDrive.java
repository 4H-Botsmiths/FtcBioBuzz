package org.firstinspires.ftc.teamcode.programs.teleop;

import org.firstinspires.ftc.teamcode.hardware.Robot;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Mecanum Drive", group = "Template")
//@Disabled() //! Remove this line when you copy this template
public class TEMPLATE_MecanumDrive extends OpMode {
  public Robot robot;

  // 
  /*
   * Code to run ONCE when the driver hits INIT
   */
  @Override
  public void init() {
    telemetry.addData("Status", "Initializing");
    telemetry.update();
    this.robot = new Robot(hardwareMap);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
  }

  /*
   * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
   */
  @Override
  public void init_loop() {
  }

  /*
   * Code to run ONCE when the driver hits PLAY
   */
  @Override
  public void start() {
  }

  /*
   * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
   */
  @Override
  public void loop() {
    double x = gamepad1.left_stick_x / 3; // Scale down the left stick X-axis input to 33% of its original value
    x *= 2; // Double the scaled input
    x += gamepad1.right_trigger * (gamepad1.left_stick_x / 3); // Let the right trigger add to the X-axis input (up to 33%, bringing the total up to 100%)
    x -= gamepad1.left_trigger * (gamepad1.left_stick_x / 3); // Let the left trigger subtract from the X-axis input (up to 33%, bringing the total down to 33%)
    double y = -gamepad1.left_stick_y / 3; // `y` and `z` follow the same pattern as `x`
    y *= 2;
    y += gamepad1.right_trigger * (-gamepad1.left_stick_y / 3);
    y -= gamepad1.left_trigger * (-gamepad1.left_stick_y / 3);
    double z = 0;
    //double z = gamepad1.right_stick_x / 3;
    //z *= 2;
    //z += gamepad1.right_trigger * (gamepad1.right_stick_x / 3);
    //z -= gamepad1.left_trigger * (gamepad1.right_stick_x / 3);

    robot.drivetrain.drive(x, y, z); // Pass the adjusted inputs to the drivetrain

    telemetry.addData("X", x);
    telemetry.addData("Y", y);
    telemetry.addData("Z", z);
    telemetry.addData("FL Power/RPM", String.format("%.0f%% / %.1f", robot.drivetrain.frontLeft.getPower() * 100,
        robot.drivetrain.frontLeft.getRPM()));
    telemetry.addData("FR Power/RPM", String.format("%.0f%% / %.1f", robot.drivetrain.frontRight.getPower() * 100,
        robot.drivetrain.frontRight.getRPM()));
    telemetry.addData("RL Power/RPM", String.format("%.0f%% / %.1f", robot.drivetrain.rearLeft.getPower() * 100,
        robot.drivetrain.rearLeft.getRPM()));
    telemetry.addData("RR Power/RPM", String.format("%.0f%% / %.1f", robot.drivetrain.rearRight.getPower() * 100,
        robot.drivetrain.rearRight.getRPM()));
    telemetry.update();
  }

  /*
   * Code to run ONCE after the driver hits STOP
   */
  @Override
  public void stop() {
  }

}
