package pl.dziobas.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Button which is resi
 * @author dziobas
 *
 */
public class ResizableButton extends Button implements Transitionable {
    private int   mHeight  = -1;
    private float MAX_GROW = 0.25f;

    public ResizableButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ResizableButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableButton(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(mHeight < 0) {
            // get initial height
            mHeight = getHeight();
        }
    }

    @Override
    public void transition(float value) {
        //changes height of button to value between 100 and 125% depending on 'value' which is in range 0..1.
        setHeight((int) (mHeight * (1 + (MAX_GROW * value))));
    }
}
