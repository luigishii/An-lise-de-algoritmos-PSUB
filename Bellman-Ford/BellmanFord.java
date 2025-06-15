import java.util.*;

// Classe que representa o grafo
class Grafo {

    // Vetor que guarda os vértices do grafo
    private final Vertice[] vertices;
    // Lista de adjacência: cada índice contém a lista de arestas do vértice correspondente
    private List<List<Aresta>> adjacencias;

    // Construtor do grafo que recebe os nomes dos vértices
    Grafo(String[] nomesVertices) { // Exemplo: {S, T, Y, X, Z}
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
        String nome; // Nome do vértice (ex: "s", "t", etc.)
        int indice; // indice do vértice no vetor
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
        int destino; // indice do vértice de destino
        int peso;    // Peso da aresta

        // Construtor de aresta
        Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }
}

// Classe que implementa o algoritmo de BellmanFord
public class BellmanFord {

    // Método que executa o algoritmo de BellmanFord a partir do vértice de origem 's'
    public boolean executar(Grafo g, int s) {
        // Passo 1: Inicializar o grafo com distancia que tendem a infinito para todos os vertices, menos a origem que será 0
        inicializarFonteUnica(g, s);

        // Passo 2:
        // complexidade: O(V-1)
        for(int i = 0; i < g.quantidadeVertices() - 1; i++){ // repete V-1 vezes
            // para cada vértice percorremos todas as arestas daquele vértice
            // complexidade: O(E), sendo E o número de arestas do grafo
            for(Grafo.Vertice u : g.getVertices()){
                for(Grafo.Aresta a : g.vizinhos(u.indice)){
                    Grafo.Vertice v = g.getVertices()[a.destino]; // pega o vertice v que é o destindo de a
                    relaxar(u, v, a.peso);
                }
            }
        }// Complexidade Passo 2: O(V * E)

        // Passo 3: Verificar se exite ciclo negativo no grafo
        for(Grafo.Vertice u : g.getVertices()){
            for(Grafo.Aresta a : g.vizinhos(u.indice)){
                 Grafo.Vertice v = g.getVertices()[a.destino]; // pega o vertice v que é o destindo de a
                if(v.distancia > u.distancia + a.peso) // verificando se é possível melhorar a distancia
                    return false; // um ciclo negativo foi encontrado
            }
        }
        return true; // o grafo não possui um ciclo negativo
    }

    // Aplica o relaxamento entre dois vértices: se o caminho via 'u' até 'v' for melhor, atualiza 'v'
    private void relaxar(Grafo.Vertice u, Grafo.Vertice v, int w) {
        // Verifica se a distância de 'v' pode ser melhorada passando por 'u'
        if (u.distancia + w < v.distancia) {
            v.distancia = u.distancia + w; // Atualiza a nova menor distância
            v.predecessor = u; // Atualiza o predecessor para reconstrução do caminho depois
        }
    }

    // Inicializa os vértices: distância infinita, sem predecessores, exceto a origem (distância zero)
    private void inicializarFonteUnica(Grafo g, int s) {
        for (Grafo.Vertice v : g.getVertices()) {
            v.distancia = Integer.MAX_VALUE; // Infinito para todos
            v.predecessor = null; // Sem predecessor
        }
        g.getVertices()[s].distancia = 0; // Origem recebe distância zero
    }

    // Método principal de execução/teste do algoritmo
    public static void main(String[] args) {
        // Cria vetor com os nomes dos vértices do grafo
        String[] nomes = {"s", "t", "x", "y", "z"};

        // Instancia um grafo com os nomes informados
        Grafo g = new Grafo(nomes);

        // Adiciona arestas ao grafo com seus respectivos pesos
        g.adicionarAresta(0, 1, 10); // s → t (peso 10)
        g.adicionarAresta(0, 3, 5);  // s → y (peso 5)
        g.adicionarAresta(1, 2, 1);  // t → x (peso 1)
        g.adicionarAresta(1, 3, 2);  // t → y (peso 2)
        g.adicionarAresta(2, 4, 4);  // x → z (peso 4)
        g.adicionarAresta(3, 1, 3);  // y → t (peso 3)
        g.adicionarAresta(3, 2, 9);  // y → x (peso 9)
        g.adicionarAresta(3, 4, 2);  // y → z (peso 2)
        g.adicionarAresta(4, 2, 6);  // z → x (peso 6)

        // Cria uma instância do algoritmo de BellmanFord e executa a partir do vértice 's' (índice 0)
        var bellmanFord = new BellmanFord();
        
        if(bellmanFord.executar(g, 0)){ // grafo não tem ciclo negativo
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
        }else{ // grafo possui ciclo negativo
            System.out.println("Não foi possível aplicar o BellmanFord");
        }
    }
}