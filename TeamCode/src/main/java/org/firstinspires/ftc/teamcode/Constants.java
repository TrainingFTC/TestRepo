package org.firstinspires.ftc.teamcode;

public class Constants {
    public static double TICKS_PER_MOTOR_REV     = 1120;
    public static double WHEEL_DIAMETER_INCHES   = 4.0;     // For figuring circumference
    public static double TICKS_PER_INCH = TICKS_PER_MOTOR_REV / (WHEEL_DIAMETER_INCHES * Math.PI);

    public static final int POSITIONING_TOLERANCE = 10; //The amount of ticks the DriveByInch method should be allowed to deviate by

    public static final int TURN_COMPENSATION = 0;
    public static final int DRIVE_COMPENSATION = 0;
    public static final int STRAFE_COMPENSATION = 0;
}