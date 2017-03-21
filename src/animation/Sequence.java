package animation;

import java.awt.Image;
import java.lang.Iterable;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;


public class Sequence implements Iterable<Image> {
    private ArrayList<HashMap.SimpleEntry<Image, Double>> frames;

    public Sequence() {
        this.frames = new ArrayList();
    }

    /**
     * @param index
     * @param img
     * @param duration
     */
    public void addFrame(int index, Image img, double duration) {
        this.frames.add(index, this.makeSimpleEntry(img, duration));
    }

    public void addFrame(Image img, double duration) {
        this.frames.add(this.makeSimpleEntry(img, duration));
    }

    /**
     * @param index
     */
    public void removeFrame(int index) {
        this.frames.remove(index);
    }

    /**
     * @param index
     * @param img
     * @param duration
     */
    public void setFrame(int index, Image img, double duration) {
        this.frames.set(index, this.makeSimpleEntry(img, duration));
    }

    /**
     * @return
     */
    public int numOfFrames() {
        return this.frames.size();
    }

    /**
     * @param index
     * @param img
     */
    public void setFrameImg(int index, Image img) {
        this.frames.set(index, makeSimpleEntry(img, this.frames.get(index).getValue()));
    }

    /**
     * @param index
     * @param duration
     */
    public void setFrameDuration(int index, double duration) {
        this.frames.get(index).setValue(duration);
    }

    @Override
    public Iterator<Image> iterator() {
        return new Iterator<Image>() {
            private int index = -1;

            @Override
            public boolean hasNext() {
                return index + 1 < frames.size();
            }

            @Override
            public Image next() {
                index++;

                if (frames.size() == index) {
                    return null;
                }

                return frames.get(index).getKey();
            }
        };
    }

    private HashMap.SimpleEntry<Image, Double> makeSimpleEntry(Image img, double duration) {
        return new HashMap.SimpleEntry<Image, Double>(img, duration);
    }
}
