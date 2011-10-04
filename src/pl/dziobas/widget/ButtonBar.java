package pl.dziobas.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import pl.dziobas.R;


/**
 *
 * @author dziobas
 *
 */
public class ButtonBar extends LinearLayout implements ViewPager.OnPageChangeListener {
    private OnButtonClicked      onButtonClicked;
    private List<Transitionable> buttons;
    private boolean              isDragging = false;

    public ButtonBar(Context context) {
        this(context, null);
    }

    public ButtonBar(Context context, AttributeSet attrs) {
        this(context, attrs, null, false);
    }

    protected ButtonBar(Context context, AttributeSet attrs, ViewGroup root, boolean attachToRoot) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.button_bar, this, true);

        ResizableButton mButtonA = (ResizableButton) findViewById(R.id.button_a);
        ResizableButton mButtonB = (ResizableButton) findViewById(R.id.button_b);
        ResizableButton mButtonC = (ResizableButton) findViewById(R.id.button_c);

        buttons                  = new ArrayList<Transitionable>();

        buttons.add(mButtonA);
        buttons.add(mButtonB);
        buttons.add(mButtonC);

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v instanceof Button) {
                    if(onButtonClicked != null) {
                        onButtonClicked.onButtonClicked(buttons.indexOf(v));
                    }
                }
            }
        };

        mButtonA.setOnClickListener(onClickListener);
        mButtonB.setOnClickListener(onClickListener);
        mButtonC.setOnClickListener(onClickListener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float offset = (float) positionOffset;

        if(isDragging) {
            //dragging to left, current fragment is moving to left, position indicates current fragment, offset is getting higher
            buttons.get(position).transition((1 - offset));

            //dragging to right, left fragment is emerging, position indicates left fragment, offset is getting lower
            if(position < (buttons.size() - 1)) {
                buttons.get(position + 1).transition(offset);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        for(int i = 0; i < buttons.size(); i++) {
            float value = (position == i) ? 1 : 0;
            buttons.get(i).transition(value);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        isDragging = state == ViewPager.SCROLL_STATE_DRAGGING;
    }

    public OnButtonClicked getOnButtonClicked() {
        return onButtonClicked;
    }

    public void setOnButtonClicked(OnButtonClicked onButtonClicked) {
        this.onButtonClicked = onButtonClicked;
    }

    /**
     * Listens for button click
     * @author dziobas
     *
     */
    public interface OnButtonClicked {
        /**
         * Button on certain position was clicked
         * @param position Position of the clicked button
         */
        void onButtonClicked(int position);
    }
}
