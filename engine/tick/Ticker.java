package engine.tick;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Ticker implements Runnable {
    private ArrayList<Tickable> objects = new ArrayList<Tickable>();
    private Thread tickThread;
    private boolean loop = false;

    public Ticker() {
        this.loop = true;
        this.tickThread = new Thread(this);
        this.tickThread.start();
    }

    public void add(Tickable e) {
        objects.add(e);
    }

    @Override
    public void run() {
        int FPS = 60;
        double nano = TimeUnit.SECONDS.toNanos(1);
        double revo = nano / FPS;
        double dt = 0;
        long lastTime = System.nanoTime();
        long currentTime = System.nanoTime();
        long t;
        try {
            while (loop) {
                currentTime = System.nanoTime();
                dt += ((currentTime - lastTime) / nano) * FPS;
                if (dt > 1) {
                    dt--;
                } else {
                    try {
                        t = (long) (revo - (currentTime - lastTime));
                        if (t > 0) {
                            TimeUnit.NANOSECONDS.sleep(t);
                        }
                    } catch (Exception e) {
                        loop = false;
                    }
                }
                for (Tickable tickable : objects) {
                    tickable.tick(dt);
                }
                lastTime = currentTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}