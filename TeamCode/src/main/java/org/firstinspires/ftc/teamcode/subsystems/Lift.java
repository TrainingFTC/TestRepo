package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Lift {
    public DcMotorEx liftMotor;

    protected HardwareMap hwMap; // can be accessed by subclasses

    public static int BOTTOM = 0; //position at the bottom
    private static final int TOP = 6000; //position at the top

    public Lift (HardwareMap hwMap){
        this.hwMap = hwMap;
    }

    public void init() {
        liftMotor = hwMap.get(DcMotorEx.class, "lift");
        liftMotor.setTargetPositionTolerance(Constants.POSITIONING_TOLERANCE);
    }

    public void moveByInch(double inches, double power){
        int targetPos = (int)(inches * Constants.TICKS_PER_INCH);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (liftMotor.isBusy() ){}
        liftMotor.setPower(0);
    }

    public void moveByInchTele(double inches, double power){
        int targetPos = (int)(inches * Constants.TICKS_PER_INCH);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        //prevent going past 0?
        if (liftMotor.getCurrentPosition() + targetPos < BOTTOM-1)
            liftMotor.setTargetPosition(BOTTOM-1);
        else
            liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveByInchTeleBeyond(double inches, double power){
        int targetPos = (int)(inches * Constants.TICKS_PER_INCH);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(power);

        //prevent going past 0?
        liftMotor.setTargetPosition(liftMotor.getCurrentPosition() + targetPos);

        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stop() {
        liftMotor.setPower(0.0);
    }

    public int positionTicks() {
        return liftMotor.getCurrentPosition();
    }

    public void firstPos() {
        moveByInch(7, 1.);
    }

    public void bottom() { // move to the bottom position
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setPower(-1.0);

        liftMotor.setTargetPosition(BOTTOM-1);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void release() { // move to the bottom position
        moveByInch(-9, 1.);
    }

    public void pull() {
        moveByInch(9, 1.);
    }
}
