package org.firstinspires.ftc.teamcode.hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.constants.TeamColor;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortal.CameraState;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import com.qualcomm.robotcore.hardware.HardwareMap;

import android.util.Size;

/*
 * This OpMode illustrates the basics of AprilTag recognition and pose estimation, using
 * two webcams.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
public class Camera {

  public static final String OBELISK_STORAGE_KEY = "Obelisk Pattern";

  /*
   * Variables used for switching cameras.
   */
  public Camera(HardwareMap hardwareMap) {
    this.webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
  }

  private WebcamName webcam;
  /**
   * The variable to store our instance of the AprilTag processor.
   */
  private AprilTagProcessor aprilTag;

  /**
   * The variable to store our instance of the vision portal.
   */
  public VisionPortal visionPortal;

  /**
   * Initialize the AprilTag processor.
   */
  public void initAprilTag() throws CameraNotAttachedException {
    if (!webcam.isAttached()) {
      throw new CameraNotAttachedException();
    }

    // Create the AprilTag processor by using a builder.
    aprilTag = new AprilTagProcessor.Builder()
        .setLensIntrinsics(541.591, 541.591, 328.0, 235.051) // Focal lengths fx, fy; Principal point cx, cy
        .build();
    // Create the vision portal by using a builder.
    visionPortal = new VisionPortal.Builder()
        .setCamera(this.webcam)
        .addProcessor(aprilTag)
        .setCameraResolution(new Size(640, 480)) //! Refine further, lower is better
        .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
        .build();

  } // end method initAprilTag()

  /**
   * Retrieves the list of AprilTags detected by the camera.
   *
   * @return         	A list of AprilTag objects representing the detected tags.
   * @throws CameraNotStreamingException  If the camera is not currently streaming.
   * @throws CameraNotAttachedException   If the camera is not attached.
   */
  public List<AprilTag> getAprilTags()
      throws CameraNotStreamingException, CameraNotAttachedException {
    if (!webcam.isAttached()) {
      throw new CameraNotAttachedException();
    } else if (visionPortal == null) {
      initAprilTag();
    }
    if (visionPortal.getCameraState() != CameraState.STREAMING) {
      throw new CameraNotStreamingException();
    }
    List<AprilTagDetection> currentDetections = aprilTag.getDetections();
    List<AprilTag> detections = new ArrayList<>();
    for (AprilTagDetection detection : currentDetections) {
      if (detection instanceof AprilTagClusterDetection) {
        detections.add(new AprilTag(detection));
      }
    }
    return detections;
  }

  /**
   * Retrieves the list of AprilTags detected by the camera for the specified team color.
   *
   * @param teamColor   The color of the team to filter AprilTags by.
   * @return         	A list of AprilTag objects representing the detected tags.
   * @throws CameraNotStreamingException  If the camera is not currently streaming.
   * @throws CameraNotAttachedException   If the camera is not attached.
   */
  public List<AprilTag> getAprilTags(TeamColor teamColor)
      throws CameraNotStreamingException, CameraNotAttachedException {
    List<AprilTag> currentDetections = this.getAprilTags();
    List<AprilTag> detections = new ArrayList<>();
    for (AprilTag detection : currentDetections) {
      if (detection.teamColor == teamColor) {
        detections.add(detection);
      }
    }
    return detections;
  }

  boolean pausingStream = false;

  /**
   * Pauses the camera stream if it is currently streaming. This saves computing resources when the camera is not needed.
   *
   * @throws CameraNotAttachedException If the camera is not attached.
   */
  public void pause() throws CameraNotAttachedException {
    if (!webcam.isAttached()) {
      throw new CameraNotAttachedException();
    } else if (visionPortal == null) {
      initAprilTag();
    }
    if (pausingStream) {
      return; // Already pausing the stream, no need to do anything else.
    }
    pausingStream = true;
    if (this.visionPortal.getCameraState() == CameraState.OPENING_CAMERA_DEVICE) {
      //You can't stop a camera stream before the device is opened
      CompletableFuture.runAsync(new Runnable() {
        @Override
        public void run() {
          while (Camera.this.visionPortal.getCameraState() == CameraState.OPENING_CAMERA_DEVICE) {
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          }
          visionPortal.stopStreaming();
          pausingStream = false;
        }
      });
    } else {
      try {
        visionPortal.stopStreaming();
      } catch (Exception e) {
        //This function has called errors before
      } finally {
        pausingStream = false;
      }
    }
  }

  /**
   * Resumes the camera stream if it was previously paused.
   * IMPORTANT: This process is not immediate; although it is fast it is recommended to not constantly pause and resume the stream as the resources saved are not beneficial
   * @throws CameraNotAttachedException If the camera is not attached.
   */
  public void resume() throws CameraNotAttachedException {
    if (!webcam.isAttached()) {
      throw new CameraNotAttachedException();
    } else if (visionPortal == null) {
      initAprilTag();
    }
    visionPortal.resumeStreaming();
  }

  public void telemetryAprilTags(Telemetry telemetry, List<AprilTag> aprilTags) {
    telemetry.addData("# AprilTags Detected", aprilTags.size());

    // Step through the list of detections and display info for each one.
    for (AprilTagDetection detection : aprilTags) {
      if (detection instanceof AprilTagSingleDetection) {
        AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

        if (singleDet.metadata != null) {
          telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
          telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y,
              detection.ftcPose.z));
          telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch,
              detection.ftcPose.roll, detection.ftcPose.yaw));
          telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range,
              detection.ftcPose.bearing, detection.ftcPose.elevation));
        } else {
          telemetry.addLine(String.format("\n==== (ID %d) Unknown", singleDet.id));
          telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", singleDet.center.x, singleDet.center.y));
        }
      } else {
        AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
        telemetry.addLine(String.format("\n==== Tag Cluster (%s)", clusterDet.metadata.name));
        telemetry.addLine(String.format("Percent tags found: %d", clusterDet.percentClusterFound));
        telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y,
            detection.ftcPose.z));
        telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll,
            detection.ftcPose.yaw));
        telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range,
            detection.ftcPose.bearing, detection.ftcPose.elevation));
      }
    }

    // Add "key" information to telemetry
    telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
    telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
    telemetry.addLine("RBE = Range, Bearing & Elevation");
  }

  public class CameraNotStreamingException extends Exception {
    public CameraNotStreamingException() {
      super("The camera is not streaming");
    }
  }

  public class CameraNotAttachedException extends Exception {
    public CameraNotAttachedException() {
      super("The camera is not attached");
    }
  }

  public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
      super("The specified tag was not found");
    }
  }

  public class AprilTag extends AprilTagDetection {
    public TeamColor teamColor;
    public boolean scorable;

    public AprilTag(AprilTagDetection detection) {
      super(detection.ftcPose, detection.rawPose, detection.robotPose, detection.frameAcquisitionNanoTime,
          detection.distanceUnit);
      if (detection instanceof AprilTagClusterDetection) {
        AprilTagClusterDetection clusterDetection = (AprilTagClusterDetection) detection;
        if (clusterDetection.metadata.name.startsWith("R")) {
          teamColor = TeamColor.RED;
        } else if (clusterDetection.metadata.name.startsWith("B")) {
          teamColor = TeamColor.BLUE;
        } else {
          teamColor = TeamColor.UNKNOWN;
        }

        if (Math.abs(clusterDetection.ftcPose.roll) < 90) {
          scorable = true;
        } else {
          scorable = false;
        }
      } else {
        teamColor = TeamColor.UNKNOWN;
      }
    }
  }
}
