package heap;

import java.util.Arrays;

public class HeapSort {
  public static int parent (int i){
    return i / 2;  
  }
  public static int left(int i){
    return 2 * i;
  }
  public static int right(int i){
    return 2 * i + 1;
  }

  private static void swap (int [] A, int i, int j){
    var temp = A[i];
    A[i] = A[j];
    A[j] = temp;
  }
  public static void maxHeapify(int [] A, int i, int n){
    //encontrar os indices dos filhos
    var l = left(i);
    var r = right(i);
    int largest;
    //encontrar, entre pai, filho esquerdo e filho direito, quem é o maior
    if( l <= n && A[l] > A[i])
      largest = l;
    else
      largest = i;

    if (r <= n && A[r] > A[largest])
      largest = r;
    //se for o caso, trocar pai por filho maior
    //aplicar o procedimento de maneira recursiva
    if(largest != i){
      swap(A, i, largest);
      maxHeapify(A, largest, n);
    }
  }
  
  public static void buildMaxHeap(int [] A, int n){
    //aplicar o heapify de baixo para cima, a partir do primeiro elemento que tem filho
    //por regra, o primeiro elemento que pode ter filho é o n / 2
    for(int i = n / 2; i >= 1; i--){
      maxHeapify(A, i, n);
    }
  }

  public static void heapSort(int [] A){
    //ignorar o indice 0
    int n = A.length - 1;
    buildMaxHeap(A, n);
    for(int i = n; i >= 2; i--){
      swap(A, 1 , n);
      n--;
      maxHeapify(A, 1, n);
    }
  }

  public static int heapMaximum(int[] A){
    // Retornar o maior valor de um max-heap = retornar a posição da raiz (posição 1)
    return A[1];
  }

  // Remove e Retorna o maior valor do max-heap
  // Rearranja a estrutura para manter a propriedade de max-heap considerando o valor removido
  public static int heapExtractMax(int[] A, int heapSize){
    
    // Se o tamanho é < 1, quer dizer que o heap está vazio
    if(heapSize < 1){
      throw new RuntimeException("Não possui elementos para remover.");
    }
    // Armazena o maior valor no max-heap, que sempre será a raiz (indice 1)
    int max = A[1];

    // Move o valor no último índice para a raiz, sobrescrevendo (excluindo) o valor atual da raiz
    A[1] = A[heapSize];

    // Reduz o tamanho do heap
    heapSize--;

    // Aplica o maxHeapify() na nova raiz para restaurar a propriedade de max-heap
    maxHeapify(A, 1, heapSize);

    return max;
  }

  public static void main(String[] args){
    int[] vetor = {0, 15, 7, 9, 20, 3, 1, 12};

    System.out.println("Vetor Original: ");
    System.out.println(Arrays.toString(vetor));

    HeapSort.heapSort(vetor);

    System.out.println("Vetor Ordenado: ");
    System.out.println(Arrays.toString(vetor));
  }
}