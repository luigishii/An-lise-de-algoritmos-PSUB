import java.util.*; // Importa todas as classes utilitárias necessárias (List, LinkedList, Stack, etc.).

// --- Classe Grafo ---
// Esta classe representa um grafo não-direcionado, que é uma coleção de vértices (nós)
// e arestas (conexões) entre eles.
public class Grafo {
    // 'V': Guarda o número total de vértices no grafo.
    private int V; 
    // 'adj': Um array de listas encadeadas (LinkedList).
    // Esta é a Lista de Adjacência, a forma como o grafo é armazenado.
    // Cada posição `adj[i]` contém uma lista de todos os vértices vizinhos do vértice `i`.
    private LinkedList<Integer>[] adj; 

    // Construtor da classe Grafo.
    // Recebe o número de vértices 'V' que o grafo terá.
    public Grafo(int V) {
        this.V = V;
        // Inicializa o array 'adj' com 'V' posições. Cada posição irá conter uma LinkedList.
        adj = new LinkedList[V]; 

        // Loop para inicializar cada uma das LinkedLists dentro do array 'adj'.
        // Isso garante que cada vértice tenha sua própria lista vazia para armazenar seus vizinhos.
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Método para adicionar uma aresta (conexão) entre dois vértices.
    // Como o grafo é não-direcionado (a conexão funciona nos dois sentidos),
    // precisamos adicionar a aresta em ambas as direções na lista de adjacência.
    public void adicionarAresta(int origem, int destino) {
        adj[origem].add(destino); // Adiciona 'destino' à lista de vizinhos de 'origem'.
        adj[destino].add(origem); // Adiciona 'origem' à lista de vizinhos de 'destino'.
    }

    // --- Método dfs (Depth-First Search - Busca em Profundidade) ---
    // Este método implementa o algoritmo DFS para percorrer o grafo
    // a partir de um 'inicio' (vértice inicial) e imprimir a ordem de visitação.
    // Ele usa uma PILHA (Stack) para gerenciar a exploração em profundidade.
    public void dfs(int inicio) {
        // 'visitado': Um array booleano para controlar quais vértices já foram visitados.
        // Isso é essencial para evitar ciclos infinitos em grafos com ciclos e para não reprocessar vértices.
        boolean[] visitado = new boolean[V]; 
        // 'pilha': A estrutura de dados principal para a DFS iterativa.
        // A pilha (Stack) segue a lógica LIFO (Last-In, First-Out - Último a Entrar, Primeiro a Sair),
        // o que naturalmente simula a exploração em profundidade.
        Stack<Integer> pilha = new Stack<>();

        pilha.push(inicio); // Começa empilhando o vértice inicial na pilha.

        // O loop principal continua enquanto houver vértices na pilha para serem explorados.
        while (!pilha.isEmpty()) {
            // Remove o vértice do topo da pilha. Este é o vértice que será processado agora.
            int atual = pilha.pop(); 

            // Verifica se o vértice 'atual' já foi visitado.
            // É importante fazer essa checagem AQUI, pois um vértice pode ser adicionado
            // múltiplas vezes à pilha por diferentes caminhos antes de ser realmente processado.
            if (!visitado[atual]) {
                System.out.print(atual + " "); // Se não foi visitado, imprime-o (mostra que está sendo visitado).
                visitado[atual] = true; // Marca o vértice como visitado.

                // Percorre todos os vizinhos do vértice 'atual'.
                // A ordem em que os vizinhos são empilhados pode influenciar a ordem de visita,
                // mas não altera a corretude do algoritmo.
                // Aqui, a iteração padrão da LinkedList fará com que o "último" vizinho na lista
                // seja o primeiro a ser desempilhado na próxima iteração (exploração mais profunda).
                for (int vizinho : adj[atual]) {
                    // Se o 'vizinho' ainda não foi visitado, ele é adicionado à pilha.
                    // Isso garante que ele será explorado mais profundamente em breve.
                    if (!visitado[vizinho]) {
                        pilha.push(vizinho);
                    }
                }
            }
        }
    }

    // --- Método main ---
    // O ponto de entrada do programa. Aqui o grafo é construído e a DFS é executada.
    public static void main(String[] args) {
        // O programa espera argumentos de linha de comando para construir o grafo.
        // Formato esperado: <num_vertices> <v1_origem> <v1_destino> <v2_origem> <v2_destino> ...
        // Ex: java Grafo 6 0 1 0 2 1 3 2 4 4 5

        // O primeiro argumento (args[0]) é o número total de vértices do grafo.
        int numVertices = Integer.parseInt(args[0]);

        // Cria uma nova instância do grafo com o número de vértices especificado.
        Grafo grafo = new Grafo(numVertices);

        // Loop para ler os pares de vértices (arestas) dos argumentos de linha de comando.
        // Começa do índice 1 (ignorando numVertices) e avança de 2 em 2 (para pegar pares).
        // O loop vai até o penúltimo argumento (args.length - 1).
        for (int i = 1; i < args.length; i += 2) {
            int origem = Integer.parseInt(args[i]);     // Converte o argumento atual para o vértice de origem.
            int destino = Integer.parseInt(args[i + 1]); // Converte o próximo argumento para o vértice de destino.
            grafo.adicionarAresta(origem, destino);      // Adiciona a aresta bidirecional ao grafo.
        }

        System.out.println("DFS a partir do vértice 0:");

        // Executa a DFS a partir do vértice 0 (você pode alterar este valor se quiser começar de outro vértice).
        grafo.dfs(0);
    }
}

/*
 * ==============================
 * TESTE DE MESA para o DFS:
 *
 * Exemplo de execução no terminal (se o arquivo for Grafo.java):
 * javac Grafo.java
 * java Grafo 6 0 1 0 2 1 3 2 4 4 5
 * ==============================
 *
 * Interpretação da entrada:
 * - `6`: O grafo terá 6 vértices, numerados de 0 a 5.
 * - `0 1`: Adiciona uma aresta entre o vértice 0 e o vértice 1 (bidirecional).
 * - `0 2`: Adiciona uma aresta entre o vértice 0 e o vértice 2.
 * - `1 3`: Adiciona uma aresta entre o vértice 1 e o vértice 3.
 * - `2 4`: Adiciona uma aresta entre o vértice 2 e o vértice 4.
 * - `4 5`: Adiciona uma aresta entre o vértice 4 e o vértice 5.
 *
 * Lista de adjacência (como o grafo é armazenado):
 * 0: [1, 2]
 * 1: [0, 3]
 * 2: [0, 4]
 * 3: [1]
 * 4: [2, 5]
 * 5: [4]
 *
 * Simulação do `dfs(0)` (Busca em Profundidade a partir do vértice 0):
 *
 * Estado Inicial (antes do loop 'while'):
 * Pilha: [0] (vértice inicial empilhado)
 * visitado: [false, false, false, false, false, false] (todos falsos)
 * Saída parcial: (nada ainda)
 *
 * --- Loop 'while (!pilha.isEmpty())' ---
 *
 * Etapa 1:
 * 'atual' = pilha.pop() -> 'atual' = 0. Pilha: []
 * `!visitado[0]` é true.
 * System.out.print(0 + " ") -> Saída: "0 "
 * visitado[0] = true -> visitado: [true, false, false, false, false, false]
 * Vizinhos de 0: [1, 2]
 * - Processando vizinho 1: `!visitado[1]` é true. pilha.push(1) -> Pilha: [1]
 * - Processando vizinho 2: `!visitado[2]` é true. pilha.push(2) -> Pilha: [1, 2]
 * (Nota: a ordem dos vizinhos na LinkedList determina a ordem de empilhamento. Se 2 foi adicionado depois de 1 na adjacência, 2 é empilhado depois de 1. Como Stack é LIFO, 2 será o próximo a ser desempilhado.)
 *
 * Etapa 2:
 * 'atual' = pilha.pop() -> 'atual' = 2. Pilha: [1]
 * `!visitado[2]` é true.
 * System.out.print(2 + " ") -> Saída: "0 2 "
 * visitado[2] = true -> visitado: [true, false, true, false, false, false]
 * Vizinhos de 2: [0, 4]
 * - Processando vizinho 0: `!visitado[0]` é false (0 já visitado). Ignora.
 * - Processando vizinho 4: `!visitado[4]` é true. pilha.push(4) -> Pilha: [1, 4]
 *
 * Etapa 3:
 * 'atual' = pilha.pop() -> 'atual' = 4. Pilha: [1]
 * `!visitado[4]` é true.
 * System.out.print(4 + " ") -> Saída: "0 2 4 "
 * visitado[4] = true -> visitado: [true, false, true, false, true, false]
 * Vizinhos de 4: [2, 5]
 * - Processando vizinho 2: `!visitado[2]` é false (2 já visitado). Ignora.
 * - Processando vizinho 5: `!visitado[5]` é true. pilha.push(5) -> Pilha: [1, 5]
 *
 * Etapa 4:
 * 'atual' = pilha.pop() -> 'atual' = 5. Pilha: [1]
 * `!visitado[5]` é true.
 * System.out.print(5 + " ") -> Saída: "0 2 4 5 "
 * visitado[5] = true -> visitado: [true, false, true, false, true, true]
 * Vizinhos de 5: [4]
 * - Processando vizinho 4: `!visitado[4]` é false (4 já visitado). Ignora.
 *
 * Etapa 5:
 * 'atual' = pilha.pop() -> 'atual' = 1. Pilha: []
 * `!visitado[1]` é true.
 * System.out.print(1 + " ") -> Saída: "0 2 4 5 1 "
 * visitado[1] = true -> visitado: [true, true, true, false, true, true]
 * Vizinhos de 1: [0, 3]
 * - Processando vizinho 0: `!visitado[0]` é false (0 já visitado). Ignora.
 * - Processando vizinho 3: `!visitado[3]` é true. pilha.push(3) -> Pilha: [3]
 *
 * Etapa 6:
 * 'atual' = pilha.pop() -> 'atual' = 3. Pilha: []
 * `!visitado[3]` é true.
 * System.out.print(3 + " ") -> Saída: "0 2 4 5 1 3 "
 * visitado[3] = true -> visitado: [true, true, true, true, true, true]
 * Vizinhos de 3: [1]
 * - Processando vizinho 1: `!visitado[1]` é false (1 já visitado). Ignora.
 *
 * Fim do loop 'while' (pilha está vazia).
 *
 * Saída final no console:
 * DFS a partir do vértice 0:
 * 0 2 4 5 1 3
 */