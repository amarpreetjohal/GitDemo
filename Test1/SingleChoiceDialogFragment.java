package com.coldwellbanker.android.ui.fragments;



import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.coldwellbanker.android.R;


public class SingleChoiceDialogFragment extends DialogFragment {
	
	private static final String LOGTAG = "SingleChoiceDialogFragment";
	
	private static final String TAG_TITLE_RESOURCE = "title_resource";
	private static final String TAG_OPTIONS_RESOURCE = "options_resource";
	private static final String TAG_VALUES_RESOURCE = "values_resource";
	private static final String TAG_CHECKED_INDEXE = "checked_indexe";
    final CharSequence myList[] = { "Tea", "Coffee", "Milk" };
	private int selectedValueIndex;
	
	ArrayList<Integer> mSelectedItems; // Where we track the selected items
	
	int optionsResource;
	int titleResource;
	String[] valuesResource;
	int checkedPosition;
	
	public interface SingleChoiceDialogListener {
		public void onSingleChoiceSelected(int selectedValue, String fragTag );
    }
	
	// Use this instance of the interface to deliver action events
	SingleChoiceDialogListener mListener;
	
	/**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static SingleChoiceDialogFragment newInstance(int titleResource, int optionsResource, int checkedIndexes) {
    	SingleChoiceDialogFragment f = new SingleChoiceDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(TAG_TITLE_RESOURCE, titleResource);
        args.putInt(TAG_OPTIONS_RESOURCE, optionsResource);     
        args.getInt(TAG_CHECKED_INDEXE, checkedIndexes);       
        f.setArguments(args);

        return f;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        titleResource = bundle.getInt(TAG_TITLE_RESOURCE);
        optionsResource = bundle.getInt(TAG_OPTIONS_RESOURCE);
        valuesResource = bundle.getStringArray(TAG_VALUES_RESOURCE);
        checkedPosition = bundle.getInt(TAG_CHECKED_INDEXE);
        
        mListener = (SingleChoiceDialogListener) getParentFragment();
    }

	  
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SingleChoiceDialogListener) getParentFragment();
        } catch (ClassCastException e) {            
            throw new ClassCastException(getParentFragment().toString()
                    + " must implement MultiChoiceDialogListener");
        }
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mSelectedItems = new ArrayList<Integer>();  // Where we track the selected items
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());			    
	    builder.setTitle(titleResource)
	   
	           .setSingleChoiceItems(optionsResource, -1,
	                      new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								selectedValueIndex = which;
							}
						})
	           .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String tag = SingleChoiceDialogFragment.this.getTag();
	            	   Log.d(LOGTAG, "tag is " + tag);
	            	   if (mListener != null) {
	            		   // mListener.onDialogNegativeClick(MultiChoiceDialogFragment.this);
	            		   mListener.onSingleChoiceSelected(selectedValueIndex, SingleChoiceDialogFragment.this.getTag() );
	            	   } else {
	            		   Log.e(LOGTAG,"Listener is NULL!!!!!!");
	            	   }
	            	   dialog.dismiss();
	               }
	           });
	           
	           

	    return builder.create();
	}

}
