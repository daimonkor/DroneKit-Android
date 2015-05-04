package org.droidplanner.core.drone.variables.calibration;

import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.ardupilotmega.msg_mag_cal_progress;
import com.MAVLink.ardupilotmega.msg_mag_cal_report;

import org.droidplanner.core.MAVLink.MavLinkCalibration;
import org.droidplanner.core.drone.DroneVariable;
import org.droidplanner.core.model.Drone;

import java.util.HashMap;

/**
 * Created by Fredia Huya-Kouadio on 5/3/15.
 */
public class MagnetometerCalibration extends DroneVariable {

    public interface OnMagnetometerCalibrationListener {
        void onCalibrationCancelled();

        void onCalibrationProgress(msg_mag_cal_progress progress);

        void onCalibrationCompleted(msg_mag_cal_report result);
    }

    private final HashMap<Byte, Info> magCalibrationTracker = new HashMap<>();

    private OnMagnetometerCalibrationListener listener;

    public MagnetometerCalibration(Drone myDrone) {
        super(myDrone);
    }

    public void setListener(OnMagnetometerCalibrationListener listener) {
        this.listener = listener;
    }

    public void startCalibration(boolean retryOnFailure, boolean saveAutomatically, int startDelay) {
        magCalibrationTracker.clear();
        MavLinkCalibration.startMagnetometerCalibration(myDrone, retryOnFailure, saveAutomatically, startDelay);
    }

    public void cancelCalibration() {
        MavLinkCalibration.cancelMagnetometerCalibration(myDrone);

        for (Info info : magCalibrationTracker.values())
            info.wasCancelled = true;

        if (listener != null)
            listener.onCalibrationCancelled();
    }

    public void acceptCalibration() {
        MavLinkCalibration.acceptMagnetometerCalibration(myDrone);
    }

    public void processCalibrationMessage(MAVLinkMessage message) {
        switch (message.msgid) {
            case msg_mag_cal_progress.MAVLINK_MSG_ID_MAG_CAL_PROGRESS: {
                msg_mag_cal_progress progress = (msg_mag_cal_progress) message;
                Info info = magCalibrationTracker.get(progress.compass_id);
                if (info == null) {
                    info = new Info();
                    magCalibrationTracker.put(progress.compass_id, info);
                }

                info.calProgress = progress;

                if (listener != null)
                    listener.onCalibrationProgress(progress);
                break;
            }

            case msg_mag_cal_report.MAVLINK_MSG_ID_MAG_CAL_REPORT: {
                msg_mag_cal_report report = (msg_mag_cal_report) message;
                Info info = magCalibrationTracker.get(report.compass_id);
                if (info == null) {
                    info = new Info();
                    magCalibrationTracker.put(report.compass_id, info);
                }

                info.calReport = report;

                if (listener != null)
                    listener.onCalibrationCompleted((msg_mag_cal_report) message);
                break;
            }
        }
    }

    public HashMap<Byte, Info> getMagCalibrationTracker() {
        return magCalibrationTracker;
    }

    public static class Info {
        msg_mag_cal_progress calProgress;
        msg_mag_cal_report calReport;
        boolean wasCancelled;
    }
}
