package controle;

import estruturas.ListaEncadeada;
import modelos.classes.TipoDeDespesa;
import persistencia.TipoDeDespesasDAO;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControladoraDespesas {
    private TipoDeDespesasDAO dao;

    public ControladoraDespesas() {
        dao = new TipoDeDespesasDAO();
    }

    private String gerarIdAleatorio() throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        try {
            return uuid.substring(0, 7).toUpperCase();
        } catch (Exception e) {
            String msg = "ControladoraDespesas - Metodo Gerar Id Aleatorio - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void incluirDespesa(String descricao, JTable tabela) throws Exception {
        try {
            String despesaID = gerarIdAleatorio();
            TipoDeDespesa despesa = new TipoDeDespesa(despesaID, descricao);
            dao.salvar(despesa);
            carregarTabela(tabela);
        } catch (Exception erro) {
            String msg = "ControladoraDespesas - Metodo Incluir Despesa - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    public void carregarTabela(JTable tabela) throws Exception {
        ListaEncadeada<TipoDeDespesa> lista = dao.listarDespesas();

        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setRowCount(0);

        for (TipoDeDespesa d : lista) {
            modeloTabela.addRow(new Object[] {
                    d.getIdTipoDeDespesa(),
                    d.getDescricao()
            });
        }
    }

    public void atualizarDespesa(TipoDeDespesa despesa, JTable tabela) throws Exception {
        try {
            dao.atualizar(despesa);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraDespesas - Metodo Atualizar Despesa - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void removerDespesa(String idDespesa, JTable tabela) throws Exception {
        try {
            dao.remover(idDespesa);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraDespesas - Metodo Remover Despesa - " + e.getMessage();
            throw new Exception(msg);
        } 
    }

    public TipoDeDespesa buscarDespesaPorId(String idDespesa) throws Exception {
        return dao.buscarPorID(idDespesa);
    }

    public ListaEncadeada<TipoDeDespesa> listarDespesasPorVeiculo(String idVeiculo) throws Exception {
        return dao.listarDespesasPorVeiculo(idVeiculo);
    }

    public String calcularTotalDespesas() throws Exception {
        try {
            ListaEncadeada<TipoDeDespesa> todasDespesas = dao.listarDespesas();
            int contador = 0;

            for (TipoDeDespesa despesa : todasDespesas) {
                contador++;
            }

            return Integer.toString(contador);
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total Despesas - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}