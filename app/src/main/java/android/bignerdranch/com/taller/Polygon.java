package android.bignerdranch.com.taller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class Polygon extends View {
    private final int color;
    private final int type;
    private final int sides;


    public Polygon(Context context, int color, int type, int sides) {
        super(context);
        this.color = color;
        this.type = type;
        this.sides = sides;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.color);
        paint.setStrokeWidth(10);
        int h = canvas.getHeight()/2;
        int w = canvas.getWidth()/2;
        int r = h / 2;
        switch (type){
            case 0:
                canvas.drawCircle(w, h, r, paint);
                break;
            case 1:
                canvas.drawRect(w-r, h-r, w+r, h+r, paint);
                break;
            case 2:
                float[][] p = new float[sides][2];
                p[0][0] = 0;
                p[0][1] = -r;
                //p[0] = rotate(p[0], 270+45);
                for(int i = 1; i < sides; i++)
                    p[i] = rotate(p[0], (360.0/sides)*i);
                Path path = new Path();
                path.reset();
                path.moveTo(p[0][0]+ w, p[0][1]+ h);
                for(int i = 1; i < sides; i++)
                    path.lineTo(p[i][0]+w, p[i][1]+h);
                path.lineTo(p[0][0]+w, p[0][1]+h);
                path.lineTo(p[1][0]+w, p[1][1]+h);
                canvas.drawPath(path, paint);
                break;
        }

        super.onDraw(canvas);
    }

    float [] rotate( float [] p , double theta) {
        double rad = Math.toRadians(theta);
        return new float []{(float) (p [0] * Math. cos ( rad ) - p [1] * Math. sin ( rad )),
                (float) (p [0] * Math. sin ( rad ) + p [1] * Math. cos ( rad ))};
    }
}
