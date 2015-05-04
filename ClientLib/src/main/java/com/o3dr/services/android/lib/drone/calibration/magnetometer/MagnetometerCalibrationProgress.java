package com.o3dr.services.android.lib.drone.calibration.magnetometer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fredia Huya-Kouadio on 5/3/15.
 */
public class MagnetometerCalibrationProgress implements Parcelable {

    private byte compassId;

    private byte completionPercentage;

    /**
     * Body frame direction vector for display
     */
    private float directionX;
    private float directionY;
    private float directionZ;

    public MagnetometerCalibrationProgress(){}

    public MagnetometerCalibrationProgress(byte compassId, byte percentage, float directionX, float directionY, float
            directionZ){
        this.compassId = compassId;
        this.completionPercentage = percentage;
        this.directionX = directionX;
        this.directionY = directionY;
        this.directionZ = directionZ;
    }

    public byte getCompassId() {
        return compassId;
    }

    public void setCompassId(byte compassId) {
        this.compassId = compassId;
    }

    public byte getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(byte completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public float getDirectionX() {
        return directionX;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }

    public float getDirectionZ() {
        return directionZ;
    }

    public void setDirectionZ(float directionZ) {
        this.directionZ = directionZ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.compassId);
        dest.writeByte(this.completionPercentage);
        dest.writeFloat(this.directionX);
        dest.writeFloat(this.directionY);
        dest.writeFloat(this.directionZ);
    }

    private MagnetometerCalibrationProgress(Parcel in) {
        this.compassId = in.readByte();
        this.completionPercentage = in.readByte();
        this.directionX = in.readFloat();
        this.directionY = in.readFloat();
        this.directionZ = in.readFloat();
    }

    public static final Creator<MagnetometerCalibrationProgress> CREATOR = new Creator<MagnetometerCalibrationProgress>() {
        public MagnetometerCalibrationProgress createFromParcel(Parcel source) {
            return new MagnetometerCalibrationProgress(source);
        }

        public MagnetometerCalibrationProgress[] newArray(int size) {
            return new MagnetometerCalibrationProgress[size];
        }
    };
}
