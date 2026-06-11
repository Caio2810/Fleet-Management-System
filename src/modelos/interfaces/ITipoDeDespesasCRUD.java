/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos.interfaces;
import java.util.ArrayList;
import modelos.classes.TipoDeDespesa;

public interface ITipoDeDespesasCRUD {
    void salvar(TipoDeDespesa despesa) throws Exception;
    ArrayList<TipoDeDespesa> listarDespesas() throws Exception;
    TipoDeDespesa buscarPorID(String idDespesa) throws Exception;
    void atualizar(TipoDeDespesa despesa) throws Exception;
    void remover(String idDespesa) throws Exception;
    ArrayList<TipoDeDespesa> listarDespesasPorVeiculo(String idDeVeiculo) throws Exception;
}