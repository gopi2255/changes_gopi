package com.umpee.app.network;

import android.widget.Toast;

import com.umpee.app.MyApp;
import com.umpee.app.R;
import com.umpee.app.model.ModelMessage;
import com.umpee.app.model.ModelUser;
import com.umpee.app.model.ModelVideo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Network {
    private static final int LIMIT1 = 5;
    private static final int LIMIT2 = 0;


    public interface AuthListener {
        void onGot(ModelUser result);
    }

    public interface UmpiresListener {
        void onGot(ArrayList<ModelUser> result);
    }

    public interface VideoListener {
        void onGot(ModelVideo result);
    }

    public interface MessageListener {
        void onGot(String result);
    }

    public static void login(String email, String password, String ip, int umpire, final AuthListener listener) {
        NetApi.getInterface().login(email, password, ip, umpire).enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()) {
                    ModelUser result = response.body();
                    listener.onGot(result);
                } else {
                    makeToast(NetError.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                makeToast(MyApp.getContext().getString(R.string.error_network));
            }
        });
    }

    public static void updateInfo(String token, String ip, final AuthListener listener) {
        NetApi.getInterface().updateInfo(token, ip).enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()) {
                    ModelUser result = response.body();
                    listener.onGot(result);
                } else {
                    makeToast(NetError.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                makeToast(MyApp.getContext().getString(R.string.error_network));
            }
        });
    }

    public static void getUmpires(String token, final UmpiresListener listener) {
        NetApi.getInterface().getUmpires(token).enqueue(new Callback<ArrayList<ModelUser>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelUser>> call, Response<ArrayList<ModelUser>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ModelUser> result = response.body();
                    listener.onGot(result);
                } else {
                    makeToast(NetError.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelUser>> call, Throwable t) {
                makeToast(MyApp.getContext().getString(R.string.error_network));

            }
        });
    }

    public static void getVideo(String url, String token, final VideoListener listener) {
        NetApi.getInterface(url).getVideo(token).enqueue(new Callback<ModelVideo>() {
            @Override
            public void onResponse(Call<ModelVideo> call, Response<ModelVideo> response) {
                if (response.isSuccessful()) {
                    ModelVideo result = response.body();
                    listener.onGot(result);
                } else {
                    makeToast(NetError.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelVideo> call, Throwable t) {
                makeToast(MyApp.getContext().getString(R.string.error_network));

            }
        });
    }

    public static void sendOut(boolean isOut, final MessageListener listener) {
        NetApi.getInterface().sendOut(isOut ? "OUT" : "NOT OUT").enqueue(new Callback<ModelMessage>() {
            @Override
            public void onResponse(Call<ModelMessage> call, Response<ModelMessage> response) {
                if (response.isSuccessful()) {
                    ModelMessage result = response.body();
                    listener.onGot(result.message);
                } else {
                    makeToast(NetError.parseError(response).getMessage());
                }
            }

            @Override
            public void onFailure(Call<ModelMessage> call, Throwable t) {
            }
        });
    }

    private static void makeToast(String string) {
        Toast.makeText(MyApp.getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
