
import java.util.*;

public class Grafo {
    // Número total de vértices do grafo
    private int vertices;
    // Lista de adjacência: cada vértice aponta para uma lista de vizinhos
    private List<Integer>[] adjacencias;

    // Construtor que inicializa a lista de adjacência
    public Grafo(int vertices) {
        this.vertices = vertices;
        this.adjacencias = new LinkedList[vertices];

        // Inicializa cada lista de vizinhos como uma LinkedList vazia
        for (int i = 0; i < vertices; i++) {
            adjacencias[i] = new LinkedList<>();
        }
    }

    // Adiciona uma aresta bidirecional entre dois vértices
    public void adicionarArestas(int v1, int v2) {
        this.adjacencias[v1].add(v2);
        this.adjacencias[v2].add(v1); // Grafo não direcionado
    }

    // Método que encontra a menor distância (número de arestas) entre dois vértices usando BFS
    public void menorCaminho(int origem, int destino) {
        int[] distancias = new int[vertices];         // Armazena a distância de cada vértice a partir da origem
        boolean[] visitados = new boolean[vertices];  // Marca os vértices já visitados na BFS

        Queue<Integer> fila = new LinkedList<>();     // Fila usada para implementar a BFS
        fila.offer(origem);                           // Enfileira o vértice de origem
        distancias[origem] = 0;                       // Distância da origem para ela mesma é 0
        visitados[origem] = true;                     // Marca a origem como visitada

        // Enquanto houver vértices na fila...
        while (!fila.isEmpty()) {
            int atual = fila.poll(); // Remove o primeiro vértice da fila

            // Para cada vizinho do vértice atual...
            for (int vizinho : adjacencias[atual]) {
                if (!visitados[vizinho]) { // Se o vizinho ainda não foi visitado...
                    fila.offer(vizinho); // Enfileira o vizinho
                    visitados[vizinho] = true; // Marca como visitado
                    distancias[vizinho] = distancias[atual] + 1; // Atualiza a distância

                    // Se encontramos o destino, podemos parar
                    if (vizinho == destino) {
                        System.out.printf("Distância mínima de %d até %d é %d\n", origem, destino, distancias[vizinho]);
                        return;
                    }
                }
            }
        }

        // Se sair do laço e não encontrou o destino, não há caminho possível
        System.out.printf("Não há caminho de %d até %d\n", origem, destino);
    }

    public static void main(String[] args) {
        // O primeiro argumento indica a quantidade de vértices
        int vertices = Integer.parseInt(args[0]);
        Grafo grafo = new Grafo(vertices); // Cria o grafo com o número de vértices

        // Os próximos argumentos até os dois últimos indicam as arestas (pares de vértices)
        for (int i = 1; i < args.length - 2; i += 2) {
            int v1 = Integer.parseInt(args[i]);
            int v2 = Integer.parseInt(args[i + 1]);
            grafo.adicionarArestas(v1, v2); // Adiciona a aresta ao grafo
        }

        // Os dois últimos argumentos representam a origem e o destino
        int origem = Integer.parseInt(args[args.length - 2]);
        int destino = Integer.parseInt(args[args.length - 1]);

        // Calcula e exibe a menor distância entre origem e destino
        grafo.menorCaminho(origem, destino);

        /*
        ==============================
        TESTE DE MESA - ENTRADA:
        java Grafo 6 0 1 0 3 1 5 2 5 3 4 4 5 0 5
        ==============================
        Números de vértices: 6 (0 a 5)
        Arestas: 
        0-1, 0-3, 1-5, 2-5, 3-4, 4-5
        Origem: 0
        Destino: 5

        Início do BFS:
        fila = [0]
        visitados = [T, F, F, F, F, F]
        distancias = [0, 0, 0, 0, 0, 0]

        Iteração 1 (atual = 0):
            vizinhos: 1, 3
            fila = [1, 3]
            visitados = [T, T, F, T, F, F]
            distancias = [0, 1, 0, 1, 0, 0]

        Iteração 2 (atual = 1):
            vizinho: 5
            fila = [3, 5]
            visitados = [T, T, F, T, F, T]
            distancias = [0, 1, 0, 1, 0, 2]
            --> destino 5 encontrado, termina a execução

        Saída impressa:
        Distância mínima de 0 até 5 é 2
        */
    }
}
