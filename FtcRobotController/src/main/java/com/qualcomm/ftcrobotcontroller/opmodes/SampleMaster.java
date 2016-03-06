package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Walnut Robotics on 3/6/2016.
 */
public class SampleMaster extends LinearOpMode{
    public void runOpMode(){
        telemetry.addData("Tests","Starting in Sample Master");
        MasterLinear sample = new MasterLinear("RED",1,1000,false,this);
        //Check for null pointers
        //Init Stage

        sample.runOpMode();
    }
}
