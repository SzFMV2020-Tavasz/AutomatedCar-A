package hu.oe.nik.szfmv.automatedcar.systemcomponents;


import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HMIKeyListener {

    KeyProcesser Processer = new KeyProcesser();

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        Processer.setVirtualFunctionBus(virtualFunctionBus);
    }

    java.awt.event.KeyListener listen = new java.awt.event.KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_W:
                    Processer.gasPedalReleased();

                    break;

                case KeyEvent.VK_S:
                    Processer.breakPedalReleased();
                    break;

                case KeyEvent.VK_D:
                    Processer.steeringReleased();
                    break;

                case KeyEvent.VK_A:
                    Processer.steeringReleased();
                    break;

                case KeyEvent.VK_0:
                    Processer.zeroReleased();
                    break;

                case KeyEvent.VK_CONTROL:
                    Processer.controlReleased();
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_H:
                    Processer.helpButtonPressed();
                    break;

                case KeyEvent.VK_W:
                    Processer.gasPedalPressed();
                    break;

                case KeyEvent.VK_S:
                    Processer.breakPedalPressed();
                    break;

                case KeyEvent.VK_D:
                    Processer.steeringRightPressed();
                    break;

                case KeyEvent.VK_A:
                    Processer.steeringLeftPressed();
                    break;

                case KeyEvent.VK_Q:
                    Processer.indexLeft();
                    break;

                case KeyEvent.VK_E:
                    Processer.indexRight();
                    break;

                case KeyEvent.VK_K:
                    Processer.LowerShift();
                    break;

                case KeyEvent.VK_L:
                    Processer.GrowShift();
                    break;

                case KeyEvent.VK_I:
                    Processer.decreaseAccSpeed();
                    break;

                case KeyEvent.VK_O:
                    Processer.increaseAccSpeed();
                    break;

                case KeyEvent.VK_R:
                    Processer.turnAccSwitch();
                    break;

                case KeyEvent.VK_J:
                    Processer.turnLaneKeepingSwitch();
                    break;

                case KeyEvent.VK_P:
                    Processer.turnParkingPilotSwitch();
                    break;

                case KeyEvent.VK_T:
                    Processer.turnAccDistance();
                    break;

                case KeyEvent.VK_0:
                    Processer.zeroPressed();
                    break;

                case KeyEvent.VK_CONTROL:
                    Processer.controlPressed();
                    break;
            }

        }
    };

    public KeyListener getHMIListener() {

        return listen;
    }
}
