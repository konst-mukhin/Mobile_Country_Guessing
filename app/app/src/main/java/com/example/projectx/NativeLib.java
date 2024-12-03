package com.example.projectx;

public class NativeLib {
    static {
        System.loadLibrary("projectx");
    }

    public native String getCountryCapital(String country);
}

