package hu.oe.nik.szfmv.automatedcar.systemcomponents;



import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class HMIKeyListener {

    KeyProcesser Processer = new KeyProcesser();

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus){
        Processer.setVirtualFunctionBus(virtualFunctionBus);
    }

    java.awt.event.KeyListener listen = new java.awt.event.KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch(keyCode)
            {
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
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();
            switch(keyCode)
            {
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
                    Processer.MinusSpeedValue();
                    break;

                case KeyEvent.VK_O:
                    Processer.PlusSpeedValue();
                    break;


                case KeyEvent.VK_T:
                   Processer.IsOnPressed();
                    break;


                case KeyEvent.VK_CONTROL:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_0:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_J:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_P:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_U:
                    Processer.FollowerGapSetter();
                    Processer.inputPacket.setAccFollowerGap(Processer.ChangeReturnFollowerGapSetter());
                    break;
            }

        }
    };

    public KeyListener getHMIListener()
    {

        return listen;
    }
}
