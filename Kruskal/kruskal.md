O Algoritmo de Kruskal é uma receita para encontrar a Árvore Geradora Mínima (AGM) em um mapa com "ruas" que têm "custos".

Imagine que você tem várias cidades e quer conectá-las todas com estradas, mas quer gastar o mínimo possível.

Como funciona (bem simples):

1 - Liste todas as ruas: Anote todas as estradas que existem no mapa, junto com seus custos.

2 - Organize por custo: Arrume essa lista das ruas mais baratas para as mais caras.

3 - Construa a rede:

    - Comece pegando a rua mais barata da sua lista.
    - Adicione essa rua à sua rede, desde que ela não forme um círculo (um atalho que te leve de volta ao mesmo ponto).
    - Continue pegando a próxima rua mais barata e adicionando, seguindo a mesma regra: só adicione se não formar um círculo.
    - Pare: Continue fazendo isso até que todas as cidades estejam conectadas.
    - O resultado é a rede de estradas que conecta todas as cidades com o menor custo total possível.

Para que serve?

Para planejar a forma mais barata de conectar pontos, seja em redes de computadores, tubulações ou até rotas de entrega.



grafos não direcionados e ponderados.