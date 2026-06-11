/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes;

public class TipoDeDespesa {
    //Atributos
    private String idTipoDeDespesa = "";
    private String descricao = "";
    
    //Metodos
    public TipoDeDespesa() {}

    public TipoDeDespesa(String idTipoDeDespesa, String descricao) {
        this.idTipoDeDespesa = idTipoDeDespesa;
        this.descricao = descricao;
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
}