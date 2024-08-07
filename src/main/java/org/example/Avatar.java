package org.example;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class Avatar implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int x, y;
    private int hp;
    private float angle;

    public Avatar(int id) {
        this.id = id;
    }

    public Avatar(int id, int x, int y, int hp, float angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.hp = hp;
        this.angle = angle;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.putInt(id);
        buffer.putInt(x);
        buffer.putInt(y);
        buffer.putInt(hp);
        buffer.putFloat(angle);
        return buffer.array();
    }

    public static Avatar fromByteArray(byte[] array, int offset) {
        ByteBuffer buffer = ByteBuffer.wrap(array, offset, 32);
        int id = buffer.getInt();
        int x = buffer.getInt();
        int y = buffer.getInt();
        int hp = buffer.getInt();
        float angle = buffer.getFloat();
        return new Avatar(id, x, y, hp, angle);
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", hp=" + hp +
                ", angle=" + angle +
                '}';
    }
}