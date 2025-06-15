Para saber se um algoritmo é Θ(n) (lê-se "Theta de n"), pense assim:

"O tempo que ele leva para rodar é SEMPRE (no melhor, no médio e no pior caso) diretamente proporcional ao tamanho do problema (n)?"

SIM: É Θ(n).

Exemplos:
Um programa que sempre passa por uma lista inteira para fazer algo com cada item (tipo, somar todos os números, imprimir todos os nomes).
Não importa o que tem na lista, ele sempre vai visitar todos os n itens uma vez.
NÃO: Se o tempo puder variar muito dependendo dos dados (tipo, ser super rápido às vezes e super lento em outras, mas sempre dentro de um certo limite máximo), aí não é Θ(n). Ele pode ser apenas O(n) (quer dizer "no máximo n").

Em resumo: Θ(n) é mais "exato". Significa que o algoritmo é sempre rápido como n, nunca muito mais rápido ou muito mais lento que n.


se ele passa por cada elemento da lista ou do array É θ(n).


