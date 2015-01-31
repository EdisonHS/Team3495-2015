
package org.usfirst.frc.team3495.robot; 
 
 
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

public class Robot extends IterativeRobot {  
	 
	//DECLARATIONS FOR JOYSTICKS, CHECK LAPTOP FOR PORT CORRELATION 
	private final Joystick lStick = new Joystick(0);//LEFT JOYSTICK GOES IN USB PORT 0
	private final Joystick rStick = new Joystick(1);//RIGHT JOYSTICK GOES IN USB PORT 1
	private final Joystick fireCon = new Joystick(2);//FIRE CONTROLLER GOES IN USB PORT 2
 	 
 	private Compressor viceroyPumpy = new Compressor(1);//DECLARATION FOR THE COMPRESSOR, OUT OF RELAY 1, PORT FOR PRESSURE SENSOR UNKNOWN 
 	
 	private Button strafeLeft = new JoystickButton(lStick,2);//JOYSTICK BUTTON 2 ON THE LEFT JOYSTICK
 	private Button strafeRight = new JoystickButton(rStick,2);//JOYSTICK BUTTON 2 ON THE RIGHT JOYSTICK
 	private Button pickUpTote = new JoystickButton(rStick,3);
 	private Button pickUpCan = new JoystickButton(rStick,1);
 	private Button dropStack = new JoystickButton(lStick,1);
 	private Button threeFourthsSpeed = new JoystickButton(lStick, 3);
 	private Button halfSpeed = new JoystickButton(lStick, 4);
 	private Button quarterSpeed = new JoystickButton(lStick,5);
 	private Button fullSpeed = new JoystickButton(lStick,6);
 	
