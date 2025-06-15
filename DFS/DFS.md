A Busca em Profundidade (DFS) é como explorar um labirinto: você escolhe um caminho e vai o mais fundo possível nele, até o fim. Se for um beco sem saída, você volta um pouco e tenta outro caminho.

Como funciona (muito simples):

Vá fundo: Comece num ponto e explore um de seus caminhos até o final.
Volte e tente outro: Se não há mais para onde ir por aquele caminho, você "retrocede" (volta) até um ponto onde ainda há opções não exploradas.
Continue: Repita o processo, indo fundo em um novo caminho.
Ele usa uma pilha (ou recursão) para fazer isso, sempre focando no último ponto que foi "entrado".

Pra que serve?

É ótimo para:

Descobrir se todo o mapa está conectado.
Encontrar qualquer caminho entre dois pontos.
Detectar se há "círculos" (ciclos) no mapa.
Resolver labirintos ou jogos que exigem explorar todas as possibilidades de um ramo.


O DFS é um algoritmo de travessia (exploração) de grafos que pode ser usado em qualquer tipo de grafo:

Grafos Não Direcionados: Sim, funciona perfeitamente.
Grafos Direcionados (Digrafos): Sim, funciona perfeitamente.
Grafos Ponderados: Sim, funciona (mas não usa os pesos para determinar o caminho mais curto, apenas para explorar).
Grafos Não Ponderados: Sim, funciona.