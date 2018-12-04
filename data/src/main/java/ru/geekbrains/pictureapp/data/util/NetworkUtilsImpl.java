package ru.geekbrains.pictureapp.data.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class NetworkUtilsImpl implements NetworkUtils {

    enum NetworkState {
        MOBILE,
        WIFI,
        ETHERNET,
        OFFLINE
    }

    private final ConnectivityManager connectivityManager;
    private NetworkState currentState = NetworkState.OFFLINE;

    @Inject
    NetworkUtilsImpl(final Context appContext) {
        connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isOnline() {
        getState();
        return currentState.equals(NetworkState.MOBILE) || currentState.equals(NetworkState.WIFI) ||
                currentState.equals(NetworkState.ETHERNET);
    }

    private void getState() {
        currentState = NetworkState.OFFLINE;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            final NetworkCapabilities networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    currentState = NetworkState.MOBILE;
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    currentState = NetworkState.WIFI;
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    currentState = NetworkState.ETHERNET;
                } else {
                    currentState = NetworkState.OFFLINE;
                }
            }
        } else {
            final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                switch (activeNetwork.getType()) {
                    case ConnectivityManager.TYPE_MOBILE:
                        currentState = NetworkState.MOBILE;
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        currentState = NetworkState.WIFI;
                        break;
                    case ConnectivityManager.TYPE_ETHERNET:
                        currentState = NetworkState.ETHERNET;
                        break;
                }
            }
        }
    }
}
