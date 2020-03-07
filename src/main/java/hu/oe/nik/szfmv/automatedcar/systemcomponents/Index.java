package hu.oe.nik.szfmv.automatedcar.systemcomponents;
enum IndexStatus{
    RIGHT,
    LEFT,
    NONE
}
public class Index {
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
