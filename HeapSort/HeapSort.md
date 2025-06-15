O HeapSort é um jeito inteligente de colocar uma lista de números em ordem, usando uma estrutura de dados especial chamada Heap.

O que é um Heap?
Imagine uma árvore de cabeça para baixo onde:

O maior número (ou o menor, dependendo do tipo de Heap) está sempre no topo.
Cada "pai" é sempre maior (ou menor) que seus "filhos" (os números abaixo dele).
Como o HeapSort Funciona (bem simples):
O processo tem duas partes:

Organize tudo como uma "pilha de maiores" (Construir o Heap):

Pegue sua lista de números e reorganize-a para que o maior número fique no topo, e os outros sigam a regra de "pai maior que filho". Isso garante que o maior valor esteja sempre no começo.
Pegue o maior e coloque no lugar (Ordenar):

Agora que o maior número está no topo, tire-o de lá e coloque-o no final da sua lista ordenada.
Pegue o último número que sobrou na "pilha" e coloque-o no topo (onde estava o maior).
Reorganize a "pilha" novamente para que o novo maior número (entre os que sobraram) suba para o topo.
Repita esses passos: tire o maior, coloque no final, reorganize a pilha.
Você continua fazendo isso até que todos os números tenham sido tirados da "pilha" e colocados em ordem no final da lista.

Em resumo: O HeapSort transforma a lista em uma "pilha" onde o maior elemento está no topo, e depois remove esse maior elemento repetidamente, colocando-o no lugar certo na lista final, até que tudo esteja em ordem.