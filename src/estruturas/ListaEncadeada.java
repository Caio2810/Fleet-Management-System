package estruturas;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista simplesmente encadeada genérica.
 *
 * Implementa Iterable<T> para que qualquer laço for-each já existente
 * no projeto continue funcionando sem alterações:
 *   for (Movimentacao m : lista) { ... }
 *
 * Métodos principais:
 *   adicionar(T)  – insere ao final
 *   remover(T)    – remove pelo equals()
 *   obter(int)    – acesso por índice (base 0)
 *   definir(int,T)– substitui o valor em determinado índice
 *   tamanho()     – número de elementos
 *   estaVazia()   – true se não houver elementos
 *   getCabeca()   – retorna o primeiro No<T> (usado na recursão)
 *   iterator()    – implementação do Iterable
 */
public class ListaEncadeada<T> implements Iterable<T> {

    private No<T> cabeca;
    private int tamanho;

    public ListaEncadeada() {
        cabeca = null;
        tamanho = 0;
    }

    /** Adiciona um elemento ao final da lista. */
    public void adicionar(T valor) {
        No<T> novoNo = new No<>(valor);
        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            No<T> atual = cabeca;
            while (atual.getProximo() != null) {
                atual = atual.getProximo();
            }
            atual.setProximo(novoNo);
        }
        tamanho++;
    }

    /**
     * Remove o primeiro elemento igual a valor (comparação por equals).
     * Não faz nada se o elemento não for encontrado.
     */
    public void remover(T valor) {
        if (cabeca == null) return;
        if (cabeca.getValor().equals(valor)) {
            cabeca = cabeca.getProximo();
            tamanho--;
            return;
        }
        No<T> atual = cabeca;
        while (atual.getProximo() != null) {
            if (atual.getProximo().getValor().equals(valor)) {
                atual.setProximo(atual.getProximo().getProximo());
                tamanho--;
                return;
            }
            atual = atual.getProximo();
        }
    }

    /**
     * Retorna o elemento no índice informado (base 0).
     * Lança IndexOutOfBoundsException se o índice for inválido.
     */
    public T obter(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora dos limites: " + indice);
        }
        No<T> atual = cabeca;
        for (int i = 0; i < indice; i++) {
            atual = atual.getProximo();
        }
        return atual.getValor();
    }

    /**
     * Substitui o valor no índice informado (base 0).
     * Usado pelo Insertion Sort para trocar elementos sem mover nós.
     */
    public void definir(int indice, T valor) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora dos limites: " + indice);
        }
        No<T> atual = cabeca;
        for (int i = 0; i < indice; i++) {
            atual = atual.getProximo();
        }
        atual.setValor(valor);
    }

    /** Retorna o número de elementos armazenados. */
    public int tamanho() {
        return tamanho;
    }

    /** Retorna true se a lista não contiver nenhum elemento. */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Retorna o primeiro nó da lista.
     * Necessário para as funções recursivas que percorrem os nós diretamente.
     */
    public No<T> getCabeca() {
        return cabeca;
    }

    /** Permite o uso em laços for-each. */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private No<T> atual = cabeca;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T val = atual.getValor();
                atual = atual.getProximo();
                return val;
            }
        };
    }
}

