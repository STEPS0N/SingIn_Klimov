package com.example.singin_klimov.domains.apis;
import android.os.AsyncTask;
import com.example.singin_klimov.datas.common.CheckInternet;
import com.example.singin_klimov.domains.callbacks.MyResponseCallback;

public class MyAsyncTask extends AsyncTask<Void, Void, String> {

    protected MyResponseCallback callback;
    protected CheckInternet checkInternet;
    public MyAsyncTask(CheckInternet checkInternet, MyResponseCallback callback){
        this.checkInternet = checkInternet;
        this.callback = callback;
    }
    @Override
    protected String doInBackground(Void... voids) { return ""; }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);

        if (callback != null){
            if (result != null && !result.startsWith("Error"))
                callback.onComplete(result);
            else
                callback.onError(result);
        }
    }

}
