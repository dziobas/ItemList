package pl.dziobas.activity;

import pl.dziobas.util.Utils;

import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;


public class BaseActivity extends FragmentActivity {
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // hide soft keyboard when user touch outside focused EditText
        View view = getCurrentFocus();

        if(view instanceof EditText) {
            int[] cooridates = new int[2];
            // getting EditText's location
            view.getLocationOnScreen(cooridates);

            Point offset = new Point(cooridates[0], cooridates[1]);
            Rect  r      = new Rect();
            view.getGlobalVisibleRect(r, offset);

            // test if event is outside edit text
            if(!r.contains((int) event.getRawX(), (int) event.getRawY())) {
                Utils.hideSoftKeyboard(this, view);
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Window window = getWindow();
        // cause better drawables quality
        window.setFormat(PixelFormat.RGBA_8888);
    }
}
