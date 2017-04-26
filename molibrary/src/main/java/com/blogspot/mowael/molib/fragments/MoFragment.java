package com.blogspot.mowael.molib.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.mowael.molib.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentManager fragmentManager;

    public MoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoFragment newInstance(String param1, String param2) {
        MoFragment fragment = new MoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mo, container, false);
    }

    public void startActivity(Class<? extends Activity> aClass) {
        startActivity(aClass, true);
    }

    public void startActivity(Class<? extends Activity> aClass, boolean finish) {
        startActivity(getContext(), aClass, null, finish);
    }

    public void startActivity(Class<? extends Activity> aClass, Bundle extras, boolean finish) {
        startActivity(getContext(), aClass, extras, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass, boolean finish) {
        startActivity(context, aClass, null, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass) {
        startActivity(context, aClass, null, false);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass, @Nullable Bundle extras, boolean finish) {
        Intent intent = new Intent(context, aClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        if (finish)
            ((Activity) context).finish();
    }

    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        if (fragmentManager == null) {
            fragmentManager = getFragmentManager();
        }
        if (isAddToBackStack)
            fragmentManager.beginTransaction().addToBackStack(null).replace(in, fragment, tag).commit();
        else fragmentManager.beginTransaction().replace(in, fragment, tag).commit();
    }

    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, fragment.getClass().getSimpleName(), isAddToBackStack);
    }

    public <T extends MoFragment> void loadFragment(T fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }

    public <T extends MoFragment> void loadFragment(T fragment, boolean isAddToBackStack) {
        loadFragment(fragment, R.id.flFragment, isAddToBackStack);
    }

    public <T extends MoFragment> void loadFragment(T fragment) {
        loadFragment(fragment, true);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
