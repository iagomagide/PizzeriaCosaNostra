package controlador;

import model.Local;
import service.LocalService;
import vista.LandingPageView;
import vista.LocalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalController {
    private LocalService service;
    private LocalView view;
    private LandingPageView landingView;

    public LocalController(LocalService service, LocalView view, LandingPageView landingView) {
        this.service = service;
        this.view = view;
        this.landingView = landingView;

        initController();
    }

    private void initController() {
        cargarLocales();

        // Los listeners de navegación se manejan en App.java
        // Solo manejamos los botones específicos de la funcionalidad de locales
        view.getBtnAgregar().addActionListener(e -> agregarLocal());
        view.getBtnActualizar().addActionListener(e -> actualizarLocal());

        // También podríamos manejar doble clic en la tabla para editar
        view.getTableLocales().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    actualizarLocal();
                }
            }
        });
    }

    public void cargarLocales() {
        try {
            List<Local> locales = service.listadoLocales();
            view.cargarLocales(locales);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar locales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void agregarLocal() {
        // Diálogo para ingresar los datos del nuevo local
        JTextField txtNombre = new JTextField();
        JTextField txtDireccion = new JTextField();
        JTextField txtCodigoPostal = new JTextField();
        JTextField txtPoblacion = new JTextField();
        JTextField txtProvincia = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtM2 = new JTextField("0");

        Object[] message = {
                "Nombre:", txtNombre,
                "Dirección:", txtDireccion,
                "Código Postal:", txtCodigoPostal,
                "Población:", txtPoblacion,
                "Provincia:", txtProvincia,
                "Teléfono:", txtTelefono,
                "Metros Cuadrados (m²):", txtM2
        };

        int option = JOptionPane.showConfirmDialog(
                view,
                message,
                "Agregar Nuevo Local",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validar campos obligatorios
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre es obligatorio");
                    return;
                }

                if (txtDireccion.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "La dirección es obligatoria");
                    return;
                }

                // Generar ID automático
                DefaultTableModel model = view.getTableModel();
                int nextId = model.getRowCount() + 1;
                String id = String.format("LCL%03d", nextId);

                // Validar y parsear metros cuadrados
                int metrosCuadrados;
                try {
                    metrosCuadrados = Integer.parseInt(txtM2.getText().trim());
                    if (metrosCuadrados < 0) {
                        JOptionPane.showMessageDialog(view, "Los metros cuadrados no pueden ser negativos");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Los metros cuadrados deben ser un número válido");
                    return;
                }

                // Crear nuevo local
                Local nuevoLocal = new Local(
                        id,
                        txtNombre.getText().trim(),
                        txtDireccion.getText().trim(),
                        txtCodigoPostal.getText().trim(),
                        txtPoblacion.getText().trim(),
                        txtProvincia.getText().trim(),
                        txtTelefono.getText().trim(),
                        metrosCuadrados
                );

                // Guardar en el servicio
                service.actualizarLocal(nuevoLocal);

                // Recargar la lista
                cargarLocales();

                JOptionPane.showMessageDialog(view, "Local agregado correctamente con ID: " + id);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Error al guardar local: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void actualizarLocal() {
        DefaultTableModel model = view.getTableModel();
        int selectedRow = view.getTableLocales().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, seleccione un local para actualizar");
            return;
        }

        try {
            // Obtener datos actuales de la fila seleccionada
            String id = (String) model.getValueAt(selectedRow, 0);
            String nombreActual = (String) model.getValueAt(selectedRow, 1);
            String direccionActual = (String) model.getValueAt(selectedRow, 2);
            String codigoPostalActual = (String) model.getValueAt(selectedRow, 3);
            String poblacionActual = (String) model.getValueAt(selectedRow, 4);
            String provinciaActual = (String) model.getValueAt(selectedRow, 5);
            String telefonoActual = (String) model.getValueAt(selectedRow, 6);
            String m2Actual = model.getValueAt(selectedRow, 7).toString();

            // Diálogo para editar los datos
            JTextField txtNombre = new JTextField(nombreActual);
            JTextField txtDireccion = new JTextField(direccionActual);
            JTextField txtCodigoPostal = new JTextField(codigoPostalActual);
            JTextField txtPoblacion = new JTextField(poblacionActual);
            JTextField txtProvincia = new JTextField(provinciaActual);
            JTextField txtTelefono = new JTextField(telefonoActual);
            JTextField txtM2 = new JTextField(m2Actual);

            Object[] message = {
                    "ID: " + id,
                    "Nombre:", txtNombre,
                    "Dirección:", txtDireccion,
                    "Código Postal:", txtCodigoPostal,
                    "Población:", txtPoblacion,
                    "Provincia:", txtProvincia,
                    "Teléfono:", txtTelefono,
                    "Metros Cuadrados (m²):", txtM2
            };

            int option = JOptionPane.showConfirmDialog(
                    view,
                    message,
                    "Actualizar Local",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (option == JOptionPane.OK_OPTION) {
                // Validar campos obligatorios
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "El nombre es obligatorio");
                    return;
                }

                if (txtDireccion.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "La dirección es obligatoria");
                    return;
                }

                // Validar y parsear metros cuadrados
                int metrosCuadrados;
                try {
                    metrosCuadrados = Integer.parseInt(txtM2.getText().trim());
                    if (metrosCuadrados < 0) {
                        JOptionPane.showMessageDialog(view, "Los metros cuadrados no pueden ser negativos");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Los metros cuadrados deben ser un número válido");
                    return;
                }

                // Crear local actualizado
                Local localActualizado = new Local(
                        id,
                        txtNombre.getText().trim(),
                        txtDireccion.getText().trim(),
                        txtCodigoPostal.getText().trim(),
                        txtPoblacion.getText().trim(),
                        txtProvincia.getText().trim(),
                        txtTelefono.getText().trim(),
                        metrosCuadrados
                );

                // Guardar cambios
                service.actualizarLocal(localActualizado);

                // Recargar la lista
                cargarLocales();

                JOptionPane.showMessageDialog(view, "Local actualizado correctamente");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Error al actualizar local: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Método para obtener un local por ID (útil para otras funcionalidades)
    public Local obtenerLocalPorId(String id) {
        try {
            List<Local> locales = service.listadoLocales();
            for (Local local : locales) {
                if (local.getId().equals(id)) {
                    return local;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al buscar local: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los locales (útil para combos en otras vistas)
    public List<Local> obtenerTodosLosLocales() {
        try {
            return service.listadoLocales();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al obtener locales: " + e.getMessage());
            return new ArrayList<Local>(); // Retorna lista vacía en caso de error
        }
    }

    // Método para validar formato de teléfono (opcional)
    private boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return true; // Teléfono vacío es válido (opcional)
        }
        // Validar que solo contenga números, espacios, paréntesis y guiones
        return telefono.matches("^[\\d\\s\\-\\(\\)\\+]+$");
    }

    // Método para validar código postal (opcional)
    private boolean validarCodigoPostal(String codigoPostal) {
        if (codigoPostal == null || codigoPostal.trim().isEmpty()) {
            return true; // Código postal vacío es válido (opcional)
        }
        // Validar formato básico de código postal (5 dígitos)
        return codigoPostal.matches("^\\d{5}$");
    }

    // Método para formatear teléfono (opcional)
    private String formatearTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "";
        }
        // Eliminar espacios y caracteres especiales, dejar solo números
        return telefono.replaceAll("[^\\d]", "");
    }
}