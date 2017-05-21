package graphics;

import com.google.gson.annotations.Expose;
import utility.Loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class Animation {
    private final static Logger LOGGER = Logger.getLogger(Animation.class.getName());

    private List<String> sprites;
    private List<Double> durations;
    private String name;

    @Expose(serialize = false, deserialize = false)
    private int inUse = 10; // timer for static despawn
    @Expose(serialize = false, deserialize = false)
    private boolean prototype =  false; // prevent original animation from despawning

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
            current.prototype = true;
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
                ret.inUse = 10;
                ret.prototype = false;

                animations.add(ret);
                return ret;
            }
        }

        return null;
    }

    public void passTime(double dt) {
        if (this.playing) {
            System.out.println("Texture passTime");
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

    public double getTotalDuration() {
        if (this.totalDuration == 0) {

            for (Double duration : this.durations) {
                this.totalDuration += duration;
            }
        }
        inUse = 10;

        return this.totalDuration;
    }

    public void setLooping() {
        inUse = 10;
        this.loop = true;
    }

    public void unsetLooping() {
        inUse = 10;
        this.loop = false;
    }

    public boolean isLooping() {
        inUse = 10;
        return this.loop;
    }

    public void start() {
        inUse = 10;
        this.playing = true;
    }

    public void stop() {
        inUse = 10;
        this.playing = false;
    }

    public void restart() {
        inUse = 10;
        this.playing = true;
        this.time = 0;
    }

    public void reset() {
        inUse = 10;
        this.playing = false;
        this.time = 0;
    }

    public void draw(float posX, float posY, float width, float height, float rotate, float scale) {
        inUse = 10;
        Texture text = getCurrentTexture();
        if (text == null){
            LOGGER.warning("Texture frame not found");
            return;
        }

        Window.drawTexture(this.getCurrentTexture(), posX, posY, width, height, rotate, scale, 0, 0, text.getWidth(), text.getHeight(), 255, 255, 255);
    }

    public String getName() {
        inUse = 10;
        return this.name;
    }

    public static void passTimeForAll(double dt) {
        for (Animation animation : animations) {
            animation.inUse -= 1;
            animation.passTime(dt);
        }
        purgeAnimations();
    }

    public Texture getCurrentTexture() {
        inUse = 10;
        Iterator<Double> it_dur = this.durations.iterator();
        Iterator<Texture> it_text = this.textures.iterator();
        Texture text = null;
        double t = 0;

        while (it_dur.hasNext() && it_text.hasNext() && t <= this.time) {
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

    private static void purgeAnimations(){
        for (Animation animation: animations){
            System.out.println(animation);
            System.out.println(animation.getName());
            System.out.println(animation.inUse);
        }

        animations.removeIf((animation -> (animation.inUse <= 0) && !animation.prototype));
    }
}