package com.blogspot.mowael.molib.user;

/**
 * Created by moham on 5/11/2017.
 */

interface IUserUtil {
    void setUser(String userName, String email);

    void setUser(String userName, String email, String apiToken);
}
