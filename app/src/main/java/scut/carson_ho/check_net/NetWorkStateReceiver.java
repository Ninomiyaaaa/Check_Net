package scut.carson_ho.check_net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by Carson_Ho on 16/10/31.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {
    public Callback callback;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("网络状态发生变化");
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            assert connMgr != null;

            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            //获取有线网连接的信息
            NetworkInfo etherNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);

            StringBuilder sb = new StringBuilder();
            sb.append(etherNetworkInfo.isConnected() ? "以太网已连接" : "以太网未连接; ");
            sb.append(wifiNetworkInfo.isConnected() ? "WIFI已连接" : "WIFI未连接; ");
            sb.append(dataNetworkInfo.isConnected() ? "移动数据已连接" : "移动数据未连接");

            if (callback != null) {
                callback.addText(sb.toString());
            }
        } else {
            //这里的就不写了，前面有写，大同小异
            System.out.println("API level 大于21");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            assert connMgr != null;
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (Network network : networks) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
                sb.append(networkInfo.getTypeName()).append(" connect is ").append(networkInfo.isConnected());
            }
            if (callback != null) {
                callback.addText(sb.toString());
            }
        }
    }

    public interface Callback {
        void addText(String text);
    }
}
