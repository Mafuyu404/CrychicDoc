package net.minecraft.client.multiplayer.resolver;

import java.net.InetSocketAddress;

public interface ResolvedServerAddress {

    String getHostName();

    String getHostIp();

    int getPort();

    InetSocketAddress asInetSocketAddress();

    static ResolvedServerAddress from(final InetSocketAddress inetSocketAddress0) {
        return new ResolvedServerAddress() {

            @Override
            public String getHostName() {
                return inetSocketAddress0.getAddress().getHostName();
            }

            @Override
            public String getHostIp() {
                return inetSocketAddress0.getAddress().getHostAddress();
            }

            @Override
            public int getPort() {
                return inetSocketAddress0.getPort();
            }

            @Override
            public InetSocketAddress asInetSocketAddress() {
                return inetSocketAddress0;
            }
        };
    }
}