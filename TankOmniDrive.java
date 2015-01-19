package org.usfirst.frc.team3495.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class TankOmniDrive {

	private int leftSideTalonPort;
	private int rightSideTalonPort;
	private int middleSideTalonPort;
	private double driveAccelerationModifier = 0;
	private double strafeAccelerationModifier = 0;
	private double strafeSpeed = 1;
	
	public TankOmniDrive(int pwmT1_2, int pwmT3_4, int pwmT5)
	{
		leftSideTalonPort = pwmT1_2;
		rightSideTalonPort = pwmT3_4;
		middleSideTalonPort = pwmT5;
	}//END OBJECT TANKOMNIDRIVE
	
	public Talon l1_2 = new Talon(leftSideTalonPort);
	public Talon r3_4 = new Talon(rightSideTalonPort);
	public Talon m1 = new Talon(middleSideTalonPort);
	
	public void accelerationDrive(double LSP, double RSP, boolean sLeft, boolean sRight)
	{
		if(LSP >= .5 || LSP <= -.5)
		{
			l1_2.set(LSP * driveAccelerationModifier);
			Timer.delay(.01);
			increaseDriveAccelerationModifier();
		}
		else if(LSP < .5 || LSP >-.5)
		{
			l1_2.set(LSP);
			Timer.delay(.001);
			decreaseDriveAccelerationModifier();
		}//END LEFT SIDE THRESHOLD ACCELERATION IF STATEMENT
		
		if(RSP >= .5 || RSP <= -.5)
		{
			r3_4.set(-RSP * driveAccelerationModifier);
			increaseDriveAccelerationModifier();
		}
		else if(RSP < .5 || RSP > -.5)
		{
			r3_4.set(-RSP);
			Timer.delay(.001);
			decreaseDriveAccelerationModifier();
		}//END RIGHT SIDE THRESHOLD ACCELERATION IF STATEMENT
		
		if(sLeft && !sRight)
		{
			m1.set(strafeSpeed * strafeAccelerationModifier);
			Timer.delay(.01);
			increaseStrafeAccelerationModifier();
		}
		else if(sRight && !sLeft)
		{
			m1.set(-strafeSpeed * strafeAccelerationModifier);
			Timer.delay(.01);
			increaseStrafeAccelerationModifier();
		}
		else
		{
			m1.set(0);
			Timer.delay(.001);
			decreaseStrafeAccelerationModifier();
		}//END MIDDLESIDE
	}//END MAIN DRIVE METHOD
	
	public void driveLeftSide(double LSP)
	{
		l1_2.set(LSP);
	}//END METHOD DRIVE LEFT SIDE
	
	public void driveRightSide(double RSP)
	{
		r3_4.set(RSP);
	}//END METHOD DRIVE RIGHT SIDE

	public void driveMiddle(double MSP)
	{
		m1.set(MSP);
	}//END METHOD DRIVE MIDDLE
	
	public void standardDrive(double LSP, double RSP, boolean sL, boolean sR, double sSM)
	{
		l1_2.set(LSP);
		r3_4.set(RSP);
		if(sL && !sR)
		{
			m1.set(sSM);
		}
		else if(sR && !sL)
		{
			m1.set(-sSM);
		}
		else
		{
			m1.set(0);
		}//END STRAFING WHEEL SETTING
	}//END METHOD STANDARD DRIVE, DRIVING WITHOUT ANY ACCELERATION, BUT RAW VALUES SPEED MOD FOR STRAFE CIM
	
	public void increaseDriveAccelerationModifier()
	{
		if(driveAccelerationModifier < 1){driveAccelerationModifier += .01;}
	}//END METHOD INCREASING THE DRIVING ACCELERATION MODIFIER
	
	public void decreaseDriveAccelerationModifier()
	{
		if(driveAccelerationModifier > 0){driveAccelerationModifier -= .01;}
	}//END METHOD DECREASING THE DRIVING ACCELERATION MODIFIER
	
	public void resetDriveAccelerationModifier()
	{
		driveAccelerationModifier = 0;
	}//END METHOD RESETING THE ACCELERATION MODIFIER
	
	public void increaseStrafeAccelerationModifier()
	{
		if(strafeAccelerationModifier < 1){strafeAccelerationModifier += .01;}
	}//END METHOD INCREASING THE STRAFING ACCELERATION MODIFIER
	
	public void decreaseStrafeAccelerationModifier()
	{
		if(strafeAccelerationModifier > 0){strafeAccelerationModifier -= .01;}
	}//END METHOD DECREASING THE STRAFING ACCELERATION MODIFIER
	
	public void resetStrafeAccelerationModifier()
	{
		strafeAccelerationModifier = 0;
	}//END METHOD RESETING THE STRAFING ACCELERATION MODIFIER
	
	public void setStrafeSpeed(double x)
	{
		strafeSpeed = x;
	}//END METHOD SETTING STRAFING SPEED
	
}
