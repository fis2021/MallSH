package userr.model;


import org.dizitart.no2.objects.Id;

import java.util.Objects;

public class Feedback {
    @Id

    private String loggedUser;
    private String description;
    private String rate;


    public Feedback(String loggedUser, String description, String rate)
    {
        this.loggedUser = loggedUser;
        this.rate = rate;
        this.description = description;

    }

    public Feedback() {
    }



    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getDescription() {
        return description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return  Objects.equals(loggedUser, feedback.loggedUser) && Objects.equals(description, feedback.description) && Objects.equals(rate, feedback.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUser, description, rate);
    }
}