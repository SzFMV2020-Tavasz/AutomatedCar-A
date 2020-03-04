package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class PedalPosition {

    private double gasPedalValue;
    private boolean gasPedalSwitch;//true -> down, false -> up
    private double breakPedalValue;
    private boolean breakPedalSwitch;

    public void gasPedalDown(){
        gasPedalSwitch=true;
        gasPedalValueIncrease();
    }

    private void gasPedalValueIncrease(){
        Thread valueChangeThread = new Thread(()->{
            int counter = 0;
            while(gasPedalSwitch && gasPedalValue<100.0)
            {
                counter++;
                if(validRange(gasPedalValue+increaseNumber(counter))) {
                    gasPedalValue += increaseNumber(counter);
                }
                else{
                    gasPedalValue=100;
                }
                Sleep();
            }
        });
        valueChangeThread.start();
    }

    public void gasPedalUp(){
        gasPedalSwitch = false;
        gasPedalValueDecrease();;
    }

    private void gasPedalValueDecrease(){
        Thread valueChangeThread = new Thread(()->{
            int counter = 0;
            while(gasPedalSwitch && gasPedalValue>=0.0)
            {
                counter++;
                if(validRange(gasPedalValue-increaseNumber(counter))) {
                    gasPedalValue -= increaseNumber(counter);
                }
                else{
                    gasPedalValue=0;
                }
                Sleep();
            }
        });
        valueChangeThread.start();
    }

    public void breakPedalDown(){
        breakPedalSwitch=true;
        breakPedalValueIncrease();
    }

    private void breakPedalValueIncrease(){
        Thread valueChangeThread = new Thread(()->{
            int counter = 0;
            while(breakPedalSwitch && breakPedalValue<100.0)
            {
                counter++;
                if(validRange(breakPedalValue+increaseNumber(counter))) {
                    breakPedalValue += increaseNumber(counter);
                }
                else{
                    breakPedalValue=100;
                }
                Sleep();
            }
        });
        valueChangeThread.start();
    }

    public void breakPedalUp(){
        breakPedalSwitch = false;
        breakPedalValueDecrease();
    }

    private void breakPedalValueDecrease(){
        Thread valueChangeThread = new Thread(()->{
            int counter = 0;
            while(!breakPedalSwitch && breakPedalValue>0.0)
            {
                counter++;
                if(validRange(breakPedalValue+increaseNumber(counter))) {
                    breakPedalValue += increaseNumber(counter);
                }
                else{
                    breakPedalValue=100;
                }
                Sleep();
            }
        });
        valueChangeThread.start();
    }

    private int increaseNumber(int counter){
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

    private boolean validRange(double value){
        if(value>=0.0 && value <=100.0){
            return true;
        }
        else{
            return false;
        }
    }
};
