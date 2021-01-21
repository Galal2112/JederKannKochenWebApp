package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

public class Series extends AbstractEntity {
    private String CityName;
    private double a,b,c,d,e,f,g,h,i,j,k,l;

    public Series(String cityName, double a, double b, double c, double d, double e, double f, double g, double h, double i, double j, double k, double l) {
        CityName = cityName;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }
}
