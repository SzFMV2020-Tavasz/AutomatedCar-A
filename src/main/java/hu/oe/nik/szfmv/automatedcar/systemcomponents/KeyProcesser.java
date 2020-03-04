package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class KeyProcesser {

    private PedalPosition pedalPos = new PedalPosition();

    public int KeyPressed(int keyCode)
    {
        return keyCode;
    }

    public int KeyReleased(int keyCode)
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
}
