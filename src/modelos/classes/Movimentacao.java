/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes;

public class Movimentacao {
    //Atributos
    private String idMovimentacao = "";
    private String idDeVeiculo = "";
    private String idTipoDeDespesa = "";
    private String descricao = "";
    private String data = "";
    private double valor = 0;
    
    //Metodos

    public Movimentacao() {}

    public Movimentacao(String idMovimentacao, String idDeVeiculo, String idTipoDeDespesa,
        String descricao, String data, double valor) {
        this.idMovimentacao = idMovimentacao;
        this.idDeVeiculo = idDeVeiculo;
        this.idTipoDeDespesa = idTipoDeDespesa;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    } 
    public String getIdMovimentacao() {
        return idMovimentacao;
    }
    public void setIdMovimentacao(String idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }
    public String getIdDeVeiculo() {
        return idDeVeiculo;
    }
    public void setIdDeVeiculo(String idDeVeiculo) {
        this.idDeVeiculo = idDeVeiculo;
    }
    public String getIdTipoDeDespesa() {
        return idTipoDeDespesa;
    }
    public void setIdTipoDeDespesa(String idTipoDeDespesa) {
        this.idTipoDeDespesa = idTipoDeDespesa;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}
