package ru.stepup.testing;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Stub {
    private HttpServer server;

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/checkGrade", ex -> {
            String grade = ex.getRequestURI().getQuery().split("=")[1];
            String resp = (Integer.parseInt(grade) >= 2 && Integer.parseInt(grade) <= 5) ? "true" : "false";
            ex.sendResponseHeaders(200, resp.length());
            ex.getResponseBody().write(resp.getBytes());
            ex.getResponseBody().close();
        });

        server.createContext("/educ", ex -> {
            String sumStr = ex.getRequestURI().getQuery().split("=")[1];
            int sum = Integer.parseInt(sumStr);
            String resp = String.valueOf(sum / 2);
            ex.sendResponseHeaders(200, resp.length());
            ex.getResponseBody().write(resp.getBytes());
            ex.getResponseBody().close();
        });
        server.start();
    }

    public void stop() {
        if (server != null) server.stop(0);
    }
}
