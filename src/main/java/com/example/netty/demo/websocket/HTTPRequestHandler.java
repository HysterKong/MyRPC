package com.example.netty.demo.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class HTTPRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HTTPRequestHandler.class
                .getProtectionDomain() .getCodeSource().getLocation();
        try {String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5); INDEX = new File(path);
        } catch (URISyntaxException e) { throw new IllegalStateException(
                "Unable to locate index.html", e); }
    }

    public HTTPRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.getUri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
        }
    }

    private void send100Continue(ChannelHandlerContext ctx) {
    }
}
