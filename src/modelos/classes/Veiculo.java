/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes;

public class Veiculo {
    //Atributos
    private String idDeVeiculo = "";
    private String placa = "";
    private String marca = "";
    private String modelo = " ";
    private int anoDeFabricacao = 0;
    private EstadoDoVeiculo estadoDoVeiculo;
    
    //Metodos
    public Veiculo() {
    }
    public Veiculo(String idDeVeiculo, String placa, String marca, String modelo, int anoDeFabricacao, EstadoDoVeiculo estadoDoVeiculo){
        
        this.idDeVeiculo = idDeVeiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anoDeFabricacao = anoDeFabricacao;
        this.estadoDoVeiculo = estadoDoVeiculo;
    }

    public String getIdDeVeiculo() {
        return idDeVeiculo;
    }

    public void setIdDeVeiculo(String idDeVeiculo) {
        this.idDeVeiculo = idDeVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void setAnoDeFabricacao(int anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public EstadoDoVeiculo isEstadoDoVeiculo() {
        return estadoDoVeiculo;
    }

    public void setEstadoDoVeiculo(EstadoDoVeiculo estadoDoVeiculo) {
        this.estadoDoVeiculo = estadoDoVeiculo;
    }
}
