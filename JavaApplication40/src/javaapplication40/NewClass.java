enum TipoPesquero {
    PEZ(10.0),
    CAMARON(15.0),
    LANGOSTA(20.0);

    public final double price;

    TipoPesquero(double price) {
        this.price = price;
    }
}
