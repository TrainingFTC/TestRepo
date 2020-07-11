// SAMPLE CHANGE

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic tank drive")
@Disabled
public class TankDrive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx spicyDrive = null;
    private DcMotorEx zestyDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        spicyDrive  = hardwareMap.get(DcMotorEx.class, "spicy_drive");
        zestyDrive = hardwareMap.get(DcMotorEx.class, "zesty_drive");

        spicyDrive.setDirection(DcMotor.Direction.FORWARD);
        zestyDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double spicyPower;
            double zestyPower;

            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            spicyPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            zestyPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            spicyDrive.setPower(spicyPower);
            zestyDrive.setPower(zestyPower);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Spicy (%.2f), Zesty (%.2f)", spicyPower, zestyPower);
            telemetry.update();
        }
    }
}
