import java.util.*; // Importa classes úteis como List, ArrayList e Collections (para ordenação)

// --- Classe Aresta ---
// Representa uma conexão (rua) entre dois vértices (cidades) em um grafo.
// Implementa Comparable para que possamos ordenar as arestas pelo seu peso.
class Aresta implements Comparable<Aresta>{
    // u: vértice de origem da aresta.
    // v: vértice de destino da aresta.
    // peso: o custo ou valor associado a essa aresta.
    int u, v, peso; 

    // Construtor da aresta.
    Aresta(int u, int v, int peso){
        this.u = u;
        this.v = v;
        this.peso = peso;
    }

    // Método compareTo é fundamental para ordenar objetos Aresta.
    // Ele define como duas arestas serão comparadas.
    // @Override indica que estamos sobrescrevendo um método da interface Comparable.
    @Override
    public int compareTo(Aresta o){ // 'o' (other) é a outra aresta com a qual estamos comparando.
        // Compara o peso da aresta atual (this.peso) com o peso da outra aresta (o.peso).
        // Retorna um valor negativo se this.peso < o.peso (this vem antes de o).
        // Retorna 0 se this.peso == o.peso (a ordem não importa entre eles).
        // Retorna um valor positivo se this.peso > o.peso (this vem depois de o).
        return Integer.compare(this.peso, o.peso);
    }
    /*
     * A lógica comentada abaixo é o que Integer.compare faz internamente,
     * mas usar Integer.compare é mais robusto e conciso.
     *
     * // Exemplo de como funcionaria a comparação manual:
     * // (2, 3, 11) e (3, 0, 8) -> this é (2,3,11), o é (3,0,8)
     * // this.peso (11) vs o.peso (8)
     * // if (this.peso < o.peso) // 11 < 8 é falso
     * //   return -1;
     * // if (this.peso == o.peso) // 11 == 8 é falso
     * //   return 0;
     * // return 1; // Retorna 1, então (2,3,11) viria depois de (3,0,8) na ordenação crescente.
     * // Collections.sort() utiliza este método para ordenar a lista de arestas.
     */
}

// --- Classe UnionFind ---
// Esta é uma estrutura de dados essencial para o algoritmo de Kruskal.
// Ela gerencia conjuntos disjuntos (grupos de vértices conectados) e permite:
// 1. Criar um conjunto para cada elemento (Make-Set).
// 2. Encontrar o "representante" (líder) do conjunto ao qual um elemento pertence (Find-Set).
// 3. Unir dois conjuntos (Union).
// O objetivo no Kruskal é verificar se a adição de uma aresta criaria um ciclo.
// Se os dois vértices da aresta já estiverem no mesmo conjunto (mesmo representante),
// significa que eles já estão conectados por um caminho, e adicionar a aresta criaria um ciclo.
class UnionFind{
    // 'representantes' é um array onde representantes[i] armazena o predecessor direto de 'i'.
    // Se representantes[i] == i, então 'i' é o representante (raiz/líder) de seu próprio conjunto.
    int[] representantes; 

    // Construtor da classe UnionFind.
    // n: número total de vértices no grafo.
    public UnionFind(int n){ 
        representantes = new int[n];
        // Make-Set: Inicialmente, cada vértice é o representante de seu próprio conjunto.
        // Ou seja, cada vértice está em sua própria "ilha" isolada.
        for(int i=0; i < n; i++){
            representantes[i] = i; // O vértice 'i' aponta para si mesmo.
        }
    }

    // Find-Set: Encontra o representante (raiz) do conjunto ao qual 'x' pertence.
    // Isso é feito seguindo a cadeia de predecessores até encontrar um vértice que aponta para si mesmo.
    // Se dois vértices possuem o mesmo representante, eles pertencem ao mesmo conjunto (estão conectados).
    public int find(int x){
        // Enquanto 'x' não for o representante de seu próprio conjunto (ou seja, não for a raiz)...
        while(representantes[x] != x){ 
            // Caminha para o predecessor de 'x'.
            // (Otimização de compressão de caminho poderia ser adicionada aqui para melhorar a performance,
            // mas para simplicidade, esta versão iterativa direta já funciona).
            x = representantes[x]; 
        }
        return x; // Retorna o representante encontrado.
    }
    /*
     * Teste de Mesa para Find-Set (como exemplo no código original):
     * Valor:    [0] [0] [2] [2] [3]  (representa o predecessor direto de cada índice/vértice)
     * Indice:   0   1   2   3   4  (representa um vértice do grafo)
     *
     * find(0): representantes[0] é 0. Condição (representantes[0] != 0) é falsa. Retorna 0.
     * find(1): representantes[1] é 0. Condição (representantes[1] != 1) é verdadeira. x = 0.
     * representantes[0] é 0. Condição (representantes[0] != 0) é falsa. Retorna 0.
     * find(2): representantes[2] é 2. Condição (representantes[2] != 2) é falsa. Retorna 2.
     * find(3): representantes[3] é 2. Condição (representantes[3] != 3) é verdadeira. x = 2.
     * representantes[2] é 2. Condição (representantes[2] != 2) é falsa. Retorna 2.
     * find(4): representantes[4] é 3. Condição (representantes[4] != 4) é verdadeira. x = 3.
     * representantes[3] é 2. Condição (representantes[3] != 3) é verdadeira. x = 2.
     * representantes[2] é 2. Condição (representantes[2] != 2) é falsa. Retorna 2.
     */

