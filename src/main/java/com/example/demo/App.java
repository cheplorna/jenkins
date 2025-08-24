package com.example.demo;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(8080);

        get("/", (req, res) -> "Hello from Java App running on EKS!");
        get("/health", (req, res) -> "OK");
    }
}
