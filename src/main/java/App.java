import controlador.IngredienteController;
import controlador.LandingPageController;
import controlador.LocalController;
import controlador.PizzaController;
import service.*;
import vista.*;

import javax.swing.*;

public class App {

    private static LandingPageView landingView;
    private static LocalView localView;
    private static PizzaView pizzaView;
    private static IngredienteView ingredienteView;

    private static LandingPageController landingController;
    private static LocalController localController;
    private static PizzaController pizzaController;
    private static IngredienteController ingredienteController;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    /*===============ZONA DE CÓDIGO MODIFICABLE===============*/
                    String pathProp = "src/main/resources/config.properties";
                    String pathLocal = "src/main/resources/locales.csv";
                    String pathPizza = "src/main/resources/Carta.xml";
                    String pathIngedientes = "src/main/resources/ingredientes.dat";
                    //Este fichero hay que generarlo
                    String pathPizzaJSON = "src/main/resources/Pizzas.json";
                    //DAOS TO-DO

                    //Servicios TO-DO
                    LandingPageService landingService = new LandingPageServiceMock();
                    LocalService localService = new LocalServiceMock();
                    PizzaService pizzaService = new PizzaServiceMock();
                    IngredienteService ingredienteService = new IngredienteServiceMock();
                    PizzaConversionService conversionService = new PizzaConversionServiceMock();

                    /*===============FIN ZONA DE CÓDIGO MODIFICABLE===============*/
                    // Inicializar vistas
                    IdiomaView idiomaView = new IdiomaView();
                    landingView = new LandingPageView();
                    landingView.setTitle("Pizzeria Cosa Nostra");
                    localView = new LocalView();
                    pizzaView = new PizzaView();
                    ingredienteView = new IngredienteView();

                    // Inicializar controladores
                    landingController = new LandingPageController(landingService, idiomaView, landingView);
                    localController = new LocalController(localService, localView, landingView);
                    pizzaController = new PizzaController(pizzaService, pizzaView, landingView,conversionService);
                    ingredienteController = new IngredienteController(ingredienteService, ingredienteView, landingView);

                    // Configurar navegación centralizada
                    configurarNavegacion();

                    // Mostrar primera pantalla
                    idiomaView.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Error inicializando la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            System.out.println("Error crítico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void configurarNavegacion() {
        // Configurar listeners de navegación en LandingPageView
        landingView.setBotonLocalesListener(e -> navegarALocales());
        landingView.setBotonPizzasListener(e -> navegarAPizzas());
        landingView.setBotonIngredientesListener(e -> navegarAIngredientes());

        // Configurar listeners de navegación en LocalView
        localView.getBtnInicio().addActionListener(e -> navegarALanding());
        localView.getBtnPizzas().addActionListener(e -> navegarAPizzas());
        localView.getBtnIngredientes().addActionListener(e -> navegarAIngredientes());

        // Configurar listeners de navegación en PizzaView
        pizzaView.getBtnInicio().addActionListener(e -> navegarALanding());
        pizzaView.getBtnLocales().addActionListener(e -> navegarALocales());
        pizzaView.getBtnIngredientes().addActionListener(e -> navegarAIngredientes());

        // Configurar listeners de navegación en IngredienteView
        ingredienteView.getBtnInicio().addActionListener(e -> navegarALanding());
        ingredienteView.getBtnLocales().addActionListener(e -> navegarALocales());
        ingredienteView.getBtnPizzas().addActionListener(e -> navegarAPizzas());
    }

    private static void navegarALanding() {
        ocultarTodasLasVistas();
        landingView.setVisible(true);
    }

    private static void navegarALocales() {
        ocultarTodasLasVistas();
        localController.cargarLocales(); // Recargar datos si es necesario
        localView.setVisible(true);
    }

    private static void navegarAPizzas() {
        ocultarTodasLasVistas();
        pizzaController.cargarPizzas(); // Recargar datos si es necesario
        pizzaView.setVisible(true);
    }

    private static void navegarAIngredientes() {
        ocultarTodasLasVistas();
        ingredienteController.cargarIngredientes(); // Recargar datos si es necesario
        ingredienteView.setVisible(true);
    }

    private static void ocultarTodasLasVistas() {
        landingView.setVisible(false);
        localView.setVisible(false);
        pizzaView.setVisible(false);
        ingredienteView.setVisible(false);
    }

}
