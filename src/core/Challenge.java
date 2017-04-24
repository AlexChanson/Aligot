package core;

import java.util.HashSet;

public class Challenge extends Level{
    private int difficulty;
    private int shots;
    private HashSet<int[]> targets;

    public Challenge(String name, String info, String bgTexture, HashSet<Planet> planets, int[] mapSize, int difficulty, int shots, HashSet<int[]> targets) {
        super(name, info, bgTexture, planets, mapSize);
        this.difficulty = difficulty;
        this.shots = shots;
        this.targets = targets;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public HashSet<int[]> getTargets() {
        return targets;
    }

    public void setTargets(HashSet<int[]> targets) {
        this.targets = targets;
    }
}
