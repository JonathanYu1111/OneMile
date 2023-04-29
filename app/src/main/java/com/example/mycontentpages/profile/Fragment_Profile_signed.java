package com.example.mycontentpages.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mycontentpages.MainActivity;
import com.example.mycontentpages.R;
import com.example.mycontentpages.Utils.OkHttp;
import com.example.mycontentpages.Utils.SpUtils;
import com.example.mycontentpages.attractionInfo.AttractionDetailsActivity;
import com.example.mycontentpages.home.Fragment_Home;
import com.example.mycontentpages.login.LoginActivity;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Profile_signed extends Fragment {

    View rootView;
    private TextView about_tv,resetPsw,user_name,settings,deleteAccount;
    private Button signOutBtn;

    String fail = "Member information is incorrect!";
    public Fragment_Profile_signed() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile_signed, container, false);
        }
        initView();

        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.user_name);
        about_tv=rootView.findViewById(R.id.about);
        resetPsw=rootView.findViewById(R.id.resetPsw);
        signOutBtn=rootView.findViewById(R.id.signOut);
        user_name=rootView.findViewById(R.id.user_name);
        settings=rootView.findViewById(R.id.settings);
        deleteAccount=rootView.findViewById(R.id.deleteAccount);
        about_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });
        resetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Reset_psw.class);
                startActivity(intent);
            }
        });
        signOutBtn.setOnClickListener(v -> {
            SpUtils.putString(MainActivity.getContext(), "token", "");
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        settings.setOnClickListener(v -> {
            Toast.makeText(getActivity(),"Coming soon, please stay tuned!",Toast.LENGTH_LONG).show();
        });

        deleteAccount.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setMessage("Confirm to delete your account?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        //send request
                        new Thread(()->{
                            try {

                                String s = OkHttp.sendPostRequest("/member/delete","");

                                JSONObject jsonObject = new JSONObject(s);
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.getString("msg");
                                if (Integer.valueOf(code) != 20021){
                                    Looper.prepare(); // 创建一个 Looper 对象
                                    Toast.makeText(getActivity(),"Delete fail! Please check again!",Toast.LENGTH_LONG).show();
                                    Looper.loop(); // 开始消息循环
                                }

                                SpUtils.putString(MainActivity.getContext(), "token", "");
                                Looper.prepare(); // 创建一个 Looper 对象
                                Toast.makeText(getActivity(),"Delete successful!",Toast.LENGTH_LONG).show();
                                Intent intent = null;
                                intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                Looper.loop(); // 开始消息循环

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    })
                    .setNegativeButton("No", (dialog, which) -> {

                    })
                    .create()
                    .show();
        });

        //send request
        new Thread(()->{
            try {

                String s = OkHttp.sendPostRequest("/member/info","");
                JSONObject jsonObject = new JSONObject(s);
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                if (Integer.valueOf(code) != 20041){
                    Looper.prepare(); // 创建一个 Looper 对象
                    Toast.makeText(getActivity(),fail,Toast.LENGTH_LONG).show();

                    SpUtils.putString(MainActivity.getContext(), "token", "");
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    Looper.loop(); // 开始消息循环
                }else {
                    String username = jsonObject.getJSONObject("data").getString("username");
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(()->{
                        user_name.setText(username);
                    });
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}