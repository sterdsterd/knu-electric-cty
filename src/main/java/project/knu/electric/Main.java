package project.knu.electric;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "최태영 바보");
        System.setProperty("apple.awt.application.appearance", "light");
        FlatLightLaf.setup();

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("SFMono Nerd Font", Font.PLAIN, 14));
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainFrame("최태영 바보");

    }
}
