package TypingTest;

import Utils.Console;

public class Timer {

    private final long durationMillis;
    private final TimeoutListener listener;

    private volatile boolean running = false;
    private Thread timerThread;

    private long startTimeMillis;
    private long stopTimeMillis;

    public interface TimeoutListener {
        void onTimeout();
    }

    public Timer(long durationMillis, TimeoutListener listener) {
        this.durationMillis = durationMillis;
        this.listener = listener;
    }

    public void start() {
        if (running) return;



        running = true;

        startTimeMillis = System.currentTimeMillis();
        stopTimeMillis = 0;
        timerThread = new Thread(() -> {
            try {
                Thread.sleep(durationMillis);
                if (running) {
                    listener.onTimeout();
                }
            } catch (InterruptedException e) {
                // Do nothing . stopped intentionally
            }

        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    public void stop() {
        running = false;
        stopTimeMillis = System.currentTimeMillis();
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public boolean isRunning() {
        return running;
    }

    public long getElapsedTime() {
        if (running) {
            return System.currentTimeMillis() - startTimeMillis;
        } else if (startTimeMillis != 0 && stopTimeMillis != 0) {
            return stopTimeMillis - startTimeMillis;
        } else {
            return 0;
        }
    }
}
