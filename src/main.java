import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


public class main {

    private static final Integer CANT_VAL = 100000;

    //private static final Integer speedupNumDec = 3;

    public static void main(String [] args) {


        final double tiempoSeq = calculoSecuencial();

        final double tiempoParalelo = calculoParalelo();

        calculoSpeedup(tiempoSeq,tiempoParalelo);

    }

    /**
     * Metodo que calcula el speedup.
     * @param tiempo1 El tiempo de ejecucion del algoritmo anterior.
     * @param tiempo2 El tiempo de ejecucion del algoritmo nuevo.
     */
    private static void calculoSpeedup(double tiempo1, double tiempo2){
        final double speedup = tiempo1/tiempo2;
        //System.out.println("Se consiguio un speedup de "+redondearValor(speedup,speedupNumDec)+"X");
        System.out.println("Speedup = "+speedup);
    }

    /**
     * Metodo secuencial de calculo de numeros primos.
     * @return Tiempo de ejecucion.
     */
    private static double calculoSecuencial() {

        final double tiempoIni, tiempoFin;
        tiempoIni = System.nanoTime();

        // Lista de numeros primos.
        List<Integer> valores = new ArrayList<>();

        // Agregar secuencialmente
        for (int i = 0; i <= CANT_VAL; i++) {
            if (esPrimo(i))
                valores.add(i);
        }

        tiempoFin = System.nanoTime() - tiempoIni;

        System.out.println("Secuencial = ("+tiempoFin/1000000+"ms)");
        System.out.println(Arrays.toString(valores.toArray()));

        return tiempoFin;
    }


    /**
     * Metodo para validar numeros primos
     *
     * @param numero
     * @return boolean del estado del numero
     */
    private static boolean esPrimo(int numero)
    {
        if (numero == 0 || numero == 1)
            return false;

        for (int i = 2; 2 * i <= numero; i++) {
            if (numero % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Metodo paralelo para calcular numeros primos en el rango.
     * @return Tiempo de calculo.
     */
    private static double calculoParalelo()
    {
        final double tiempoIni, tiempoFin;
        tiempoIni = System.nanoTime();

        // Obtener la cantidad de nucleos.
        final int cantNucleos = Runtime.getRuntime().availableProcessors();

        // Cantidad de numeros por cada hilo.
        final int numsPorHilo = (CANT_VAL /cantNucleos);

        // El tamaño del pool sera la cantidad de nucleos.
        final ExecutorService pool = Executors.newFixedThreadPool(cantNucleos);

        // Lista de futuros.
        List<Future> futures = new ArrayList<>();

        // Lista para numeros
        List<Integer> valores = new ArrayList<>();


        for (int i = 0; i < cantNucleos; i++) {
            int i1 = i;

            // Ejecutar Callable, guarda la var en future
            final Future<List<Integer>> future = pool.submit(() -> {

                List<Integer> numsDelHilo = new ArrayList<>();

                int limite;
                if (i1 == cantNucleos - 1) {
                    limite = CANT_VAL;
                } else {
                    limite = ((i1 + 1) * numsPorHilo) - 1;
                }
                // verificar
                for (int j = (i1)*numsPorHilo; j <= limite; j++) {
                    if (esPrimo(j))
                        numsDelHilo.add(j);
                }
                return numsDelHilo;
            });

            // Agrega el valor a la lista
            futures.add(future);
        }

        pool.shutdown();

        // Unir las listas
        for (Future future : futures) {

            try {
                List<Integer> resultado = (List<Integer>) future.get();
                for (int i = 0; i < resultado.size(); i++) {
                    valores.add(resultado.get(i));
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        tiempoFin = System.nanoTime() - tiempoIni;

        System.out.println("Paralelo: ("+tiempoFin/1000000+"ms)");
        System.out.println(Arrays.toString(valores.toArray()));

        return tiempoFin;
    }

}