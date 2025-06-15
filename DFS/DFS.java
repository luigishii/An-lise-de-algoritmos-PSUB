import java.util.*;

public class Grafo {
    private int V; // Número de vértices do grafo
    private LinkedList<Integer>[] adj; // Array de listas de adjacência (uma lista para cada vértice)

    // Construtor que inicializa o grafo com V vértices
    public Grafo(int V) {
        this.V = V;
        adj = new LinkedList[V]; // Cria o array com V posições

        // Inicializa cada lista de adjacência como uma nova LinkedList
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Método para adicionar uma aresta do vértice 'origem' para 'destino'
    public void adicionarAresta(int origem, int destino) {
        adj[origem].add(destino); // Adiciona 'destino' à lista de adjacência de 'origem'
        // Como o grafo é não-direcionado, adicionamos também a aresta de volta
        adj[destino].add(origem);
    }

    // Método que realiza a busca em profundidade, usando pilha
    public void dfs(int inicio) {
        boolean[] visitado = new boolean[V]; // Vetor para controlar quais vértices já foram visitados
        Stack<Integer> pilha = new Stack<>();

        pilha.push(inicio); // Empilha o vértice inicial

        // Executa enquanto houver elementos na pilha
        while (!pilha.isEmpty()) {
            int atual = pilha.pop(); // Remove o topo da pilha e armazena na variável 'atual'

            // Se o vértice ainda não foi visitado
            if (!visitado[atual]) {
                System.out.print(atual + " "); // Exibe o vértice no console
                visitado[atual] = true; // Marca como visitado

                // Percorre os vizinhos do vértice atual
                for (int vizinho : adj[atual]) {
                    // Se o vizinho ainda não foi visitado, adiciona à pilha
                    if (!visitado[vizinho]) {
                        pilha.push(vizinho);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Lê o número de vértices a partir do primeiro argumento
        int numVertices = Integer.parseInt(args[0]);

        // Cria o grafo com o número de vértices informado
        Grafo grafo = new Grafo(numVertices);

        // Lê os pares de vértices (arestas) dos argumentos restantes e adiciona a aresta no grafo
        for (int i = 1; i < args.length - 1; i += 2) {
            int origem = Integer.parseInt(args[i]);
            int destino = Integer.parseInt(args[i + 1]);
            grafo.adicionarAresta(origem, destino);
        }

        System.out.println("DFS a partir do vértice 0:");

        // Executa a DFS a partir do vértice 0
        grafo.dfs(0);
    }
}

// Teste de Mesa para o DFS:

// Exemplo de execução para a entrada:
// java Grafo 6 0 1 0 2 1 3 2 4 4 5

// Lista de adjacência gerada:
// 0: [1, 2]
// 1: [0, 3]
// 2: [0, 4]
// 3: [1]
// 4: [2, 5]
// 5: [4]

// Simulação do DFS a partir do vértice 0:

// Etapa 0
// Pilha:         [0]
// visitado:      [false, false, false, false, false, false]
// Saída parcial: ---

// Etapa 1
// Vértice atual: 0
// Pilha:         []
// visitado:      [true, false, false, false, false, false]
// Saída parcial: 0
// Vizinho empilhado: 1, 2
// Pilha:         [1, 2]

// Etapa 2
// Vértice atual: 2
// Pilha:         [1]
// visitado:      [true, false, true, false, false, false]
// Saída parcial: 0 2
// Vizinho empilhado: 4 (0 já visitado)
// Pilha:         [1, 4]

// Etapa 3
// Vértice atual: 4
// Pilha:         [1]
// visitado:      [true, false, true, false, true, false]
// Saída parcial: 0 2 4
// Vizinho empilhado: 5 (2 já visitado)
// Pilha:         [1, 5]

// Etapa 4
// Vértice atual: 5
// Pilha:         [1]
// visitado:      [true, false, true, false, true, true]
// Saída parcial: 0 2 4 5
// Vizinho: 4 (já visitado), nada novo na pilha

// Etapa 5
// Vértice atual: 1
// Pilha:         []
// visitado:      [true, true, true, false, true, true]
// Saída parcial: 0 2 4 5 1
// Vizinho empilhado: 3 (0 já visitado)
// Pilha:         [3]

// Etapa 6
// Vértice atual: 3
// Pilha:         []
// visitado:      [true, true, true, true, true, true]
// Saída parcial: 0 2 4 5 1 3

// Fim da execução — todos os vértices conectados ao 0 foram visitados.