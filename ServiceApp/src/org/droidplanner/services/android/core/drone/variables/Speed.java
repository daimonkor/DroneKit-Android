package org.droidplanner.services.android.core.drone.variables;

import org.droidplanner.services.android.core.drone.DroneInterfaces;
import org.droidplanner.services.android.core.drone.DroneVariable;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;
import org.droidplanner.services.android.core.parameters.Parameter;

public class Speed extends DroneVariable {

	private double verticalSpeed = 0;
	private double groundSpeed = 0;
	private double airSpeed = 0;
	private double targetSpeed = 0;

	public Speed(MavLinkDrone myDrone) {
		super(myDrone);
	}

	public double getVerticalSpeed() {
		return verticalSpeed;
	}

	public double getGroundSpeed() {
		return groundSpeed;
	}

	public double getAirSpeed() {
		return airSpeed;
	}

	public double getTargetSpeed() {
		return targetSpeed;
	}

	public void setSpeedError(double aspd_error) {
		targetSpeed = (aspd_error + airSpeed);
	}

	public void setGroundAndAirSpeeds(double groundSpeed, double airSpeed, double climb) {
        boolean speedUpdated = false;
        if(this.groundSpeed != groundSpeed) {
            this.groundSpeed = groundSpeed;
            speedUpdated = true;
        }

        if(this.airSpeed != airSpeed){
            this.airSpeed = airSpeed;
            speedUpdated = true;
        }

        if(this.verticalSpeed != climb){
            this.verticalSpeed = climb;
            speedUpdated = true;
        }

        if(speedUpdated) {
            myDrone.notifyDroneEvent(DroneInterfaces.DroneEventsType.SPEED);
        }
	}

	public double getSpeedParameter(){
		Parameter param = myDrone.getParameters().getParameter("WPNAV_SPEED");
		if (param == null ) {
			return -1;
		}else{
			return (param.value/100);
		}
			
	}
}
