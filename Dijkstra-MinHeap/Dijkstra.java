import java.util.*; // Importa as classes utilitárias (List, ArrayList, etc.)

// Classe que representa o grafo
class Grafo {

    // Vetor que guarda os vértices do grafo
    private final Vertice[] vertices;
    // Lista de adjacência: cada índice contém a lista de arestas do vértice correspondente
    private List<List<Aresta>> adjacencias;

    // Construtor do grafo que recebe os nomes dos vértices
    Grafo(String[] nomesVertices) { // Exemplo: {RegiaoA, RegiaoB, RegiaoC, RegiaoD, RegiaoE}
        // Inicializa a lista principal de adjacências
        this.adjacencias = new ArrayList<>();
        // Cria o vetor de vértices
        this.vertices = new Vertice[nomesVertices.length];

        for (int i = 0; i < nomesVertices.length; i++) {
            // Inicializa a lista de vizinhos para cada vértice
            this.adjacencias.add(new ArrayList<>());
            // Cria cada vértice com nome e índice
            this.vertices[i] = new Vertice(nomesVertices[i], i);
        }
    }

    // Método para adicionar uma aresta direcionada ao grafo
    public void adicionarAresta(int origem, int destino, int peso) {
        // Adiciona uma nova aresta à lista de vizinhos do vértice de origem
        // É um grafo direcionado pois adiciona somente em uma direção específica (origem -> destino)
        adjacencias.get(origem).add(new Aresta(destino, peso));
    }

    // Retorna a lista de arestas/vizinhos de um vértice dado pelo índice
    public List<Aresta> vizinhos(int u) {
        return adjacencias.get(u);
    }

    // Retorna o vetor de vértices do grafo
    public Vertice[] getVertices() {
        return vertices;
    }

    // Retorna a quantidade de vértices do grafo
    public int quantidadeVertices() {
        return vertices.length;
    }

    // Classe interna que representa um vértice do grafo
    static class Vertice {
        String nome; // Nome do vértice
        int indice; // Índice do vértice no vetor
        int distancia; // Distância estimada a partir da origem (inicialmente infinita)
        Vertice predecessor; // Predecessor no caminho mínimo

        // Construtor de vértice
        Vertice(String nome, int indice) {
            this.nome = nome;
            this.indice = indice;
            this.distancia = Integer.MAX_VALUE; // Inicia com "infinito"
            this.predecessor = null; // Sem predecessor inicialmente
        }
    }

    // Classe interna que representa uma aresta do grafo
    static class Aresta {
        int destino; // Índice do vértice de destino
        int peso;    // Peso da aresta

        // Construtor de aresta
        Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }
}

// Classe que implementa o algoritmo de Dijkstra
public class Dijkstra {

    // Método que executa o algoritmo de Dijkstra a partir do vértice de origem
    public void executar(Grafo g, int s) {
        inicializarFonteUnica(g, s); // Define todas as distâncias como infinito, exceto a origem (distância 0)

        // Cria o heap de prioridade mínimo com todos os vértices do grafo
        MinHeap q = new MinHeap(g.getVertices()); // q = fila de prioridade (queu)

        // Enquanto houver vértices na fila
        while (!q.isEmpty()) {
            var u = q.extractMin(); // Seleciona e remove o vértice com menor distância estimada

            // Para cada vizinho de 'u' (ou seja, cada aresta que sai de 'u')
            for (Grafo.Aresta aresta : g.vizinhos(u.indice)) {
                // Recupera o vértice de destino da aresta
                Grafo.Vertice v = g.getVertices()[aresta.destino];

                // Tenta relaxar a aresta (u, v)
                if(relaxar(u, v, aresta.peso)){ //u.distancia + aresta.peso > v.distancia
                    // Se relaxou e v ainda está no heap, atualiza sua posição
                    if(q.contains(v)){
                        q.decreaseKey(v);
                    }
                }
            }
        }
    }
    
    // Aplica o relaxamento entre dois vértices: se o caminho via 'u' até 'v' for melhor, atualiza 'v'
    private boolean relaxar(Grafo.Vertice u, Grafo.Vertice v, int w) {
        // Verifica se a distância de 'v' pode ser melhorada passando por 'u'
        if (u.distancia + w < v.distancia) {
            v.distancia = u.distancia + w; // Atualiza a nova menor distância
            v.predecessor = u; // Atualiza o predecessor para reconstrução do caminho depois
            return true;
        }
        return false;
    }

