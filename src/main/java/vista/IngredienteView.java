package vista;

import model.Ingrediente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IngredienteView extends JFrame {
    private JButton btnInicio = new JButton("Inicio");
    private JButton btnLocales = new JButton("Locales");
    private JButton btnPizzas = new JButton("Pizzas");

    private JTable tableIngredientes;
    private DefaultTableModel tableModel;
    private JButton btnAgregar = new JButton("Agregar Ingrediente");
    private JButton btnActualizar = new JButton("Actualizar Ingrediente");

    public IngredienteView() {
        setTitle("Pizzería Cosa Nostra - Gestión de Ingredientes");
        setSize(1200, 600);
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
        estilizarBoton(btnLocales, new Color(255, 140, 0));    // Naranja
        estilizarBoton(btnPizzas, new Color(34, 139, 34));     // Verde

        panelIzquierdo.add(btnInicio);
        panelIzquierdo.add(btnLocales);
        panelIzquierdo.add(btnPizzas);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel principal con header y contenido
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(250, 250, 245));
        panelPrincipal.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Header
        JPanel headerPanel = crearHeader();
        panelPrincipal.add(headerPanel, BorderLayout.NORTH);

        // Panel derecho con grid de ingredientes
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Proveedor", "Nombre Proveedor", "Gluten", "Lácteos", "Huevos", "Picante"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que ninguna celda sea editable directamente
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Para mejorar la renderización de las columnas
                if (columnIndex >= 4 && columnIndex <= 7) { // Columnas SI/NO
                    return String.class;
                }
                return Object.class;
            }
        };

        tableIngredientes = new JTable(tableModel);
        tableIngredientes.setSelectionBackground(new Color(34, 139, 34, 100)); // Verde semitransparente
        tableIngredientes.setSelectionForeground(Color.BLACK);
        tableIngredientes.setRowHeight(30);
        tableIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableIngredientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableIngredientes.getTableHeader().setBackground(new Color(44, 44, 44));
        tableIngredientes.getTableHeader().setForeground(Color.WHITE);
        tableIngredientes.getTableHeader().setReorderingAllowed(false);

        // Personalizar ancho de columnas
        tableIngredientes.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID
        tableIngredientes.getColumnModel().getColumn(1).setPreferredWidth(150);  // Nombre
        tableIngredientes.getColumnModel().getColumn(2).setPreferredWidth(100);  // Proveedor ID
        tableIngredientes.getColumnModel().getColumn(3).setPreferredWidth(150);  // Nombre Proveedor
        tableIngredientes.getColumnModel().getColumn(4).setPreferredWidth(80);   // Gluten
        tableIngredientes.getColumnModel().getColumn(5).setPreferredWidth(80);   // Lácteos
        tableIngredientes.getColumnModel().getColumn(6).setPreferredWidth(80);   // Huevos
        tableIngredientes.getColumnModel().getColumn(7).setPreferredWidth(80);   // Picante

        // Renderizador personalizado para columnas SI/NO
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 4; i <= 7; i++) {
            tableIngredientes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tableIngredientes);
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
                BorderFactory.createLineBorder(new Color(34, 139, 34), 3), // Verde para ingredientes
                BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));

        JLabel titulo = new JLabel("Gestión de Ingredientes", JLabel.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 28));
        titulo.setForeground(new Color(255, 215, 0)); // Dorado
        titulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel subtitulo = new JLabel("Ingredientes artesanales de la más alta calidad", JLabel.CENTER);
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

    public void cargarIngredientes(List<Ingrediente> ingredientes) {
        tableModel.setRowCount(0);
        for (Ingrediente i : ingredientes) {
            tableModel.addRow(new Object[]{
                    i.getId(),
                    i.getNombre(),
                    i.getProveedor(),
                    i.getNombreProveedor(),
                    formatearSiNo(i.isGluten()),
                    formatearSiNo(i.isLacteos()),
                    formatearSiNo(i.isHuevos()),
                    formatearSiNo(i.isPicante())
            });
        }

        // Aplicar renderizador personalizado para colorear las filas
        tableIngredientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                // Colorear las columnas SI/NO
                if (column >= 4 && column <= 7) {
                    if ("SI".equals(value)) {
                        c.setForeground(new Color(220, 20, 60)); // Rojo para SI
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(new Color(34, 139, 34)); // Verde para NO
                        setFont(getFont().deriveFont(Font.BOLD));
                    }
                } else {
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }

                // Centrar columnas específicas
                if (column == 0 || (column >= 4 && column <= 7)) {
                    setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }

                return c;
            }
        });
    }

    private String formatearSiNo(boolean valor) {
        return valor ? "SI" : "NO";
    }

    // Getters para los botones y componentes
    public JTable getTableIngredientes() {
        return tableIngredientes;
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

    public JButton getBtnPizzas() {
        return btnPizzas;
    }
}