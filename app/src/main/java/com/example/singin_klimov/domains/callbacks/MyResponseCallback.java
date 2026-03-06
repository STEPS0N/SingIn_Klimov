package com.example.singin_klimov.domains.callbacks;

public interface MyResponseCallback {

    void onComplete(String result);

    void onError(String error);

}
