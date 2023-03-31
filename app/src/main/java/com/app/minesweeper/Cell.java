package com.app.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

public class Cell implements Parcelable {
    public boolean isMine = false;
    public int nextMines = 0;
    public boolean isFlagged = false;
    private int x;
    private int y;
    public STATUS status = null;

    public Cell() {

    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected Cell(Parcel in) {
        isMine = in.readByte() != 0;
        nextMines = in.readInt();
        isFlagged = in.readByte() != 0;
        x = in.readInt();
        y = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isMine ? 1 : 0));
        dest.writeInt(nextMines);
        dest.writeByte((byte) (isFlagged ? 1 : 0));
        dest.writeInt(x);
        dest.writeInt(y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cell> CREATOR = new Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public enum STATUS {
        OPEN, CLOSE
    }

}
