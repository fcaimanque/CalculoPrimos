import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Fernando Caimanque
 */


public class CalcularPrimos {
    /**
     * Constante de la cantidad maxima de hilos
     */
    private static final int CANT_HILOS = 16;
    /**
     * Constante de la cantidad maximo de primos
     */
    private static final int MAX_PRIMOS = 600000;


    /**
     * Main del programa
     *
     * @param args
     */
    public static void main(String[] args) {

        // Ciclo que itera en el rango definido por su maximo y minimo
        for (int cantPrimos = (100 * 1000); cantPrimos <= MAX_PRIMOS; cantPrimos += (100 * 1000)) {

            double tiempoPrev = 0;

            System.out.println("Total a calcular = " + cantPrimos);

            // Ciclo que itera por la cantidad de hilos usados
            for (int cantHilos = 1; cantHilos <= CANT_HILOS; cantHilos++) {

                // Se mide el tiempo inicial del programa
                long tiempoIni = System.currentTimeMillis();

                // Se define la cantidad de hilos a usar
                ExecutorService service = Executors.newFixedThreadPool(cantHilos);

                // Ciclo que asigna al hilo la tarea
                for (double i = 2; i <= cantPrimos; i++) {

                    // Se ejecuta la tarea
                    service.submit(new NumeroPrimo(i));

                }

                // Se termina service
                service.shutdown();

                // Se calcula la diferencia entre tiempo inicial y final, obteniendose el tiempo de ejecucion
                long resultado = System.currentTimeMillis() - tiempoIni;

                double tiempoFin = (double) resultado;

                if (cantHilos == 1) {

                    tiempoPrev = tiempoFin;

                }

                double speedup = (tiempoPrev / tiempoFin);

                System.out.println("Cantidad de Hilos: " + cantHilos + "| Tiempo: " + tiempoFin + " msec    | Speed-Up: " + speedup + " sec");
            }
        }
    }

    /**
     * Verificacion de numeros primos
     *
     * @param numero que se evalua
     */
    static void esPrimo(double numero) {

        int contador = 2;

        boolean primo = true;

        if (numero == 0 || numero == 1){
            primo = false;
        }

        for (int i = 2; 2 * i <= numero; i++) {
            if (numero % i == 0) {
                primo = false;
            }
            contador++;
        }
        primo = true;
    }
}
