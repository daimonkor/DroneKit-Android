package org.droidplanner.services.android.core.drone.variables;

import org.droidplanner.services.android.core.drone.DroneInterfaces.DroneEventsType;
import org.droidplanner.services.android.core.drone.DroneVariable;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;
import org.droidplanner.services.android.core.parameters.Parameter;

public class Battery extends DroneVariable {
	private double battVolt = -1;
	private double battRemain = -1;
	private double battCurrent = -1;

	public Battery(MavLinkDrone myDrone) {
		super(myDrone);
	}

	public double getBattVolt() {
		return battVolt;
	}

	public double getBattRemain() {
		return battRemain;
	}

	public double getBattCurrent() {
		return battCurrent;
	}

	public Double getBattDischarge() {
		Parameter battCap = myDrone.getParameters().getParameter("BATT_CAPACITY");
		if (battCap == null || battRemain == -1) {
			return null;			
		}
		return (1-battRemain/100.0)*battCap.value; 
	}
	
	public void setBatteryState(double battVolt, double battRemain, double battCurrent) {
		if (this.battVolt != battVolt || this.battRemain != battRemain || this.battCurrent != battCurrent) {
			this.battVolt = battVolt;
			this.battRemain = battRemain;
			this.battCurrent = battCurrent;
			myDrone.notifyDroneEvent(DroneEventsType.BATTERY);
		}
	}

}