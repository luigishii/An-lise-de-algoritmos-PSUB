import java.util.*;

// --- Classe Grafo ---
// Esta classe representa um grafo, que é uma estrutura de dados usada para modelar relacionamentos
// entre diferentes entidades (chamadas vértices ou nós).
class Grafo {

    // Vetor que guarda os vértices do grafo.
    // Cada elemento do vetor 'vertices' é um objeto Vertice, que contém informações como nome, índice,
    // distância estimada da origem e o predecessor no caminho mais curto.
    private final Vertice[] vertices;

    // Lista de adjacência: Esta é a representação do grafo.
    // Cada índice desta lista principal (adjacencias) corresponde a um vértice.
    // O valor em cada índice é uma lista de objetos 'Aresta', que representam as conexões (arestas)
    // que saem daquele vértice para outros.
    private List<List<Aresta>> adjacencias;

    // Construtor do grafo que recebe os nomes dos vértices.
    // Exemplo: Se 'nomesVertices' for {"S", "T", "Y", "X", "Z"}, ele criará 5 vértices.
    Grafo(String[] nomesVertices) {
        // Inicializa a lista principal de adjacências. Como é uma lista de listas,
        // primeiro inicializamos a lista externa.
        this.adjacencias = new ArrayList<>();
        // Cria o vetor de vértices com o tamanho igual ao número de nomes fornecidos.
        this.vertices = new Vertice[nomesVertices.length];

        // Loop para inicializar cada vértice e sua lista de adjacências.
        for (int i = 0; i < nomesVertices.length; i++) {
            // Para cada vértice, adicionamos uma nova lista vazia à lista de adjacências.
            // Esta lista vazia será preenchida com as arestas que saem deste vértice.
            this.adjacencias.add(new ArrayList<>());
            // Cria um novo objeto 'Vertice' com o nome e o índice (posição no vetor) e o armazena.
            this.vertices[i] = new Vertice(nomesVertices[i], i);
        }
    }

    // Método para adicionar uma aresta direcionada ao grafo.
    // 'origem': índice do vértice de onde a aresta parte.
    // 'destino': índice do vértice para onde a aresta vai.
    // 'peso': o custo ou "tamanho" da aresta.
    public void adicionarAresta(int origem, int destino, int peso) {
        // Adiciona uma nova aresta à lista de vizinhos do vértice de origem.
        // Usamos 'get(origem)' para acessar a lista de arestas que saem do vértice 'origem'.
        // 'new Aresta(destino, peso)' cria a aresta que liga 'origem' a 'destino' com o 'peso' especificado.
        adjacencias.get(origem).add(new Aresta(destino, peso));
    }

    // Retorna a lista de arestas/vizinhos de um vértice dado pelo índice 'u'.
    // Isso nos permite saber para quais vértices 'u' pode ir e com que custo.
    public List<Aresta> vizinhos(int u) {
        return adjacencias.get(u);
    }

    // Retorna o vetor de todos os vértices do grafo.
    public Vertice[] getVertices() {
        return vertices;
    }

    // Retorna a quantidade de vértices (nós) no grafo.
    public int quantidadeVertices() {
        return vertices.length;
    }

    // --- Classe interna Vertice ---
    // Representa um único nó no grafo. É uma classe interna pois está fortemente acoplada à classe Grafo.
    static class Vertice {
        String nome; // Nome do vértice (ex: "s", "t", etc.), para facilitar a leitura.
        int indice; // Índice numérico do vértice no vetor 'vertices' do grafo.
        int distancia; // Distância estimada da origem até este vértice.
                       // Inicialmente, é um valor muito alto (representando "infinito").
        Vertice predecessor; // O vértice que vem antes no caminho mais curto encontrado até agora.
                             // Usado para reconstruir o caminho.

        // Construtor de vértice.
        Vertice(String nome, int indice) {
            this.nome = nome;
            this.indice = indice;
            this.distancia = Integer.MAX_VALUE; // Inicia com o maior valor inteiro possível, simulando "infinito".
            this.predecessor = null; // Sem predecessor definido no início.
        }
    }

