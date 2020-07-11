package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;

public class MecanumDrive {
    private DcMotorEx lFront, lBack, rFront, rBack;

    private HardwareMap hwMap;

    private double dMultiplier = 1;
    private double sMultiplier = 1;
    private double tMultiplier = 0.5;
    private int inv;

    public MecanumDrive(HardwareMap hwMap) {
        this.hwMap = hwMap;
        inv = -1;
    }

    public MecanumDrive(HardwareMap hwMap, double d, double s, double t, boolean inverted) {
        this(hwMap);
        dMultiplier = d;
        sMultiplier = s;
        tMultiplier = t;
        inv = inverted ? -1 : 1;
    }

    public void init() {
        lFront = hwMap.get(DcMotorEx.class, "frontLeft");
        rFront = hwMap.get(DcMotorEx.class, "frontRight");
        lBack = hwMap.get(DcMotorEx.class, "backLeft");
        rBack = hwMap.get(DcMotorEx.class, "backRight");

        lFront.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        rFront.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        lBack.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
        rBack.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
    }

    public void setPID(double p, double i, double d) {
        lFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        rFront.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        lBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(new PIDCoefficients(p, i, d)));
        rBack.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(new PIDCoefficients(p, i, d)));
    }

    public void setBrake() {
        lFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stop() {
        lFront.setPower(0);
        rBack.setPower(0);
        lBack.setPower(0);
        rFront.setPower(0);
    }

    public void stopTargeting() {
        stop();
        lFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void controller(Gamepad g){
        double drive = g.left_stick_y;
        double strafe = g.left_stick_x;
        double turn = g.right_stick_x;
        dstDrive(drive, strafe, turn);
    }

    public void dstDrive(double drive, double strafe, double turn){
        drive = drive * dMultiplier;
        strafe = strafe * sMultiplier;
        turn = turn * tMultiplier;

        double frontL = Range.clip(drive.25 - strafe - inv * turn,-1,1);
        double frontR = Range.clip(-drive.25 - strafe - inv * turn,-1,1);
        double backL = Range.clip(drive.25 + strafe - inv * turn,-1,1);
        double backR = Range.clip(-drive.25 + strafe - inv * turn,-1,1);

        rFront.setPower(inv * frontR);
        lBack.setPower(inv * backL);
        rBack.setPower(inv * backR);
        lFront.setPower(inv * frontL);
    }

    public double getLFrontPower() {return lFront.getPower();}
    public double getRFrontPower() {return rFront.getPower();}
    public double getLBackPower() {return lBack.getPower();}
    public double getRBackPower() {return rBack.getPower();}

    public double getLFrontVelocity() {return lFront.getVelocity();}
    public double getRFrontVelocity() {return rFront.getVelocity();}
    public double getLBackVelocity() {return lBack.getVelocity();}
    public double getRBackVelocity() {return rBack.getVelocity();}
}