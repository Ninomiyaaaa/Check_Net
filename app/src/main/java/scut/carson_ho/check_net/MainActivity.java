package scut.carson_ho.check_net;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

public class MainActivity extends AppCompatActivity {

    NetWorkStateReceiver netWorkStateReceiver;
    private AppCompatTextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvContent = (AppCompatTextView)findViewById(R.id.tv_content);

        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册");

        netWorkStateReceiver.callback = new NetWorkStateReceiver.Callback() {
            @Override
            public void addText(String text) {
                String prevContent = tvContent.getText().toString();
                tvContent.setText(prevContent.isEmpty() ? text : prevContent + "\n" + text);
            }
        };
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销");
        super.onDestroy();
    }
}
