package com.raymond.udacity.bakingapp.util;

import android.os.Bundle;

import androidx.collection.ArrayMap;

import java.util.Map;

public final class Util {
    private Util(){}

    public static Map<String, String> bundleToMap(Bundle bundle) {
        final ArrayMap<String, String> map = new ArrayMap<>();
        for (String s : bundle.keySet()) {
            map.put(s, String.valueOf(bundle.get(s)));
        }
        return map;
    }
}
