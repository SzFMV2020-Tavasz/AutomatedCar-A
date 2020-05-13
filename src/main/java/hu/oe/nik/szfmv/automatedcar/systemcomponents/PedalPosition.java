package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class PedalPosition {

    private VirtualFunctionBus virtualFunctionBus;
    private double gasPedalValue;
    private boolean gasPedalSwitch;
    private double breakPedalValue;
    private boolean breakPedalSwitch;
    private double steeringWheelValue;
    private boolean steeringWheelRight;
    private boolean steeringWheelLeft;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void startSteeringRight() {
        if (!steeringWheelRight) {
            steeringWheelRight = true;
            steeringWheelLeft = false;
            steeringRight();
        }
    }

    private void steeringRight() {
        if (steeringWheelValue < 0) {
            steeringWheelReleased();
        } else {
            Thread valueChangeThread = new Thread(() -> {
                int counter = 0;
                while (steeringWheelRight && steeringWheelValue < 180.0) {

                    if (steeringWheelValueValidRange(steeringWheelValue + increaseNumber(counter))) {
                        steeringWheelValue += increaseNumber(counter);
                    } else {
                        steeringWheelValue = 180;
                    }
                    virtualFunctionBus.manualCarControlPacket.setSteeringWheelValue(steeringWheelValue);
                    virtualFunctionBus.guiInputPacket.setSteeringWheelValue(steeringWheelValue);
                    counter++;
                    sleep();
                }
            });
            valueChangeThread.start();
        }
    }

    public void startSteeringLeft() {
        if (!steeringWheelLeft) {
            steeringWheelLeft = true;
            steeringWheelRight = false;
            steeringLeft();
        }
    }

    private void steeringLeft() {
        if (steeringWheelValue > 0) {
            steeringWheelReleased();
        } else {
            Thread valueChangeThread = new Thread(() -> {
                int counter = 0;
                while (steeringWheelLeft && steeringWheelValue > -180.0) {

                    if (steeringWheelValueValidRange(steeringWheelValue + increaseNumber(counter))) {
                        steeringWheelValue -= increaseNumber(counter);
                    } else {
                        steeringWheelValue = -180;
                    }
                    virtualFunctionBus.manualCarControlPacket.setSteeringWheelValue(steeringWheelValue);
                    virtualFunctionBus.guiInputPacket.setSteeringWheelValue(steeringWheelValue);
                    counter++;
                    sleep();
                }
            });
            valueChangeThread.start();
        }
    }

    public void steeringWheelReleased() {
        steeringWheelRight = false;
        steeringWheelLeft = false;
        Thread valueChangeThread = new Thread(() -> {
            int counter = 0;
            while (!steeringWheelRight && !steeringWheelLeft && !(steeringWheelValue == 0.0)) {
                counter++;
                steeringWheelValue += steeringWheelToZero(steeringWheelValue, counter);
                virtualFunctionBus.manualCarControlPacket.setSteeringWheelValue(steeringWheelValue);
                virtualFunctionBus.guiInputPacket.setSteeringWheelValue(steeringWheelValue);
                sleep();
            }
        });
        valueChangeThread.start();
    }

    public void gasPedalDown() {
        if (!gasPedalSwitch) {
            gasPedalSwitch = true;
            gasPedalValueIncrease();
        }
    }

    private void gasPedalValueIncrease() {
        Thread valueChangeThread = new Thread(() -> {
            int counter = 0;
            while (gasPedalSwitch && gasPedalValue < 100.0) {

                if (pedalValueValidRange(gasPedalValue + increaseNumber(counter))) {
                    gasPedalValue += increaseNumber(counter);
                } else {
                    gasPedalValue = 100;
                }
                virtualFunctionBus.manualCarControlPacket.setGasPedalValue(gasPedalValue);
                virtualFunctionBus.guiInputPacket.setGasPedalValue(gasPedalValue);
                counter++;
                sleep();
            }
        });
        valueChangeThread.start();
    }

    public void gasPedalUp() {
        gasPedalSwitch = false;
        gasPedalValueDecrease();
    }

    private void gasPedalValueDecrease() {
        Thread valueChangeThread = new Thread(() -> {
            int counter = 0;
            while (!gasPedalSwitch && gasPedalValue >= 0.0) {

                if (pedalValueValidRange(gasPedalValue - increaseNumber(counter))) {
                    gasPedalValue -= increaseNumber(counter);
                } else {
                    gasPedalValue = 0;
                }
                virtualFunctionBus.manualCarControlPacket.setGasPedalValue(gasPedalValue);
                virtualFunctionBus.guiInputPacket.setGasPedalValue(gasPedalValue);
                counter++;
                sleep();
            }
        });
        valueChangeThread.start();
    }

    public void breakPedalDown() {
        if (!breakPedalSwitch) {
            breakPedalSwitch = true;
            breakPedalValueIncrease();
        }
    }

    private void breakPedalValueIncrease() {
        Thread valueChangeThread = new Thread(() -> {
            int counter = 0;
            while (breakPedalSwitch && breakPedalValue < 100.0) {

                if (pedalValueValidRange(breakPedalValue + increaseNumber(counter))) {
                    breakPedalValue += increaseNumber(counter);
                } else {
                    breakPedalValue = 100;
                }
                virtualFunctionBus.manualCarControlPacket.setBreakPedalValue(breakPedalValue);
                virtualFunctionBus.guiInputPacket.setBreakPedalValue(breakPedalValue);
                counter++;
                sleep();
            }
        });
        valueChangeThread.start();
    }

    public void breakPedalUp() {
        breakPedalSwitch = false;
        breakPedalValueDecrease();
    }

    private void breakPedalValueDecrease() {
        Thread valueChangeThread = new Thread(() -> {
            int counter = 0;
            while (!breakPedalSwitch && breakPedalValue > 0.0) {

                if (pedalValueValidRange(breakPedalValue - increaseNumber(counter))) {
                    breakPedalValue -= increaseNumber(counter);
                } else {
                    breakPedalValue = 0;
                }
                virtualFunctionBus.manualCarControlPacket.setBreakPedalValue(breakPedalValue);
                virtualFunctionBus.guiInputPacket.setBreakPedalValue(breakPedalValue);
                counter++;
                sleep();
            }
        });
        valueChangeThread.start();
    }

    private int increaseNumber(int counter) {
        int numberToIncrease;
        if (counter < 5) {
            numberToIncrease = 5;
        } else if (counter < 10) {
            numberToIncrease = 10;
        } else {
            numberToIncrease = 15;
        }
        return numberToIncrease;
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean pedalValueValidRange(double value) {
        if (value >= 0.0 && value <= 100.0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean steeringWheelValueValidRange(double value) {
        if (value >= -180.0 && value <= 180.0) {
            return true;
        } else {
            return false;
        }
    }

    private int steeringWheelToZero(double value, int counter) {

        int outputValue;
        if (counter < 5) {
            outputValue = 10;
        } else if (counter < 10) {
            outputValue = 20;
        } else {
            outputValue = 30;
        }

        if (value < 0) {
            if (value + outputValue > 0) {
                outputValue = (int) -value;
            } else {
                outputValue = outputValue;
            }
        } else {
            if (value - outputValue < 0) {
                outputValue = (int) -value;
            } else {
                outputValue = -outputValue;
            }
        }
        return outputValue;
    }
}
