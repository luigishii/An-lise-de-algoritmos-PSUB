import java.util.*; // Importa todas as classes utilitárias, como List, LinkedList e Queue.

// Classe principal que representa o Grafo e implementa a lógica do BFS.
public class Grafo {
    // 'vertices': Variável que guarda o número total de vértices (nós) no grafo.
    private int vertices;
    // 'adjacencias': Esta é a Lista de Adjacência, a forma como o grafo é representado.
    // É um array de Listas; cada índice 'i' do array 'adjacencias' contém uma lista
    // dos vértices vizinhos do vértice 'i'.
    private List<Integer>[] adjacencias;

    // Construtor do Grafo.
    // Recebe como parâmetro o número de vértices que o grafo terá.
    public Grafo(int vertices) {
        this.vertices = vertices;
        // Inicializa o array de listas de adjacência.
        // O tamanho do array é o número de vértices, pois cada vértice terá sua própria lista de vizinhos.
        this.adjacencias = new LinkedList[vertices];

        // Loop para inicializar cada posição do array 'adjacencias' com uma nova LinkedList vazia.
        // Isso garante que cada vértice tenha sua própria lista para armazenar seus vizinhos.
        for (int i = 0; i < vertices; i++) {
            adjacencias[i] = new LinkedList<>();
        }
    }

    // Método para adicionar uma aresta (conexão) bidirecional entre dois vértices.
    // 'v1' e 'v2' são os índices dos vértices a serem conectados.
    public void adicionarArestas(int v1, int v2) {
        // Adiciona 'v2' à lista de vizinhos de 'v1'.
        this.adjacencias[v1].add(v2);
        // Adiciona 'v1' à lista de vizinhos de 'v2'.
        // Isso torna o grafo "não direcionado", ou seja, a conexão funciona nos dois sentidos.
        this.adjacencias[v2].add(v1); 
    }

    // Método que encontra a menor distância (número de arestas/passos)
    // entre um vértice de 'origem' e um vértice de 'destino' usando o algoritmo BFS.
    public void menorCaminho(int origem, int destino) {
        // 'distancias': Array para armazenar a menor distância (número de arestas)
        // de cada vértice a partir do vértice de 'origem'.
        int[] distancias = new int[vertices]; 
        // 'visitados': Array de booleanos para marcar quais vértices já foram visitados durante a BFS.
        // Isso evita loops infinitos em ciclos e reprocessamento de vértices.
        boolean[] visitados = new boolean[vertices]; 

        // 'fila': Fila usada para implementar a lógica da Busca em Largura.
        // A BFS explora nível por nível, e a fila garante essa ordem (FIFO: Primeiro a Entrar, Primeiro a Sair).
        Queue<Integer> fila = new LinkedList<>(); 

        // --- Inicialização da BFS ---
        fila.offer(origem); // Enfileira o vértice de 'origem'. É o primeiro a ser visitado.
        distancias[origem] = 0; // A distância da origem para ela mesma é sempre 0.
        visitados[origem] = true; // Marca o vértice de origem como visitado.

        // --- Loop Principal da BFS ---
        // Continua enquanto houver vértices na fila para serem explorados.
        while (!fila.isEmpty()) {
            int atual = fila.poll(); // Remove (desenfileira) o próximo vértice a ser explorado da fila.

            // Para cada vizinho do vértice 'atual'...
            for (int vizinho : adjacencias[atual]) {
                // Se o 'vizinho' ainda não foi visitado...
                if (!visitados[vizinho]) { 
                    fila.offer(vizinho); // Adiciona o 'vizinho' ao final da fila para ser visitado posteriormente.
                    visitados[vizinho] = true; // Marca o 'vizinho' como visitado para não processá-lo novamente.
                    // A distância do 'vizinho' é a distância do 'atual' mais 1 (pois é um passo a mais).
                    distancias[vizinho] = distancias[atual] + 1; 

                    // Otimização: Se o 'vizinho' que acabamos de descobrir é o nosso 'destino',
                    // então encontramos o caminho mais curto (em número de passos) e podemos parar.
                    if (vizinho == destino) {
                        System.out.printf("Distância mínima de %d até %d é %d\n", origem, destino, distancias[vizinho]);
                        return; // Termina o método.
                    }
                }
            }
        }

        // Se o loop 'while' terminar e o 'destino' não foi encontrado, significa que não há caminho.
        System.out.printf("Não há caminho de %d até %d\n", origem, destino);
    }

