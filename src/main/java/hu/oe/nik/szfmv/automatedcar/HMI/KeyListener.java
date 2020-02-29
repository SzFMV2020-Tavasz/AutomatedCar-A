package hu.oe.nik.szfmv.automatedcar.HMI;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.SamplePacket;

import java.awt.event.KeyEvent;

public class KeyListener {

    KeyProcesser Processer = new KeyProcesser();

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
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_S:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_D:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_A:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_Q:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_E:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_K:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_L:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_I:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_O:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_T:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_CONTROL:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_0:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_J:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_P:
                    Processer.KeyReleased(keyCode);
                    break;

                case KeyEvent.VK_U:
                    Processer.KeyReleased(keyCode);
                    break;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode)
            {
                case KeyEvent.VK_W:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_S:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_D:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_A:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_Q:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_E:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_K:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_L:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_I:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_O:
                    Processer.KeyPressed(keyCode);
                    break;

                case KeyEvent.VK_T:
                    Processer.KeyPressed(keyCode);
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
                    Processer.KeyPressed(keyCode);
                    break;
            }

        }
    };


}
