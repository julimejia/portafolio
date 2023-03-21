import java.util.*;

public class aeropuerto {
    public static int salidas;

    class Node implements Comparable<Node> {
        private int tipo;
        private int Costo;
        private List<Node> neighbors;

        public Node(int tipo) {
            this.tipo = tipo;
            neighbors = new ArrayList<>();
            this.Costo = Integer.MAX_VALUE;
        }

        public void agregarArista(Node neighbor) {
            if (!neighbors.contains(neighbor)) {
                neighbors.add(neighbor);
            }
        }

        public int getTipo() {
            return this.tipo;
        }

        public int getCosto() {
            return this.Costo;
        }

        public void SetCosto(int costo) {
            this.Costo = costo;
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        @Override
        public int compareTo(Node otroNodo) {
            return Integer.compare(this.Costo, otroNodo.getCosto());
        }
    }

    class grafo {
        private Map<Integer, aeropuerto.Node> nodos;

        public grafo() {
            nodos = new HashMap<>();
        }

        public void agregarNodo(int tipo) {
            Node nodo = new Node(tipo);
            nodos.put(tipo, nodo);
        }

        public void agregarArista(int origen, int destino) {
            Node nodoOrigen = nodos.get(origen);
            Node nodoDestino = nodos.get(destino);
            nodoOrigen.agregarArista(nodoDestino);
        }

        public Node obtenerNodo(int tipo) {
            return nodos.get(tipo);
        }

        public List<Node> obtenerNodos() {
            return new ArrayList<Node>(nodos.values());
        }
    }

    public static void matrizAGrafo(grafo grafo, int[][] matriz) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                int tipoOrigen = matriz[i][j];

                Node nodoOrigen = grafo.obtenerNodo(tipoOrigen);
                if (nodoOrigen == null) {
                    continue;
                }

                if (i > 0) {
                    int tipoVecino = matriz[i - 1][j];
                    Node nodoVecino = grafo.obtenerNodo(tipoVecino);
                    if (nodoVecino != null) {
                        nodoOrigen.agregarArista(nodoVecino);
                        nodoVecino.agregarArista(nodoOrigen);
                    }
                }

                if (i < filas - 1) {
                    int tipoVecino = matriz[i + 1][j];
                    Node nodoVecino = grafo.obtenerNodo(tipoVecino);
                    if (nodoVecino != null) {
                        nodoOrigen.agregarArista(nodoVecino);
                        nodoVecino.agregarArista(nodoOrigen);
                    }
                }

                if (j > 0) {
                    int tipoVecino = matriz[i][j - 1];
                    Node nodoVecino = grafo.obtenerNodo(tipoVecino);
                    if (nodoVecino != null) {
                        nodoOrigen.agregarArista(nodoVecino);
                        nodoVecino.agregarArista(nodoOrigen);
                    }
                }

