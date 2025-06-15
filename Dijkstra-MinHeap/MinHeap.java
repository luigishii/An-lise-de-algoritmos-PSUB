import java.util.HashMap;
import java.util.Map;

// Implementação manual de uma fila de prioridade usando Min-Heap para vértices de um grafo
public class MinHeap {
    private Grafo.Vertice[] A; // Array do heap, começando no índice 1 (posição 0 não usada)
    private int heapSize; // Quantos elementos estão atualmente no heap

    // Mapeia o índice do vértice (g.vertices[i].indice) para a posição dele dentro do array A
    private Map<Integer, Integer> posicao;

    // Construtor: recebe os vértices do grafo e constrói um heap mínimo com base em suas distâncias
    public MinHeap(Grafo.Vertice[] vertices) {
        heapSize = vertices.length;
        A = new Grafo.Vertice[heapSize + 1]; // heap começa do índice 1
        posicao = new HashMap<>();

        // Preenche o array e o map de posições
        for (int i = 0; i < heapSize; i++) {
            A[i + 1] = vertices[i]; // pula índice 0
            posicao.put(vertices[i].indice, i + 1); // guarda onde está o vértice com índice `indice`
        }

        // Organiza os elementos de A de acordo com a propriedade de Min-Heap
        buildMinHeap();
    }

    // Retorna o índice do pai de um nó no heap
    private int parent(int i) { return i / 2; }

    // Índice do filho à esquerda
    private int left(int i) { return 2 * i; }

    // Índice do filho à direita
    private int right(int i) { return 2 * i + 1; }

    // Troca dois elementos no heap e atualiza o map de posições
    private void swap(int i, int j) {
        Grafo.Vertice temp = A[i];
        A[i] = A[j];
        A[j] = temp;

        // Atualiza as posições após a troca
        posicao.put(A[i].indice, i);
        posicao.put(A[j].indice, j);
    }

    // Garante que a subárvore com raiz em `i` obedeça à propriedade de Min-Heap
    private void minHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;

        // Encontra o menor entre pai e filhos
        if (l <= heapSize && A[l].distancia < A[smallest].distancia) smallest = l;
        if (r <= heapSize && A[r].distancia < A[smallest].distancia) smallest = r;

        // Se o menor não for o pai, troca e continua recursivamente
        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    // Constrói o Min-Heap aplicando `minHeapify` de "baixo para cima" (nó raiz até as folhas)
    private void buildMinHeap() {
        for (int i = heapSize / 2; i >= 1; i--) {
            minHeapify(i);
        }
    }

    // Verifica se o heap está vazio
    public boolean isEmpty() {
        return heapSize < 1;
    }

    // Remove e Retorna o vértice com a menor distância (posição 1 do heap)
    public Grafo.Vertice extractMin() {
        if (heapSize < 1) throw new RuntimeException("Heap vazio");

        Grafo.Vertice min = A[1]; // raiz
        A[1] = A[heapSize]; // move último elemento para a raiz
        posicao.put(A[1].indice, 1); // atualiza posição no map
        heapSize--; // reduz tamanho do heap
        minHeapify(1); // reestabelece propriedade de min heap

        return min; // retorna o vértice com menor distância
    }

    // Atualiza a posição de um vértice no heap após redução de sua distância
    public void decreaseKey(Grafo.Vertice v) {
        int i = posicao.get(v.indice); // pega a posição atual do vértice no heap

        // "Sobe" o vértice enquanto a distância do pai for maior
        while (i > 1 && A[parent(i)].distancia > A[i].distancia) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // Verifica se um vértice ainda está presente no heap
    public boolean contains(Grafo.Vertice v) {
        return posicao.containsKey(v.indice) && posicao.get(v.indice) <= heapSize;
    }
}