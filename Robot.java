
package org.usfirst.frc.team3495.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Button;


public class Robot extends IterativeRobot {

	private SmartDashboard display = new SmartDashboard();//DECLARATION FOR DISPLAY
	private NetworkTable Kamerade = NetworkTable.getTable("SmartDashboard");
	
	private final Joystick lStick = new Joystick(0);//DECLARATIONS FOR JOYSTICKS, CHECK LAPTOP FOR PORT CORRELATION
	private final Joystick rStick = new Joystick(1);
	private final Joystick fireCon = new Joystick(2);
	
	private Talon l1 = new Talon(0);//DECLARATIONS FOR DRIVETRAIN TALONS, PARAMETER IS LOCATION ON THE ROBORIO FOR PWM PORTS
	private Talon l2 = new Talon(1);
	private Talon r1 = new Talon(2);
	private Talon r2 = new Talon(3);
	private Talon m1 = new Talon(4);
	
	private DoubleSolenoid DS1 = new DoubleSolenoid(1,2);//DECLARATION FOR THE DOUBLESOLENOIDS, 1ST IN PORTS 1 AND 2, 2ND IN PORTS 3 AND 4
	private DoubleSolenoid DS2 = new DoubleSolenoid(3,4);
	
	private Compressor viceroyPumpy = new Compressor(1);//DECLARATION FOR THE COMPRESSOR, OUT OF RELAY 1
	
	private void tankDrive(double x, double y, double z)//BEGIN CUSTOM METHOD, TAKES 3 DOUBLES FOR PARAMETERS (LSTICK Y,RSTICK Y, LSTICK X)
	{
		l1.set(x);//SETS TALONS L1 AND L2 TO VALUE OF Y AXIS OF LSTICK
		l2.set(x);
		r1.set(y);//SETS TALONS R1 AND R2 TO VALUE OF Y AXIS OF RSTICK
		r2.set(y);
		if(lStick.getX()>.2 || lStick.getX()<-.2)//CHECKS TO SEE IF VALUE OF X AXIS OF LSTICK IS GREATER THAN THE 40% THRESHOLD, 20% BOTH WAYS
		{
			m1.set(z);//IF PAST THRESHOLD, SET MOTOR SPEED TO RAW VALUE OF X AXIS OF LSTICK
		}
		else// IF NOT PAST THE THRESHOLD, SET MOTOR SPEED TO 0 
		{
			m1.set(0);
		}//END ELSE IF X AXIS THRESHOLD
		
	}//END TANKDRIVE METHOD
	
    public void robotInit() {
    	viceroyPumpy.start();
    }//END ROBOT INIT

    public void autonomousPeriodic() {
    }//END AUTONOMOUS PERIODIC
    
    public void autonomousInit()
    {
    	
    }//END AUTONOMOUS INIT

    public void teleopPeriodic() {
        tankDrive(lStick.getY(), rStick.getY(), lStick.getX());
    }//END TELEOP PERIODIC
    
    public void testInit()
    {
    	
    }//END TEST INIT
    
    public void testPeriodic() {
    
    }//END TEST PERIODIC
    
    public void disabledInit()
    {
    	
    }//END DISABLED INIT
}
