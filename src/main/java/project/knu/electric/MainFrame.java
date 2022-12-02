package project.knu.electric;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class MainFrame extends JFrame {
    private final double MAXIMUM_WEIGHT = 100.0;
    private Container frame = this.getContentPane();
    private JPanel leftPanel;

    private JSlider slider;
    private JLabel weightLabel;
    private JButton startEngineButton, stopEngineButton;

    private JTextArea logArea;
    private Thread thread;

    public MainFrame(String title) {
        super(title);
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();

        this.setVisible(true);
    }

    private void initUI() {
        frame.setLayout(new GridLayout(1, 2));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        JLabel title = new JLabel("최태영 바보", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("GangwonEduPower", Font.BOLD, 38));
        upperPanel.add(title);

        JLabel subtitle = new JLabel("이율원 천재", SwingConstants.CENTER);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 30, 0));
        upperPanel.add(subtitle);

        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout());
        startEngineButton = new JButton("Start Engine");
        startEngineButton.setMargin(new Insets(10, 10, 10, 10));
        startEngineButton.addActionListener(e -> {
            startEngine();
        });
        stopEngineButton = new JButton("Stop Engine");
        stopEngineButton.setMargin(new Insets(10, 10, 10, 10));
        stopEngineButton.setEnabled(false);
        stopEngineButton.addActionListener(e -> {
            stopEngine();
        });
        buttonGroup.add(startEngineButton);
        buttonGroup.add(stopEngineButton);
        upperPanel.add(buttonGroup);

        weightLabel = new JLabel(weightFormatter(0), SwingConstants.CENTER);
        weightLabel.setFont(new Font("SFMono Nerd Font", Font.BOLD, 28));
        weightLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        leftPanel.add(weightLabel, BorderLayout.SOUTH);


        leftPanel.add(upperPanel, BorderLayout.NORTH);

        getNewSlider();
        slider.setEnabled(false);

        frame.add(leftPanel);

        logArea = new JTextArea();
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea));
    }

    public void startEngine() {
        getNewSlider();
        logArea.setText(logArea.getText().trim() + "\nEngine Started");
        slider.setEnabled(true);

        stopEngineButton.setEnabled(true);
        startEngineButton.setEnabled(false);

        thread = new Thread(new engineThread(slider, logArea));
        thread.start();
    }

    public void stopEngine() {
        logArea.setText(logArea.getText().trim() + "\nEngine Stopped");
        slider.setEnabled(false);
        startEngineButton.setEnabled(true);
        stopEngineButton.setEnabled(false);
        thread.interrupt();
    }

    public void getNewSlider() {
        if (slider != null)
            leftPanel.remove(slider);
        weightLabel.setText(weightFormatter(0));
        weightLabel.setForeground(Color.black);
        slider = new JSlider();
        slider.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        slider.setMinimum(0);
        slider.setMaximum(15000);
        slider.addChangeListener(e -> {
            weightLabel.setText(weightFormatter(slider.getValue()));

            if (slider.getValue() >= MAXIMUM_WEIGHT * 100) {
                logArea.setText(logArea.getText().trim() + "\nWeight Over " + weightFormatter((int) MAXIMUM_WEIGHT * 100) + " Detected");
                weightLabel.setForeground(Color.decode("#FF3B30"));
                stopEngine();
            }
        });
        slider.setMinorTickSpacing(100);
        slider.setMajorTickSpacing(1000);
        slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> sliderLabel = new Hashtable<>();
        for (int i = 0; i <= 15000; i += 5000) {
            sliderLabel.put(i, new JLabel(String.valueOf(i / 100)));
        }
        sliderLabel.put((int) (MAXIMUM_WEIGHT * 100), new JLabel("<html><font color='#FF3B30'><b>" + (int) (MAXIMUM_WEIGHT) + "</b></font></html>"));
        slider.setLabelTable(sliderLabel);
        slider.setPaintLabels(true);

        leftPanel.add(slider, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private String weightFormatter(int weight) {
        String integer = String.format("%03d", weight / 100);
        String fractional = String.format("%02d", weight % 100);

        return integer + "." + fractional + "kg";
    }


}
