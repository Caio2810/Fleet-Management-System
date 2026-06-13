/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes.classes_visao;

/**
 *
 * @author caioaraujocunha
 */
public class Model_Card {
    // private Icon icon;
    private String title;
    private String values;
    private String descricao;
    
    public Model_Card() {
    }

    // public Icon getIcon() {
    //     return icon;
    // }

    // public void setIcon(Icon icon) {
    //     this.icon = icon;
    // }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Model_Card(String title, String values, String descricao  ) {
        // this.icon = icon;
        this.title = title;
        this.values = values;
        this.descricao = descricao;
    }
}
