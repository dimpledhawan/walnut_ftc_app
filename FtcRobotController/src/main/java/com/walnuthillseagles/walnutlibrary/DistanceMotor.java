package com.walnuthillseagles.walnutlibrary;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Yan Vologzhanin on 1/2/2016.
 */
public class DistanceMotor extends LinearMotor implements Runnable, Auto {
    //Constants
    //How often the code will check if its current operation is done

    //Precisions of wheels.
    public static final int RANGEVAL = 30;
    //Fields
    //Please measure in Inches
    private double circumference;
    private double gearRatio;
    //Distance value used in operate
    private int distance;
    //Parrallel Thread
    private Thread runner;
    public DistanceMotor(DcMotor myMotor, String myName, boolean encoderCheck,boolean isReveresed,
                         double myDiameter,double myGearRatio, int myEncoder){
        //Create Motor
        super(myMotor, myName, encoderCheck, isReveresed);
        motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        while(motor.getMode()!= DcMotorController.RunMode.RUN_TO_POSITION){
            try{
                Thread.sleep(WAITRESOLUTION);
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }

        //Values involving bot
        circumference = myDiameter*Math.PI;
        gearRatio = myGearRatio;
        encoderRot = myEncoder;

        //Values involving distances
        distance = 0;
        speedLimit = 0;

        motor.setTargetPosition(0);
        motor.setPower(0);

        runner = new Thread();

        //"Prime" Motor
        //@TODO Figure out how to do this for user

    }
    //Starts operation with given parameters
    public void operate(double inches, double mySpeedLimit){
        //@TODO Does this handle Going backwards?
        distance = (int)((inches / circumference / gearRatio) * encoderRot * orientation);
        //Speedlimit is always positive
        speedLimit = Math.abs(mySpeedLimit);

        //Start new process
        runner = new Thread(this);
        this.motor.setTargetPosition(distance);
        this.setPower(speedLimit);
        runner.start();
    }
    public void operate(double inches) {
        this.operate(inches, 1);
    }
    //Allows other methods to change speed midway through method
    public void changeSpeedLimit(double mySpeedLimit){
        speedLimit = mySpeedLimit;
    }
    public void run(){
        //Go for it
        try {
            timer.sleep(WAITRESOLUTION*5);
            //Debug
            //Wait until in Pos
            //@TODO Better way to do this?
            while (!inRange(distance, motor.getCurrentPosition())
                    || Math.abs(motor.getCurrentPosition()) > Math.abs(distance)) {

                    this.sleep(WAITRESOLUTION);


            }
            this.fullStop();
            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            motor.setTargetPosition(0);
            distance = 0;

            runner = new Thread(this);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            stop();

        }
    }
    public int getDistance(){
        return distance;
    }
    public double getSpeedLimit(){return speedLimit;}
    public void fullStop(){
        motor.setPower(0);
        try{
            resetEncoder();
            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            while(motor.getMode()!= DcMotorController.RunMode.RUN_TO_POSITION){
                timer.sleep(WAITRESOLUTION);
        }
        }
        catch(InterruptedException e){
            motor.setPower(0);
            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            Thread.currentThread().interrupt();
        }


    }
    //Timers
    public void waitForCompletion() throws InterruptedException{
        if(runner != null)
            runner.join();
    }
    //Private helper methods

}
