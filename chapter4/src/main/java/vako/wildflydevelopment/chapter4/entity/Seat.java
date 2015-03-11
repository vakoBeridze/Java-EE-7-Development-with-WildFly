package vako.wildflydevelopment.chapter4.entity;

/**
 * Created by vako on 3/11/15.
 */
public class Seat {
    private int id;

    private String name;

    private int price;

    private boolean booked;

    public Seat(int id, String name, int price) {
        this(id, name, price, false);
    }

    private Seat(int id, String name, int price, boolean booked) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.booked = booked;
    }

    public Seat getBookedSeat() {
        return new Seat(getId(), getName(), getPrice(), true);
    }

    public boolean isBooked() {
        return booked;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", name=" + name + ", price=" + price + ", booked=" + booked + "]";
    }

}
