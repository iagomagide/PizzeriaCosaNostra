package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LandingPageView extends JFrame {
    private JButton btnLocales = new JButton("Nuestros Locales");
    private JButton btnPizzas = new JButton("Nuestras Pizzas");
    private JButton btnIngredientes = new JButton("Ingredientes Artesanales");
    private JPanel contentPanel;

    public LandingPageView() {
        setTitle("Pizzería Cosa Nostra - Tradición Italiana");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        aplicarEstiloModerno();

        // Panel izquierdo con botones de navegación
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(3, 1, 15, 15));
        panelIzquierdo.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelIzquierdo.setBackground(new Color(44, 44, 44));

        // Estilizar botones CON IMÁGENES
        estilizarBotonConImagen(btnLocales, new Color(220, 20, 60), crearIconoLocal());
        estilizarBotonConImagen(btnPizzas, new Color(255, 140, 0), crearIconoPizza());
        estilizarBotonConImagen(btnIngredientes, new Color(34, 139, 34), crearIconoIngrediente());

        panelIzquierdo.add(btnLocales);
        panelIzquierdo.add(btnPizzas);
        panelIzquierdo.add(btnIngredientes);
        add(panelIzquierdo, BorderLayout.WEST);

        // Panel principal con contenido
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(250, 250, 245));
        add(contentPanel, BorderLayout.CENTER);
    }

    private void aplicarEstiloModerno() {
        try {
            UIManager.put("ScrollPane.background", new Color(250, 250, 245));
            UIManager.put("Viewport.background", new Color(250, 250, 245));
        } catch (Exception e) {
            System.out.println("Error en personalización de colores: " + e.getMessage());
        }
    }

    private void estilizarBotonConImagen(JButton boton, Color colorFondo, ImageIcon icono) {
        if (icono != null) {
            Image imagen = icono.getImage();
            Image imagenEscalada = imagen.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenEscalada));
        }

        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setIconTextGap(15);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorFondo.darker(), 3),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
                boton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(colorFondo.darker(), 2),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }
        });
    }

    // MÉTODOS PARA CREAR ICONOS DE BOTONES
    private ImageIcon crearIconoLocal() {
        return crearIconoPersonalizado("🏪", new Color(255, 255, 255), 32);
    }

    private ImageIcon crearIconoPizza() {
        return crearIconoPersonalizado("🍕", new Color(255, 255, 255), 32);
    }

    private ImageIcon crearIconoIngrediente() {
        return crearIconoPersonalizado("🥬", new Color(255, 255, 255), 32);
    }

    // MÉTODOS PARA CREAR ICONOS DE SECCIONES (MÁS GRANDES)
    private ImageIcon crearIconoQuienesSomos() {
        return crearIconoPersonalizado("👨‍🍳", new Color(220, 20, 60), 48);
    }

    private ImageIcon crearIconoPasion() {
        return crearIconoPersonalizado("❤️", new Color(255, 140, 0), 48);
    }

    private ImageIcon crearIconoExperiencia() {
        return crearIconoPersonalizado("⭐", new Color(34, 139, 34), 48);
    }

    private ImageIcon crearIconoPersonalizado(String emoji, Color colorTexto, int size) {
        BufferedImage imagen = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagen.createGraphics();

        // Configurar calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Fondo transparente
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, size, size);

        // Dibujar el emoji
        g2d.setColor(colorTexto);
        int fontSize = size * 3 / 4; // Tamaño de fuente proporcional al icono
        g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, fontSize));

        FontMetrics fm = g2d.getFontMetrics();
        int x = (size - fm.stringWidth(emoji)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();

        g2d.drawString(emoji, x, y);
        g2d.dispose();

        return new ImageIcon(imagen);
    }

    public void mostrarContenido(String quienesSomos, String amorProductos, String experiencia) {
        contentPanel.removeAll();

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(250, 250, 245));
        mainContent.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header con logo y título
        JPanel headerPanel = crearHeader();
        mainContent.add(headerPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sección Quiénes Somos CON ICONO
        if (quienesSomos != null && !quienesSomos.isEmpty()) {
            mainContent.add(crearSeccionConIcono("Quiénes Somos", quienesSomos,
                    new Color(220, 20, 60), crearIconoQuienesSomos()));
            mainContent.add(Box.createRigidArea(new Dimension(0, 25)));
        }

        // Sección Amor por los Productos CON ICONO
        if (amorProductos != null && !amorProductos.isEmpty()) {
            mainContent.add(crearSeccionConIcono("Nuestra Pasión", amorProductos,
                    new Color(255, 140, 0), crearIconoPasion()));
            mainContent.add(Box.createRigidArea(new Dimension(0, 25)));
        }

        // Sección Experiencia CON ICONO
        if (experiencia != null && !experiencia.isEmpty()) {
            mainContent.add(crearSeccionConIcono("La Experiencia Cosa Nostra", experiencia,
                    new Color(34, 139, 34), crearIconoExperiencia()));
        }

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(250, 250, 245));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // NUEVO MÉTODO: Crear sección con icono
    private JPanel crearSeccionConIcono(String titulo, String contenido, Color color, ImageIcon icono) {
        JPanel seccionPanel = new JPanel();
        seccionPanel.setLayout(new BorderLayout(15, 15));
        seccionPanel.setBackground(Color.WHITE);
        seccionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        seccionPanel.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        // Panel superior con icono y título
        JPanel panelTitulo = new JPanel(new BorderLayout(15, 0));
        panelTitulo.setBackground(Color.WHITE);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Icono
        JLabel iconoLabel = new JLabel(icono);
        iconoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Título
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tituloLabel.setForeground(color);

        panelTitulo.add(iconoLabel, BorderLayout.WEST);
        panelTitulo.add(tituloLabel, BorderLayout.CENTER);

        // Contenido
        JTextArea contenidoArea = new JTextArea(contenido);
        contenidoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contenidoArea.setLineWrap(true);
        contenidoArea.setWrapStyleWord(true);
        contenidoArea.setEditable(false);
        contenidoArea.setBackground(Color.WHITE);
        contenidoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Decoración lateral
        JPanel decoracionPanel = new JPanel();
        decoracionPanel.setBackground(color);
        decoracionPanel.setPreferredSize(new Dimension(5, 0));

        seccionPanel.add(panelTitulo, BorderLayout.NORTH);
        seccionPanel.add(decoracionPanel, BorderLayout.WEST);
        seccionPanel.add(new JScrollPane(contenidoArea), BorderLayout.CENTER);

        return seccionPanel;
    }

    // MÉTODO ORIGINAL (por si acaso)
    private JPanel crearSeccion(String titulo, String contenido, Color color) {
        return crearSeccionConIcono(titulo, contenido, color, crearIconoPorDefecto(titulo, color));
    }

    private ImageIcon crearIconoPorDefecto(String titulo, Color color) {
        String emoji = "⭐";
        if (titulo.contains("Quiénes")) emoji = "👨‍🍳";
        else if (titulo.contains("Pasión")) emoji = "❤️";
        else if (titulo.contains("Experiencia")) emoji = "⭐";
        return crearIconoPersonalizado(emoji, color, 48);
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(44, 44, 44));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 20, 60), 3),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        JLabel titulo = new JLabel("Pizzería Cosa Nostra", JLabel.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 36));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel subtitulo = new JLabel("Auténtica Tradición Italiana desde 1985", JLabel.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitulo.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(new Color(44, 44, 44));
        textPanel.add(titulo, BorderLayout.CENTER);
        textPanel.add(subtitulo, BorderLayout.SOUTH);

        headerPanel.add(textPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    public void setBotonLocalesListener(ActionListener listener) {
        btnLocales.addActionListener(listener);
    }

    public void setBotonPizzasListener(ActionListener listener) {
        btnPizzas.addActionListener(listener);
    }

    public void setBotonIngredientesListener(ActionListener listener) {
        btnIngredientes.addActionListener(listener);
    }
}