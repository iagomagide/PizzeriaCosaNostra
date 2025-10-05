package controlador;

import model.Ingrediente;
import service.IngredienteService;
import vista.IngredienteView;
import vista.LandingPageView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredienteController {
    private IngredienteService service;
    private IngredienteView view;
    private LandingPageView landingView;

    public IngredienteController(IngredienteService service, IngredienteView view, LandingPageView landingView) {
        this.service = service;
        this.view = view;
        this.landingView = landingView;

        initController();
    }

    private void initController() {
        cargarIngredientes();

        // Los listeners de navegación se manejan en App.java
        // Solo manejamos los botones específicos de la funcionalidad de ingredientes
        view.getBtnAgregar().addActionListener(e -> agregarIngrediente());
        view.getBtnActualizar().addActionListener(e -> actualizarIngrediente());

        // También podríamos manejar doble clic en la tabla para editar
        view.getTableIngredientes().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    actualizarIngrediente();
                }
            }
        });
    }

    public void cargarIngredientes() {
        try {
            List<Ingrediente> ingredientes = service.listadoIngredientes();
            view.cargarIngredientes(ingredientes);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar ingredientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void agregarIngrediente() {
        // Diálogo para ingresar los datos del nuevo ingrediente
        JTextField txtNombre = new JTextField();
        JTextField txtProveedor = new JTextField();
        JTextField txtNombreProveedor = new JTextField();

        JCheckBox chkGluten = new JCheckBox("Contiene Gluten");
        JCheckBox chkLacteos = new JCheckBox("Contiene Lácteos");
        JCheckBox chkHuevos = new JCheckBox("Contiene Huevos");
        JCheckBox chkPicante = new JCheckBox("Es Picante");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("ID Proveedor:"));
        panel.add(txtProveedor);
        panel.add(new JLabel("Nombre Proveedor:"));
        panel.add(txtNombreProveedor);
        panel.add(chkGluten);
        panel.add(chkLacteos);
        panel.add(chkHuevos);
        panel.add(chkPicante);

        int option = JOptionPane.showConfirmDialog(
                view,
                panel,
                "Agregar Nuevo Ingrediente",
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
                DefaultTableModel model = view.getTableModel();
                int nextId = model.getRowCount() + 1;
                String id = String.format("ING%03d", nextId);

                // Crear nuevo ingrediente
                Ingrediente nuevoIngrediente = new Ingrediente(
                        id,
                        txtNombre.getText().trim(),
                        txtProveedor.getText().trim(),
                        txtNombreProveedor.getText().trim(),
                        chkGluten.isSelected(),
                        chkLacteos.isSelected(),
                        chkHuevos.isSelected(),
                        chkPicante.isSelected()
                );

                // Guardar en el servicio
                service.actualizarIngrediente(nuevoIngrediente);

                // Recargar la lista
                cargarIngredientes();

                JOptionPane.showMessageDialog(view, "Ingrediente agregado correctamente con ID: " + id);

            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(view, "Error al guardar ingrediente: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void actualizarIngrediente() {
        DefaultTableModel model = view.getTableModel();
        int selectedRow = view.getTableIngredientes().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione un ingrediente para actualizar");
            return;
        }

        try {
            // Obtener datos actuales de la fila seleccionada
            String id = (String) model.getValueAt(selectedRow, 0);
            String nombreActual = (String) model.getValueAt(selectedRow, 1);
            String proveedorActual = (String) model.getValueAt(selectedRow, 2);
            String nombreProveedorActual = (String) model.getValueAt(selectedRow, 3);
            boolean glutenActual = model.getValueAt(selectedRow, 4).equals("SI");
            boolean lacteosActual = model.getValueAt(selectedRow, 5).equals("SI");
            boolean huevosActual = model.getValueAt(selectedRow, 6).equals("SI");
            boolean picanteActual = model.getValueAt(selectedRow, 7).equals("SI");

            // Diálogo para editar los datos
            JTextField txtNombre = new JTextField(nombreActual);
            JTextField txtProveedor = new JTextField(proveedorActual);
            JTextField txtNombreProveedor = new JTextField(nombreProveedorActual);

            JCheckBox chkGluten = new JCheckBox("Contiene Gluten", glutenActual);
            JCheckBox chkLacteos = new JCheckBox("Contiene Lácteos", lacteosActual);
            JCheckBox chkHuevos = new JCheckBox("Contiene Huevos", huevosActual);
            JCheckBox chkPicante = new JCheckBox("Es Picante", picanteActual);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(new JLabel("ID: " + id));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("ID Proveedor:"));
            panel.add(txtProveedor);
            panel.add(new JLabel("Nombre Proveedor:"));
            panel.add(txtNombreProveedor);
            panel.add(chkGluten);
            panel.add(chkLacteos);
            panel.add(chkHuevos);
            panel.add(chkPicante);

            int option = JOptionPane.showConfirmDialog(
                    view,
                    panel,
                    "Actualizar Ingrediente",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                // Validar campos obligatorios
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre es obligatorio");
                    return;
                }

                // Crear ingrediente actualizado
                Ingrediente ingredienteActualizado = new Ingrediente(
                        id,
                        txtNombre.getText().trim(),
                        txtProveedor.getText().trim(),
                        txtNombreProveedor.getText().trim(),
                        chkGluten.isSelected(),
                        chkLacteos.isSelected(),
                        chkHuevos.isSelected(),
                        chkPicante.isSelected()
                );

                // Guardar cambios
                service.actualizarIngrediente(ingredienteActualizado);

                // Recargar la lista
                cargarIngredientes();

                JOptionPane.showMessageDialog(view, "Ingrediente actualizado correctamente");
            }

        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(view, "Error al actualizar ingrediente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para obtener un ingrediente por ID (útil para otras funcionalidades)
    public Ingrediente obtenerIngredientePorId(String id) {
        try {
            List<Ingrediente> ingredientes = service.listadoIngredientes();
            for (Ingrediente ingrediente : ingredientes) {
                if (ingrediente.getId().equals(id)) {
                    return ingrediente;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Error al buscar ingrediente: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los ingredientes (útil para combos en otras vistas)
    public List<Ingrediente> obtenerTodosLosIngredientes() {
        try {
            return service.listadoIngredientes();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(view, "Error al obtener ingredientes: " + e.getMessage());
            return new ArrayList<Ingrediente>(); // Retorna lista vacía en caso de error
        }
    }
}
