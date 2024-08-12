
final class BarcoPesquero extends Barco {

    private int pecesCapturados;
    private final TipoPesquero tipoPesquero;

    public BarcoPesquero(String nombre, TipoPesquero tipoPesquero) {
        super(nombre);
        this.tipoPesquero = tipoPesquero;
        this.pecesCapturados = 0;
    }

    @Override
    public void agregarElemento() {
        pecesCapturados++;
    }

    @Override
    public double vaciarCobrar() {
        double total = pecesCapturados * tipoPesquero.price;
        pecesCapturados = 0;
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo de barco: " + tipoPesquero + ", Peces capturados: " + pecesCapturados;
    }
}
