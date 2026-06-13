package algoritmos;

import estruturas.ListaEncadeada;
import java.util.Comparator;

/**
 * Classe utilitária com algoritmo de ordenação implementado manualmente.
 * Não utiliza Collections.sort nem Arrays.sort.
 */
public class Ordenador {

    /**
     * Ordena a lista encadeada utilizando o algoritmo Insertion Sort.
     *
     * Funcionamento:
     *   - Percorre a lista da esquerda para a direita (índice 1 até n-1).
     *   - Para cada elemento (chave), desloca para a direita todos os elementos
     *     anteriores que sejam maiores que a chave.
     *   - Insere a chave na posição correta.
     *   - A ordenação é feita in-place, trocando apenas os valores dos nós
     *     (sem mover os nós em si), o que mantém a estrutura encadeada intacta.
     *
     * @param <T>        tipo dos elementos da lista
     * @param lista      a lista a ser ordenada (modificada no lugar)
     * @param comparador define a ordem entre dois elementos
     */
    public static <T> void insertionSort(ListaEncadeada<T> lista, Comparator<T> comparador) {
        if (lista.estaVazia() || lista.tamanho() == 1) return;

        int n = lista.tamanho();
        for (int i = 1; i < n; i++) {
            T chave = lista.obter(i);
            int j = i - 1;
            // Desloca elementos maiores que a chave uma posição à frente
            while (j >= 0 && comparador.compare(lista.obter(j), chave) > 0) {
                lista.definir(j + 1, lista.obter(j));
                j--;
            }
            // Coloca a chave na posição correta
            lista.definir(j + 1, chave);
        }
    }
}

