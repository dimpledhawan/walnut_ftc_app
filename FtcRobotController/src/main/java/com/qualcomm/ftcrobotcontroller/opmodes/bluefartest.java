package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Walnut Robotics on 3/13/2016.
 */
public class bluefartest extends LinearOpMode
{
    public void runOpMode(){
        //Prepare
        telemetry.addData("Tests", "Starting in Sample Master");
        AutoSections stepToTest = AutoSections.POSITION2START;
        TestLinear sample = new TestLinear("BLUE",2,0,false,this, stepToTest);
        telemetry.addData("Tests", "I'M READY FOR FREADY!");
        //Do
        try{
            waitForStart();
            sample.execute();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
