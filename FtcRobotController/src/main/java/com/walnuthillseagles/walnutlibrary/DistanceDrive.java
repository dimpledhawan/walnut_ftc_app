package com.walnuthillseagles.walnutlibrary;

import java.util.ArrayList;

/**
 * Created by Yan Vologzhanin on 1/4/2016.
 */

public class DistanceDrive {
    private ArrayList<DistanceMotor> leftDrive;
    private ArrayList<DistanceMotor> rightDrive;
    private double robotWidth;

    //Just cause
    public static final int REVERSEORIENTATION = -1;

    //Constructor used for first two motors
    public DistanceDrive(DistanceMotor myLeft, DistanceMotor myRight, double width){
        //Initilize ArrayLists
        leftDrive = new ArrayList<DistanceMotor>();
        rightDrive = new ArrayList<DistanceMotor>();
        //Add motors to these lists
        leftDrive.add(myLeft);
        rightDrive.add(myRight);
        //Initilize other variables
        robotWidth = width;
    }
    //Add additional motors
    public void addLeft(DistanceMotor myLeft){
        leftDrive.add(myLeft);
    }
    public void addRight(DistanceMotor myRight){
        rightDrive.add(myRight);
    }
    //Autonomous Methods
    public void linearDrive(double inches, double pow){
        //Tell motors to start
        operateMotors(leftDrive,inches,pow);
        operateMotors(rightDrive, inches, pow);
    }
    //Right is positive, left is negetive
    public void tankTurn(double degrees, double pow){
        if(degrees != 0){
            double factor = 360/degrees;
            double distance = (Math.PI * robotWidth)/factor;
            //One is inverted to create a tank turn
            operateMotors(leftDrive,distance,pow);
            operateMotors(rightDrive, distance * REVERSEORIENTATION, pow * REVERSEORIENTATION);
        }

    }
    public void tankTurn(double degrees){
        tankTurn(degrees, 1);
    }

    public void pivotTurn(double degrees, double pow){
        if(degrees!=0){
            double factor = Math.abs(360/degrees);
            double distance =  (Math.PI * robotWidth * 2)/factor;

            if(pow>0){
                if(degrees>0)
                    operateMotors(leftDrive,distance,pow);
                else
                    operateMotors(rightDrive,distance,pow);

            }
            else //if(pow<0)
            {
                if(degrees>0)
                    operateMotors(rightDrive,-distance,pow);
                else
                    operateMotors(leftDrive,-distance,pow);
            }
        }

    }
    public void forwardPivotTurn(double degrees){
        pivotTurn(degrees,1);
    }

    public void backwardsPivotTurn(double degrees){
        pivotTurn(degrees,-1);
    }
    public void stop(){
        for(int i=0;i<leftDrive.size();i++)
            leftDrive.get(i).stop();
        for(int i=0;i<rightDrive.size();i++)
            rightDrive.get(i).stop();
    }
    public void fullStop(){
        for(int i=0;i<leftDrive.size();i++)
            leftDrive.get(i).fullStop();
        for(int i=0;i<rightDrive.size();i++)
            rightDrive.get(i).fullStop();
    }
    
    //Timers
    public void waitForCompletion() throws InterruptedException{
        for(int i=0;i<leftDrive.size();i++)
            leftDrive.get(i).waitForCompletion();
        for(int i=0;i<rightDrive.size();i++)
            rightDrive.get(i).waitForCompletion();
    }
    //Helpper Private methods
    private void operateMotors(ArrayList<DistanceMotor> myMotors, double distance, double pow){
        for(int i=0;i<myMotors.size();i++){
            myMotors.get(i).operate(distance,pow);
        }
    }
}
