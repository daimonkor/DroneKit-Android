package org.droidplanner.services.android.core.MAVLink.command.doCmd;

import com.MAVLink.ardupilotmega.msg_digicam_control;
import com.MAVLink.ardupilotmega.msg_mount_control;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_mission_set_current;
import com.MAVLink.enums.MAV_CMD;
import com.o3dr.services.android.lib.model.ICommandListener;

import org.droidplanner.services.android.core.helpers.coordinates.Coord3D;
import org.droidplanner.services.android.core.mission.commands.EpmGripper;
import org.droidplanner.services.android.core.drone.autopilot.MavLinkDrone;

public class MavLinkDoCmds {
    public static void setROI(MavLinkDrone drone, Coord3D coord, ICommandListener listener) {
        if (drone == null)
            return;

        msg_command_long msg = new msg_command_long();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.command = MAV_CMD.MAV_CMD_DO_SET_ROI;

        msg.param5 = (float) coord.getX();
        msg.param6 = (float) coord.getY();
        msg.param7 = (float) coord.getAltitude();

        drone.getMavClient().sendMavMessage(msg, listener);
    }

    public static void resetROI(MavLinkDrone drone, ICommandListener listener) {
        if (drone == null)
            return;

        setROI(drone, new Coord3D(0, 0, 0), listener);
    }

    public static void triggerCamera(MavLinkDrone drone) {
        if (drone == null)
            return;

        msg_digicam_control msg = new msg_digicam_control();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.shot = 1;
        drone.getMavClient().sendMavMessage(msg, null);
    }

    public static void empCommand(MavLinkDrone drone, boolean release, ICommandListener listener) {
        if (drone == null)
            return;

        msg_command_long msg = new msg_command_long();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.command = EpmGripper.MAV_CMD_DO_GRIPPER;
        msg.param2 = release ? EpmGripper.GRIPPER_ACTION_RELEASE : EpmGripper.GRIPPER_ACTION_GRAB;

        drone.getMavClient().sendMavMessage(msg, listener);
    }

    /**
     * Set a Relay pin’s voltage high or low
     *
     * @param drone       target vehicle
     * @param relayNumber
     * @param enabled     true for relay to be on, false for relay to be off.
     */
    public static void setRelay(MavLinkDrone drone, int relayNumber, boolean enabled, ICommandListener listener) {
        if (drone == null)
            return;

        msg_command_long msg = new msg_command_long();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.command = MAV_CMD.MAV_CMD_DO_SET_RELAY;
        msg.param1 = relayNumber;
        msg.param2 = enabled ? 1 : 0;

        drone.getMavClient().sendMavMessage(msg, listener);
    }

    /**
     * Move a servo to a particular pwm value
     *
     * @param drone   target vehicle
     * @param channel he output channel the servo is attached to
     * @param pwm     PWM value to output to the servo. Servo’s generally accept pwm values between 1000 and 2000
     */
    public static void setServo(MavLinkDrone drone, int channel, int pwm, ICommandListener listener) {
        if (drone == null)
            return;

        msg_command_long msg = new msg_command_long();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.command = MAV_CMD.MAV_CMD_DO_SET_SERVO;
        msg.param1 = channel;
        msg.param2 = pwm;

        drone.getMavClient().sendMavMessage(msg, listener);
    }

    /**
     * Set the orientation of a gimbal
     *
     * @param drone       target vehicle
     * @param pitch       the desired gimbal pitch in degrees
     * @param roll       the desired gimbal roll in degrees
     * @param yaw       the desired gimbal yaw in degrees
     * @param listener Register a callback to receive update of the command execution state.
     */
    public static void setGimbalOrientation(MavLinkDrone drone, double pitch, double roll, double yaw, ICommandListener
            listener) {
        if (drone == null)
            return;

        msg_mount_control msg = new msg_mount_control();
        msg.target_system = drone.getSysid();
        msg.target_component = drone.getCompid();
        msg.input_a = (int) (pitch * 100);
        msg.input_b = (int) (roll * 100);
        msg.input_c = (int) (yaw * 100);

        drone.getMavClient().sendMavMessage(msg, listener);
    }

    /**
     * Jump to the desired command in the mission list. Repeat this action only the specified number of times
     * @param drone               target vehicle
     * @param waypoint    command
     * @param listener            Register a callback to receive update of the command execution state.
     */
    public static void gotoWaypoint(MavLinkDrone drone, int waypoint, ICommandListener listener){
        if (drone == null)
            return;
        msg_mission_set_current msg = new msg_mission_set_current();
        msg.seq = waypoint;
        drone.getMavClient().sendMavMessage(msg, listener);
    }

}
