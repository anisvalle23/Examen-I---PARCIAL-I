import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Muelle {

    private final ArrayList<Barco> barcos;
    private JTextArea outputArea;

    public Muelle() {
        barcos = new ArrayList<>();
        createAndShowGUI();
    }

    public static void main(String[] args) {
        new Muelle();
    }

    private void createAndShowGUI() {
        
        JFrame frame = new JFrame("Muelle - Gestión de Barcos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); 
        frame.setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 225)); 
        panel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel welcomeLabel = new JLabel("Bienvenido al Muelle");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        panel.add(welcomeLabel, gbc);

        
        JButton agregarBarcoBtn = createButton("Agregar Barco");
        JButton agregarElementoBtn = createButton("Agregar Elemento");
        JButton vaciarBarcoBtn = createButton("Vaciar Barco y Cobrar");
        JButton barcosDesdeBtn = createButton("Mostrar Barcos desde Año");

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        panel.add(agregarBarcoBtn, gbc);
        gbc.gridy = 2;
        panel.add(agregarElementoBtn, gbc);
        gbc.gridy = 3;
        panel.add(vaciarBarcoBtn, gbc);
        gbc.gridy = 4;
        panel.add(barcosDesdeBtn, gbc);

        outputArea = new JTextArea(20, 40); 
        outputArea.setEditable(false);
        outputArea.setLineWrap(true); 
        outputArea.setWrapStyleWord(true); 
        JScrollPane scrollPane = new JScrollPane(outputArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 4; 
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0; 
        panel.add(scrollPane, gbc);

        
        frame.add(panel);
        frame.setVisible(true);

        
        agregarBarcoBtn.addActionListener(e -> agregarBarco());
        agregarElementoBtn.addActionListener(e -> agregarElemento());
        vaciarBarcoBtn.addActionListener(e -> vaciarBarco());
        barcosDesdeBtn.addActionListener(e -> barcosDesde());
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40)); 
        return button;
    }

    public void agregarBarco() {
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
                try {
                    int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de pasajeros:"));
                    double precioBoleto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del boleto:"));
                    barcos.add(new BarcoPasajero(nombre, capacidad, precioBoleto));
                    outputArea.append("Barco de pasajeros agregado: " + nombre + " (Capacidad: " + capacidad + ")\n");
                } catch (NumberFormatException e) {
                    outputArea.append("Capacidad o precio no válido.\n");
                }
            }
        }
    }

    public void agregarElemento() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del barco:");
        if (nombre != null && !nombre.isEmpty()) {
            for (Barco barco : barcos) {
                if (barco.getNombre().equalsIgnoreCase(nombre)) {
                    barco.agregarElemento();
                    if (barco instanceof BarcoPasajero) {
                        outputArea.append("Pasajero agregado al barco: " + nombre + "\n");
                    } else {
                        outputArea.append("Elemento agregado al barco: " + nombre + "\n");
                    }
                    return;
                }
            }
            outputArea.append("No se encontró un barco con ese nombre.\n");
        }
    }

    public void vaciarBarco() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del barco:");
        if (nombre != null && !nombre.isEmpty()) {
            for (Barco barco : barcos) {
                if (barco.getNombre().equalsIgnoreCase(nombre)) {
                    StringBuilder mensaje = new StringBuilder(barco.toString() + "\n");
                    double total = barco.vaciarCobrar();
                    if (barco instanceof BarcoPasajero) {
                        ((BarcoPasajero) barco).listarPasajeros(); 
                    }
                    mensaje.append("Total cobrado: $").append(total).append("\n");
                    outputArea.append(mensaje.toString());
                    return;
                }
            }
            outputArea.append("No se encontró un barco con ese nombre.\n");
        }
    }

    public void barcosDesde() {
        String input = JOptionPane.showInputDialog("Ingrese el año:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int year = Integer.parseInt(input.trim());
                StringBuilder mensaje = new StringBuilder();
                barcosDesdeRecursivo(year, 0, mensaje);
                outputArea.append(mensaje.toString());
            } catch (NumberFormatException e) {
                outputArea.append("Entrada no válida. Por favor, ingrese un año válido.\n");
            }
        } else {
            outputArea.append("Operación cancelada o entrada vacía.\n");
        }
    }

    private void barcosDesdeRecursivo(int year, int indice, StringBuilder mensaje) {
        if (indice < barcos.size()) {
            Barco barco = barcos.get(indice);
            if (barco.getFechaCirculacion().getYear() >= year) {
                mensaje.append(barco.getNombre()).append(" - ").append(barco.getFechaCirculacion()).append("\n");
            }
            barcosDesdeRecursivo(year, indice + 1, mensaje);
        }
    }
}