    // --- Classe interna Aresta ---
    // Representa uma conexão direcionada entre dois vértices no grafo.
    // Também é uma classe interna, pois serve especificamente ao contexto do Grafo.
    static class Aresta {
        int destino; // O índice do vértice para onde esta aresta leva.
        int peso;    // O "custo" de percorrer esta aresta. Pode ser positivo, zero ou negativo.

        // Construtor de aresta.
        Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }
}

// --- Classe BellmanFord ---
// Implementa o algoritmo de Bellman-Ford para encontrar os caminhos mais curtos em um grafo.
public class BellmanFord {

    // Método principal que executa o algoritmo de Bellman-Ford.
    // 'g': o grafo no qual queremos encontrar os caminhos mais curtos.
    // 's': o índice do vértice de origem (fonte) do qual calcularemos as distâncias.
    // Retorna 'true' se não houver ciclos negativos alcançáveis da origem, 'false' caso contrário.
    public boolean executar(Grafo g, int s) {
        // Passo 1: Inicialização.
        // Define a distância da origem como 0 e a de todos os outros vértices como "infinito".
        // Todos os predecessores são nulos.
        inicializarFonteUnica(g, s);

        // --- Passo 2: Relaxamento das arestas ---
        // Este é o coração do algoritmo. Ele repete o processo de "relaxamento" V-1 vezes.
        // A complexidade deste loop externo é O(V), onde V é o número de vértices.
        for (int i = 0; i < g.quantidadeVertices() - 1; i++) { // Repete V-1 vezes.
            // Para cada iteração, percorremos todos os vértices do grafo.
            // O(V) para este loop.
            for (Grafo.Vertice u : g.getVertices()) {
                // É crucial verificar se 'u.distancia' ainda é Integer.MAX_VALUE.
                // Se for, significa que 'u' ainda não foi alcançado pela origem,
                // então não há como relaxar as arestas que saem dele de forma útil ainda.
                if (u.distancia == Integer.MAX_VALUE) {
                    continue; // Pula para o próximo vértice se 'u' for inalcançável.
                }
                // Para cada vértice 'u', percorremos todas as arestas que saem dele (seus vizinhos).
                // O(E_u) para este loop, onde E_u é o número de arestas que saem de 'u'.
                // A soma de E_u para todos os 'u' é O(E), onde E é o número total de arestas no grafo.
                for (Grafo.Aresta a : g.vizinhos(u.indice)) {
                    // Pega o vértice de destino 'v' da aresta 'a'.
                    Grafo.Vertice v = g.getVertices()[a.destino];
                    // Chama o método 'relaxar' para tentar encontrar um caminho mais curto para 'v' via 'u'.
                    relaxar(u, v, a.peso);
                }
            }
        } // Complexidade total do Passo 2: O(V * E)

        // --- Passo 3: Verificação de ciclos negativos ---
        // Após V-1 iterações, se o grafo não contiver ciclos negativos, todos os menores caminhos
        // já foram encontrados. Uma V-ésima iteração (ou simplesmente uma checagem final)
        // serve para detectar se um relaxamento ainda é possível.
        // Se for, significa que há um ciclo negativo alcançável da origem.
        for (Grafo.Vertice u : g.getVertices()) {
            // Novamente, se 'u' é inalcançável, suas arestas não podem formar um ciclo negativo acessível.
            if (u.distancia == Integer.MAX_VALUE) {
                continue;
            }
            for (Grafo.Aresta a : g.vizinhos(u.indice)) {
                // Pega o vértice de destino 'v' da aresta 'a'.
                Grafo.Vertice v = g.getVertices()[a.destino];
                // Se ainda for possível encontrar um caminho mais curto para 'v' (ou seja,
                // u.distancia + a.peso é menor que v.distancia),
                // isso indica a presença de um ciclo negativo.
                if (v.distancia > u.distancia + a.peso) {
                    return false; // Um ciclo negativo foi encontrado. O algoritmo falha aqui.
                }
            }
        }
        return true; // O grafo não possui um ciclo negativo alcançável da origem.
    }