 	public OmniTankDrive driveTrain = new OmniTankDrive(0,1,2,3,4);//DECLARATION FOR THE OMNITANKDRIVE DRIVETRAIN
 	/*CLASS CONTAINS METHODS FOR DIFFERENT STYLES OF DRIVING, AND METHODS FOR MANIPULATING THE DRIVETRAIN IN SPECIFIC WAYS
 	 * 
 	 * WHEN VARIABLE REFERED TO A DOUBLE FOR A MOTOR THE VALUE MUST BE BETWEEN -1.0 AND 1.0
 	 * 
 	 * THE DECLEARATION PARAMETERS FOR THE OBJECT(LEFT SIDE TALON 1 PORT, LEFT SIDE TALON 2 PORT, RIGHT SIDE TALON 1 PORT, RIGHT SIDE TALON 2 PORT, MIDDLE TALON PORT)
 	 * 
 	 * DRIVE STYLES INCLUDE THE FOLLOWING WITH PROPER SYNTAX:
 	 * 
 	 * driveTrain.singleControllerAxisDrive(rStick.getY(), rStick.getX(),lStick.getY());
 	 * 		TANKDRIVE ON ONE CONTROLLER, STRAFE ON THE OTHER CONTROLLER
 	 * 
 	 * driveTrain.standardDrive(lStick.getY(), rStick.getY(), strafeLeft.get(), strafeRight.get());
 	 * 		RAW CONTROLLER VALUES FOR STANDARD TANK DRIVE, USES SINGLE SPEED AND BUTTONS FOR STRAFING, STRAFESPEED CAN BE CHANGED- SEE BELOW DRIVE METHODS
 	 * 
 	 * driveTrain.axisDrive(lStick.getY(), rStick.getY(), rStick.getX());
 	 * 		RAW CONTROLLER VALUES FOR STANDARD TANK DRIVE, USES X AXIS OF ONE CONTROLLER FOR SPEED OF STRAFE WHEEL- HAS 20% TOLERANCE RANGE FOR ACTIVATION
 	 * 
 	 * driveTrain. standardTankAccelerationStrafeDrive(lStick.getY() , rStick.getY() , strafeLeft.get(), strafeRight.get());
 	 * 		STANDARD TANK DRIVE- CONTROLLER VALUES * SPEEDMOD, USES ACCELERATING SPEED AND BUTTONS FOR STRAFING
 	 * 
 	 * driveTrain.rawAxisDrive(lStick.getY(), rStick.getY(), rStick.getX());
 	 * 		RAW CONTROLLER VALUES FOR STANDARD TANK DRIVE, USES RAW CONTROLLER VALUE FOR STRAFEING
 	 * 
 	 * METHODS THAT ARE NOT DRIVING STYLES WITH PROPER SYNTAX:
 	 * 
 	 * driveTrain.increaseStrafeAccelerationModifier();
 	 * 		WILL INCREASE THE STRAFE ACCELERATION MODIFIER BY .01
 	 * 
 	 * driveTrain.decreaseStrafeAccelerationModifier();
 	 * 		WILL DECREASE THE STRAFE ACCELERATION MODIFIER BY .01
 	 * 
 	 * driveTrain.resetStrafeAccelerationModifier();
 	 * 		WILL RESET THE STRAFE ACCELERATION MODIFIER TO 0
 	 * 
 	 * driveTrain.setStrafeSpeed(double x);
 	 * 		SETS THE STRAFESPEED VARIABLE TO X, STRAFESPEED VARIABLE USED IN ONLY A FEW DRIVETRAIN METHODS
 	 * 
 	 * driveTrain.pointSevenFiveSpeed();
 	 * 		SETS THE TANKDRIVE SPEEDMOD VARIABLE TO 3/4 SPEED
 	 * 
 	 * driveTrain.halfSpeed();
 	 * 		SETS THE TANKDRIVE SPEEDMOD VARIABLE TO 1/2 SPEED
 	 * 
 	 * driveTrain.quarterSpeed();
 	 * 		SETS THE TANKDRIVE SPEEDMOD VARIABLE TO 1/4 SPEED
 	 * 
 	 * driveTrain.fullSpeed();
 	 * 		SETS THE TANKDRIVE SPEEMOD VARIABLE TO 1/1 SPEED
 	 * 
 	 * driveTrain.driveMiddle(double MSP);
 	 * 		SETS THE MIDDLE WHEEL TO THE VALUE OF MSP
 	 * 
 	 * driveTrain.driveLeftSide(double LSP);
 	 * 		SETS THE LEFT SIDE OF THE DRIVETRAIN TO THE VALUE OF LSP
 	 * 
 	 * driveTrain.driveRightSide(double RSP);
 	 * 		SETS THE RIGHT SIDE OF THE DRIVETRAIN TO THE VALUE OF RSP
 	 * 
 	*/
 	public ElevatorCarriage liftSystem = new ElevatorCarriage(0,1,2,3,4,5);//DECLARATION FOR THE ELEVATORCARRIAGE LIFTSYSTEM
 	/*CLASS INCLUDES ALL METHODS REQUIRED TO OPERATE THE CARRIAGE, AS WELL AS DECLARATIONS FOR 3 DOUBLESOLENOIDS REQUIRED TO OPERATE
 	 * 
 	 * FOR DECLARATION PARAMTERS DESCRIPTION   DS = DOUBLESOLENOID
 	 * DECLARATION PARAMETERS FOR THE OBJECT(DS 1 PORT 1, DS 1 PORT 2, DS 2 PORT 1, DS 2 PORT 2, DS 3 PORT 1 DS 3 PORT 2)
 	 * 
 	 * METHODS FOR THE LIFT SYSTEM ARE AS FOLLOWS WITH PROPER SYNTAX:
 	 * 
 	 * liftSystem.raiseCarriage();
 	 * 		RAISES THE CARRIAGE WITHOUT PICKING UP OR DROPPING ANY TOTES OR CANS
 	 * 
 	 * liftSystem.lowerCarriage();
 	 * 		LOWERS THE CARRIAGE WITHOUT PICKING UP OR DROPPING ANY TOTES OR CANS
 	 * 
 	 * liftSystem.getTote();
 	 * 		WILL ONLY GAIN POSSESION OF THE TOTE
 	 * 
 	 * liftSystem.releaseTote();
 	 * 		WILL ONLY RELEASE POSSESION OF THE TOTE
 	 * 
 	 * liftSystem.getCan();
 	 * 		WILL ONLY GAIN POSSESION OF THE CAN
 	 * 
 	 * liftSystem.releaseCan();
 	 * 		WILL ONLY RELEASE POSSESION OF THE CAN
 	 * 
 	 * liftSystem.actuateCanPickup();
 	 * 		WILL LOWER CARRIAGE, GAIN POSSESION OF THE CAN, AND RAISE CARRIAGE
 	 * 
 	 * liftSystem.actuateTotePickup();
 	 * 		WILL RELEASE POSSESION OF TOTE, LOWER CARRIAGE, GAIN POSSESION OF TOTE, AND THEN RAISE CARRIAGE
 	 * 
 	 * liftSystem.releaseStack();
 	 * 		WILL LOWER THE CARRIAGE, WAIT 1 SECOND, THEN RELEASES POSSESION OF THE TOTE AND CAN
 	 * 
 	*/
     public void robotInit() {//CODE THAT IS EXECUTED WHEN THE ROBOT IS ENABLED IN ANY MODE, EXECUTED ONCE
     	viceroyPumpy.start();//START THE COMPRESSOR TO PUMP AIR
     	liftSystem.lowerCarriage();//LOWER THE CARRAIGE AT THE BEGINNING OF ENABLING THE ROBOT
     	liftSystem.releaseTote();//MAKE SURE THAT THE TOTE GRABBING PISTONS ARE NOT EXTENDED
     }//END ROBOT INIT 

