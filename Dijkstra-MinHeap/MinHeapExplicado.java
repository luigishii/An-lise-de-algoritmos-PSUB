import java.util.HashMap; // Para usar a estrutura HashMap
import java.util.Map;     // Para usar a interface Map

// Implementação manual de uma fila de prioridade usando Min-Heap para vértices de um grafo.
// Esta classe gerencia os vértices do grafo com base em suas distâncias estimadas,
// sempre mantendo o vértice com a menor distância na "raiz" do heap.
public class MinHeap {
    // 'A': O array que armazena os vértices do heap.
    // Usamos o índice 1 como raiz (a posição 0 não é utilizada) para simplificar
    // os cálculos de índices de pai e filhos (e.g., filho da esquerda de i é 2*i).
    private Grafo.Vertice[] A; 
    // 'heapSize': Representa o número de elementos válidos que estão atualmente no heap.
    // Ele diminui quando um elemento é extraído.
    private int heapSize; 

    // 'posicao': Um mapa crucial para o desempenho do algoritmo.
    // Ele mapeia o INDICE DO VÉRTICE (que é um identificador único do vértice no grafo)
    // para a POSIÇÃO ATUAL desse vértice dentro do array 'A' do heap.
    // Isso é vital para operações como 'decreaseKey', pois permite encontrar
    // e atualizar rapidamente a posição de um vértice sem ter que percorrer todo o array.
    private Map<Integer, Integer> posicao; 

    // Construtor do MinHeap. Recebe um array de 'vertices' do grafo.
    // Ele inicializa o heap com todos esses vértices.
    public MinHeap(Grafo.Vertice[] vertices) {
        heapSize = vertices.length; // O tamanho inicial do heap é igual ao número total de vértices do grafo.
        A = new Grafo.Vertice[heapSize + 1]; // Cria o array do heap (tamanho + 1 para usar índice 1 como base).
        posicao = new HashMap<>(); // Inicializa o mapa que rastreia as posições dos vértices.

        // Preenche o array 'A' do heap com os vértices e o mapa 'posicao' com suas localizações iniciais.
        for (int i = 0; i < heapSize; i++) {
            A[i + 1] = vertices[i]; // Coloca o vértice no array do heap (começando da posição 1).
            posicao.put(vertices[i].indice, i + 1); // Registra a posição do vértice no mapa: (índice_do_vértice, posição_no_heap).
        }

        // Após preencher o array, organiza-o para que ele satisfaça a propriedade de Min-Heap.
        // Isso garante que o menor elemento estará na raiz (A[1]) após a construção.
        buildMinHeap();
    }

    // Retorna o índice do pai de um nó no heap (para um heap baseado em 1).
    private int parent(int i) { return i / 2; }

    // Retorna o índice do filho à esquerda de um nó.
    private int left(int i) { return 2 * i; }

    // Retorna o índice do filho à direita de um nó.
    private int right(int i) { return 2 * i + 1; }

    // Troca dois elementos (vértices) de posição no array 'A' do heap.
    // **Crucialmente**, ela também ATUALIZA as posições desses vértices no mapa 'posicao' para manter a consistência.
    private void swap(int i, int j) {
        Grafo.Vertice temp = A[i]; // Guarda temporariamente o vértice na posição 'i'.
        A[i] = A[j];             // Move o vértice de 'j' para 'i'.
        A[j] = temp;             // Move o vértice original de 'i' para 'j'.

        // Atualiza o mapa 'posicao' com as novas posições dos vértices após a troca.
        posicao.put(A[i].indice, i);
        posicao.put(A[j].indice, j);
    }

