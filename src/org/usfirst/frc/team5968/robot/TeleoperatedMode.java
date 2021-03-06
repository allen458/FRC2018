package org.usfirst.frc.team5968.robot;

import org.usfirst.frc.team5968.robot.PortMap.USB;

import edu.wpi.first.wpilibj.Joystick;

public class TeleoperatedMode implements IRobotMode {
    
    private Lift lift;
    private IGrabber grabber;
    private IDrive drive;
    private Joystick leftJoystick;
    private Joystick rightJoystick;
    
    private final double TOLERANCE = 0.1;
    
    public TeleoperatedMode(IDrive drive) {
        leftJoystick = new Joystick(PortMap.portOf(USB.LEFT));
        rightJoystick = new Joystick(PortMap.portOf(USB.RIGHT));
        
        lift = new Lift(drive);
        grabber = new Grabber();
        this.drive = drive;
    }

    @Override
    public void init() {
       lift.goToCurrentHeight();
        drive.init();
    }

    @Override
    public void periodic() {
        drive.driveManual(getLeftStick(), getRightStick());
        if (getButtonPressed(1)){
            grabber.toggleGrabbing();
        }
        
        if (getButtonPressed(2) || getButtonPressed(5)){
            lift.goToGroundHeight();
        }
        
        if (getButtonPressed(3) || getButtonPressed(6)){
            lift.goToSwitchHeight();
        }
        
        if (getButtonPressed(4) || getButtonPressed(7)){
            lift.goToScaleHeight();
        } 
    }
    
    private double getLeftStick() {
        double leftY = leftJoystick.getY();
        return (Math.abs(leftY) < TOLERANCE) ? 0 : leftY;
    }
    
    private double getRightStick() {
        double rightY = rightJoystick.getY();
        return (Math.abs(rightY) < TOLERANCE) ? 0 : rightY;
    }
    
    private boolean getButtonPressed(int buttonNumber) {
        if (buttonNumber <= 4) {
            return leftJoystick.getRawButton(buttonNumber);
        } else {
            return rightJoystick.getRawButton(buttonNumber);
        }
    }
    
}
