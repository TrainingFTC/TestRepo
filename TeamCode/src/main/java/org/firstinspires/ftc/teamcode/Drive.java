package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.subsystems.*;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
//@Disabled
public class Drive extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive driveTrain;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        driveTrain = new MecanumDrive(hardwareMap);
        driveTrain.init();
    }

    // runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    // runs after drive hits PLAY
    @Override
    public void start() {
        runtime.reset();
    }

    // runs REPEATEDLY after the driver hits PLAY but before they hit STOP
    public void loop() {
        driveTrain.controller(gamepad1);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Status", "opMode Time: " + time);

        telemetry.addData("Power", "L (%.2f), R (%.2f)",
                driveTrain.getLFrontPower(), driveTrain.getRFrontPower());
        telemetry.addData("Velocity", "L (%.2f), R (%.2f)",
                driveTrain.getLFrontVelocity(), driveTrain.getRFrontVelocity());
    }

    public void stop() {
    }
}
