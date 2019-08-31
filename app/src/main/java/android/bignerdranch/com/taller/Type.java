package android.bignerdranch.com.taller;

import android.content.Context;
import android.view.View;

public class Type {
    public int type;
    public int sides;
    public String name;
    public Type(int type, int sides, String name){
        this.type = type;
        this.sides = sides;
        this.name = name;
    }

    public View inflateView(int color, Context context){
        return new Polygon(context, color, type, sides);
    }
}
