package com.example.healthfriend.UserScreens.Fragments.profile.domain;

import com.example.healthfriend.UserScreens.IndividualUser;

public interface GetUserCallback {
    void onSuccess(IndividualUser individualUser);

    void onError(String error);
}
