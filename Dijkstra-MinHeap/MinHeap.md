Imagine que você está organizando uma competição e precisa saber, a todo momento, quem é o participante com o menor tempo. Um Min-Heap é a ferramenta perfeita para isso!

O que é?
Pense no Min-Heap como uma fila super organizada onde:

O item no topo é sempre o menor de todos.
Cada "pai" é sempre menor ou igual aos seus "filhos".
Como funciona (muito simples):
Adicionar: Colocou um item novo? Ele vai para o final e "sobe" na fila, trocando de lugar com seu pai, até encontrar a posição certa (onde ele é maior que seu pai, mas menor que seus filhos).
Pegar o menor: Quer o menor item? Ele está sempre no topo. Você o pega, e o último item da fila vai para o topo, depois "desce", trocando com o menor de seus filhos, até encontrar seu lugar correto.
Atualizar: Se um item que já está na fila muda de valor e fica menor, ele "sobe" da mesma forma que um item novo, garantindo que a ordem esteja sempre correta.
Por que é útil?
Para algoritmos como o Dijkstra, que precisam encontrar o caminho mais curto, o Min-Heap permite encontrar e gerenciar rapidamente a próxima "cidade" mais próxima a ser visitada. Ele torna a busca por caminhos mínimos muito mais eficiente!