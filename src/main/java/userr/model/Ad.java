package userr.model;


import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Ad {
    @Id
    private String id;
    private String price;
    private String title;
    private String description;
    private boolean appliances;
    private boolean clothes;
    private boolean cars;
    private boolean furniture;
    private String photoPath;
    private String vusername;

    public Ad( String id,String price,String title,String description, boolean appliances,
               boolean clothes, boolean cars, boolean furniture,String photoPath, String vusername)
    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.appliances = appliances;
        this.clothes = clothes;
        this.cars = cars;
        this.furniture = furniture;
        this.photoPath = photoPath;
        this.vusername = vusername;
    }

    public Ad() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAppliances() {
        return appliances;
    }

    public void setAppliances(boolean appliances) {
        this.appliances = appliances;
    }

    public boolean isClothes() {
        return clothes;
    }

    public void setClothes(boolean clothes) {
        this.clothes = clothes;
    }

    public boolean isCars() { return cars; }

    public void setCars(boolean cars) {
        this.cars = cars;
    }

    public boolean isFurniture() {
        return furniture;
    }

    public void setFurniture(boolean furniture) {
        this.furniture = furniture;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getVusername() { return vusername; }

    public void setVusername(String vusername) { this.vusername = vusername; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        if (vusername != null ? !vusername.equals(ad.vusername) : ad.vusername != null) return false;
        if (title != null ? !title.equals(ad.title) : ad.title != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, description, appliances, clothes, cars, furniture, photoPath, vusername);
    }
}