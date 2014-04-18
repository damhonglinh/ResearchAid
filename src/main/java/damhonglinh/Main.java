package damhonglinh;

import damhonglinh.view.MainFrame;

import javax.swing.*;

/**
 * User: Dam Linh
 * Date: 17/04/14
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        MainFrame mf = new MainFrame();
    }
}
