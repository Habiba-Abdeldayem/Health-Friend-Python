package com.example.healthfriend.UserScreens.Fragments.profile.domain;

import com.example.healthfriend.UserScreens.Fragments.profile.data.ProfileUser;
import com.example.healthfriend.UserScreens.User;

public interface GetUserCallback {
    void onSuccess(User user);

    void onError(String error);
}
