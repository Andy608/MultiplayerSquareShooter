package com.codingparty.entity.util;

public class Color {

    public float r, g, b, a;

    public Color(Color c) {
        this.set(c);
    }
    
    public Color(float r, float g, float b, float a) {
        this.set(r, g, b, a);
    }

    private void clamp() {
        if (r < 0) r = 0;
        else if (r > 1) r = 1;

        if (g < 0) g = 0;
        else if (g > 1) g = 1;

        if (b < 0) b = 0;
        else if (b > 1) b = 1;

        if (a < 0) a = 0;
        else if (a > 1) a = 1;
    }

    public void set(Color another) {
        this.r = another.r;
        this.g = another.g;
        this.b = another.b;
        this.a = another.a;
    }

    public void set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        clamp();
    }

    public int toInt() {
        int A = Math.round(255 * a);
        int R = Math.round(255 * r);
        int G = Math.round(255 * g);
        int B = Math.round(255 * b);

        A = (A << 24) & 0xFF000000;
        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;
        
        return A | R | G | B;
    }
    
    public static Color fromInt(int value) {
    	int alpha = (value >> 24) & 0xFF;
    	int red = (value >> 16) & 0xFF;
    	int green = (value >> 8) & 0xFF;
    	int blue = value & 0xFF;
    	
    	return new Color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

    @Override
    public String toString() {
        return this.toInt() + " OR " + "(" + this.r + ", " + this.g + ", " + this.b + ")";
    }
}
