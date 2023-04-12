package com.app.minesweeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
  *代表地雷區中單個方格的類
 */
public class Cell implements Parcelable {
    public boolean isMine = false; //是否有地雷
    public int nextMines = 0; //周遭地雷數量
    public boolean isFlagged = false; //玩家是否插旗
    public int x; //座標x
    public int y; //座標y
    public CellStatus status = null; //方格狀態(開或合)

    /**
     * constructor
    */
    public Cell() {

    }
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Constructor for parceling.
     * @param in The parcel to read from.
     */
    protected Cell(Parcel in) {
        isMine = in.readByte() != 0;
        nextMines = in.readInt();
        isFlagged = in.readByte() != 0;
        x = in.readInt();
        y = in.readInt();
    } // read from Parcel

    /**
     * Writes this cell to a parcel.
     * @param dest The parcel to write to.
     * @param flags Additional flags for writing.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isMine ? 1 : 0));
        dest.writeInt(nextMines);
        dest.writeByte((byte) (isFlagged ? 1 : 0));
        dest.writeInt(x);
        dest.writeInt(y);
    }

    /**
     * Returns additional flags for this parcelable object.
     * @return The additional flags.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Creator for parceling.
     */
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

}