    // --- Método principal (main) para testar o algoritmo BFS ---
    public static void main(String[] args) {
        // O programa espera argumentos de linha de comando para construir o grafo e definir origem/destino.
        // Formato esperado: <num_vertices> <v1> <v2> <v3> <v4> ... <origem> <destino>
        // Ex: java Grafo 6 0 1 0 3 1 5 2 5 3 4 4 5 0 5

        // Pega o primeiro argumento e converte para inteiro, que é o número total de vértices.
        int vertices = Integer.parseInt(args[0]);
        // Cria uma nova instância do grafo com o número de vértices especificado.
        Grafo grafo = new Grafo(vertices); 

        // Loop para ler os pares de vértices (arestas) dos argumentos de linha de comando.
        // O loop vai do segundo argumento (índice 1) até o antepenúltimo argumento.
        // 'i += 2' porque lemos pares de vértices.
        for (int i = 1; i < args.length - 2; i += 2) {
            int v1 = Integer.parseInt(args[i]);     // Converte o argumento atual para o primeiro vértice da aresta.
            int v2 = Integer.parseInt(args[i + 1]); // Converte o próximo argumento para o segundo vértice da aresta.
            grafo.adicionarArestas(v1, v2);          // Adiciona a aresta bidirecional ao grafo.
        }

        // Os dois últimos argumentos são a origem e o destino da busca.
        int origem = Integer.parseInt(args[args.length - 2]);
        int destino = Integer.parseInt(args[args.length - 1]);

        // Chama o método 'menorCaminho' para executar o BFS e encontrar/exibir a distância mínima.
        grafo.menorCaminho(origem, destino);

        /*
         * ==============================
         * TESTE DE MESA - ENTRADA DE EXEMPLO:
         * Para rodar este exemplo no terminal:
         * javac Grafo.java
         * java Grafo 6 0 1 0 3 1 5 2 5 3 4 4 5 0 5
         * ==============================
         * Interpretação da entrada:
         * Números de vértices: 6 (os vértices são numerados de 0 a 5).
         * Arestas:
         * 0-1 (conexão entre vértice 0 e 1)
         * 0-3 (conexão entre vértice 0 e 3)
         * 1-5 (conexão entre vértice 1 e 5)
         * 2-5 (conexão entre vértice 2 e 5)
         * 3-4 (conexão entre vértice 3 e 4)
         * 4-5 (conexão entre vértice 4 e 5)
         * Origem da busca: 0
         * Destino da busca: 5
         *
         * Simulação da execução (Passo a Passo da BFS):
         *
         * Estado Inicial:
         * fila = [] (vazia)
         * distancias = [0, 0, 0, 0, 0, 0] (inicialmente, todos os valores são 0 por padrão para int[])
         * visitados = [F, F, F, F, F, F] (inicialmente, todos são false por padrão para boolean[])
         *
         * Inicialização da 'menorCaminho(0, 5)':
         * fila.offer(0)  -> fila = [0]
         * distancias[0] = 0
         * visitados[0] = true -> visitados = [T, F, F, F, F, F]
         *
         * Loop 'while (!fila.isEmpty())':
         *
         * Iteração 1:
         * 'atual' = fila.poll() -> 'atual' = 0; fila = []
         * Vizinhos de 0: 1, 3
         * - Processando vizinho 1:
         * !visitados[1] é true.
         * fila.offer(1)      -> fila = [1]
         * visitados[1] = true -> visitados = [T, T, F, F, F, F]
         * distancias[1] = distancias[0] + 1 = 0 + 1 = 1 -> distancias = [0, 1, 0, 0, 0, 0]
         * vizinho (1) != destino (5). Continua.
         * - Processando vizinho 3:
         * !visitados[3] é true.
         * fila.offer(3)      -> fila = [1, 3]
         * visitados[3] = true -> visitados = [T, T, F, T, F, F]
         * distancias[3] = distancias[0] + 1 = 0 + 1 = 1 -> distancias = [0, 1, 0, 1, 0, 0]
         * vizinho (3) != destino (5). Continua.
         *
         * Iteração 2:
         * 'atual' = fila.poll() -> 'atual' = 1; fila = [3]
         * Vizinhos de 1: 0, 5
         * - Processando vizinho 0:
         * !visitados[0] é false (já foi visitado). Ignora.
         * - Processando vizinho 5:
         * !visitados[5] é true.
         * fila.offer(5)      -> fila = [3, 5]
         * visitados[5] = true -> visitados = [T, T, F, T, F, T]
         * distancias[5] = distancias[1] + 1 = 1 + 1 = 2 -> distancias = [0, 1, 0, 1, 0, 2]
         * vizinho (5) == destino (5) é true!
         * Imprime: "Distância mínima de 0 até 5 é 2"
         * return; (O método termina aqui).
         *
         * Saída impressa no console:
         * Distância mínima de 0 até 5 é 2
         */
    }
}