package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name="Driver Control", group="main")
//@Disabled
public class DriverControl extends OpMode {
    //public objects: gamepad1, gamepad2, telemetry, hardwareMap,
    //                time (seconds this opmode has been running, updated before each loop() call
    private ElapsedTime runtime = new ElapsedTime();
    private DriveTrainMecanum driveTrain;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        driveTrain = new DriveTrainMecanum(hardwareMap);
        driveTrain.init();
    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY, optional
     * maybe useful for vision code to run
     */
    @Override
    public void init_loop() {
    }
    /*
     * Code to run ONCE when the driver hits PLAY, optional,
     * maybe useful for starting another thread
     */
    @Override
    public void start() {
        runtime.reset();
    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     * frenquency is about 25ms
     */
    @Override
    public void loop() {
        driveTrain.controller(gamepad1);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Status", "opMode Time: " + time);

        telemetry.addData("power", "left (%.2f), right (%.2f)",
                driveTrain.getLFPower(), driveTrain.getRFPower());
        telemetry.addData("velocity", "left (%.2f), right (%.2f)",
                driveTrain.getLFVelocity(), driveTrain.getRFVelocity());
    }
    /*
     * Code to run ONCE after the driver hits STOP, optional
     */
    @Override
    public void stop() {
    }
}

