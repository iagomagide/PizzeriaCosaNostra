package vista;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class IdiomaView extends JFrame {
    private JComboBox<String> comboIdioma = new JComboBox<>(new String[]{"ES - Español", "EN - English", "GAL - Galego"});
    private JButton btnAceptar = new JButton("Aceptar");

    public IdiomaView() {
        setTitle("Pizzería Cosa Nostra - Selección de Idioma");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // DISEÑO ABSOLUTO
        getContentPane().setBackground(new Color(250, 250, 245));

        // Header
        JPanel headerPanel = crearHeader();
        headerPanel.setBounds(0, 0, 450, 100);
        add(headerPanel);

        // Etiqueta
        JLabel etiqueta = new JLabel("Selecciona tu idioma preferido:");
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 16));
        etiqueta.setForeground(new Color(44, 44, 44));
        etiqueta.setBounds(75, 110, 300, 30);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        add(etiqueta);

        // ComboBox - POSICIÓN ABSOLUTA
        comboIdioma.setBounds(125, 150, 200, 40);
        estilizarComboBox(comboIdioma);
        add(comboIdioma);

        // Botón - POSICIÓN ABSOLUTA
        btnAceptar.setBounds(175, 210, 100, 40);
        estilizarBotonAceptar(btnAceptar);
        add(btnAceptar);

        comboIdioma.setSelectedIndex(0);
    }

    private JPanel crearHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(44, 44, 44));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 20, 60), 3),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titulo = new JLabel("Bienvenido a Cosa Nostra", JLabel.CENTER);
        titulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
        titulo.setForeground(new Color(255, 215, 0));

        JLabel subtitulo = new JLabel("Auténtica Pizzería Italiana", JLabel.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        subtitulo.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(new Color(44, 44, 44));
        textPanel.add(titulo, BorderLayout.CENTER);
        textPanel.add(subtitulo, BorderLayout.SOUTH);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        return headerPanel;
    }

    private void estilizarComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(44, 44, 44));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    private void estilizarBotonAceptar(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(220, 20, 60));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 10, 50), 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getIdiomaSeleccionado() {
        String seleccion = (String) comboIdioma.getSelectedItem();
        if (seleccion != null) {
            return seleccion.substring(0, 2);
        }
        return "ES";
    }

    public void setAceptarListener(ActionListener listener) {
        btnAceptar.addActionListener(listener);
    }
}
