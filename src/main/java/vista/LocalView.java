package vista;

import model.Local;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LocalView extends JFrame {
    private JButton btnInicio = new JButton("Inicio");
    private JButton btnPizzas = new JButton("Pizzas");
    private JButton btnIngredientes = new JButton("Ingredientes");

    private JTable tableLocales;
    private DefaultTableModel tableModel;
    private JButton btnAgregar = new JButton("Agregar Local");
    private JButton btnActualizar = new JButton("Actualizar Local");

    public LocalView() {
        setTitle("Pizzería Cosa Nostra - Gestión de Locales");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        aplicarEstiloModerno();

        // Panel izquierdo con botones de navegación
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(3, 1, 15, 15));
        panelIzquierdo.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelIzquierdo.setBackground(new Color(44, 44, 44));

        // Estilizar botones
        estilizarBoton(btnInicio, new Color(220, 20, 60));     // Rojo pizzeria
        estilizarBoton(btnPizzas, new Color(255, 140, 0));     // Naranja
        estilizarBoton(btnIngredientes, new Color(34, 139, 34)); // Verde

        panelIzquierdo.add(btnInicio);
        panelIzquierdo.add(btnPizzas);
        panelIzquierdo.add(btnIngredientes);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel principal con header y contenido
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(250, 250, 245));
        panelPrincipal.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header
        JPanel headerPanel = crearHeader();
        panelPrincipal.add(headerPanel, BorderLayout.NORTH);

        // Panel derecho con grid de locales
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Dirección", "CP", "Población", "Provincia", "Teléfono", "m²"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que ninguna celda sea editable directamente
                return false;
            }
        };

        tableLocales = new JTable(tableModel);
        tableLocales.setSelectionBackground(new Color(220, 20, 60, 100));
        tableLocales.setSelectionForeground(Color.BLACK);
        tableLocales.setRowHeight(30);
        tableLocales.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableLocales.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableLocales.getTableHeader().setBackground(new Color(44, 44, 44));
        tableLocales.getTableHeader().setForeground(Color.WHITE);
        tableLocales.getTableHeader().setReorderingAllowed(false);

        // Personalizar ancho de columnas
        tableLocales.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tableLocales.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tableLocales.getColumnModel().getColumn(2).setPreferredWidth(200); // Dirección
        tableLocales.getColumnModel().getColumn(3).setPreferredWidth(80);  // CP
        tableLocales.getColumnModel().getColumn(4).setPreferredWidth(120); // Población
        tableLocales.getColumnModel().getColumn(5).setPreferredWidth(120); // Provincia
        tableLocales.getColumnModel().getColumn(6).setPreferredWidth(120); // Teléfono
        tableLocales.getColumnModel().getColumn(7).setPreferredWidth(60);  // m²

        JScrollPane scrollPane = new JScrollPane(tableLocales);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablaPanel = new JPanel(new BorderLayout());
        tablaPanel.setBackground(new Color(250, 250, 245));
        tablaPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        tablaPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones inferiores
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(250, 250, 245));
        panelBotones.setBorder(new EmptyBorder(10, 0, 20, 0));

        estilizarBotonAccion(btnAgregar, new Color(34, 139, 34));    // Verde para agregar
        estilizarBotonAccion(btnActualizar, new Color(255, 140, 0)); // Naranja para actualizar

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);

        tablaPanel.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(tablaPanel, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void aplicarEstiloModerno() {
        try {
            UIManager.put("ScrollPane.background", new Color(250, 250, 245));
            UIManager.put("Viewport.background", new Color(250, 250, 245));
            UIManager.put("Table.background", Color.WHITE);
            UIManager.put("Table.gridColor", new Color(240, 240, 240));
        } catch (Exception e) {
            System.out.println("Error en personalización de colores: " + e.getMessage());
        }
    }

    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 2),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover manual
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorFondo.darker(), 3),
                        BorderFactory.createEmptyBorder(12, 20, 12, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorFondo.darker(), 2),
                        BorderFactory.createEmptyBorder(12, 20, 12, 20)
                ));
            }
        });
    }

    private void estilizarBotonAccion(JButton boton, Color colorFondo) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 2),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover para botones de acción
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(44, 44, 44));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 20, 60), 3),
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        JLabel titulo = new JLabel("Gestión de Locales", JLabel.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 28));
        titulo.setForeground(new Color(255, 215, 0)); // Dorado
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel subtitulo = new JLabel("Administra los locales de Pizzería Cosa Nostra", JLabel.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subtitulo.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(new Color(44, 44, 44));
        textPanel.add(titulo, BorderLayout.CENTER);
        textPanel.add(subtitulo, BorderLayout.SOUTH);

        headerPanel.add(textPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    public void cargarLocales(List<Local> locales) {
        tableModel.setRowCount(0);
        for (Local l : locales) {
            tableModel.addRow(new Object[]{
                    l.getId(),
                    l.getNombre(),
                    l.getDireccion(),
                    l.getCodigoPostal(),
                    l.getPoblacion(),
                    l.getProvincia(),
                    formatearTelefono(l.getTelefono()),
                    l.getM2()
            });
        }

        // Resaltar filas pares e impares
        tableLocales.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 248, 248));
                    }
                }

                // Centrar algunas columnas
                if (column == 0 || column == 3 || column == 7) { // ID, CP, m²
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });
    }

    private String formatearTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "No disponible";
        }
        // Formato básico para teléfono
        String soloNumeros = telefono.replaceAll("[^\\d]", "");
        if (soloNumeros.length() == 9) {
            return soloNumeros.replaceFirst("(\\d{3})(\\d{3})(\\d{3})", "$1 $2 $3");
        }
        return telefono;
    }

    public JTable getTableLocales() {
        return tableLocales;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnInicio() {
        return btnInicio;
    }

    public JButton getBtnPizzas() {
        return btnPizzas;
    }

    public JButton getBtnIngredientes() {
        return btnIngredientes;
    }
}