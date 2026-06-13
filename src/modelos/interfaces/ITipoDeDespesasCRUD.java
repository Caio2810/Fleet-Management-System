/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos.interfaces;
import estruturas.ListaEncadeada;
import modelos.classes.TipoDeDespesa;

public interface ITipoDeDespesasCRUD {
    void salvar(TipoDeDespesa despesa) throws Exception;
    ListaEncadeada<TipoDeDespesa> listarDespesas() throws Exception;
    TipoDeDespesa buscarPorID(String idDespesa) throws Exception;
    void atualizar(TipoDeDespesa despesa) throws Exception;
    void remover(String idDespesa) throws Exception;
    ListaEncadeada<TipoDeDespesa> listarDespesasPorVeiculo(String idDeVeiculo) throws Exception;
}