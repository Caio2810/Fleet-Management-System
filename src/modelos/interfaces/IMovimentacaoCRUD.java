/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos.interfaces;

import java.util.ArrayList;
import modelos.classes.Movimentacao;

public interface IMovimentacaoCRUD {
    void salvar(Movimentacao movimentacao) throws Exception;
    void remover(String movimentacaoID) throws Exception;
    void atualizar(Movimentacao movimentacao) throws Exception;
    ArrayList<Movimentacao> listarMovimentacoes() throws Exception;
    Movimentacao buscarPorID(String idMovimentacao) throws Exception;
    ArrayList<Movimentacao> listarMovimentacoesPorMes(int mes, int ano) throws Exception;
    double calcularSomatorioPorMes(ArrayList<Movimentacao> movimentacoes) throws Exception;
    ArrayList<Movimentacao> listarGastosCombustivelPorMes(int mes, int ano) throws Exception;
    ArrayList<Movimentacao> listarMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception;
    ArrayList<Movimentacao> listarGastosIPVAPorAno(int ano) throws Exception;
    double calcularTotalIPVAPorAno(int ano) throws Exception;
    double calcularTotalMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception;
    String contarTodasMovimentacoes() throws Exception;
    ArrayList<Movimentacao> listarDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception;
    double calcularTotalDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception;



}
