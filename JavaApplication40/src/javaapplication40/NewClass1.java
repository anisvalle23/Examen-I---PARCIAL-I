public class BarcoPesquero extends Barco {
    private int pecesCapturados;
    private final TipoPesquero tipoPesquero;

    public BarcoPesquero(String nombre, TipoPesquero tipoPesquero) {
        super(nombre);  // Llama al constructor de la clase base Barco
        this.tipoPesquero = tipoPesquero;
        this.pecesCapturados = 0;  // Inicializa pecesCapturados en 0
    }

    @Override
    public void agregarElemento() {
        pecesCapturados++;  // Incrementa la cantidad de peces capturados
    }

    @Override
    public double vaciarCobrar() {
        double total = pecesCapturados * tipoPesquero.price;  // Calcula el total basado en el precio del tipo de pesquero
        pecesCapturados = 0;  // Vacía el barco
        return total;
    }

    @Override
    public double precioElemento() {
        return tipoPesquero.price;  // Retorna el precio por elemento según el tipo de pesquero
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo de barco: " + tipoPesquero.name() + ", Peces capturados: " + pecesCapturados;
    }
}
