/*
 *  Clase NumeroPrimo que ejecuta funcion de Main
 */
public class NumeroPrimo implements Runnable {

    // variable
    private double numero;

    /**
     * Constructor de la Clase
     *
     * @param num
     */
    NumeroPrimo(double num) {

        // Se entrega el valor del parametro a la variable numero
        this.numero = num;

    }

    /**
     * Ejecuta la funcion de Main
     */
    @Override
    public void run() {

        // Ejecuta el metodo de verificacion
        CalcularPrimos.esPrimo(this.numero);

    }
}