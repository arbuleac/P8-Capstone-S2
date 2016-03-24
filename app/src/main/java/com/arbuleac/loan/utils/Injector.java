package com.arbuleac.loan.utils;

import android.content.Context;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * This class serves as main entry point for obtaining components.
 * <p/>
 */
public class Injector {

    static Map<Class, Object> objectMap = new HashMap<>();

    public static void init(Context context) {
        Firebase firebase = new Firebase("https://loancalculator.firebaseio.com/");
        objectMap.put(Firebase.class, firebase);
    }

    public static <T> T obtain(Class<T> type) {
        return (T) objectMap.get(type);
    }
}
