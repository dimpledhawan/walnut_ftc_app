package com.walnuthillseagles.walnutlibrary;

import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

/**
 * Created by Yan Vologzhanin on 2/8/2016.
 */
public class ContinuousServo implements Drivable{
    private Servo servo;
    private ArrayList<IncEvent> tablePos;
    private double deadZone;
    private int orientation;
    private String name;
    private double trueCenter;
    public class IncEvent{
        public int tableIndex;
        public int orientation;
        public IncEvent(String control, boolean isReversed){
            tableIndex = addTable(AnalogValues.valueOf(control.toUpperCase()));
            orientation = isReversed ? -1 : 1;
        }
        @Override
        public String toString(){
            return "Index " + tableIndex + " and orientaion " + orientation;
        }
    }

    public ContinuousServo(Servo myServo, String myName, double startPos,
                           String myControl, boolean reverse, double myDeadzone) {
        servo = myServo;
        name = myName;
        tablePos = new ArrayList<IncEvent>();
        //Assign Table Position
        trueCenter = startPos;
        tablePos.add(new IncEvent(myControl.toUpperCase(), reverse));
        deadZone = myDeadzone;
        //@TODO: Use this if necessary 
        //trueCenter = startPos;
        stop();
    }
    public void stop(){
        servo.setPosition(trueCenter);
    }
    private int addTable(AnalogValues myControl){
        switch(myControl){
            case LEFTX1:
                return 0;
            case LEFTY1:
                return 1;
            case RIGHTX1:
                return 2;
            case RIGHTY1:
                return 3;
            case LEFTZ1:
                return 4;
            case RIGHTZ1:
                return 5;
            case LEFTX2:
                return 6;
            case LEFTY2:
                return 7;
            case RIGHTX2:
                return 8;
            case RIGHTY2:
                return 9;
            case LEFTZ2:
                return 10;
            case RIGHTZ2:
                return 11;
            default:
                return 0;
        }
    }
    public void addInput(String input, boolean isReversed){
        tablePos.add(new IncEvent(input.toUpperCase(),isReversed));
    }
    public void setPower(double power) {
        servo.setPosition(power / 2 + 0.5);
    }
    public void operate(){
        ArrayList<Integer> zeroChecker = new ArrayList<Integer>();
        for(int i=0;i<tablePos.size();i++)
        {
            double val = VirtualGamepad.doubleValues[tablePos.get(i).tableIndex];
            zeroChecker.add((int) val);
            if(Math.abs(val)>deadZone )
                this.setPower(val* tablePos.get(i).orientation);
            else if(Math.abs(val)>0.95)
                this.setPower(0.95 * tablePos.get(i).orientation);
        }
        int isZero = 0;
        for(int i=0;i<zeroChecker.size();i++){
            isZero = Math.max(isZero, Math.abs(zeroChecker.get(i)));
        }
        if(isZero == 0)
            this.stop();

    }
    //Debugging
    public String getTablePos(){
        String ret = "";
        for(int i=0;i<tablePos.size();i++){
            ret += i + ":" + tablePos.get(i) + ":";
        }
        return ret;
    }
}
