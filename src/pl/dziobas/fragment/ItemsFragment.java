package pl.dziobas.fragment;

import pl.dziobas.R;
import pl.dziobas.R.id;
import pl.dziobas.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import pl.dziobas.widget.EditTextPlus;
import pl.dziobas.widget.EditTextPlus.OnItemAdded;


/**
 * Fragment that contains text field and list with items
 * @author dziobas
 *
 */
public class ItemsFragment extends Fragment {
    private static final String  ITEM_ARRAY = "item_array";
    public static final String   TITLE      = "title";
    private ArrayList<String>    mItems     = new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view                           = inflater.inflate(R.layout.fragment, container, false);

        final EditTextPlus edit = (EditTextPlus) view.findViewById(R.id.input);
        edit.setOnTextAdded(new OnItemAdded() {
                @Override
                public void onItemAdded(String text) {
                    addItem(text);
                }

                @Override
                public boolean clearOnAdd() {
                    return true;
                }
            });

        restoreState(savedInstanceState);

        final ListView list = (ListView) view.findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_row, R.id.text, mItems);
        list.setAdapter(mAdapter);

        return view;
    }
    
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupTitle(getArguments());
	}

	private void setupTitle(Bundle bundle){
    	if(bundle != null) {
            TextView text  = (TextView) getView().findViewById(R.id.text);
            String   title = bundle.getString(TITLE);

            if(title != null) {
                text.setText(title);
            } else {
                text.setText("Items");
            }
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        if((savedInstanceState != null) && savedInstanceState.containsKey(ITEM_ARRAY)) {
            ArrayList<String> list = savedInstanceState.getStringArrayList(ITEM_ARRAY);

            for(String s : list) {
                mItems.add(s);
            }
        }
    }

    public void addItem(String text) {
        mItems.add(text);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(ITEM_ARRAY, mItems);
        super.onSaveInstanceState(outState);
    }
}
