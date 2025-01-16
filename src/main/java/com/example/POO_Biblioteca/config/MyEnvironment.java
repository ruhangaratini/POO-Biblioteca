package com.example.POO_Biblioteca.config;

import io.github.cdimascio.dotenv.Dotenv;

public class MyEnvironment {
    private static MyEnvironment instance;
    private final Dotenv env;

    public static MyEnvironment getInstance() {
        if(instance == null)
            instance = new MyEnvironment();

        return instance;
    }

    private MyEnvironment() {
        env = Dotenv.load();
    }

    public String get(String key) {
        return env.get(key);
    }
}
