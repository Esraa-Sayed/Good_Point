package com.helloworld.goodpoint.retrofit;

import android.content.SharedPreferences;

import com.helloworld.goodpoint.pojo.Token;

public class TokenManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;

    private TokenManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences prefs){
        if(INSTANCE == null){
            INSTANCE = new TokenManager(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(Token token){
        editor.putString("access", token.getAccess()).commit();
        editor.putString("refresh", token.getRefresh()).commit();
    }

    public void deleteToken(){
        editor.remove("access").commit();
        editor.remove("refresh").commit();
    }

    public Token getToken(){
        Token token = new Token();
        token.setAccess(prefs.getString("access", null));
        token.setRefresh(prefs.getString("refresh", null));
        return token;
    }


}

