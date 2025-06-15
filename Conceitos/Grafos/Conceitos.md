1. Tipos de Grafos Baseados em Direção:

    Grafo Não Direcionado:

        - As arestas não têm sentido. Se há uma aresta de A para B, significa que você pode ir de A para B E de B para A.
        - Representação: (A, B) ou {A, B}.
    
    Grafo Direcionado (Digrafo):
        
        - As arestas têm sentido. Uma aresta de A para B significa que você pode ir de A para B, mas não necessariamente de B para A.
        - Representação: (A → B) ou <A, B>.

2. Tipos de Grafos Baseados em Peso:

    Grafo Não Ponderado:

        - As arestas não têm um valor associado (ou todas têm o mesmo valor, como 1). A "distância" é o número de arestas.
    
    Grafo Ponderado:

        - Cada aresta tem um peso (ou custo) associado. Esse peso pode representar distância, tempo, custo financeiro, etc.

3. Elementos e Propriedades de Vértices e Arestas:
     
    - Vértices Adjacentes: Dois vértices são adjacentes se estão conectados diretamente por uma aresta.

    - Grau de um Vértice (para grafos não direcionados): O número de arestas conectadas a ele.

    - Grau de Entrada (In-degree) e Grau de Saída (Out-degree) (para grafos direcionados):

        - Grau de Entrada: Número de arestas que chegam ao vértice.
        - Grau de Saída: Número de arestas que saem do vértice.

    - Caminho: Uma sequência de vértices conectados por arestas.

    - Ciclo (ou Circuito): Um caminho que começa e termina no mesmo vértice, sem repetir arestas (e geralmente sem repetir vértices, exceto o inicial/final).

    - Comprimento de um Caminho:

        - Em grafos não ponderados: Número de arestas no caminho.
        - Em grafos ponderados: Soma dos pesos das arestas no caminho.


4. Relações e Estruturas em Grafos:

    Grafo Conexo (para grafos não direcionados):
        - Existe um caminho entre quaisquer dois vértices no grafo. Todo o grafo é uma única "peça".

    Componente Conexo: Se um grafo não é conexo, ele pode ser dividido em componentes conexos, que são as partes "conexas" separadas do grafo.

    Grafo Fortemente Conexo (para grafos direcionados):

        - Existe um caminho direcionado de A para B E um caminho direcionado de B para A, para quaisquer dois vértices A e B.

    Componente Fortemente Conexo (CFC): Em um digrafo, é um subconjunto de vértices onde todos os vértices são mutuamente alcançáveis.

    Grafo Acíclico: Um grafo que não contém nenhum ciclo.

    Grafo Acíclico Direcionado (DAG - Directed Acyclic Graph): Um grafo direcionado que não possui ciclos. Muito importante em agendamento de tarefas e dependências.


    Árvore:

        - Um tipo especial de grafo não direcionado que é conexo e não contém ciclos.
        - Em uma árvore com V vértices, sempre haverá exatamente V−1 arestas.


    Floresta: Um conjunto de árvores (um grafo acíclico não direcionado que pode ser desconexo).

    Árvore Geradora (Spanning Tree):

        - Um subgrafo de um grafo conexo que é uma árvore e conecta todos os vértices do grafo original.
        Sempre terá V−1 arestas.

    Árvore Geradora Mínima (AGM ou MST - Minimum Spanning Tree):

        - Em um grafo ponderado, é uma Árvore Geradora cuja soma dos pesos das arestas é a menor possível.
        - Algoritmos: Kruskal, Prim.

5. Representações de Grafos:

    Matriz de Adjacência:

        Uma matriz V×V (onde V é o número de vértices).
        matriz[i][j] = 1 (ou peso da aresta) se existe uma aresta de i para j. 0 caso contrário.
        Vantagens: Fácil de verificar se uma aresta existe (O(1)), boa para grafos densos (muitas arestas).
        Desvantagens: Consome muita memória para grafos esparsos (poucas arestas), pois a maioria da matriz será zero.

    
    Lista de Adjacência:

        Um array (ou vetor) de listas.
        Cada posição array[i] contém uma lista de todos os vértices adjacentes a i.
        Vantagens: Eficiente em memória para grafos esparsos, fácil de encontrar todos os vizinhos de um vértice.
        Desvantagens: Verificar a existência de uma aresta entre dois vértices específicos pode ser mais lento (O(grau do vértice)).

6. Algoritmos Famosos de Grafos:

    BFS (Busca em Largura - Breadth-First Search):

        Explora o grafo nível por nível (vizinhos diretos, depois vizinhos dos vizinhos, etc.).
        Usa uma fila (Queue).
        Para que serve: Encontrar o caminho mais curto em grafos não ponderados, encontrar componentes conexos, web crawlers.
    
    DFS (Busca em Profundidade - Depth-First Search):

        Explora o grafo indo o mais fundo possível por um caminho antes de retroceder e tentar outro.
        Usa uma pilha (Stack) ou recursão.
        Para que serve: Detecção de ciclos, ordenação topológica, encontrar componentes fortemente conexos.
        
    Dijkstra:

        Encontra o caminho mais curto de uma única origem para todos os outros vértices em grafos ponderados com pesos não negativos.
        Usa uma fila de prioridade (Min-Heap).

    Bellman-Ford:

        Encontra o caminho mais curto de uma única origem para todos os outros vértices em grafos ponderados que podem ter pesos negativos.
        Consegue detectar ciclos negativos.
        É mais lento que Dijkstra.

    Kruskal:

        Encontra a Árvore Geradora Mínima (AGM) em grafos ponderados não direcionados.
        Funciona de forma gulosa, adicionando as arestas de menor peso que não formam ciclo.
        Usa uma estrutura Union-Find para detectar ciclos.

    Prim:

        Outro algoritmo para encontrar a Árvore Geradora Mínima (AGM) em grafos ponderados não direcionados.
        Também funciona de forma gulosa, crescendo a árvore a partir de um vértice inicial.