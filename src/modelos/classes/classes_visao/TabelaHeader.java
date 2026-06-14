/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes.classes_visao;

import themes.Theme;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author caioaraujocunha
 */
public class TabelaHeader extends JLabel {

    public TabelaHeader(String text) {
        super(text);
        setOpaque(true);
        setBackground(Theme.CARDCOLOR);
        setFont(new Font("sansserif", 1, 12));
        setForeground(Color.WHITE);
        setBorder(new EmptyBorder(10, 5, 10, 5));
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(Theme.CARDCOLOR);
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
}