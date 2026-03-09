package com.example.singin_klimov.datas.apis;

import android.util.Log;

import com.example.singin_klimov.datas.common.CheckInternet;
import com.example.singin_klimov.domains.apis.MyAsyncTask;
import com.example.singin_klimov.domains.callbacks.MyResponseCallback;
import com.example.singin_klimov.domains.models.User;
import com.google.gson.GsonBuilder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class UserCreate extends MyAsyncTask {

    private User user;

    public UserCreate(User user, CheckInternet checkInternet, MyResponseCallback callback) {
        super(checkInternet, callback);
        this.user = user;
    }

    @Override
    protected String doInBackground(Void... voids){
        if (!checkInternet.isWiFiConnection() && !checkInternet.isMobileConnection())
            return "Error: no internet connection";

        String rawData = new GsonBuilder().create().toJson(this.user);

        // Добавьте логирование того, что отправляете
        Log.d("USER CREATE", "Отправляемые данные: " + rawData);

        try {
            Connection.Response response = Jsoup.connect("http://192.168.100.5:5000/api/user/create")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)  // Оставляем true чтобы получить тело ответа даже при ошибке
                    .method(Connection.Method.POST)
                    .header("Content-type", "application/json")
                    .requestBody(rawData)
                    .timeout(10000) // Таймаут 10 секунд
                    .execute();

            // Возвращаем полную информацию
            return "Статус: " + response.statusCode() + ", Тело: " + response.body();

        } catch (IOException e){
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
