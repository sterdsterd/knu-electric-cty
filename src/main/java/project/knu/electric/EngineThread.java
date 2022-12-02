package project.knu.electric;

import javax.swing.*;

public class EngineThread implements Runnable {

    private final int CYCLE_DURATION = 30 * 1000;
    private JSlider weightSensor;
    private JTextArea log;

    public EngineThread(JSlider weightSensor, JTextArea log) {
        this.weightSensor = weightSensor;
        this.log = log;
    }

    @Override
    public void run() {
        while (true) {
            try {
                log.setText(log.getText().trim() + "\nWeight Sensor On");
                weightSensor.setEnabled(true);

                Thread.sleep(CYCLE_DURATION);

                log.setText(log.getText().trim() + "\nWeight Sensor Off");
                weightSensor.setEnabled(false);

                Thread.sleep(CYCLE_DURATION);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
