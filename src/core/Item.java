package core;

public abstract class Item {
    protected String name, icon;

    public Item(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public abstract void use(Player user);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