                if (j < columnas - 1) {
                    int tipoVecino = matriz[i][j + 1];
                    Node nodoVecino = grafo.obtenerNodo(tipoVecino);
                    if (nodoVecino != null) {
                        nodoOrigen.agregarArista(nodoVecino);
                        nodoVecino.agregarArista(nodoOrigen);
                    }
                }
            }
        }
    }

    public static void imprimir(grafo grafo) {
        for (Node nodo : grafo.obtenerNodos()) {
            List<Integer> vecinos = new ArrayList<>();
            for (Node vecino : nodo.neighbors) {
                vecinos.add(vecino.tipo);
            }
            System.out.println("Nodo " + nodo.tipo + ": " + nodo.Costo + " " + vecinos);
        }
    }

    public static void imprimir(int matriz[][], grafo grafo, List<Integer> aterrizaje) {

        int filas = matriz.length;
        int columnas = matriz[0].length;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j] + " | ");
            }
            System.out.println();
        }

        System.out.println(aterrizaje.toString());
        for (Node nodo : grafo.obtenerNodos()) {
            List<Integer> vecinos = new ArrayList<>();
            for (Node vecino : nodo.neighbors) {
                vecinos.add(vecino.tipo);
            }
            System.out.println("Nodo " + nodo.tipo + ": " + nodo.Costo + " " + vecinos);
        }
    }

    public static void ponderar(grafo grafo) {

        PriorityQueue<aeropuerto.Node> prioridadOrdenamiento = new PriorityQueue<aeropuerto.Node>();

        for (Node nodo : grafo.obtenerNodos()) {
            if (salidas > 0 && nodo.tipo >= 1000 && nodo.Costo != -Integer.MAX_VALUE) {

                nodo.SetCosto(0);
                for (Node otroNodo : (List<Node>) nodo.neighbors) {
                    if (otroNodo.tipo > 0 && otroNodo.tipo < 1000) {
                        otroNodo.SetCosto(nodo.Costo + 1);
                        prioridadOrdenamiento.add(otroNodo);
                    }
                    if (otroNodo.tipo <= -10) {
                        otroNodo.SetCosto(nodo.Costo);
                        prioridadOrdenamiento.add(otroNodo);
                    }
                }
            }
        }

        while (!prioridadOrdenamiento.isEmpty()) {
            ponderar(grafo, prioridadOrdenamiento.poll(), prioridadOrdenamiento);
        }
    }

    public static void ponderar(grafo grafo, Node nodito, PriorityQueue<aeropuerto.Node> prioridadOrdenamiento) {
        for (Node nodo : (List<Node>) nodito.neighbors) {
            if (nodito.Costo < nodo.Costo && nodo.Costo != -Integer.MAX_VALUE && nodito.Costo != -Integer.MAX_VALUE) {
                if (nodo.tipo > 0 && nodo.tipo < 1000) {
                    nodo.SetCosto(nodito.Costo + 1);
                    prioridadOrdenamiento.add(nodo);
                }
                if (nodo.tipo <= -10) {
                    nodo.SetCosto(nodito.Costo);

                    prioridadOrdenamiento.add(nodo);

                }
            } else {
                continue;
            }
        }
        while (!prioridadOrdenamiento.isEmpty()) {
            ponderar(grafo, prioridadOrdenamiento.poll(), prioridadOrdenamiento);
        }
    }

    public static boolean backtrack1ng(grafo grafo, Deque<Integer> llegadaYsalida, int[][] matriz,  aeropuerto aeropuerto) {//Stack pila
        int sale = 0;
        Node no;
        if (llegadaYsalida.isEmpty()) {
            return true;
        }
        System.out.println(llegadaYsalida);
        int AvionEntraysale = llegadaYsalida.pop();
        aeropuerto.Node max = aeropuerto.new Node(1);

        if (AvionEntraysale >= 1) {
            max.SetCosto(-max.Costo);
            for (Node nodo : grafo.obtenerNodos()) { //busco todos aquellos que sean aeropuerto
                if (nodo.tipo >= 1000) {

                    no = busquedaDelMayorCosto(nodo); //bfs para el nodo de mayor costo y asignar allí algun avion
                    if (no.Costo > max.Costo) {
                        max = no;
                    }

                } else {
                    continue;
                }
                max.SetCosto(-Integer.MAX_VALUE);
                ponderar(grafo);
                //imprimir(grafo);
                matriz[AvionEntraysale- 1][1] = max.tipo;
                if(backtrack1ng(grafo,llegadaYsalida, matriz,  aeropuerto) == true)
                    return  true;
                matriz[AvionEntraysale - 1][1] = 0;
                llegadaYsalida.push(AvionEntraysale);

            }
            return false;



        }else{
            System.out.println("sale"+llegadaYsalida);
          sale =  matriz[Math.abs(AvionEntraysale)-1][1];
            System.out.println("sale el avion"+sale);
            System.out.println(sale);
            for (Node nodo : grafo.obtenerNodos()) {
                if(nodo.tipo == sale){
                    for (Node nodito: nodo.neighbors) {
                        if(nodito.Costo > -Integer.MAX_VALUE){
                            nodo.SetCosto(15);
                            ponderar(grafo);
                            imprimir(grafo);
                           // System.out.println(nodo.tipo);
                           // System.out.println(nodo.getCosto());
                           // System.out.println("sali saliii");
                            if(backtrack1ng(grafo,llegadaYsalida, matriz,  aeropuerto) == true)
                                return  true;
                        }else{
                            continue;
                        }
                    }
                    return false;
                }else{
                    continue;
                }

            }



            }

        return false;
    }


    public static Node busquedaDelMayorCosto(Node nodoInicial) {// bfs
        Queue<Node> cola = new LinkedList<>();
        Set<Node> visitados = new HashSet<>();
        Node nodoMaxCosto = nodoInicial;
        cola.add(nodoInicial);
        visitados.add(nodoInicial);

        while (!cola.isEmpty()) {
            Node nodoActual = cola.poll();
            for (Node vecino : nodoActual.getNeighbors()) {
                if (!visitados.contains(vecino)) {
                    cola.add(vecino);
                    visitados.add(vecino);
                    if (vecino.getCosto() > nodoMaxCosto.getCosto() && vecino.getCosto() < Integer.MAX_VALUE) {
                        nodoMaxCosto = vecino;
                    }
                }
            }
        }

        return nodoMaxCosto;
    }

    public static int[][] SetMatrix(int aviones) {
        int[][] matrix = new int[aviones][2];
        for (int i = 0; i < aviones; i++) {
            matrix[i][0] = i + 1;
            matrix[i][1] = 0;
        }


        return matrix;
    }

    public static void main(String[] args) {

        aeropuerto aeropuerto = new aeropuerto();
        aeropuerto.grafo grafo = aeropuerto.new grafo();

        Scanner sc = new Scanner(System.in);
        int aviones = sc.nextInt();
        int filas = sc.nextInt();
        int columnas = sc.nextInt();
        int ayudante;
        int[][] matriz = new int[filas][columnas];
        int v1 = 0, v2 = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                String valor = sc.next();
                if (valor.equals("..")) {
                    valor = String.valueOf(-10 + v1);
                    v1--;
                }
                if (valor.equals("##")) {
                    valor = String.valueOf(5000);
                }
                if (valor.equals("==")) {
                    valor = String.valueOf(1000 + v2);
                    v2++;
                    salidas++;
                }
                ayudante = Integer.parseInt(valor);
                matriz[i][j] = ayudante;
                if (ayudante != 5000) {
                    grafo.agregarNodo(Integer.parseInt(valor));
                }
            }

        }
        matrizAGrafo(grafo, matriz);
        sc.nextLine();
        String input = sc.nextLine();
        String[] inputArray = input.split(" ");
        List<Integer> aterrizaje = new ArrayList<>();
        for (String s : inputArray) {
            int num = Integer.parseInt(s.substring(1));
            int sign = (s.charAt(0) == '+') ? 1 : -1;
            aterrizaje.add(sign * num);
        }
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = aterrizaje.size() - 1; i >= 0; i--) {
            deque.push(aterrizaje.get(i));
        }
        sc.close();
        ponderar(grafo);
        int[][] mt = SetMatrix(aviones);
        backtrack1ng(grafo,deque, mt,aeropuerto);
        // imprimir(matriz, grafo, aterrizaje);
        for (int i = 0; i < mt.length; i++) {
            System.out.println(mt[i][1] + "  " + mt[i][0]);


        }

    }

}

