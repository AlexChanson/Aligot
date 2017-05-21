package graphics;

import com.google.gson.annotations.Expose;
import utility.Loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Animation {
    private List<String> sprites;
    private List<Double> durations;
    private String name;

    private static ArrayList<Animation> animations;

    @Expose(serialize = false, deserialize = false)
    private double time = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean playing = false;
    @Expose(serialize = false, deserialize = false)
    private double totalDuration = 0;
    @Expose(serialize = false, deserialize = false)
    private boolean loop = false;
    @Expose(serialize = false, deserialize = false)
    private ArrayList<Texture> textures = new ArrayList<>();

    static {
        animations = Loader.loadAll(Animation.class, "animations");

        Iterator<Animation> it = animations.iterator();
        Iterator<String> it_sprt;

        String sprt;
        Animation current;

        while (it.hasNext()) {
            current = it.next();
            it_sprt = current.sprites.iterator();

            while (it_sprt.hasNext()) {
                sprt = it_sprt.next();
                current.textures.add(new Texture(sprt));
            }
        }
    }

    public static Animation getAnimation(String name) {
        Animation ret;

        for (Animation animation : animations) {
            if (animation.getName().equals(name)) {
                ret = new Animation();

                ret.sprites = animation.sprites;
                ret.durations = animation.durations;
                ret.textures = animation.textures;
                ret.name = name;

                return ret;
            }
        }

        return null;
    }

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

    public void restart() {
        this.playing = true;
        this.time = 0;
    }

    public void reset() {
        this.playing = false;
        this.time = 0;
    }

    public void draw(float posX, float posY, float width, float height, float rotate, float scale) {
        Texture text = getCurrentTexture();

        Window.drawTexture(this.getCurrentTexture(), posX, posY, width, height, rotate, scale, 0, 0, text.getWidth(), text.getHeight(), 255, 255, 255);
    }

    public String getName() {
        return this.name;
    }

    public static void passTimeForAll(double dt) {
        Iterator<Animation> it = animations.iterator();

        while (it.hasNext()) {
            it.next().passTime(dt);
        }
    }

    public Texture getCurrentTexture() {
        Iterator<Double> it_dur = this.durations.iterator();
        Iterator<Texture> it_text = this.textures.iterator();
        Texture text = null;
        double t = 0;

        while (it_dur.hasNext() && it_text.hasNext() && t < this.time) {
            t += it_dur.next();
            text = it_text.next();
        }

        return text;
    }

    public boolean isFirstFrame() {
        return this.time < this.durations.get(0);
    }

    public boolean isLastFrame() {
        return getTotalDuration() - this.durations.get(this.durations.size() - 1) <= this.time;
    }
}