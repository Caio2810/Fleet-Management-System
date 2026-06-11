/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.classes.classes_visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author caioaraujocunha
 */
public class Tabela extends JTable {

    public Tabela() {
        setShowHorizontalLines(true);
        setRowHeight(40);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getTableHeader().setReorderingAllowed(false); // aq permite que o usuario nao mova as colunas da tabela
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i,
                    int i1) {
                TabelaHeader header = new TabelaHeader(o + "");
                // if (i1 == 4) {
                // header.setHorizontalAlignment(JLabel.CENTER);
                // }
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1,
                    int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                // if (i1 == 4) {
                // ((DefaultTableCellRenderer) com).setHorizontalAlignment(JLabel.CENTER);
                // } else {
                ((DefaultTableCellRenderer) com).setHorizontalAlignment(JLabel.LEFT);
                // }

                com.setBackground(Color.WHITE);
                setBorder(noFocusBorder);
                if (selected) {
                    com.setForeground(new Color(255,139,0));
                    com.setFont(new Font("Menlo", Font.BOLD, 13));
                } else {
                    com.setForeground(new Color(102, 102, 102));
                }
                return com;

            }
        });
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void addRow(Object[] row) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.addRow(row);
    }

}
