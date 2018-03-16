package zytjyh.com.hellocharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ZYTJYH on 14/3/2018.
 */

public class LineName extends View {
    public LineName(Context context) {
        super(context);
    }

    public LineName(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineName(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setTextSize(40);
        paint.setColor(getResources().getColor(R.color.FIFOColor));
        canvas.drawText("FIFO",800,60,paint);
        paint.setStrokeWidth(5);
        canvas.drawLine(900,45,1000,45,paint);
        paint.setColor(getResources().getColor(R.color.LRUColor));
        canvas.drawText("LRU",800,130,paint);
        canvas.drawLine(900,115,1000,115,paint);
        paint.setColor(getResources().getColor(R.color.LFUColor));
        canvas.drawText("OPT",800,200,paint);
        canvas.drawLine(900,185,1000,185,paint);
        paint.setColor(getResources().getColor(R.color.OTPColor));
        canvas.drawText("LFU",800,270,paint);
        canvas.drawLine(900,255,1000,255,paint);
    }
}
