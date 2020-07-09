package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;

public class DriveTrainMecanum {
    private DcMotorEx leftFront, leftBack, rightFront, rightBack;

    private HardwareMap hwMap;

    private double dMultiplier = 1;
    private double sMultiplier = 1;
    private double tMultiplier = 0.5;
    private int inv;

    public DriveTrainMecanum(HardwareMap hwMap) {
        this.hwMap = hwMap;
        inv = -1;
    }

    public DriveTrainMecanum(HardwareMap hwMap, double d, double s, double t, boolean inverted) {
        this(hwMap);
        dMultiplier = d;
        sMultiplier = s;
        tMultiplier = t;
        inv = inverted ? -1 : 1;
    }

    public void init() {
        leftFront = hwMap.get(DcMotorEx.class, "frontLeft");
        rightFront = hwMap.get(DcMotorEx.class, "frontRight");
        leftBack = hwMap.get(DcMotorEx.class, "backLeft");
        rightBack = hwMap.get(DcMotorEx.class, "backRight");

        leftFront.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        rightFront.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        leftBack.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        rightBack.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
    }

    public void setPID(double p, double i, double d) {
        leftFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        rightFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        leftBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        rightBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(new PIDCoefficients(p, i, d)));
    }

    public void setBrake() {
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        leftFront.setPower(0);
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
    }

    public void stopTargeting() {
        stop();
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void controller(Gamepad g){
        double drive = g.left_stick_y;
        double strafe = g.left_stick_x;
        double turn = g.right_stick_x;
        dstDrive(drive, strafe, turn);
    }

    public void dstDrive(double drive, double strafe, double turn){
        /*
        //Driving and strafing were switched, so I switched driveMult and strafeMult and inverted drive.
        //double strafeMult = -drive * dMultiplier;
        //double driveMult = strafe * sMultiplier;
*/
        drive = drive * dMultiplier;
        strafe = strafe * sMultiplier;
        turn = turn * tMultiplier;
/*
        double angle = Math.atan2(driveMult, strafeMult);
        double power = Math.sqrt(Math.pow(driveMult, 2) + Math.pow(strafeMult, 2));
        aDriveRaw(angle, power, turnMult);*/
        double frontLeft = Range.clip(drive - strafe - inv*turn,-1,1);
        double frontRight = Range.clip(-drive - strafe - inv*turn,-1,1);
        double backLeft = Range.clip(drive + strafe - inv*turn,-1,1);
        double backRight = Range.clip(-drive + strafe - inv*turn,-1,1);

        rightFront.setPower(inv*frontRight);
        leftBack.setPower(inv*backLeft);
        rightBack.setPower(inv*backRight);
        leftFront.setPower(inv*frontLeft);
    }

    public double getLFPower() {return leftFront.getPower();}
    public double getRFPower() {return rightFront.getPower();}
    public double getLBPower() {return leftBack.getPower();}
    public double getRBPower() {return rightBack.getPower();}

    public double getLFVelocity() {return leftFront.getVelocity();}
    public double getRFVelocity() {return rightFront.getVelocity();}
    public double getLBVelocity() {return leftBack.getVelocity();}
    public double getRBVelocity() {return rightBack.getVelocity();}
}
