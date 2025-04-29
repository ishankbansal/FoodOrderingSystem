package model.common;

public class Item {
    private final String name;

    public Item(String name) {
        this.name = name.toLowerCase();
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Item)) return false;
        Item item = (Item) obj;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
