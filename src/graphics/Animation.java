package graphics;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.util.Iterator;
import java.util.List;

class Animation {
    private List<String> sprites;
    private List<Double> durations;

    @Expose(serialize = false, deserialize = false)
    private double time = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean playing = false;
    @Expose(serialize = false, deserialize = false)
    private double totalDuration = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean loop = false;

    private static final String animationsDir = System.getProperty("user.dir") + File.separator + "ressources" + File.separator + "animations" + File.separator;

    public static Animation load(String res) {
        Gson gson = new Gson();

        return gson.fromJson(animationsDir + res + ".json", Animation.class);
    }

    public void pastTime(double dt) {
        if (this.playing) {
            this.time += dt;

            if (loop) {
                time %= getTotalDuration();
            }
            else if (this.time >= getTotalDuration()) {
                this.time = getTotalDuration();
                this.playing = false;
            }
        }
    }

    private double getTotalDuration() {
        if (this.totalDuration == 0) {
            Iterator<Double> it = this.durations.iterator();

            while (it.hasNext()) {
                this.totalDuration += it.next();
            }
        }

        return this.totalDuration;
    }

    public void setLooping() {
        this.loop = true;
    }

    public void unsetLooping() {
        this.loop = false;
    }

    public boolean isLooping() {
        return this.loop;
    }

    public void start() {
        this.playing = true;
    }

    public void stop() {
        this.playing = false;
    }

    public void reset() {
        this.playing = false;
        this.time = 0;
    }

    public void draw(int posX, int posY, int width, int height, float rotate) {
        Iterator<Double> it_dur = this.durations.iterator();
        Iterator<String> it_sprt = this.sprites.iterator();
        double t = 0;
        String sprt;
        boolean cont = true;

        while (it_dur.hasNext() && it_sprt.hasNext() && cont) {
            t += it_dur.next();
            sprt = it_sprt.next();

            if (t > this.time) {
                cont = false;
                Window.drawSprite(sprt, posX, posY, width, height, rotate);
            }
        }
    }
}