
import javax.swing.*;

final class BarcoPasajero extends Barco {

    private final String[] pasajeros;
    private final double precioBoleto;
    private int contadorPasajeros;

    public BarcoPasajero(String nombre, int capacidad, double precioBoleto) {
        super(nombre);
        this.pasajeros = new String[capacidad];
        this.precioBoleto = precioBoleto;
        this.contadorPasajeros = 0;
    }

    @Override
    public void agregarElemento() {
        if (contadorPasajeros < pasajeros.length) {
            String nombrePasajero = JOptionPane.showInputDialog("Ingrese el nombre del pasajero:");
            if (nombrePasajero != null && !nombrePasajero.isEmpty()) {
                pasajeros[contadorPasajeros++] = nombrePasajero;
            } else {
                JOptionPane.showMessageDialog(null, "Nombre del pasajero no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay espacio disponible en el barco.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public double vaciarCobrar() {
        double total = contadorPasajeros * precioBoleto;
        contadorPasajeros = 0; 
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + ", Cantidad de pasajeros: " + contadorPasajeros;
    }

    public void listarPasajeros() {
        StringBuilder mensaje = new StringBuilder("Pasajeros:\n");
        for (int i = 0; i < contadorPasajeros; i++) {
            mensaje.append(pasajeros[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, mensaje.toString(), "Listado de Pasajeros", JOptionPane.INFORMATION_MESSAGE);
    }
}
