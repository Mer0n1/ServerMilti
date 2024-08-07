package org.example;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class AvatarBullet implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x_begin, y_begin;
    private float x_end, y_end;
    private double speed;
    private float angle;
    private int idOwner;

    public AvatarBullet(int x_begin, int y_begin, float x_end, float y_end, double speed,
                        float angle, int idOwner) {
        this.x_begin = x_begin;
        this.y_begin = y_begin;
        this.x_end = x_end;
        this.y_end = y_end;
        this.speed = speed;
        this.angle = angle;
        this.idOwner = idOwner;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(36);
        buffer.putInt(x_begin);
        buffer.putInt(y_begin);
        buffer.putDouble(speed);
        buffer.putFloat(angle);
        buffer.putInt(idOwner);
        return buffer.array();
    }

    public static AvatarBullet fromByteArray(byte[] array, int offset) {
        ByteBuffer buffer = ByteBuffer.wrap(array, offset, 36);
        int x_begin = buffer.getInt();
        int y_begin = buffer.getInt();
        double speed= buffer.getDouble();
        float angle = buffer.getFloat();
        int idOwner = buffer.getInt();
        return new AvatarBullet(x_begin, y_begin,
                0, 0, speed, angle, idOwner);
    }

    public int getX_begin() {
        return x_begin;
    }

    public int getY_begin() {
        return y_begin;
    }

    public float getX_end() {
        return x_end;
    }

    public float getY_end() {
        return y_end;
    }

    public double getSpeed() {
        return speed;
    }

    public void setX_begin(int x_begin) {
        this.x_begin = x_begin;
    }

    public void setY_begin(int y_begin) {
        this.y_begin = y_begin;
    }

    public void setX_end(float x_end) {
        this.x_end = x_end;
    }

    public void setY_end(float y_end) {
        this.y_end = y_end;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public float getAngle() {
        return angle;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    @Override
    public String toString() {
        return "AvatarBullet{" +
                "x_begin=" + x_begin +
                ", y_begin=" + y_begin +
                ", x_end=" + x_end +
                ", y_end=" + y_end +
                ", speed=" + speed +
                ", angle=" + angle +
                ", idOwner=" + idOwner +
                '}';
    }
}
