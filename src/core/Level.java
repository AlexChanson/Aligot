package core;

import physics.RigidBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class Level {
    private String name, info, bgTexture;
    private HashSet<RigidBody> elements = new HashSet<>();
    private ArrayList<Spawn> spawns = new ArrayList<>();

    public Level(String name) {
        this.name = name;
    }

    public Level(String name, String info, String bgTexture) {
        this.name = name;
        this.info = info;
        this.bgTexture = bgTexture;
    }

    public Level(String name, String info, String bgTexture, HashSet<RigidBody> elements, ArrayList<Spawn> spawns) {
        this.name = name;
        this.info = info;
        this.bgTexture = bgTexture;
        this.elements = elements;
        this.spawns = spawns;
    }

    public void addElement(RigidBody... elements){
        Collections.addAll(this.elements, elements);
    }

    public void addSpawns(Spawn... spawns){
        Collections.addAll(this.spawns, spawns);
    }

    public String getName() {
        return name;
    }

    public HashSet<RigidBody> getElements() {
        return elements;
    }

    public void setElements(HashSet<RigidBody> elements) {
        this.elements = elements;
    }

    public ArrayList<Spawn> getSpawns() {
        return spawns;
    }

    public void setSpawns(ArrayList<Spawn> spawns) {
        this.spawns = spawns;
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
}
