// TESTE DE MESA:
/*
Para acompanhar visualmente o funcionamento do algoritmo com um exemplo,
você pode usar o teste de mesa no link:
https://drive.google.com/file/d/1y3UJjbLQ__6dq0_Vq1PojkHH09NRueoO/view?usp=sharing
*/

import java.util.*; // Importa as classes utilitárias (List, ArrayList, Scanner, etc.)

// --- Classe Grafo ---
// Esta classe representa um grafo (o nosso "mapa"), com suas regiões (vértices)
// e as conexões entre elas (arestas), que possuem pesos (custos/distâncias).
class Grafo {

    // Vetor que guarda todos os vértices do grafo. Cada posição [i] contém um objeto Vertice.
    private final Vertice[] vertices;
    // Lista de adjacência: Esta é a forma como as conexões do grafo são armazenadas.
    // 'adjacencias' é uma lista onde cada índice corresponde a um vértice de origem.
    // O valor nesse índice é uma outra lista, contendo todas as 'Aresta's que SAEM daquele vértice.
    private List<List<Aresta>> adjacencias;

    // Construtor do grafo. Ele recebe um array de nomes para os vértices.
    // Exemplo: se nomesVertices = {"RegiaoA", "RegiaoB"}, o grafo terá 2 vértices.
    Grafo(String[] nomesVertices) {
        // Inicializa a lista principal de adjacências.
        this.adjacencias = new ArrayList<>();
        // Cria o array de objetos Vertice, com o tamanho igual ao número de nomes fornecidos.
        this.vertices = new Vertice[nomesVertices.length];

        // Loop para inicializar cada vértice e sua lista de adjacências vazia.
        for (int i = 0; i < nomesVertices.length; i++) {
            // Adiciona uma nova lista vazia à lista de adjacências para cada vértice.
            // Esta lista será preenchida posteriormente com as arestas que partem deste vértice 'i'.
            this.adjacencias.add(new ArrayList<>());
            // Cria um novo objeto Vertice com o nome e o índice (posição no array) e o armazena.
            this.vertices[i] = new Vertice(nomesVertices[i], i);
        }
    }

    // Método para adicionar uma aresta (uma "rua") ao grafo.
    // 'origem': o índice do vértice de onde a aresta parte.
    // 'destino': o índice do vértice para onde a aresta leva.
    // 'peso': o "custo" ou "distância" de percorrer essa aresta.
    public void adicionarAresta(int origem, int destino, int peso) {
        // Adiciona uma nova Aresta à lista de adjacência do vértice de 'origem'.
        // Como só adicionamos em uma direção (origem -> destino), este é um grafo direcionado.
        adjacencias.get(origem).add(new Aresta(destino, peso));
    }

    // Retorna a lista de arestas (vizinhos) que saem de um vértice 'u', dado pelo seu índice.
    public List<Aresta> vizinhos(int u) {
        return adjacencias.get(u);
    }

    // Retorna o array de todos os objetos Vertice do grafo.
    public Vertice[] getVertices() {
        return vertices;
    }

    // Retorna a quantidade total de vértices no grafo.
    public int quantidadeVertices() {
        return vertices.length;
    }

    // --- Classe interna Vertice ---
    // Representa um único nó ou "região" no grafo.
    // É uma classe 'static' aninhada, o que significa que não precisa de uma instância de 'Grafo'
    // para ser instanciada, mas está logicamente agrupada a 'Grafo'.
    static class Vertice {
        String nome; // Nome da região (ex: "RegiaoA"), para facilitar a identificação.
        int indice; // Posição numérica do vértice no array 'vertices' do grafo.
        // 'distancia': Armazena a menor distância conhecida da origem até este vértice.
        // É inicializada com um valor muito alto (Integer.MAX_VALUE) para simular "infinito".
        int distancia; 
        // 'predecessor': O vértice que vem imediatamente antes no caminho mais curto encontrado até agora.
        // Usado para reconstruir a rota completa no final.
        Vertice predecessor; 

        // Construtor do Vertice.
        Vertice(String nome, int indice) {
            this.nome = nome;
            this.indice = indice;
            this.distancia = Integer.MAX_VALUE; // Define a distância inicial como "infinito" (maior valor int).
            this.predecessor = null; // Inicialmente, não há predecessor definido.
        }
    }

    // --- Classe interna Aresta ---
    // Representa uma conexão ou "rua" direcionada entre dois vértices.
    static class Aresta {
        int destino; // O índice do vértice para onde esta aresta leva.
        int peso;    // O "custo" de percorrer esta aresta. Para Dijkstra, este peso deve ser não negativo.

        // Construtor da Aresta.
        Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }
    }
}

// --- Classe Dijkstra ---
// Esta classe contém a implementação principal do algoritmo de Dijkstra para encontrar os
// caminhos mais curtos de uma única origem para todos os outros vértices em um grafo
// com pesos de arestas não negativos.
public class Dijkstra {

