package edu.gatech.cs2340.hgt;


public class Sale {
    private String username;
    private String itemName;
    private String thresholdPrice;
    private String timestamp;
    private String imageUrl;

    public Sale(String itemName, String username, String timestamp, String thresholdPrice) {
        this.itemName = itemName;
        this.username = username;
        this.timestamp = timestamp;
        this.thresholdPrice = thresholdPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getThresholdPrice() {
        return thresholdPrice;
    }

    public void setThresholdPrice(String thresholdPrice) {
        this.thresholdPrice = thresholdPrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;

        Sale sale = (Sale) o;

        if (!itemName.equals(sale.itemName)) return false;
        if (!thresholdPrice.equals(sale.thresholdPrice)) return false;
        return username.equals(sale.username);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + itemName.hashCode();
        result = 31 * result + thresholdPrice.hashCode();
        return result;
    }
}
