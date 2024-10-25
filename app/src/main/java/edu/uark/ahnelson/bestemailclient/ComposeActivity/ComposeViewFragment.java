//package edu.uark.ahnelson.bestemailclient.ComposeActivity;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import edu.uark.ahnelson.bestemailclient.R;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ComposeViewFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ComposeViewFragment extends Fragment implements ComposeContract.View{
//
//    private ComposeContract.Presenter mPresenter;
//
//    public ComposeViewFragment() {
//        // Required empty public constructor
//    }
//
//
//    public static ComposeViewFragment newInstance() {
//        ComposeViewFragment fragment = new ComposeViewFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_inbox, container, false);
//    }
//
//    @Override
//    public void setPresenter(ComposeContract.Presenter presenter) {
//        mPresenter = presenter;
//    }
//}