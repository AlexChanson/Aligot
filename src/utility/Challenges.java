package utility;


import core.model.Level;

import java.util.ArrayList;

public class Challenges {
    private static final ArrayList<Level> challenges;

    static {
        challenges = Loader.loadAll(Level.class, "challenges");
    }

    public static ArrayList<Level> get(){
            return new ArrayList<>(challenges);
    }
}