    // Método que executa o algoritmo de Dijkstra a partir do vértice de origem 's'.
    public void executar(Grafo g, int s) {
        // Passo 1: Inicialização.
        // Define a distância da origem 's' como 0. Todas as outras distâncias são "infinito".
        // Todos os vértices não têm predecessor no início.
        inicializarFonteUnica(g, s); 

        // Cria o heap de prioridade (MinHeap) com todos os vértices do grafo.
        // O vértice de origem 's' (com distância 0) será o primeiro ou um dos primeiros a ser extraído.
        MinHeap q = new MinHeap(g.getVertices()); 

        // Passo 2: Loop Principal do Dijkstra.
        // O algoritmo continua enquanto houver vértices na fila de prioridade para serem processados.
        // Um vértice é removido da fila quando sua distância mais curta da origem é finalizada.
        while (!q.isEmpty()) {
            // Extrai o vértice 'u' com a MENOR distância estimada da origem do heap.
            // Esta é a parte "gulosa" do Dijkstra: sempre processamos o caminho mais promissor primeiro.
            var u = q.extractMin(); 

            // Para cada vizinho 'v' de 'u' (ou seja, para cada aresta que sai de 'u')...
            for (Grafo.Aresta aresta : g.vizinhos(u.indice)) {
                // Pega o objeto Vertice 'v' que é o destino da 'aresta'.
                Grafo.Vertice v = g.getVertices()[aresta.destino];

                // Tenta "relaxar" a aresta (u, v).
                // Relaxar significa verificar se o caminho da origem para 'v'
                // passando por 'u' (u.distancia + aresta.peso) é mais curto que a distância atual de 'v'.
                if(relaxar(u, v, aresta.peso)){ 
                    // Se o relaxamento foi bem-sucedido (ou seja, a distância de 'v' foi reduzida)
                    // E 'v' ainda está presente no heap (ainda não foi processado/extraído)...
                    if(q.contains(v)){
                        // Atualiza a posição de 'v' no heap.
                        // Como a distância de 'v' diminuiu, ele pode precisar "subir" no heap
                        // para manter a propriedade do heap (menor distância no topo).
                        q.decreaseKey(v); 
                    }
                }
            }
        }
    }
    
    // relaxar: Aplica a operação de relaxamento para uma aresta (u, v) com peso 'w'.
    // Esta é a parte central de atualização do algoritmo.
    // Retorna 'true' se a distância de 'v' foi atualizada, 'false' caso contrário.
    private boolean relaxar(Grafo.Vertice u, Grafo.Vertice v, int w) {
        // IMPORTANTE: A condição 'u.distancia != Integer.MAX_VALUE' é CRUCIAL!
        // Ela garante que só tentamos relaxar arestas a partir de vértices que já são alcançáveis
        // e têm uma distância finita. Somar com Integer.MAX_VALUE pode causar overflow.
        // Se a distância de 'u' + o peso da aresta for menor que a distância atual de 'v'...
        if (u.distancia != Integer.MAX_VALUE && u.distancia + w < v.distancia) {
            v.distancia = u.distancia + w; // Atualiza a nova menor distância para 'v'.
            v.predecessor = u;             // Define 'u' como o predecessor de 'v' no caminho mais curto.
            return true;                   // Indica que a distância de 'v' foi melhorada.
        }
        return false;                      // A distância de 'v' não foi melhorada (o caminho existente era melhor ou igual).
    }

    // inicializarFonteUnica: Prepara o grafo para a execução do Dijkstra.
    // Define as distâncias iniciais e predecessores de todos os vértices.
    private void inicializarFonteUnica(Grafo g, int s) {
        // Percorre todos os vértices do grafo.
        for (Grafo.Vertice v : g.getVertices()) {
            v.distancia = Integer.MAX_VALUE; // Define a distância de todos como "infinito".
            v.predecessor = null;           // Define todos os predecessores como nulos.
        }
        // A distância do vértice de origem 's' para ele mesmo é 0.
        g.getVertices()[s].distancia = 0; 
    }

    // buscarIndicePorNome: Método auxiliar estático para encontrar o índice numérico de um vértice pelo seu nome.
    // Útil para permitir que o usuário insira nomes de regiões em vez de índices numéricos.
    private static int buscarIndicePorNome(Grafo g, String nome) {
        for (Grafo.Vertice v : g.getVertices()) {
            if (v.nome.equalsIgnoreCase(nome)) { // Compara os nomes ignorando maiúsculas/minúsculas.
                return v.indice;
            }
        }
        return -1; // Retorna -1 se o nome da região não for encontrado no grafo.
    }