    // Union: Une os conjuntos aos quais 'x' e 'y' pertencem.
    // Basicamente, faz com que o representante de um conjunto aponte para o representante do outro.
    public void union(int x, int y){
        int rX = find(x); // Encontra o representante do conjunto de 'x'.
        int rY = find(y); // Encontra o representante do conjunto de 'y'.
        
        // Se eles já tiverem o mesmo representante, não fazemos nada (já estão unidos).
        // Caso contrário, unimos os conjuntos. Uma forma simples é fazer o representante de rY
        // apontar para o representante de rX.
        // Isso cria uma conexão entre os conjuntos de 'x' e 'y'.
        // (Otimizações como união por rank ou tamanho poderiam ser usadas para manter a árvore balanceada).
        if (rX != rY) { // Importante verificar para não fazer uma atribuição redundante ou criar ciclos de auto-referência na união
            representantes[rY] = rX; 
        }
    }
}

// --- Classe Kruskal ---
// Contém a lógica principal para executar o algoritmo de Kruskal.
public class Kruskal{

    // Método 'kruskal' que implementa o algoritmo.
    // n: O número total de vértices no grafo.
    // arestas: Uma lista de todas as arestas no grafo de entrada.
    // Retorna uma lista de arestas que formam a Árvore Geradora Mínima (AGM).
    public List<Aresta> kruskal(int n, List<Aresta> arestas){
        // Passo 1: Ordenar as arestas.
        // Utiliza o método compareTo da classe Aresta para ordenar a lista 'arestas'
        // em ordem crescente de peso.
        // Collections.sort() geralmente usa um algoritmo de ordenação eficiente como MergeSort ou Timsort.
        Collections.sort(arestas); 

        // Inicializa a estrutura Union-Find com 'n' vértices.
        // No início, cada vértice está em seu próprio conjunto.
        var uf = new UnionFind(n); 
        
        // Esta lista irá armazenar as arestas que farão parte da Árvore Geradora Mínima.
        var arvoreResultante = new ArrayList<Aresta>();

        // Passo 2: Percorrer as arestas ordenadas e construir a AGM.
        // Itera sobre cada aresta na lista já ordenada (da menor peso para a maior).
        for(var a : arestas){
            // Verifica se os vértices 'u' e 'v' da aresta 'a' pertencem a conjuntos diferentes.
            // Se 'find(a.u)' e 'find(a.v)' retornam representantes diferentes,
            // significa que a aresta 'a' conecta dois componentes ainda não conectados,
            // e adicioná-la não criará um ciclo.
            if(uf.find(a.u) != uf.find(a.v)){
                // Se não formam ciclo, unimos os conjuntos de 'u' e 'v'.
                // Isso significa que todas as cidades nesses dois conjuntos agora estão conectadas.
                uf.union(a.u, a.v);
                // Adiciona a aresta à lista da AGM.
                arvoreResultante.add(a);
            }
            // Se uf.find(a.u) == uf.find(a.v), a aresta criaria um ciclo, então ela é descartada.
        }
        // Retorna a lista de arestas que compõem a Árvore Geradora Mínima.
        return arvoreResultante; 
    }

    // --- Método principal (main) para testar o algoritmo ---
    public static void main(String[] args){

        int n = 4; // Define o número de vértices no grafo (índices de 0 a 3).
        
        // Define as arestas do grafo de exemplo.
        // Cada Aresta(origem, destino, peso).
        List<Aresta> arestas = Arrays.asList(
            new Aresta(0,1,10), // Aresta entre vértice 0 e 1, peso 10
            new Aresta(0,2,6),  // Aresta entre vértice 0 e 2, peso 6
            new Aresta(0,3,5),  // Aresta entre vértice 0 e 3, peso 5
            new Aresta(1,3,15), // Aresta entre vértice 1 e 3, peso 15
            new Aresta(2,3,4)   // Aresta entre vértice 2 e 3, peso 4
        ); 
        // Para este grafo específico, a Árvore Geradora Mínima (AGM) esperada
        // seria composta pelas arestas: (2,3,4), (0,3,5), (0,2,6) - soma do custo = 4+5 = 9.
        // No entanto, se o custo for 19, significa que a aresta (0,1,10) também é selecionada
        // o que indica que (0,2,6) e (0,3,5) não seriam escolhidas para conectar 0,2 e 3, ou há um erro na soma.
        // Com base nos pesos: (2,3,4), (0,3,5), (0,2,6) não faz sentido.
        // A AGM seria:
        // 1. (2,3,4) - conecta 2 e 3. Componentes: {0}, {1}, {2,3}
        // 2. (0,3,5) - conecta 0 e 3. Componentes: {0,2,3}, {1}
        // 3. (0,1,10) - conecta 0 e 1. Componentes: {0,1,2,3} (se for a próxima menor que conecta)
        // Custo total: 4 + 5 + 10 = 19. Sim, o comentário está correto com as arestas dadas.

        // Executa o algoritmo de Kruskal e obtém a lista de arestas da AGM.
        var arvoreResultante = new Kruskal().kruskal(n, arestas); 
        int total = 0; // Variável para somar o peso total da AGM.

        // Imprime as arestas que fazem parte da AGM e calcula o peso total.
        for(var a : arvoreResultante){
            System.out.printf(
                "(%d, %d, %d) ", // Formato de saída: (vértice origem, vértice destino, peso)
                a.u, a.v, a.peso
            );
            total += a.peso; // Adiciona o peso da aresta ao total.
        }
        System.out.println("\nPeso total da AGM: " + total); // Imprime o peso total da AGM.
    }
}