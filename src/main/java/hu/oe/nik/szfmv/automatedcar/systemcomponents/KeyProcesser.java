package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class KeyProcesser {

    private PedalPosition pedalPos = new PedalPosition();

    private Index index = new Index();

    public int KeyPressed(int keyCode)
    {
        return keyCode;
    }

    public void gasPedalPressed(){
        pedalPos.gasPedalDown();
    }

    public void gasPedalReleased(){
        pedalPos.gasPedalUp();
    }

    public void breakPedalPressed(){pedalPos.breakPedalDown();}

    public void breakPedalReleased(){pedalPos.breakPedalUp();}

    public void steeringLeftPressed(){pedalPos.startSteeringLeft();}

    public void steeringRightPressed(){pedalPos.startSteeringRight();}

    public void steeringReleased(){pedalPos.steeringWheelReleased();}

    public void indexRight(){index.setStatusRight();}

    public void indexLeft(){index.setStatsLeft();}
}
