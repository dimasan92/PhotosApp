package ru.geekbrains.data.util;

public interface NetworkUtils {

    boolean isOnline();

    boolean isOffline();

    boolean isMobile();

    boolean isWiFi();

    boolean isEthernet();
}
