package com.example.healthfriend.UserScreens.Fragments.profile.domain;

import com.example.healthfriend.UserScreens.Fragments.profile.data.ProfileUser;

public interface GetUserCallback {
    void onSuccess(ProfileUser user);

    void onError(String error);
}
