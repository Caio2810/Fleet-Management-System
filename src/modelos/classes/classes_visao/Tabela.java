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
        getTableHeader().setReorderingAllowed(false); // impede o usuário de mover as colunas
        
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i,
                    int i1) {
                TabelaHeader header = new TabelaHeader(o + "");
                return header;
            }
        });
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1,
                    int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                
                ((DefaultTableCellRenderer) com).setHorizontalAlignment(JLabel.LEFT);
                setBorder(noFocusBorder);
                
                if (selected) {
                    // Mantém o seu destaque customizado: Texto Laranja e em Negrito
                    com.setForeground(new Color(255, 139, 0));
                    com.setFont(new Font("Menlo", Font.BOLD, 13));
                    // REMOVIDO: O background de seleção agora será o padrão nativo do FlatLaf Dark!
                } else {
                    // CORRIGIDO: Cores dinâmicas! Puxam automaticamente o fundo e o texto do FlatLaf ativo
                    com.setBackground(jtable.getBackground());
                    com.setForeground(jtable.getForeground());
                    com.setFont(jtable.getFont());
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