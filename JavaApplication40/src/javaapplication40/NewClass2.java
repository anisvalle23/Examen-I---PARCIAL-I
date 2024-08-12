import java.util.Scanner;

public class BarcoPasajero extends Barco {
    private final String[] pasajeros;
    private final double precioBoleto;
    private int contadorPasajeros;

    public BarcoPasajero(String nombre, int capacidad, double precioBoleto) {
        super(nombre);  // Llama al constructor de la clase base Barco
        this.pasajeros = new String[capacidad];
        this.precioBoleto = precioBoleto;
        this.contadorPasajeros = 0;  // Inicializa el contador de pasajeros en 0
    }

    @Override
    public void agregarElemento() {
        if (contadorPasajeros < pasajeros.length) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el nombre del pasajero: ");
            pasajeros[contadorPasajeros++] = scanner.nextLine();  // Agrega el pasajero al arreglo
        } else {
            System.out.println("No hay espacio disponible en el barco.");
        }
    }

    @Override
    public double vaciarCobrar() {
        double total = contadorPasajeros * precioBoleto;  // Calcula el total generado por el barco
        contadorPasajeros = 0;  // Vacía el barco
        return total;
    }

    @Override
    public double precioElemento() {
        return precioBoleto;  // Retorna el precio del boleto
    }

    @Override
    public String toString() {
        return super.toString() + ", Cantidad de pasajeros que compraron boleto: " + contadorPasajeros;
    }

    public void listarPasajeros() {
        listarPasajerosRecursivo(0);
    }

    private void listarPasajerosRecursivo(int indice) {
        if (indice < contadorPasajeros) {
            System.out.println(pasajeros[indice]);
            listarPasajerosRecursivo(indice + 1);
        }
    }
}
