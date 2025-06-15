Regra do dedão: Se você vê um único for ou while que passa por toda a lista de dados, e não tem outro for dentro dele dependendo do tamanho da lista, é quase certo que é O(n).


Regra do Dedão para Vários fors Separados:

Se você vê vários fors (ou whiles) um depois do outro (não aninhados), onde cada um deles passa por toda a lista de dados:

Pense: "Qual é o for mais 'pesado'?"

Se todos os fors passam pela lista completa (ou uma parte que cresce com a lista), sem estarem um dentro do outro:

A complexidade total ainda será O(n).

O(n) - loop normal
for (int i = 0; i < lista.length; i++) {
    // faz outra coisa O(1)
}

O(n^2) - esse aqui tem um loop aninhado
for (int i = 0; i < lista.length; i++) {
    for (int j = 0; j < lista.length; j++) {
        // faz algo O(1)
    }
}