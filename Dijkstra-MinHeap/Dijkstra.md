O Algoritmo de Dijkstra é como um GPS inteligente que encontra o caminho mais curto (e, portanto, mais barato ou mais rápido) de um ponto de partida para todos os outros pontos em um mapa.

Como funciona (bem simples):

Comece na sua origem: Você sabe que a distância da sua casa até ela mesma é zero. Para todo o resto, a distância é "infinita" por enquanto.
Sempre pegue o caminho mais fácil: Das cidades que você ainda não explorou, vá para aquela que tem a menor distância conhecida da sua origem.
Atualize os vizinhos: Ao chegar nessa cidade, verifique os vizinhos dela. Se você conseguir chegar a um vizinho por um caminho mais curto do que o que você tinha anotado antes, atualize essa distância.
Repita: Continue escolhendo a cidade mais próxima e atualizando seus vizinhos até que todos os lugares que você consegue alcançar tenham sido mapeados.
Importante: Ele só funciona se todos os "custos" das ruas forem positivos (sem atalhos que te fazem "ganhar" tempo ou dinheiro).

É o algoritmo ideal para achar a melhor rota em mapas, redes de internet e outros sistemas onde os custos são sempre para frente.

Você usa o algoritmo de Dijkstra para grafos ponderados (com pesos nas arestas), onde todos os pesos das arestas são não negativos (ou seja, positivos ou zero).