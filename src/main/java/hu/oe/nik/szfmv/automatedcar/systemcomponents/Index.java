package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Index {
    private VirtualFunctionBus virtualFunctionBus;
    private IndexStatus status;

    public enum IndexStatus {
        RIGHT,
        LEFT,
        NONE
    }


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
}
