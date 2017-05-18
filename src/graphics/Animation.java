package graphics;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Animation {
    private static String ressourcesFolderPath = System.getProperty("user.dir") + File.separator + "ressources" + File.separator + "assets" + File.separator;
    private List<String> sprites;
    private List<Double> durations;
    private ArrayList<Texture> textures = new ArrayList<>();

    @Expose(serialize = false, deserialize = false)
    private double time = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean playing = false;
    @Expose(serialize = false, deserialize = false)
    private double totalDuration = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean loop = false;

    public void passTime(double dt) {
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

    public void draw(int posX, int posY, int width, int height, float rotate, float scale) {
        if (this.textures.size() == 0) {
            Iterator<String> it_sprt = this.sprites.iterator();

            while (it_sprt.hasNext()) {
                this.textures.add(new Texture(ressourcesFolderPath + it_sprt.next()));
            }
        }

        Iterator<Double> it_dur = this.durations.iterator();
        Iterator<Texture> it_text = this.textures.iterator();
        Texture text;
        double t = 0;
        boolean cont = true;

        while (it_dur.hasNext() && it_text.hasNext() && cont) {
            t += it_dur.next();
            text = it_text.next();

            if (t > this.time) {
                cont = false;
                Window.drawTexture(text, posX, posY, width, height, rotate, scale, 0, 0, text.getWidth(), text.getHeight(), 255, 255, 255);
            }
        }
    }
}