package com.banco;

import com.banco.controladores.CuentaController;
import com.banco.controladores.UsuarioController;
import com.banco.repositorio.ConexionBD;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class App {
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.setHandler(new DefaultHandler());

        ServletContextHandler context = new ServletContextHandler();

        context.setContextPath("/");
        context.addServlet(UsuarioController.class, "/usuario/*");
        context.addServlet(CuentaController.class, "/cuenta/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
