package rvt.Interface_In_A_Box;

import java.util.ArrayList;

public class Box implements Packable {
    private double maxWeigth;
    private ArrayList<Packable> items;

    public Box(double maxWeigth) {
        this.maxWeigth = maxWeigth;
        this.items = new ArrayList<>();
    }

    public void add(Packable item) {
        // Only add if it doesn't exceed capacity
        if (this.weight() + item.weight() <= this.maxWeigth) {
            this.items.add(item);
        }
    }

    @Override
    public double weight() {
        double totalWeight = 0;
        for (Packable item : items) {
            totalWeight += item.weight();
        }
        return totalWeight;
    }

    @Override
    public String toString() {
        return "Box: " + items.size() + " items, total weight " + this.weight() + " kg";
    }
}



