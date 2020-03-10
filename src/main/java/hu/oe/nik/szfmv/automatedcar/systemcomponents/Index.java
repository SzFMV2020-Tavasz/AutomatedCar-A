package hu.oe.nik.szfmv.automatedcar.systemcomponents;
public class Index {
    public enum IndexStatus{
        RIGHT,
        LEFT,
        NONE
    }

    private IndexStatus status;

    public void setStatusRight(){
        if(status==IndexStatus.RIGHT){
            status=IndexStatus.NONE;
        }
        else{
        status=IndexStatus.RIGHT;
        }
    }

    public void setStatsLeft(){
        if(status==IndexStatus.LEFT){
            status=IndexStatus.NONE;
        }
        else{
            status=IndexStatus.LEFT;
        }
    }
}
