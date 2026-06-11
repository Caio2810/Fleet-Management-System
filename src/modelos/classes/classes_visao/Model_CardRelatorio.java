/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes.classes_visao;

import javax.swing.Icon;

/**
 *
 * @author caioaraujocunha
 */
public class Model_CardRelatorio {
    // private Icon icon;
    private String title;
    private String descricao;
    
    public Model_CardRelatorio() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Model_CardRelatorio(String title, String descricao  ) {
        // this.icon = icon;
        this.title = title;
        this.descricao = descricao;
    }
}
