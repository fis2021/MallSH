package userr.model;


import org.dizitart.no2.objects.Id;

public class Ad {
    @Id
    private String title;
    private String price;
    private String description;
    private boolean appliances;
    private boolean clothes;
    private boolean cars;
    private boolean furniture;
    private String photoPath;

    public Ad(String title, String price,String description, boolean appliances, boolean clothes, boolean cars, boolean furniture,String photoPath) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.appliances = appliances;
        this.clothes = clothes;
        this.cars = cars;
        this.furniture = furniture;
        this.photoPath = photoPath;
    }

    public Ad() {
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
}

