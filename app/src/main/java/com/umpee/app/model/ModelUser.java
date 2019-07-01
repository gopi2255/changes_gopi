package com.umpee.app.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.umpee.app.AppConfig;
import com.umpee.app.cache.Cache;
import com.umpee.app.custom.Const;


public class ModelUser extends ModelBase {

    @SerializedName(ID)
    public int id = 0;
    @SerializedName(USERNAME)
    public String username;
    @SerializedName(EMAIL)
    public String email;
    @SerializedName(PASSWORD)
    public String password;
    @SerializedName(IP)
    public String ip;
    @SerializedName(UMPIRE)
    public int umpire;
    @SerializedName(TOKEN)
    public String token;


    private static String KEY = AppConfig.VER + "AppUser";

    public void save() {
        Cache.getInstance().putObject(KEY, this);
    }

    public static ModelUser build() {
        try {
            ModelUser data = (ModelUser) Cache.getInstance().getObject(KEY, ModelUser.class);
            if (data == null) data = new ModelUser();
            return data;
        } catch (Exception e) {
            return new ModelUser();
        }
    }

    public boolean hasAuthorized() {
        return !TextUtils.isEmpty(token);
    }

    public boolean isUmpire() {
        return umpire == Const.UMPIRE;
    }

    public String getPublicURL() {
        if (TextUtils.isEmpty(ip)) return null;
        else return "http://" + ip + ":8080";
    }

    public void logout() {
        token = "";
        save();
    }

}
