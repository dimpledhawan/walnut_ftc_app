package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by dimpledhawan on 2/27/16.
 */
public abstract class Master_Autonomous extends LinearOpMode
{
//    public DcMotor motorLeft;
//        EncoderMotorTask motorTaskLeft;
//    public DcMotor motorRight;
//        EncoderMotorTask motorTaskRight;
//
//    public DcMotor spinners;
//
//    public Servo belt;
//    public Servo hook;
//    public Servo climber;
//
//    public GyroSensor gyro;
//        GyroMotorClass gyroTurn;
//
//    //Auto Parameters
//    public String alliance;
//    public String startPos;
//    public long delay;
//    public String willPark;
//
//    //Useful Constants
//    public static final double MSECSTOSECS = 1000;
//    public Master_Autonomous(String myAlliance, String myStartPos, double myDelay, String parkCheck){
//        alliance = myAlliance;
//        startPos = myStartPos;
//        delay = (long) (myDelay * MSECSTOSECS);
//        willPark = parkCheck;
//
//   }
//    public void initRobot(){
//        motorLeft = hardwareMap.dcMotor.get("motorLeft");
//        motorRight = hardwareMap.dcMotor.get("motorRight");
//        motorTaskLeft = new EncoderMotorTask(this, motorLeft);
//        motorTaskRight = new EncoderMotorTask(this, motorRight);
//        gyroTurn = new GyroMotorClass(this, gyro, motorLeft, motorRight);
//        spinners = hardwareMap.dcMotor.get("spinners");
//        climber = hardwareMap.servo.get("climber");
//        gyro = hardwareMap.gyroSensor.get("gyro");
//
//        gyro.calibrate();
//
//        climber.setPosition(.75);
//        //belt.setPosition(.5); //commented out but still initializing it!!
//    }
//
//    public void runOpMode(){
//        try {
//            //Init all parts
//            initRobot();
//            EncoderMotorTask motorTaskLeft = new EncoderMotorTask(this, motorLeft);
//            EncoderMotorTask motorTaskRight = new EncoderMotorTask(this, motorRight);
//            GyroMotorClass gyroTurn = new GyroMotorClass(this, gyro, motorLeft, motorRight);
//
//            while (gyro.isCalibrating()) {
//                try {
//                    sleep(50);
//                    waitForStart();
//                } catch (InterruptedException e) {
//
//                }
//            }
//
//            int step = 0;
//
//            if (delay > 0) {
//                long msecDelay = delay * 1000;
//                sleep(msecDelay);
//            }
//
//            switch (alliance)
//            {
//                case "red":
//                    spinners.setPower(1);
//                    moveForward(0.5, Direction.MOTOR_FORWARD, Direction.MOTOR_BACKWARD, 72);
//                    Turn(motorTaskLeft, motorLeft, 40);
//                    moveForward(0.5, Direction.MOTOR_FORWARD, Direction.MOTOR_BACKWARD, 12);
//                    dumpClimbers();
//                    stopRobot();
//
//                case "blue":
//                    spinners.setPower(0);
//                    moveForward(0.5, Direction.MOTOR_BACKWARD, Direction.MOTOR_FORWARD, 72);
//                    Turn(motorTaskRight, motorRight, 40);
//                    moveForward(0.5, Direction.MOTOR_BACKWARD, Direction.MOTOR_FORWARD, 12);
//                    dumpClimbers();
//                    stopRobot();
//            }
//            switch (willPark)
//            {
//                case "true":
//                    stopRobot();
//
//                case "false":
//                    switch (alliance)
//                    {
//                        case "red":
//                            moveForward(0.5, Direction.MOTOR_FORWARD, Direction.MOTOR_BACKWARD, 29);
//
//                        case "blue":
//                            moveForward(0.5, Direction.MOTOR_BACKWARD, Direction.MOTOR_FORWARD, 29);
//                    }
//            }
//            spinners.setPower(0);
//            waitOneFullHardwareCycle();
//        }
//            catch(InterruptedException e)
//            {
//                stop();
//            }
//        }
//
//    private void dumpClimbers() throws InterruptedException {
//        climber.setPosition(.18);
//        sleep(1000);
//        climber.setPosition(.42);
//        sleep(1000);
//    }
//
//    private void moveForward(double power, Direction leftOrientation, Direction rightOrientation, double distance) throws InterruptedException {
//        double targEnc1 = 1440 * distance / 4 / Math.PI;
//        if (!motorTaskLeft.isRunning()) {
//            motorTaskLeft.startMotor("step1 left", power, targEnc1, leftOrientation);
//        }
//        if (!motorTaskRight.isRunning()) {
//            motorTaskRight.startMotor("step1 right", power, targEnc1, rightOrientation);
//        }
//
//        while (!(motorTaskLeft.targetReached() && motorTaskRight.targetReached())) {
//
//        }
//
//        stopRobot();
//    }
//
//    private void Turn(EncoderMotorTask motorTask, DcMotor motor, int angle){
//        double targEnc = 1440 * angle / 360 * 36 / 4 / Math.PI;
//        try{
//            motorTask.startMotor("Name", 0.5, targEnc, Direction.MOTOR_BACKWARD);
//
//        while (!motorTask.targetReached()) {
//
//        }
//
//        motor.setPower(0);
//        motorTask.stop();
//        }
//        catch(InterruptedException e)
//        {
//            stop();
//        }
//    }
//
//    public void stopRobot(){
//
//        try{
//            motorRight.setPower(0);
//            motorLeft.setPower(0);
//            motorTaskLeft.stop();
//            motorTaskRight.stop();
//            spinners.setPower(0);
//        }
//        catch(InterruptedException e) {
//
//        }
//
//    }
}
