O BFS (Busca em Largura) é como um explorador de mapa que sempre visita as cidades em "ondas", das mais próximas para as mais distantes.

Como funciona (muito simples):

Comece na sua cidade: Visite ela primeiro.
Explore os vizinhos diretos: Depois, visite todos os que estão a apenas "uma rua" de distância.
Explore os vizinhos dos vizinhos: Em seguida, visite todos os que estão a "duas ruas" de distância, e assim por diante.
Ele faz isso usando uma fila: as cidades que precisam ser visitadas são colocadas no final da fila, e as mais antigas (as mais "próximas") são tiradas do início para serem exploradas.

Pra que serve?

É perfeito para achar o caminho mais curto (em número de passos/ruas) em mapas sem custos variáveis, como:

Encontrar o menor número de cliques para chegar a alguém no LinkedIn.
Achar a rota mais rápida em um labirinto sem obstáculos especiais.
Rastrear como a informação se espalha em uma rede.

O algoritmo BFS (Busca em Largura) é usado principalmente para grafos não ponderados.