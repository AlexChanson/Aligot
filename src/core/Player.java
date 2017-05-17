package core;

import physics.RigidBody;

import java.util.ArrayList;

public class Player extends GameObject{
    private String name;
    private Weapon currentWeapon;
    private int health, maxHealth;
    private ArrayList<Item> inventory;
    private double position, rotation;
    private boolean looking_right;
    private boolean on_ground;

    public Player(RigidBody rigidBody, String texture, String name, int health) {
        super(rigidBody, texture);
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        inventory = new ArrayList<>();
        looking_right = true;
        on_ground = false;
    }

    public boolean isOn_ground() {
        return on_ground;
    }

    public void setOn_ground(boolean on_ground) {
        this.on_ground = on_ground;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean isLooking_right() {
        return looking_right;
    }

    public void setLooking_right(boolean looking_right) {
        this.looking_right = looking_right;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
}
