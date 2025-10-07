package vista;

import model.Pizza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class PizzaView extends JFrame {
    private JButton btnInicio = new JButton("Inicio");
    private JButton btnLocales = new JButton("Locales");
    private JButton btnIngredientes = new JButton("Ingredientes");

    private JTable tablePizzas;
    private DefaultTableModel tableModel;
    private JButton btnAgregar = new JButton("Agregar Pizza");
    private JButton btnActualizar = new JButton("Actualizar Pizza");
    private JButton btnExportarJSON = new JButton("Exportar JSON");

    public PizzaView() {
        setTitle("Pizzería Cosa Nostra - Gestión de Pizzas");
        setSize(1300, 600);
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
        estilizarBoton(btnInicio, new Color(220, 20, 60));        // Rojo pizzeria
        estilizarBoton(btnLocales, new Color(255, 140, 0));       // Naranja
        estilizarBoton(btnIngredientes, new Color(34, 139, 34));  // Verde
        estilizarBotonAccion(btnExportarJSON, new Color(70, 130, 180)); //Azul acero

        panelIzquierdo.add(btnInicio);
        panelIzquierdo.add(btnLocales);
        panelIzquierdo.add(btnIngredientes);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel principal con header y contenido
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(250, 250, 245));
        panelPrincipal.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header
        JPanel headerPanel = crearHeader();
        panelPrincipal.add(headerPanel, BorderLayout.NORTH);

        // Panel con grid de pizzas
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Descripción", "Calorías", "Precio", "Tiempo Prep.", "Ingredientes"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Para mejorar la renderización de las columnas
                if (columnIndex == 3 || columnIndex == 4) { // Calorías y Precio
                    return Object.class;
                }
                return String.class;
            }
        };

        tablePizzas = new JTable(tableModel);
        tablePizzas.setSelectionBackground(new Color(255, 140, 0, 100)); // Naranja semitransparente
        tablePizzas.setSelectionForeground(Color.BLACK);
        tablePizzas.setRowHeight(35); // Más alto para mejor lectura
        tablePizzas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablePizzas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablePizzas.getTableHeader().setBackground(new Color(44, 44, 44));
        tablePizzas.getTableHeader().setForeground(Color.WHITE);
        tablePizzas.getTableHeader().setReorderingAllowed(false);

        // Personalizar ancho de columnas
        tablePizzas.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID
        tablePizzas.getColumnModel().getColumn(1).setPreferredWidth(180);  // Nombre
        tablePizzas.getColumnModel().getColumn(2).setPreferredWidth(300);  // Descripción
        tablePizzas.getColumnModel().getColumn(3).setPreferredWidth(90);   // Calorías
        tablePizzas.getColumnModel().getColumn(4).setPreferredWidth(90);   // Precio
        tablePizzas.getColumnModel().getColumn(5).setPreferredWidth(110);  // Tiempo Prep.
        tablePizzas.getColumnModel().getColumn(6).setPreferredWidth(250);  // Ingredientes

        // Renderizador personalizado para centrar columnas numéricas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i <= 5; i++) {
            if (i == 3 || i == 4 || i == 5) { // Calorías, Precio, Tiempo Prep.
                tablePizzas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tablePizzas);
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
        panelBotones.add(btnExportarJSON);

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
                BorderFactory.createLineBorder(new Color(255, 140, 0), 3), // Naranja para pizzas
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        JLabel titulo = new JLabel("Nuestro Menú de Pizzas", JLabel.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 28));
        titulo.setForeground(new Color(255, 215, 0)); // Dorado
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel subtitulo = new JLabel("Las auténticas pizzas italianas de la familia Cosa Nostra", JLabel.CENTER);
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

    public void cargarPizzas(List<Pizza> pizzas) {
        tableModel.setRowCount(0);
        for (Pizza p : pizzas) {
            String ingredientesStr = String.join(", ", p.getIngredientes());
            //System.out.println("Cargando pizza: " + p.getNombre() + " - Ingredientes: " + ingredientesStr);

            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getCalorias() + " cal",
                    String.format("€%.2f", p.getPrecio()),
                    p.getTiempoPreparacion() + " min",
                    ingredientesStr
            });
        }

        // Aplicar renderizador personalizado para colorear las filas
        tablePizzas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                // Estilo especial para columnas de precio
                if (column == 4 && value != null) {
                    c.setForeground(new Color(34, 139, 34)); // Verde para precios
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if (column == 3 && value != null) {
                    c.setForeground(new Color(220, 20, 60)); // Rojo para calorías
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }

                // Centrar columnas específicas
                if (column == 0 || column == 3 || column == 4 || column == 5) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });

        // Ajustar automáticamente el alto de las filas según el contenido
        tablePizzas.setRowHeight(35);
        for (int row = 0; row < tablePizzas.getRowCount(); row++) {
            int preferredHeight = tablePizzas.getRowHeight();
            for (int column = 0; column < tablePizzas.getColumnCount(); column++) {
                Component comp = tablePizzas.prepareRenderer(tablePizzas.getCellRenderer(row, column), row, column);
                preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
            }
            tablePizzas.setRowHeight(row, Math.min(preferredHeight, 100)); // Límite máximo
        }
    }
    public void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // Getters para los botones y componentes
    public JTable getTablePizzas() {
        return tablePizzas;
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

    public JButton getBtnLocales() {
        return btnLocales;
    }

    public JButton getBtnIngredientes() {
        return btnIngredientes;
    }

    public JButton getBtnExportarJSON() {
        return btnExportarJSON;
    }
}