    // Inicializa os vértices: distância infinita, sem predecessores, exceto a origem (distância zero)
    private void inicializarFonteUnica(Grafo g, int s) {
        for (Grafo.Vertice v : g.getVertices()) {
            v.distancia = Integer.MAX_VALUE; // Infinito para todos
            v.predecessor = null; // Sem predecessor
        }
        g.getVertices()[s].distancia = 0; // Origem recebe distância zero
    }

    // Método auxiliar para buscar o índice de um vértice pelo seu nome
    private static int buscarIndicePorNome(Grafo g, String nome) {
    for (Grafo.Vertice v : g.getVertices()) {
        if (v.nome.equalsIgnoreCase(nome)) {
            return v.indice;
        }
    }
        return -1; // Retorna -1 se não encontrar
    }

    // Método principal teste do algoritmo
    public static void main(String[] args) {
    
        // Cria vetor com os nomes dos vértices (regiões) do grafo
        String[] nomes = {"RegiaoA", "RegiaoB", "RegiaoC", "RegiaoD", "RegiaoE"};

        // Instancia um grafo com os nomes informados
        Grafo g = new Grafo(nomes);

        // Adiciona arestas ao grafo com seus respectivos pesos
        g.adicionarAresta(0, 1, 10); // RegiaoA → RegiaoB (peso 10)
        g.adicionarAresta(0, 3, 5);  // RagiaoA → RegiaoD (peso 5)
        g.adicionarAresta(1, 2, 1);  // RegiaoB → RegiaoC (peso 1)
        g.adicionarAresta(1, 3, 2);  // RegiaoB → RegiaoD (peso 2)
        g.adicionarAresta(2, 4, 4);  // RegiaoC → RegiaoE (peso 4)
        g.adicionarAresta(3, 1, 3);  // RegiaoD → RegiaoB (peso 3)
        g.adicionarAresta(3, 2, 9);  // RegiaoD → RegiaoC (peso 9)
        g.adicionarAresta(3, 4, 2);  // RegiaoD → RegiaoE (peso 2)
        g.adicionarAresta(4, 2, 6);  // RegiaoE → RegiaoC (peso 6)

        Scanner sc = new Scanner(System.in);
        // Solicita ao usuário a região de origem e destino
        System.out.print("Digite a região de ORIGEM: ");
        String nomeOrigem = sc.nextLine();

        System.out.print("Digite a região de DESTINO: ");
        String nomeDestino = sc.nextLine();

        // Busca os índices das regiões informadas
        int indiceOrigem = buscarIndicePorNome(g, nomeOrigem);
        int indiceDestino = buscarIndicePorNome(g, nomeDestino);

        // Verifica se os nomes são válidos
        if (indiceOrigem == -1 || indiceDestino == -1) {
            System.out.println("Região de origem ou destino inválida.");
            return;
        }
  
        // Executa o algoritmo de Dijkstra a partir da origem informada
        var dijkstra = new Dijkstra();
        dijkstra.executar(g, indiceOrigem);
  
        // Exibe os resultados: distância mínima e predecessor de cada vértice
        for (Grafo.Vertice v : g.getVertices()) {
            // Imprime a menor distância da origem 's' até o vértice atual
            System.out.printf("Distância de s a %s: %d\n", v.nome, v.distancia);

            // Imprime o predecessor, se existir
            System.out.printf("%s\n",
                v.predecessor != null
                    ? "Predecessor de " + v.nome + ": " + v.predecessor.nome
                    : "Não tem predecessor"
            );
        }

        // Exibe especificamente o caminho e o custo até a região de destino
        System.out.println("\nResumo:");
        Grafo.Vertice destinoVertice = g.getVertices()[indiceDestino];
        if (destinoVertice.distancia == Integer.MAX_VALUE) {
            System.out.println("Não existe caminho de " + nomeOrigem + " até " + nomeDestino);
        } else {
            System.out.println("Menor custo de " + nomeOrigem + " até " + nomeDestino + ": " + destinoVertice.distancia);
        }
    }
}