     public void autonomousPeriodic()//CODE THAT IS EXECUTED OVER AND OVER DURING THE AUTONOMOUS PERIOD
     { 
     }//END AUTONOMOUS PERIODIC 
      
     public void autonomousInit()//CODE THAT IS EXECUTED AT THE BEGINNING OF THE AUTONOMOUS PERIOD, EXECUTED ONCE
     { 
     	 
     }//END AUTONOMOUS INIT 
 
 
     public void teleopPeriodic()//CODE THAT IS CONSTANTLY EXECUTED DURING THE TELEOPERATED PERIOD
     { 
    	 if(pickUpCan.get())//IF BUTTON PICK UP CAN IS PRESSED05
    	 {
    		 liftSystem.actuateCanPickup();//CALLS COMAND ACTUATE CAN PICKUP PICKING UP THE CAN
    	 }//END IF PICKUP CAN
    	 
    	 if(pickUpTote.get())//IF BUTTON PICK UP TOTE IS PRESSED
    	 {
    		 liftSystem.actuateTotePickup();//CALLS COMMAND ACTUATE TOTE PICKUP  TO PICKUP A TOTE 
    	 }//END IF TOTE PICKUP
    	 
    	 if(dropStack.get())//IF BUTTON DROPSTACK IS PRESSED
    	 {
    		 liftSystem.releaseStack();//CALLS COMMAND RELEASE STACK TO RELEASE THE STACK
    	 }//END IF DROP STACK
    	 
    	 if(threeFourthsSpeed.get())//IF BUTTON THREEFOURTHSPEED IS PRESSED
    	 {
    		 driveTrain.pointSevenFiveSpeed();//CALLS COMMAND POINT SEVEN FIVE SPEED TO SET SPEEDMOD TO .75
    	 }//END IF SET SPEEDMOD TO .75
    	 
    	 if(quarterSpeed.get())//IF BUTTON QUARTER SPEED IS PRESSED
    	 {
    		 driveTrain.quarterSpeed();//CALLS COMMAND QUARTER SPEED TO SET SPEEDMOD TO .25
    	 }//END IF SET SPEEDMOD TO .25
    	 
    	 if(halfSpeed.get())//IF BUTTON HALF SPEED IS PRESSED
    	 {
    		 driveTrain.halfSpeed();//CALLS COMMAND HALF SPEED TO SET SPEEDMOD TO HALF SPEED
    	 }//END IF SET SPEEDMOD TO .5
    	 
    	 if(fullSpeed.get())//CHECKS TO SEE IF FULLSPEED BUTTON IS PRESSED
    	 {
    		 driveTrain.fullSpeed();//CALLS COMMAND FULLSPEED TO SET SPEEDMOD TO FULLSPEED
    	 }//END SET SPEEDMOD TO 1
    	 
    	 driveTrain.standardTankAccelerationStrafeDrive(lStick.getY(), rStick.getY(), strafeLeft.get(), strafeRight.get());
    	 //EXECUTE OMNITANKDRIVE METHOD STANDARDTANKACCELERATIONSTRAFEDRIVE   GO TO DECLARATION FOR DETAILS ON METHOD
     }//END TELEOP PERIODIC 
      
     public void testInit()//CODE THAT IS EXECUTED AT THE BEGINNING OF THE TEST MODE ONCE 
     { 
     	 
     }//END TEST INIT 
      
     public void testPeriodic()//CODE THAT IS CONSTANTLY EXECUTED DURING THE TEST MODE
     { 
      
     }//END TEST PERIODIC 
    
     public void disabledInit()//CODE THAT IS EXECUTED DURING THE START OF BEING DISABLED 
     { 
    	 //PUT ANY ACTIONS YOU WANT THE ROBOT TO EXECUTE AFTER THE MATCH IS OVER HERE
    	 liftSystem.lowerCarriage();
    	 Timer.delay(1);
    	 liftSystem.releaseTote();
    	 driveTrain.rawAxisDrive(0,0,0);
    	 
     }//END DISABLED INIT 
 } 
 


 
   
