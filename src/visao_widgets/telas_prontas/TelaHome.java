package visao_widgets.telas_prontas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Locale;
import javax.swing.*;
import java.awt.Frame;

import controle.ControladoraDespesas;
import controle.ControladoraMovimentacao;
import controle.ControladoraVeiculos;
import estruturas.ListaEncadeada;
import modelos.classes.Veiculo;
import modelos.classes.classes_visao.Model_Card;
import themes.Theme;

public class TelaHome extends javax.swing.JPanel {

    ControladoraVeiculos controladoraVeiculos = new ControladoraVeiculos();
    ControladoraDespesas controladoraDespesas = new ControladoraDespesas();
    ControladoraMovimentacao controladoraMovimentacao = new ControladoraMovimentacao();

    public TelaHome() {
        initComponents();
        setOpaque(false);
        atualizarDashboard();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new visao_widgets.Card();
        card2 = new visao_widgets.Card();
        formPageBorder1 = new visao_widgets.FormPageBorder();
        jLabel1 = new javax.swing.JLabel();

        // =========================================================================
        // CARREGAMENTO DOS ÍCONES DA PASTA src/images/
        // Substitua os nomes dos arquivos (.png, .jpg, etc.) pelos arquivos reais que colocar lá
        // =========================================================================
        Icon ic1 = new ImageIcon(getClass().getResource("/imagens/ic_veiculo.png"));
        Icon ic2 = new ImageIcon(getClass().getResource("/imagens/ic_2.png"));
        Icon ic3 = new ImageIcon(getClass().getResource("/imagens/ic_gas.png"));
        Icon ic4 = new ImageIcon(getClass().getResource("/imagens/ic_ipva.png"));
        Icon ic5 = new ImageIcon(getClass().getResource("/imagens/ic_inactives.png"));
        Icon ic6 = new ImageIcon(getClass().getResource("/imagens/ic_bills.png"));
        // =========================================================================

        relatorio1 = new visao_widgets.ReportItem("Despesas por veículo", ic1);
        relatorio2 = new visao_widgets.ReportItem("Soma geral das despesas de frota em um mês", ic2);
        relatorio3 = new visao_widgets.ReportItem("Gastos com combustível em um mês", ic3);
        relatorio4 = new visao_widgets.ReportItem("Soma do IPVA de um ano da frota", ic4);
        relatorio5 = new visao_widgets.ReportItem("Listar veículos inativos", ic5);
        relatorio6 = new visao_widgets.ReportItem("Relatório das multas pagas por veículo em um ano", ic6);

        setBackground(Theme.BACKGROUND);
        card1.setForeground(Theme.CARDCOLOR);
        card2.setForeground(Theme.CARDCOLOR);
        formPageBorder1.setBackground(Theme.CARDCOLOR);

        jLabel1.setFont(new java.awt.Font("DIN Alternate", 0, 24)); // NOI18N
        jLabel1.setText("RELATÓRIOS");

        relatorio3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio3ActionPerformed(null);
            }
        });

        relatorio4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio4ActionPerformed(null);
            }
        });

        relatorio2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio2ActionPerformed(null);
            }
        });

        relatorio1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio1ActionPerformed(null);
            }
        });

        relatorio5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio5ActionPerformed(null);
            }
        });

        relatorio6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatorio6ActionPerformed(null);
            }
        });

        javax.swing.GroupLayout formPageBorder1Layout = new javax.swing.GroupLayout(formPageBorder1);
        formPageBorder1.setLayout(formPageBorder1Layout);
        formPageBorder1Layout.setHorizontalGroup(
            formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formPageBorder1Layout.createSequentialGroup()
                .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(formPageBorder1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(formPageBorder1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(relatorio1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(relatorio2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                            .addComponent(relatorio3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(relatorio4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(relatorio5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(relatorio6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );
        formPageBorder1Layout.setVerticalGroup(
            formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPageBorder1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(relatorio1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(relatorio4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(relatorio2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(relatorio5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(formPageBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(relatorio3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(relatorio6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(formPageBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(formPageBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void relatorio3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio3ActionPerformed
        try {
            visao_widgets.paineis_de_telas.Relatorio3 formPanel = new visao_widgets.paineis_de_telas.Relatorio3();
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Relatório: Somatório Gasto com Gasolina da Frota Mensal", true);
            dialog.setContentPane(formPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception erro) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir o relatório: " + erro.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_relatorio3ActionPerformed

    private void relatorio4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio4ActionPerformed
        try {
            visao_widgets.paineis_de_telas.Relatorio4 formPanel = new visao_widgets.paineis_de_telas.Relatorio4();
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Relatório: Somatório do IPVA de um determinado ano de toda a frota;", true);
            dialog.setContentPane(formPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception erro) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir o relatório: " + erro.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_relatorio4ActionPerformed

    private void relatorio5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio5ActionPerformed
        try {
            // Alterado para receber ListaEncadeada do controle refatorado
            ListaEncadeada<Veiculo> listarVeiculosInativos = controladoraVeiculos.listarVeiculosInativos();
            controladoraVeiculos.gerarRelatorio5PDF(listarVeiculosInativos);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao gerar relatório 5: " + e.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_relatorio5ActionPerformed

    private void relatorio6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio6ActionPerformed
        try {
            visao_widgets.paineis_de_telas.Relatorio6 formPanel = new visao_widgets.paineis_de_telas.Relatorio6();
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Relatório: Multas pagas por veículo em um determinado ano.", true);
            dialog.setContentPane(formPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception erro) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir o relatório: " + erro.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_relatorio6ActionPerformed

    private void relatorio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio1ActionPerformed
        try {
            visao_widgets.paineis_de_telas.Relatorio1 formPanel = new visao_widgets.paineis_de_telas.Relatorio1();
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Relatório: Despesas realizadas em um determinado veículo;s", true);
            dialog.setContentPane(formPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception erro) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir o relatório: " + erro.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_relatorio1ActionPerformed

    private void relatorio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatorio2ActionPerformed
        try {
            visao_widgets.paineis_de_telas.Relatorio2 formPanel = new visao_widgets.paineis_de_telas.Relatorio2();
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Relatório: Somatório Mensal de Despesas", true);
            dialog.setContentPane(formPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (Exception erro) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao abrir o relatório: " + erro.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        super.paintComponent(g);
    }

    private void carregadorDadosVeiculoDashboard() {
        try {
            String contadorVeiculosAtivos = controladoraVeiculos.contadorVeiculosAtivos();
            card1.setData(new Model_Card("TOTAL DE VEÍCULOS", contadorVeiculosAtivos, "desde o início"));
        } catch (Exception erro) {
            System.out.println("Erro ao carregar dados de veículos para o dashboard: " + erro.getMessage());
            card1.setData(new Model_Card("Veículos", "0", "Sem dados obtidos"));
        }
    }

    private void carregadorDadosMovimentacaoDashboard() {
        try {
            double contadorDespesas = controladoraMovimentacao.buscarTotalGastoEmDespesas();
            String valorTemporario = String.format(Locale.US, "%.2f", contadorDespesas);
            String valorFormatado = "R$ " + valorTemporario.replace('.', ',');
            card2.setData(new Model_Card("TOTAL DE DESPESAS", valorFormatado, "desde o início"));
        } catch (Exception erro) {
            System.out.println("Erro ao carregar dados de veículos para o dashboard: " + erro.getMessage());
            card2.setData(new Model_Card("Despesas", "0", "Sem dados obtidos"));
        }
    }

    public void atualizarDashboard() {
        carregadorDadosVeiculoDashboard();
        carregadorDadosMovimentacaoDashboard();
    }

    private visao_widgets.Card card1;
    private visao_widgets.Card card2;
    private visao_widgets.FormPageBorder formPageBorder1;
    private javax.swing.JLabel jLabel1;
    private visao_widgets.ReportItem relatorio1;
    private visao_widgets.ReportItem relatorio2;
    private visao_widgets.ReportItem relatorio3;
    private visao_widgets.ReportItem relatorio4;
    private visao_widgets.ReportItem relatorio5;
    private visao_widgets.ReportItem relatorio6;
}