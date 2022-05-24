package com.shui.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class WebClientTest extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        WebClientOptions options = new WebClientOptions().setUserAgent("My-App/1.2.3");
        options.setKeepAlive(false);
        WebClient client = WebClient.create(vertx, options);
        client
                .getAbs("http://www.baidu.com")
                .send()
                .onSuccess(res -> {
                    System.out.println(res.statusCode());
                })
                .onFailure(err -> {
                    System.out.println(err.getMessage());
                })
                .onComplete(complete -> {
                    System.out.println(complete.result());
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebClientTest());
    }

}
