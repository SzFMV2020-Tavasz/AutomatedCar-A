package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class PedalPosition {

    private double gasPedalValue;
    private boolean gasPedalSwitch;//true -> down, false -> up

    public void gasPedalDown(){
        gasPedalSwitch=true;
        gasPedalValueIncrease();
    }
    private void gasPedalValueIncrease(){
        Thread increaseValueThread = new Thread(()->{
            int counter = 0;
            while(gasPedalSwitch && gasPedalValue<=100.0)
            {
                counter++;
                gasPedalValue+=increaseNunber(counter);
                Sleep();

            }
        });
        increaseValueThread.start();
    }

    public void gasPedalUp(){
        gasPedalSwitch = false;
        gasPedalValueDecrease();;
    }

    private void gasPedalValueDecrease(){
        Thread decreaseValueThread = new Thread(()->{
            int counter = 0;
            while(gasPedalSwitch && gasPedalValue>=100.0)
            {
                counter++;
                gasPedalValue-=increaseNunber(counter);
                Sleep();
            }
        });
        decreaseValueThread.start();
    }

    private int increaseNunber(int counter){
        if(counter <5){
            return 5;
        }
        else if(counter < 10){
            return 10;
        }
        else{
            return 15;
        }
    }

    private void Sleep(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
};
