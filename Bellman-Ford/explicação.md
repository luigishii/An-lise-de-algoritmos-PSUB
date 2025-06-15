O algoritmo Bellman-Ford é como se você quisesse achar o caminho mais rápido ou barato para ir da sua casa até todas as outras casas de uma cidade.

1 - Chute inicial: De cara, você acha que todas as casas estão MUITO longe.

2 - Corrida de checagem: Aí, você "corre" por todas as ruas da cidade várias vezes, sempre se perguntando: "Se eu vier por essa rua, consigo chegar na próxima casa mais rápido do que eu imaginava antes?". Se sim, você atualiza a sua estimativa.

3- Detecta "atalhos mágicos": A grande sacada do Bellman-Ford é que ele consegue identificar se existe algum "atalho mágico" no mapa que te faria ganhar tempo (ou dinheiro) infinitamente se você ficasse passando por ele. Se ele encontra um desses, ele te avisa que não tem um "caminho mais rápido" definitivo.

É isso! Ele encontra o melhor caminho, mesmo com "ruas estranhas", e te alerta se algo "mágico" está acontecendo no mapa

CARACTERÍSTICAS: 
 
  - Encontra o menor caminho de uma origem para todos os outros destinos;
  - Lida com pesos de arestas negativos;
  - Detecta ciclos de peso negativo; 
  - É mais lento que o Dijkstra para grafos sem pesos negativos;
  - Garante encontrar o menor caminho se não houver ciclos negativos acessíveis;
  - Baseado na técnica de relaxamento;


Em resumo, o Bellman-Ford é um algoritmo robusto para encontrar os menores caminhos, especialmente útil quando você tem custos negativos e precisa de uma forma de detectar se há "armadilhas" de custos negativos no seu mapa.

Você usa o algoritmo Bellman-Ford para grafos ponderados (com pesos nas arestas) que podem conter pesos negativos.