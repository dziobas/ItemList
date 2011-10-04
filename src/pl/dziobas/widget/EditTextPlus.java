package pl.dziobas.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import pl.dziobas.R;
import pl.dziobas.util.Utils;


/**
 * Text field with 'add' button for adding new items.
 * @author dziobas
 *
 */
public class EditTextPlus extends RelativeLayout {
    private OnItemAdded mOnItemAdded;

    public EditTextPlus(Context context) {
        this(context, null);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           view     = inflater.inflate(R.layout.edittext_plus, this, true);

        final EditText edit     = (EditText) view.findViewById(R.id.edit); // text field
        edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        // hide keyboard when edittext loose focus
                        Utils.hideSoftKeyboard(getContext(), edit);
                    }
                }
            });
        edit.setOnEditorActionListener(new OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    //add item on enter key pressed
                    if((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        add(v.getText().toString());

                        return true;
                    }

                    return false;
                }
            });

        ImageView img = (ImageView) view.findViewById(R.id.add); //add button
        img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    add(edit.getText().toString());
                }
            });
    }

    /**
     * Add new item with text.
     * @param text
     */
    public void add(String text) {
        if(mOnItemAdded != null) {
            mOnItemAdded.onItemAdded(text);

            if(mOnItemAdded.clearOnAdd()) {
                //clear text field
                final EditText edit = (EditText) findViewById(R.id.edit);
                edit.setText("");
            }
        }
    }

    public OnItemAdded getOnTextAdded() {
        return mOnItemAdded;
    }

    public void setOnTextAdded(OnItemAdded onTextAdded) {
        this.mOnItemAdded = onTextAdded;
    }

    /**
     * Listener for text field used to add items.
     * @author dziobas
     *
     */
    public interface OnItemAdded {
        /**
         * Invoked when add button is pressed.
         * @param text Inputed text.
         */
        void onItemAdded(String text);

        /**
         * Indicate whether clear or not the text field.
         * @return If true then text field is cleared.
         */
        boolean clearOnAdd();
    }
}
