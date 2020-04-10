package hu.oe.nik.szfmv.automatedcar.systemcomponents;


import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HMIKeyListener {

    KeyProcesser keyProcesser = new KeyProcesser();

    java.awt.event.KeyListener listen = new java.awt.event.KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W:
                    keyProcesser.gasPedalReleased();

                    break;

                case KeyEvent.VK_S:
                    keyProcesser.breakPedalReleased();
                    break;

                case KeyEvent.VK_D:
                    keyProcesser.steeringReleased();
                    break;

                case KeyEvent.VK_A:
                    keyProcesser.steeringReleased();
                    break;

                case KeyEvent.VK_0:
                    keyProcesser.zeroReleased();
                    break;

                case KeyEvent.VK_CONTROL:
                    keyProcesser.controlReleased();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_H:
                    keyProcesser.helpButtonPressed();
                    break;

                case KeyEvent.VK_W:
                    keyProcesser.gasPedalPressed();
                    break;

                case KeyEvent.VK_S:
                    keyProcesser.breakPedalPressed();
                    break;

                case KeyEvent.VK_D:
                    keyProcesser.steeringRightPressed();
                    break;

                case KeyEvent.VK_A:
                    keyProcesser.steeringLeftPressed();
                    break;

                case KeyEvent.VK_Q:
                    keyProcesser.indexLeft();
                    break;

                case KeyEvent.VK_E:
                    keyProcesser.indexRight();
                    break;

                case KeyEvent.VK_K:
                    keyProcesser.lowerShift();
                    break;

                case KeyEvent.VK_L:
                    keyProcesser.growShift();
                    break;

                case KeyEvent.VK_I:
                    keyProcesser.decreaseAccSpeed();
                    break;

                case KeyEvent.VK_O:
                    keyProcesser.increaseAccSpeed();
                    break;

                case KeyEvent.VK_R:
                    keyProcesser.turnAccSwitch();
                    break;

                case KeyEvent.VK_J:
                    keyProcesser.turnLaneKeepingSwitch();
                    break;

                case KeyEvent.VK_P:
                    keyProcesser.turnParkingPilotSwitch();
                    break;

                case KeyEvent.VK_T:
                    keyProcesser.turnAccDistance();
                    break;


                case KeyEvent.VK_0:
                    keyProcesser.zeroPressed();
                    break;

                case KeyEvent.VK_CONTROL:
                    keyProcesser.controlPressed();
                    break;
                default:
                    break;
            }

        }
    };

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        keyProcesser.setVirtualFunctionBus(virtualFunctionBus);
    }

    public KeyListener getHMIListener() {

        return listen;
    }
}
