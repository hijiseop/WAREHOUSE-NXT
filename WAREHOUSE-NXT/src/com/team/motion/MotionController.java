package com.team.motion;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import rp.config.WheeledRobotConfiguration;
import rp.systems.RobotProgrammingDemo;
import rp.systems.WheeledRobotSystem;


public class MotionController extends RobotProgrammingDemo{
	
	private static final float TRAVEL_SPEED = 0.15f;
	private static final int JUNCTION_DETECT_VALUE = 1;
	private static final int LINE_FOLLOW_ANGLE = 6;
	private static final int TIME_DELAY_MS = 90;
	private static final int LINE_ADJUST_DETECT_VALUE = 2;
	private static final float JUNCTION_TRAVEL_DIST = 0.08F;
	private static final int JUNCTION_ROTATE_ANGLE = 95;
	private static final int CALIBRATION_LOOP_CHECKS = 5;
	
	private int totalCalibrationChecks = 0;
	private int totalLeftValues = 0;
	private int totalRightValues = 0;
	private int sensDiff = 0;
	
	private final SensorPort leftSensor;
	private final SensorPort rightSensor;
	private final DifferentialPilot pilot;
	private LightSensor left;
	private LightSensor right;
	
	private Integer[] directions;
	
	public MotionController(WheeledRobotConfiguration robotConfig, SensorPort s2, SensorPort s3) {
		this.pilot =  new WheeledRobotSystem(robotConfig).getPilot();
		this.leftSensor = s2;
		this.rightSensor = s3;
		left = new LightSensor(leftSensor);
		right = new LightSensor(rightSensor);
	}
	
	@Override
	public void run() {
		
		int previousLeft = left.getLightValue();
		int previousRight = right.getLightValue();
		
		int junctionsReached = 0;
		
		calibrate();
		
		pilot.rotate(JUNCTION_ROTATE_ANGLE*directions[0]);
		junctionsReached++;
		pilot.travel(1000,true);
		pilot.setTravelSpeed(TRAVEL_SPEED);
		
		
		
		while (m_run) {
			
			int leftLight = left.getLightValue();
			leftLight -= sensDiff; //3;	//adjusts for any difference in sensitivity between the sensors
			int rightLight = right.getLightValue();
			
			
			if (
			   ((previousLeft-leftLight)>=JUNCTION_DETECT_VALUE) && ((previousRight-rightLight)>=JUNCTION_DETECT_VALUE)) {
				if (junctionsReached<directions.length) {
					junction(directions[junctionsReached]);
					junctionsReached++;
				}
				else {
					pilot.stop();
					pilot.travel(JUNCTION_TRAVEL_DIST,true);
					break;
				}
			}
			
			else if((leftLight - rightLight) > LINE_ADJUST_DETECT_VALUE){
				
				rotate(-LINE_FOLLOW_ANGLE);
				
			} else if ((leftLight - rightLight) < -LINE_ADJUST_DETECT_VALUE) {
				
				rotate(LINE_FOLLOW_ANGLE);
				
			} 
			Delay.msDelay(TIME_DELAY_MS);
			
			previousLeft = leftLight;
			previousRight = rightLight;
			
		}
	}
	
	public void setDirs(Integer[] integers){
		this.directions = integers;
	}

	private void calibrate(){
		for(int i = 0; i < CALIBRATION_LOOP_CHECKS; i++){
			totalLeftValues += left.getLightValue();
			totalRightValues += right.getLightValue();
			totalCalibrationChecks++;
			Delay.msDelay(TIME_DELAY_MS);
		}
		
		sensDiff = (totalLeftValues/totalCalibrationChecks) - (totalRightValues/totalCalibrationChecks);
	}
	
	private void rotate(int angle){
		pilot.stop();
		pilot.rotate(angle);
		pilot.travel(1000,true);
	}
	
	private void junction(int rotationMultiplier) {
			pilot.stop();
			pilot.travel(JUNCTION_TRAVEL_DIST,false);
			
			calibrate();
			pilot.rotate(JUNCTION_ROTATE_ANGLE*rotationMultiplier);
			pilot.travel(1000,true);
	}
}

