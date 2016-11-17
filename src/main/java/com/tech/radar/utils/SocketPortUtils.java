package com.tech.radar.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dmumma on 11/5/16.
 */
public final class SocketPortUtils {

    private SocketPortUtils() {

    }

    /**
     * next free port.
     *
     * @param from
     * @param to
     * @return port
     */
    public static int nextFreePort(final int from, final int to) {
        int port = ThreadLocalRandom.current().nextInt(from, to);
        while (true) {
            if (isLocalPortFree(port)) {
                return port;
            } else {
                port = ThreadLocalRandom.current().nextInt(from, to);
            }
        }
    }

    private static boolean isLocalPortFree(final int port) {
        try {
            new ServerSocket(port).close();
            return true;
        } catch (final IOException e) {
            return false;
        }
    }
}
