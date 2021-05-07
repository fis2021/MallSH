package userr.model;


import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class User {
    @Id
    private String username;
    private String password;
    private String passwordconfirm;
    private String firstname;
    private String secondname;
    private String phonenumber;
    private String address;
    private String photoPath;

    public User(String username, String password,String passwordconfirm, String firstname,String secondname,String phonenumber,String address,String photoPath) {
        this.username = username;
        this.password = password;
        this.passwordconfirm = passwordconfirm;
        this.firstname = firstname;
        this.secondname = secondname;
        this.phonenumber = phonenumber;
        this.address = address;
        this.photoPath = photoPath;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordconfirm() {
        return passwordconfirm;
    }

    public void setPasswordconfirm(String passwordconfirm) {
        this.passwordconfirm = passwordconfirm;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) { this.secondname = secondname; }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoPath(){return photoPath;}

    public void setPhotoPath(String photoPath){ this.photoPath = photoPath;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(username, user.username)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}