    // main: Método principal onde o programa começa a ser executado.
    public static void main(String[] args) {
        // Cria um array de strings com os nomes das regiões (vértices) do nosso mapa de exemplo.
        String[] nomes = {"RegiaoA", "RegiaoB", "RegiaoC", "RegiaoD", "RegiaoE"};

        // Instancia um novo objeto Grafo com as regiões definidas.
        Grafo g = new Grafo(nomes);

        // Adiciona as arestas (conexões entre regiões) ao grafo, com seus respectivos pesos (custos/distâncias).
        // Formato: adicionarAresta(índice_origem, índice_destino, peso).
        g.adicionarAresta(0, 1, 10); // RegiaoA (índice 0) → RegiaoB (índice 1) com peso 10
        g.adicionarAresta(0, 3, 5);  // RegiaoA (0) → RegiaoD (3) com peso 5
        g.adicionarAresta(1, 2, 1);  // RegiaoB (1) → RegiaoC (2) com peso 1
        g.adicionarAresta(1, 3, 2);  // RegiaoB (1) → RegiaoD (3) com peso 2
        g.adicionarAresta(2, 4, 4);  // RegiaoC (2) → RegiaoE (4) com peso 4
        g.adicionarAresta(3, 1, 3);  // RegiaoD (3) → RegiaoB (1) com peso 3
        g.adicionarAresta(3, 2, 9);  // RegiaoD (3) → RegiaoC (2) com peso 9
        g.adicionarAresta(3, 4, 2);  // RegiaoD (3) → RegiaoE (4) com peso 2
        g.adicionarAresta(4, 2, 6);  // RegiaoE (4) → RegiaoC (2) com peso 6

        // Cria um objeto Scanner para ler a entrada do usuário.
        Scanner sc = new Scanner(System.in);
        // Solicita ao usuário que digite o nome da região de origem.
        System.out.print("Digite a região de ORIGEM: ");
        String nomeOrigem = sc.nextLine();

        // Solicita ao usuário que digite o nome da região de destino.
        System.out.print("Digite a região de DESTINO: ");
        String nomeDestino = sc.nextLine();

        // Busca os índices numéricos correspondentes aos nomes das regiões informadas pelo usuário.
        int indiceOrigem = buscarIndicePorNome(g, nomeOrigem);
        int indiceDestino = buscarIndicePorNome(g, nomeDestino);

        // Verifica se os nomes das regiões de origem ou destino são válidos (encontrados no grafo).
        if (indiceOrigem == -1 || indiceDestino == -1) {
            System.out.println("Região de origem ou destino inválida. Por favor, verifique os nomes digitados.");
            sc.close(); // Fecha o scanner antes de encerrar o programa.
            return;
        }
 
        // Cria uma instância do algoritmo de Dijkstra.
        var dijkstra = new Dijkstra();
        // Executa o algoritmo de Dijkstra a partir do índice da região de origem fornecida pelo usuário.
        dijkstra.executar(g, indiceOrigem);
 
        // --- Exibição dos Resultados Detalhados para Todas as Regiões ---
        System.out.println("\n--- Distâncias Mínimas e Predecessores a partir de " + nomes[indiceOrigem] + " ---");
        // Itera sobre todos os vértices (regiões) do grafo para imprimir seus resultados.
        for (Grafo.Vertice v : g.getVertices()) {
            // Imprime a menor distância calculada da região de origem até o vértice atual.
            // Se a distância ainda for Integer.MAX_VALUE, significa que o vértice é inalcançável.
            System.out.printf("Distância de %s a %s: %d\n", nomes[indiceOrigem], v.nome, v.distancia);

            // Imprime o predecessor do vértice, que é o vértice que o antecede no caminho mais curto.
            // Isso ajuda a reconstruir a rota.
            System.out.printf("%s\n",
                v.predecessor != null // Se o vértice tem um predecessor definido...
                    ? "Predecessor de " + v.nome + ": " + v.predecessor.nome // Mostra o nome do predecessor.
                    : (v.indice == indiceOrigem ? "É a própria origem" : "Não tem predecessor (inalcançável da origem)") // Se não tem, verifica se é a origem ou se é inalcançável.
            );
        }

        System.out.println("\n--- Resumo do Caminho para a Região de Destino ---");
        // Recupera o objeto Vertice correspondente à região de destino escolhida pelo usuário.
        Grafo.Vertice destinoVertice = g.getVertices()[indiceDestino];
        // Verifica se a região de destino é alcançável a partir da origem.
        if (destinoVertice.distancia == Integer.MAX_VALUE) {
            System.out.println("Não existe caminho de " + nomeOrigem + " até " + nomeDestino + ".");
        } else {
            System.out.println("O menor custo de " + nomeOrigem + " até " + nomeDestino + " é: " + destinoVertice.distancia);
        }
        sc.close(); // Fecha o scanner para liberar os recursos do sistema.
    }
}