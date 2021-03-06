package org.droidplanner.services.android.core.drone.variables;

import org.droidplanner.services.android.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.services.android.core.drone.DroneVariable;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;

public class Orientation extends DroneVariable {
	private double roll = 0;
	private double pitch = 0;
	private double yaw = 0;

	public Orientation(MavLinkDrone myDrone) {
		super(myDrone);
	}

	public double getRoll() {
		return roll;
	}

	public double getPitch() {
		return pitch;
	}

	public double getYaw() {
		return yaw;
	}

	public void setRollPitchYaw(double roll, double pitch, double yaw) {
        if(this.roll != roll || this.pitch != pitch || this.yaw != yaw) {
            this.roll = roll;
            this.pitch = pitch;
            this.yaw = yaw;
            myDrone.notifyDroneEvent(DroneEventsType.ATTITUDE);
        }
	}

}