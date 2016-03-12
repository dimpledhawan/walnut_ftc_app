package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Walnut Robotics on 3/6/2016.
 */
public class SampleMaster extends LinearOpMode{
    public void runOpMode(){
        //Prepare
        telemetry.addData("Tests","Starting in Sample Master");
        MasterLinear sample = new MasterLinear("RED",1,2,true,this);
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
