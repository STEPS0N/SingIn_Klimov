package com.example.singin_klimov.presentations;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.singin_klimov.R;
import com.example.singin_klimov.datas.apis.UserLogin;
import com.example.singin_klimov.datas.common.CheckInternet;
import com.example.singin_klimov.domains.callbacks.MyResponseCallback;
import com.example.singin_klimov.domains.models.User;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        TextView btnOpenSingIn = findViewById(R.id.btn_open_sign_in);

        btnOpenSingIn.setOnClickListener(v -> {
            Intent SingIn = new Intent(this, RegInActivity.class);
            startActivity(SingIn);
        });

        TextView btnResetPassword = findViewById(R.id.btn_open_reset_password);

        btnResetPassword.setOnClickListener(v -> {
            Intent Reset = new Intent(this, ResetPasswordActivity.class);
            startActivity(Reset);
        });

        Button btnLogIn = findViewById(R.id.btn_log_in);

        btnLogIn.setOnClickListener(v -> {
            TextView etEmail = findViewById(R.id.et_email);
            TextView etPassword = findViewById(R.id.et_password);

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (email.isEmpty()){
                Toast.makeText(this, "Не указана почта пользователя", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!email.matches("^[aA-zZ.?&,]{2,20}@[aA-zZ]{2,20}.[aA-zZ]{2,3}$")){
                Toast.makeText(this, "Формат почты должен быть такой: xx@xx.xx", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (password.isEmpty()){
                Toast.makeText(this, "Не указан пароль пользователя", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                etEmail.setText("");
                etPassword.setText("");
                Toast.makeText(this, "Пользователь авторизован", Toast.LENGTH_SHORT).show();
            }

            requestUserLogin(email, password);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void requestUserLogin(String email, String password) {
        Context context = this;
        CheckInternet checkInternet = new CheckInternet(this);

        User User = new User();
        User.email = email;
        User.password = password;

        UserLogin RequestUserLogin = new UserLogin(
                User,
                checkInternet,
                new MyResponseCallback() {
                    @Override
                    public void onComplete(String result) {
                        Log.d("USER LOGIN", result);
                        Toast.makeText(context, "Успешная авторизация пользователя", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Log.d("USER LOGIN", error);
                        Toast.makeText(context, "При авторизации возникли ошибки", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestUserLogin.execute();
    }
}