package controlador;

import model.Pizza;
import service.PizzaService;
import vista.LandingPageView;
import vista.PizzaView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import model.Pizza;
import service.PizzaConversionService;
import service.PizzaService;
import vista.LandingPageView;
import vista.PizzaView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaController {
    private PizzaService service;
    private PizzaView view;
    private LandingPageView landingView;
    private PizzaConversionService conversionService;

    public PizzaController(PizzaService service, PizzaView view, LandingPageView landingView, PizzaConversionService conversionService) {
        this.service = service;
        this.view = view;
        this.landingView = landingView;
        this.conversionService = conversionService;
        initController();
    }

    private void initController() {
        cargarPizzas();

        // Los listeners de navegación se manejan en App.java
        // Solo manejamos los botones específicos de la funcionalidad de pizzas
        view.getBtnAgregar().addActionListener(e -> agregarPizza());
        view.getBtnActualizar().addActionListener(e -> actualizarPizza());
        view.getBtnExportarJSON().addActionListener(e -> exportarPizzaJSON());

        // También podríamos manejar doble clic en la tabla para editar
        view.getTablePizzas().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    actualizarPizza();
                }
            }
        });
    }

    public void cargarPizzas() {
        try {
            List<Pizza> pizzas = service.listadoPizzas();
            view.cargarPizzas(pizzas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al cargar pizzas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void agregarPizza() {
        // Diálogo para ingresar los datos de la nueva pizza
        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtCalorias = new JTextField("0");
        JTextField txtPrecio = new JTextField("0.0");
        JTextField txtTiempoPreparacion = new JTextField("0");
        JTextField txtIngredientes = new JTextField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Descripción:", txtDescripcion,
                "Calorías:", txtCalorias,
                "Precio:", txtPrecio,
                "Tiempo Preparación (min):", txtTiempoPreparacion,
                "Ingredientes (IDs separados por coma):", txtIngredientes
        };

        int option = JOptionPane.showConfirmDialog(
                view,
                message,
                "Agregar Nueva Pizza",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validar campos obligatorios
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre es obligatorio");
                    return;
                }

                // Generar ID automático
                List<Pizza> pizzasExistentes = service.listadoPizzas();
                int nextId = pizzasExistentes.size() + 1;
                String id = String.format("PZZ%03d", nextId);

                // Parsear ingredientes como lista de IngredienteRef
                List<String> ingredientes = new ArrayList<>();
                if (!txtIngredientes.getText().trim().isEmpty()) {
                    String[] ingredientesArray = txtIngredientes.getText().split(",\\s*");
                    for (String ingredienteId : ingredientesArray) {
                        ingredientes.add(ingredienteId.trim());
                    }
                }

                // Validar números
                int calorias = Integer.parseInt(txtCalorias.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int tiempoPreparacion = Integer.parseInt(txtTiempoPreparacion.getText().trim());

                if (calorias < 0 || precio < 0 || tiempoPreparacion < 0) {
                    JOptionPane.showMessageDialog(view, "Los valores numéricos no pueden ser negativos");
                    return;
                }

                // Crear nueva pizza
                Pizza nuevaPizza = new Pizza();
                nuevaPizza.setId(id);
                nuevaPizza.setNombre(txtNombre.getText().trim());
                nuevaPizza.setDescripcion(txtDescripcion.getText().trim());
                nuevaPizza.setCalorias(calorias);
                nuevaPizza.setPrecio(precio);
                nuevaPizza.setTiempoPreparacion(tiempoPreparacion);
                nuevaPizza.setIngredientes(ingredientes);

                // Guardar en el servicio
                service.actualizarPizza(nuevaPizza);

                // Recargar la lista
                cargarPizzas();

                JOptionPane.showMessageDialog(view, "Pizza agregada correctamente con ID: " + id);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Error en formato numérico: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error al guardar pizza: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void actualizarPizza() {
        int selectedRow = view.getTablePizzas().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione una pizza para actualizar");
            return;
        }

        try {
            // Obtener la pizza seleccionada
            String id = (String) view.getTableModel().getValueAt(selectedRow, 0);
            Pizza pizzaActual = obtenerPizzaPorId(id);

            if (pizzaActual == null) {
                JOptionPane.showMessageDialog(view, "No se pudo encontrar la pizza seleccionada");
                return;
            }

            // Convertir ingredientes a string para el diálogo
            String ingredientesActualesStr = String.join(", ", pizzaActual.getIngredientes());

            // Diálogo para editar los datos
            JTextField txtNombre = new JTextField(pizzaActual.getNombre());
            JTextField txtDescripcion = new JTextField(pizzaActual.getDescripcion());
            JTextField txtCalorias = new JTextField(String.valueOf(pizzaActual.getCalorias()));
            JTextField txtPrecio = new JTextField(String.valueOf(pizzaActual.getPrecio()));
            JTextField txtTiempoPreparacion = new JTextField(String.valueOf(pizzaActual.getTiempoPreparacion()));
            JTextField txtIngredientes = new JTextField(ingredientesActualesStr);

            Object[] message = {
                    "ID: " + id,
                    "Nombre:", txtNombre,
                    "Descripción:", txtDescripcion,
                    "Calorías:", txtCalorias,
                    "Precio:", txtPrecio,
                    "Tiempo Preparación (min):", txtTiempoPreparacion,
                    "Ingredientes (IDs separados por coma):", txtIngredientes
            };

            int option = JOptionPane.showConfirmDialog(
                    view,
                    message,
                    "Actualizar Pizza",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                // Validar campos obligatorios
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre es obligatorio");
                    return;
                }

                // Parsear ingredientes como lista de IngredienteRef
                List<String> ingredientes = new ArrayList<>();
                if (!txtIngredientes.getText().trim().isEmpty()) {
                    String[] ingredientesArray = txtIngredientes.getText().split(",\\s*");
                    for (String ingredienteId : ingredientesArray) {
                        ingredientes.add(ingredienteId.trim());
                    }
                }

                // Validar números
                int calorias = Integer.parseInt(txtCalorias.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int tiempoPreparacion = Integer.parseInt(txtTiempoPreparacion.getText().trim());

                if (calorias < 0 || precio < 0 || tiempoPreparacion < 0) {
                    JOptionPane.showMessageDialog(view, "Los valores numéricos no pueden ser negativos");
                    return;
                }

                // Crear pizza actualizada
                Pizza pizzaActualizada = new Pizza();
                pizzaActualizada.setId(id);
                pizzaActualizada.setNombre(txtNombre.getText().trim());
                pizzaActualizada.setDescripcion(txtDescripcion.getText().trim());
                pizzaActualizada.setCalorias(calorias);
                pizzaActualizada.setPrecio(precio);
                pizzaActualizada.setTiempoPreparacion(tiempoPreparacion);
                pizzaActualizada.setIngredientes(ingredientes);

                // Guardar cambios
                service.actualizarPizza(pizzaActualizada);

                // Recargar la lista
                cargarPizzas();

                JOptionPane.showMessageDialog(view, "Pizza actualizada correctamente");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Error en formato numérico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al actualizar pizza: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para obtener una pizza por ID
    public Pizza obtenerPizzaPorId(String id) {
        try {
            List<Pizza> pizzas = service.listadoPizzas();
            for (Pizza pizza : pizzas) {
                if (pizza.getId().equals(id)) {
                    return pizza;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al buscar pizza: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void exportarPizzaJSON(){
        if (this.conversionService.generarFicheroPizzas()) {
            view.mostrarMensajeExito("Fichero generado correctamente");
        } else {
            view.mostrarMensajeError("Error en la generación de fichero");
        }
    }
}