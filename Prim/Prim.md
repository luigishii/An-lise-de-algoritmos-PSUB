O Algoritmo de Prim é uma receita para construir a Árvore Geradora Mínima (AGM) em um mapa com ruas que têm custos. A AGM é a forma mais barata de conectar todas as cidades sem criar ciclos (atalhos desnecessários).

Como funciona (bem simples):

Comece em qualquer cidade. Adicione-a à sua rede (que começa vazia).
Sempre pegue a rua mais barata: Olhe para todas as ruas que saem da sua rede e se conectam a uma cidade nova (ainda não na rede). Escolha a rua mais barata entre elas.
Adicione a rua e a cidade: Inclua essa rua e a nova cidade na sua rede.
Repita: Volte ao passo 2 e continue pegando a rua mais barata que conecta a uma nova cidade, até que todas as cidades estejam na sua rede.
O resultado é a rede de ruas com o custo total mais baixo possível que conecta todas as cidades.

Para que serve?
É como planejar a instalação de cabos de rede ou tubulações de água para conectar todos os pontos com o menor gasto possível.