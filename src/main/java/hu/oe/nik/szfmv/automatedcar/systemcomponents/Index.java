package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Index {
    private VirtualFunctionBus virtualFunctionBus;
    private IndexStatus status;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void setStatusRight() {
        if (status == IndexStatus.RIGHT) {
            status = IndexStatus.NONE;
        } else {
            status = IndexStatus.RIGHT;
        }
        virtualFunctionBus.guiInputPacket.setIndexStatus(status);
    }

    public void setStatsLeft() {
        if (status == IndexStatus.LEFT) {
            status = IndexStatus.NONE;
        } else {
            status = IndexStatus.LEFT;
        }
        virtualFunctionBus.guiInputPacket.setIndexStatus(status);
    }

    /**Indicates whether the car is indexing and in what direction.
     * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
    public enum IndexStatus {

        /**Index is turned off.*/
        NONE,

        /**Index is turned on towards left.*/
        LEFT,

        /**Index is turned on towards right.*/
        RIGHT,

        /**Index is turned on on both sides.*/
        BOTH;

        @Deprecated(forRemoval = true)
        public int asValue() {
            switch (this) {
                case LEFT: return -1;
                case NONE: return 0;
                case RIGHT: return 1;
                default: throw new IllegalStateException("No integer representation for car index status: " + this);
            }
        }

        @Deprecated(forRemoval = true)
        public static IndexStatus fromValue(int value) {
            if (value < 0) {
                return LEFT;
            } else if (value > 0) {
                return RIGHT;
            } else {
                return NONE;
            }
        }
    }

}
