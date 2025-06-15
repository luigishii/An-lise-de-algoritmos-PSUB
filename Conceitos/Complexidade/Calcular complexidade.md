Como calcular a Complexidade de um Programa (O(n))

Calcular a complexidade é descobrir como o "tempo" que um programa leva para rodar cresce quando a quantidade de dados (n) aumenta. Usamos o Big O (O(n)) para isso.


public class ProcessaVetor{
    public static void main(String[] args) {
        int[] vetor = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            vetor[i] = Integer.parseInt(args[i]);
        }
        int soma=0;
        for (int i=0; i < vetor.length; i++) {
            soma += vetor[i];
        }
        int maximo = vetor[0];
        for (int i = 1; i<vetor.length; i++) {
            if (vetor[i] > maximo) {
                maximo = vetor[i];
            }
        }
        System.out.println(soma);
        System.out.println(maximo);
    }

}


Calculando no seu Programa (ProcessaVetor):

Vamos ver seu código linha por linha, pensando que n é o tamanho do vetor:

1º Bloco (for para preencher o vetor):

O for roda n vezes.
Dentro dele, tudo é rápido (O(1)).
Complexidade: O(n)
2º Bloco (for para somar):

O for roda n vezes.
Dentro dele, tudo é rápido (O(1)).
Complexidade: O(n)
3º Bloco (for para achar o maior):

O for roda n vezes.
Dentro dele, tudo é rápido (O(1)).
Complexidade: O(n)
4º Bloco (Imprimir):

É rápido (O(1)).
Complexidade: O(1)
Complexidade Total:

Seu programa faz as 4 partes uma depois da outra: O(n) + O(n) + O(n) + O(1).

A maior complexidade aqui é O(n).

Resposta: A complexidade desse programa é O(n).


