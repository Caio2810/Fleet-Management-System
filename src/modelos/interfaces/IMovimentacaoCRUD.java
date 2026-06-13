package modelos.interfaces;

import estruturas.ListaEncadeada;
import modelos.classes.Movimentacao;

public interface IMovimentacaoCRUD {
    void salvar(Movimentacao movimentacao) throws Exception;
    void remover(String movimentacaoID) throws Exception;
    void atualizar(Movimentacao movimentacao) throws Exception;
    ListaEncadeada<Movimentacao> listarMovimentacoes() throws Exception;
    Movimentacao buscarPorID(String idMovimentacao) throws Exception;
    ListaEncadeada<Movimentacao> listarMovimentacoesPorMes(int mes, int ano) throws Exception;
    double calcularSomatorioPorMes(ListaEncadeada<Movimentacao> movimentacoes) throws Exception;
    ListaEncadeada<Movimentacao> listarGastosCombustivelPorMes(int mes, int ano) throws Exception;
    ListaEncadeada<Movimentacao> listarMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception;
    ListaEncadeada<Movimentacao> listarGastosIPVAPorAno(int ano) throws Exception;
    double calcularTotalIPVAPorAno(int ano) throws Exception;
    double calcularTotalMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception;
    String contarTodasMovimentacoes() throws Exception;
    ListaEncadeada<Movimentacao> listarDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception;
    double calcularTotalDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception;
}