package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Walnut Robotics on 3/15/2016.
 */
public class servotest extends LinearOpMode {
    public void runOpMode(){
        //Prepare
        telemetry.addData("Tests", "Starting in Sample Master");
        AutoSections stepToTest = AutoSections.DEPLOYCLIMBERS;
        TestLinear sample = new TestLinear("RED",2,0,false,this, stepToTest);
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
