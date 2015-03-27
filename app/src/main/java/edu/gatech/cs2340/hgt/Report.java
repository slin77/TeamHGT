package edu.gatech.cs2340.hgt;

/**
 * Created by root on 3/12/15.
 */
public class Report {
    String itemName;
    String price;
    String location;
    String reporter;

    public Report(String price, String location, String itemName, String reporter) {
        this.price = price;
        this.location = location;
        this.itemName = itemName;
        this.reporter = reporter;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;

        Report report = (Report) o;

        if (!location.equals(report.location)) return false;
        if (!itemName.equals(report.itemName)) return false;
        if (!price.equals(report.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemName.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "name: " + itemName + "\nprice: " + price + " from: " + reporter + "\n";
    }
}
