import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MuelleGUI {
    private final ArrayList<Barco> barcos;

    public MuelleGUI() {
        barcos = new ArrayList<>();
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MuelleGUI::new);
    }

    private void createAndShowGUI() {
        // Crear el marco principal
        JFrame frame = new JFrame("Muelle - Gestión de Barcos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 222, 220)); // Color rosado nude
        panel.setLayout(new GridBagLayout()); // Centrar el contenido
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margen alrededor de los componentes

        // Etiqueta de bienvenida
        JLabel welcomeLabel = new JLabel("Bienvenido al Muelle");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        // Botones del menú
        JButton agregarBarcoBtn = new JButton("Agregar Barco");
        JButton agregarElementoBtn = new JButton("Agregar Elemento");
        JButton vaciarBarcoBtn = new JButton("Vaciar Barco y Cobrar");
        JButton barcosDesdeBtn = new JButton("Mostrar Barcos desde Año");

        // Configuración de botones
        agregarBarcoBtn.setPreferredSize(new Dimension(200, 30));
        agregarElementoBtn.setPreferredSize(new Dimension(200, 30));
        vaciarBarcoBtn.setPreferredSize(new Dimension(200, 30));
        barcosDesdeBtn.setPreferredSize(new Dimension(200, 30));

        // Agregar botones al panel
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(agregarBarcoBtn, gbc);
        gbc.gridy = 2;
        panel.add(agregarElementoBtn, gbc);
        gbc.gridy = 3;
        panel.add(vaciarBarcoBtn, gbc);
        gbc.gridy = 4;
        panel.add(barcosDesdeBtn, gbc);

        // Área de texto para mostrar resultados
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Asignar acciones a los botones
        agregarBarcoBtn.addActionListener(e -> agregarBarco(outputArea));
        agregarElementoBtn.addActionListener(e -> agregarElemento(outputArea));
        vaciarBarcoBtn.addActionListener(e -> vaciarBarco(outputArea));
        barcosDesdeBtn.addActionListener(e -> barcosDesde(outputArea));

        // Agregar el panel al marco
        frame.add(panel);
        frame.setVisible(true);
    }

    private void agregarBarco(JTextArea outputArea) {
        String[] options = {"PESQUERO", "PASAJERO"};
        String tipo = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de barco:", 
            "Agregar Barco", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (tipo != null) {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del barco:");

            if (nombre == null || nombre.isEmpty()) {
                outputArea.append("Nombre de barco no válido.\n");
                return;
            }

            for (Barco barco : barcos) {
                if (barco.getNombre().equalsIgnoreCase(nombre)) {
                    outputArea.append("Ya existe un barco con ese nombre.\n");
                    return;
                }
            }

            if (tipo.equals("PESQUERO")) {
                String[] tiposPesquero = {"PEZ", "CAMARON", "LANGOSTA"};
                TipoPesquero tipoPesquero = TipoPesquero.valueOf(
                        (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de pesquero:", 
                            "Tipo de Pesquero", JOptionPane.QUESTION_MESSAGE, null, tiposPesquero, tiposPesquero[0]));

                barcos.add(new BarcoPesquero(nombre, tipoPesquero));
                outputArea.append("Barco pesquero agregado: " + nombre + " (" + tipoPesquero + ")\n");

            } else if (tipo.equals("PASAJERO")) {
                int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de pasajeros:"));
                double precioBoleto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del boleto:"));
                barcos.add(new BarcoPasajero(nombre, capacidad, precioBoleto));
                outputArea.append("Barco de pasajeros agregado: " + nombre + " (Capacidad: " + capacidad + ")\n");
            }
        }
    }

    private void agregarElemento(JTextArea outputArea) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del barco:");
        if (nombre != null && !nombre.isEmpty()) {
            for (Barco barco : barcos) {
                if (barco.getNombre().equalsIgnoreCase(nombre)) {
                    barco.agregarElemento();
                    outputArea.append("Elemento agregado al barco: " + nombre + "\n");
                    return;
                }
            }
            outputArea.append("No se encontró un barco con ese nombre.\n");
        }
    }

    private void vaciarBarco(JTextArea outputArea) {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del barco:");
        if (nombre != null && !nombre.isEmpty()) {
            for (Barco barco : barcos) {
                if (barco.getNombre().equalsIgnoreCase(nombre)) {
                    outputArea.append(barco.toString() + "\n");
                    double total = barco.vaciarCobrar();
                    if (barco instanceof BarcoPasajero) {
                        ((BarcoPasajero) barco).listarPasajeros();
                    }
                    outputArea.append("Total cobrado: $" + total + "\n");
                    return;
                }
            }
            outputArea.append("No se encontró un barco con ese nombre.\n");
        }
    }

    private void barcosDesde(JTextArea outputArea) {
        int year = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año:"));
        barcosDesdeRecursivo(outputArea, year, 0);
    }

    private void barcosDesdeRecursivo(JTextArea outputArea, int year, int indice) {
        if (indice < barcos.size()) {
            Barco barco = barcos.get(indice);
            if (barco.getFechaCirculacion().getYear() >= year) {
                outputArea.append(barco.getNombre() + " - " + barco.getFechaCirculacion() + "\n");
            }
            barcosDesdeRecursivo(outputArea, year, indice + 1);
        }
    }
}