    // --- Método relaxar ---
    // Tenta melhorar a estimativa da distância para um vértice 'v' usando um caminho através de 'u'.
    // 'u': vértice de origem da aresta.
    // 'v': vértice de destino da aresta.
    // 'w': peso da aresta (u, v).
    private void relaxar(Grafo.Vertice u, Grafo.Vertice v, int w) {
        // A condição 'u.distancia != Integer.MAX_VALUE' é crucial:
        // Só podemos relaxar uma aresta (u,v) se 'u' já tiver uma distância finita da origem.
        // Caso contrário, 'u.distancia + w' resultaria em overflow ou um cálculo incorreto.
        if (u.distancia != Integer.MAX_VALUE && u.distancia + w < v.distancia) {
            v.distancia = u.distancia + w; // Atualiza 'v.distancia' com o novo (menor) valor.
            v.predecessor = u;             // Define 'u' como o predecessor de 'v' no caminho mais curto.
        }
    }

    // --- Método inicializarFonteUnica ---
    // Prepara o grafo para a execução do Bellman-Ford.
    // 'g': o grafo a ser inicializado.
    // 's': o índice do vértice de origem (fonte).
    private void inicializarFonteUnica(Grafo g, int s) {
        // Percorre todos os vértices do grafo.
        for (Grafo.Vertice v : g.getVertices()) {
            v.distancia = Integer.MAX_VALUE; // Define a distância de todos os vértices como "infinito".
            v.predecessor = null;           // Remove qualquer predecessor.
        }
        // Define a distância do vértice de origem 's' como 0, pois a distância dele para ele mesmo é zero.
        g.getVertices()[s].distancia = 0;
    }

    // --- Método principal (main) para execução e teste ---
    public static void main(String[] args) {
        // Cria um vetor com os nomes dos vértices que serão usados no grafo.
        String[] nomes = {"s", "t", "x", "y", "z"};

        // Instancia um novo grafo com os nomes dos vértices.
        Grafo g = new Grafo(nomes);

        // Adiciona as arestas ao grafo com seus respectivos pesos.
        // O formato é: adicionarAresta(índice da origem, índice do destino, peso).
        // Ex: g.adicionarAresta(0, 1, 10) significa uma aresta de 's' (índice 0) para 't' (índice 1) com peso 10.
        g.adicionarAresta(0, 1, 10); // s → t (peso 10)
        g.adicionarAresta(0, 3, 5);  // s → y (peso 5)
        g.adicionarAresta(1, 2, 1);  // t → x (peso 1)
        g.adicionarAresta(1, 3, 2);  // t → y (peso 2)
        g.adicionarAresta(2, 4, 4);  // x → z (peso 4)
        g.adicionarAresta(3, 1, 3);  // y → t (peso 3)
        g.adicionarAresta(3, 2, 9);  // y → x (peso 9)
        g.adicionarAresta(3, 4, 2);  // y → z (peso 2)
        g.adicionarAresta(4, 2, 6);  // z → x (peso 6)

        // Cria uma instância do algoritmo de BellmanFord.
        var bellmanFord = new BellmanFord();

        // Executa o algoritmo a partir do vértice 's' (índice 0).
        // O 'if' verifica o valor de retorno do método 'executar':
        // 'true' se não há ciclo negativo, 'false' se um ciclo negativo foi encontrado.
        if (bellmanFord.executar(g, 0)) { // Se o grafo não tem ciclo negativo...
            // Itera sobre todos os vértices para exibir os resultados.
            for (Grafo.Vertice v : g.getVertices()) {
                // Imprime a menor distância encontrada da origem 's' até o vértice atual.
                System.out.printf("Distância de s a %s: %d\n", v.nome, v.distancia);

                // Imprime o predecessor do vértice atual, se existir.
                // Isso ajuda a reconstruir o caminho.
                System.out.printf("%s\n",
                    v.predecessor != null
                        ? "Predecessor de " + v.nome + ": " + v.predecessor.nome // Se tem predecessor, mostra o nome.
                        : "Não tem predecessor" // Se não tem (geralmente para a origem), informa.
                );
            }
        } else { // Se o grafo possui um ciclo negativo...
            System.out.println("Não foi possível aplicar o BellmanFord: ciclo negativo detectado.");
        }
    }
}