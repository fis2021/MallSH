package userr.services;


import org.dizitart.no2.objects.ObjectRepository;
import userr.model.User;
import userr.model.Ad;

import java.util.Objects;

import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.eq;


public class MyAdsService {

    private static ObjectRepository<User> userRepository = UserService.getUsers();
    private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();


    public static boolean checkIfMyAd(Ad ad, String loggedUser) {
        if(Objects.equals(ad.getVusername(),loggedUser))
            return true;
        else return false;
    }
}