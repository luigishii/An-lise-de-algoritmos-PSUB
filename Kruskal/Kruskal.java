import java.util.*; //Collections

class Aresta implements Comparable<Aresta>{
    int u, v, peso; // u - vértice de origem da aresta; v - vértice de destino

    Aresta(int u, int v, int peso){
        this.u = u;
        this.v = v;
        this.peso = peso;
    }

    // define um critério de comparação
    @Override
    public int compareTo(Aresta o){ //o = other
        return Integer.compare(this.peso, o.peso);
    }
    // (2, 3, 11) e (3, 0, 8)
    // ListaArestas.sort()
    /* if (this.peso < o.peso)
        return -1;
       if (this.peso == o.peso)
        return 0;
       return 1; */
}

// Classe que contém os métodos union(), find-set() e make-set()
class UnionFind{
    int[] representantes; // último predecessor

    public UnionFind(int n){ // n = número de vértices do Grafo de entrada
        representantes = new int[n];
        // Make-Set = Cada vértice, inicialmente, fica em sua própria posição (conjunto) no vetor de representantes
        for(int i=0; i < n; i++){
            representantes[i] = i;
        }
    }

    // Find-Set
    // O método find(int x) percorre os predecessores de um vértice até o último predecessor possível
    // Dizemos que o último predecessor possível de um vértice é o seu representante
    // Se dois vértices possuem o mesmo representante então colocar uma aresta entre eles geraria um ciclo 
    public int find(int x){
        while(representantes[x] != x){ // encontra um representante, quando representantes[x] == x;
            x = representantes[x];
        }
        return x;
    }
    // Teste de Mesa: Find-Set
    // Valor:  [0] [0] [2] [2] [3], representa o predecessor direto
    // indice:  0   1   2   3   4, representa um vértice do grafo
    // find(0): 0 é seu próprio representante, retornaria 0
    // find(1): 1 -> 0, representante de 1 é 0, retornaria 0
    // find(2): 2 é seu próprio representante. retornaria 2
    // find(3): 3 -> 2, representante de 3 é 2, retornaria 2
    // find(4): 4 -> 3 -> 2, representante de 4 é 2, retornaria 2

    //Union
    public void union(int x, int y){
        int rX = find(x); // rX = representante de X
        int rY = find(y);
        representantes[rY] = rX; // Cria uma conexão entre os vértices x e y: eles pertencem ao mesmo conjunto
    }
}

public class Kruskal{
    public List<Aresta> kruskal(int n, List<Aresta> arestas){
        // Ordenando as arestas de forma crescente utilizando o peso como critério
        // MergeSort()
        Collections.sort(arestas); // Grafo é representado por uma lista de arestas (objeto arestas)
        var uf = new UnionFind(n); 
        var arvoreResultante = new ArrayList<Aresta>();

        // Percorremos todos as arestas do grafo
        for(var a : arestas){
            // Se a aresta entre u e v não geraria um ciclo, podemos inserir a aresta
            if(uf.find(a.u) != uf.find(a.v)){
                uf.union(a.u,a.v);
                arvoreResultante.add(a);
            }
        }
        return arvoreResultante; // árvore geradora mínima
    }

    public static void main(String[] args){

        int n = 4; // índice dos vértices vai de 0 até 3
        List<Aresta> arestas = Arrays.asList(
            new Aresta(0,1,10),
            new Aresta(0,2,6),
            new Aresta(0,3,5),
            new Aresta(1,3,15),
            new Aresta(2,3,4)
        ); // para esse grafo, a árvore geradora mínima teria um custo = 19

        var arvoreResultante = new Kruskal().kruskal(n, arestas); // kruskal retorna a árvore geradora mínima
        int total = 0;

        // imprimir os resultados
        for(var a : arvoreResultante){
            System.out.printf(
                "(%d, %d, %d) ",
                a.u, a.v, a.peso
            );
            total += a.peso;
        }
        System.out.println("Peso total: " + total);
    }
}