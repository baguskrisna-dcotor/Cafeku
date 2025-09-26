
package com.example.cafeku;
public class MenuItem {
    private String name;
    private String price;
    private String imageFileName; // ganti nama biar jelas

    public MenuItem(String name, String price, String imageFileName) {
        this.name = name;
        this.price = price;
        this.imageFileName = imageFileName;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getImageFileName() { return imageFileName; }
}
