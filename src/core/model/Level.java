package core.model;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Alexandre
 * The class holding the entire level
 */
public class Level {
    private String name, info, bgTexture;
    private HashSet<Planet> planets = new HashSet<>();
    private int[] mapSize;
    private Challenge challenge;

    public Level(String name) {
        this.name = name;
    }

    public Level(String name, String info, String bgTexture) {
        this.name = name;
        this.info = info;
        this.bgTexture = bgTexture;
    }

    public Level(String name, String info, String bgTexture, HashSet<Planet> planets, int[] mapSize) {
        this.name = name;
        this.info = info;
        this.bgTexture = bgTexture;
        this.planets = planets;
        this.mapSize = mapSize;
    }

    public Level(String name, String info, String bgTexture, HashSet<Planet> planets, int[] mapSize, Challenge challenge) {
        this.name = name;
        this.info = info;
        this.bgTexture = bgTexture;
        this.planets = planets;
        this.mapSize = mapSize;
        this.challenge = challenge;
    }

    public void addPlanet(Planet... elements){
        Collections.addAll(this.planets, elements);
    }

    public String getName() {
        return name;
    }

    public HashSet<Planet> getPlanets() {
        return planets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getBgTexture() {
        return bgTexture;
    }

    public void setBgTexture(String bgTexture) {
        this.bgTexture = bgTexture;
    }

    public void setMapSize(int[] mapSize) {
        this.mapSize = mapSize;
    }

    public int[] getMapSize() {
        return mapSize;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}
