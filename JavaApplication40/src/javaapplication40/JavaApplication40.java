import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Barco {
    private final String nombre;
    private final LocalDate fechaCirculacion;

    public Barco(String nombre) {
        this.nombre = nombre;
        this.fechaCirculacion = LocalDate.now(); // Inicializa con la fecha actual
    }

    public final String getNombre() {
        return nombre;
    }

    public final LocalDate getFechaCirculacion() {
        return fechaCirculacion;
    }

    @Override
    public String toString() {
        return "Nombre del barco: " + nombre;
    }

    public abstract void agregarElemento();
    public abstract double vaciarCobrar();
    public abstract double precioElemento();
}
