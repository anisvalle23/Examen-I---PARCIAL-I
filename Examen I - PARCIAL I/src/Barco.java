
import java.time.LocalDate;

abstract class Barco {

    private final String nombre;
    private final LocalDate fechaCirculacion;

    public Barco(String nombre) {
        this.nombre = nombre;
        this.fechaCirculacion = LocalDate.now();
    }

    public final String getNombre() {
        return nombre;
    }

    public final LocalDate getFechaCirculacion() {
        return fechaCirculacion;
    }

    public abstract void agregarElemento();

    public abstract double vaciarCobrar();

    @Override
    public String toString() {
        return "Nombre del barco: " + nombre;
    }
}