    // minHeapify: Esta é a função central que mantém a propriedade de Min-Heap para uma subárvore.
    // Ela garante que o valor do nó pai seja sempre menor ou igual ao valor de seus filhos.
    // Se a propriedade é violada, o nó é trocado com o menor de seus filhos, e o processo se repete recursivamente.
    private void minHeapify(int i) {
        int l = left(i);   // Índice do filho esquerdo.
        int r = right(i);  // Índice do filho direito.
        int smallest = i;  // Assume, inicialmente, que o menor elemento é o próprio nó 'i'.

        // Verifica se o filho esquerdo existe (está dentro do 'heapSize') e se sua distância é menor que a do 'smallest' atual.
        if (l <= heapSize && A[l].distancia < A[smallest].distancia) {
            smallest = l; // Se sim, o filho esquerdo é o novo 'smallest'.
        }
        // Verifica se o filho direito existe e se sua distância é menor que a do 'smallest' atual.
        if (r <= heapSize && A[r].distancia < A[smallest].distancia) {
            smallest = r; // Se sim, o filho direito é o novo 'smallest'.
        }

        // Se o 'smallest' não é o nó original 'i' (ou seja, um filho era menor que o pai)...
        if (smallest != i) {
            swap(i, smallest);       // Troca o nó 'i' com o 'smallest' (o menor filho).
            minHeapify(smallest);    // Chama recursivamente minHeapify para o nó que foi movido
                                     // (agora na posição 'smallest') para garantir que a propriedade
                                     // de heap mínimo seja mantida nessa subárvore.
        }
    }

    // buildMinHeap: Constrói um heap mínimo a partir de um array inicial de vértices.
    // Ele faz isso chamando 'minHeapify' de forma eficiente, começando pelos nós mais "baixos" que têm filhos
    // e subindo até a raiz.
    private void buildMinHeap() {
        // Começa do último nó que tem filhos (heapSize / 2) e vai retrocedendo até a raiz (índice 1).
        for (int i = heapSize / 2; i >= 1; i--) {
            minHeapify(i); // Garante a propriedade de heap mínimo para cada subárvore.
        }
    }

    // Verifica se o heap está vazio (ou seja, se não há mais elementos válidos para extrair).
    public boolean isEmpty() {
        return heapSize < 1;
    }

    // extractMin: Remove e retorna o vértice com a menor distância.
    // Este vértice é sempre a raiz do heap (A[1]) em um Min-Heap.
    public Grafo.Vertice extractMin() {
        if (heapSize < 1) { // Verifica se o heap está vazio antes de tentar extrair.
            throw new RuntimeException("Heap vazio: Não é possível extrair de um heap vazio."); 
        }

        Grafo.Vertice min = A[1]; // O vértice com a menor distância é a raiz.
        A[1] = A[heapSize]; // Move o último elemento do heap para a posição da raiz.
        posicao.put(A[1].indice, 1); // Atualiza a posição do elemento recém-movido para a raiz no mapa 'posicao'.
        heapSize--; // Reduz o tamanho lógico do heap (o último elemento agora está na raiz e será reorganizado).
        minHeapify(1); // Restaura a propriedade de Min-Heap a partir da nova raiz (posição 1).

        return min; // Retorna o vértice que foi extraído (o de menor distância).
    }

    // decreaseKey: É chamada quando a distância de um vértice 'v' que já está no heap é REDUZIDA.
    // Isso significa que 'v' pode ter se tornado "mais leve" e, portanto, pode precisar "subir" no heap
    // para manter a propriedade de Min-Heap (onde o pai é sempre menor ou igual aos filhos).
    public void decreaseKey(Grafo.Vertice v) {
        // Pega a posição atual do vértice 'v' no array 'A' do heap usando o mapa 'posicao'.
        int i = posicao.get(v.indice); 

        // "Sobe" o vértice 'v' no heap.
        // Enquanto 'v' não for a raiz (i > 1) E a distância do pai for maior que a distância de 'v'...
        while (i > 1 && A[parent(i)].distancia > A[i].distancia) {
            swap(i, parent(i)); // Troca 'v' com seu pai.
            i = parent(i);     // Atualiza 'i' para a nova posição de 'v' (que agora é a antiga posição do pai).
        }
    }

    // contains: Verifica se um vértice ainda está "logicamente" presente no heap (ou seja, ainda não foi extraído).
    public boolean contains(Grafo.Vertice v) {
        // Verifica duas condições:
        // 1. Se a chave do vértice existe no mapa 'posicao'.
        // 2. Se a posição mapeada está dentro do tamanho atual do 'heapSize' (evita contar elementos que
        //    foram "removidos" logicamente, mas ainda estão no array subjacente).
        return posicao.containsKey(v.indice) && posicao.get(v.indice) <= heapSize;
    }
}