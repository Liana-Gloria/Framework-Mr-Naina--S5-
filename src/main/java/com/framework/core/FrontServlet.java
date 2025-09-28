package com.framework.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet {

    // Map pour stocker les routes dynamiques
    private Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> routes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();

        // Définir les routes ici
        routes.put("/glo", this::handleGlo);
        routes.put("/hello", this::handleHello);
        // Ajouter d'autres routes facilement
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Chercher la route correspondante
        BiConsumer<HttpServletRequest, HttpServletResponse> handler = routes.get(path);

        if (handler != null) {
            handler.accept(req, resp); // exécuter la route
        } else {
            // Si pas de route correspondante → page par défaut
            resp.setContentType("text/plain");
            resp.getWriter().println("Page not found: " + path);
        }
    }

    // Exemple de méthodes pour chaque route
    private void handleGlo(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/plain");
            resp.getWriter().println("Welcome to /glo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleHello(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/plain");
            resp.getWriter().println("Hello world route!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
