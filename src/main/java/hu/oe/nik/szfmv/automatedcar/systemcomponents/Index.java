package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Index {
    public enum IndexStatus{
        RIGHT,
        LEFT,
        NONE
    }

    private VirtualFunctionBus virtualFunctionBus;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus){
        this.virtualFunctionBus=virtualFunctionBus;
    }

    private IndexStatus status;

    public void setStatusRight(){
        if(status==IndexStatus.RIGHT){
            status=IndexStatus.NONE;
        }
        else{
        status=IndexStatus.RIGHT;
        }
        virtualFunctionBus.guiInputPacket.setIndexStatus(status);
    }

    public void setStatsLeft(){
        if(status==IndexStatus.LEFT){
            status=IndexStatus.NONE;
        }
        else{
            status=IndexStatus.LEFT;
        }
        virtualFunctionBus.guiInputPacket.setIndexStatus(status);
    }
}
