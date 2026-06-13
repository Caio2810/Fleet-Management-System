package persistencia;

import estruturas.ListaEncadeada;
import modelos.classes.Movimentacao;
import modelos.classes.TipoDeDespesa;
import modelos.interfaces.ITipoDeDespesasCRUD;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TipoDeDespesasDAO implements ITipoDeDespesasCRUD {
    private String nomeDoArquivoNoDisco = null;

    public TipoDeDespesasDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/Despesas.txt";
    }

    @Override
    public void salvar(TipoDeDespesa tipoDeDespesa) throws Exception {
        try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String str = tipoDeDespesa.getIdTipoDeDespesa() + ";" + tipoDeDespesa.getDescricao() + "\n";
            bw.write(str);
            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Salvar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<TipoDeDespesa> listarDespesas() throws Exception {
        try {
            ListaEncadeada<TipoDeDespesa> listaDeTiposDeDespesas = new ListaEncadeada<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idTipoDeDespesaAux = vetorStr[0];
                String descricao = vetorStr[1];
                TipoDeDespesa objTipoDeDespesa = new TipoDeDespesa(idTipoDeDespesaAux, descricao);
                listaDeTiposDeDespesas.adicionar(objTipoDeDespesa);
            }
            br.close();
            return listaDeTiposDeDespesas;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Lista - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public TipoDeDespesa buscarPorID(String idTipoDeDespesa) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String idTipoDeDespesaAux = vetorStr[0];
                if (idTipoDeDespesaAux.equals(idTipoDeDespesa)) {
                    String descricao = vetorStr[1];
                    TipoDeDespesa objTipoDeDespesa = new TipoDeDespesa(idTipoDeDespesaAux, descricao);
                    br.close();
                    return objTipoDeDespesa;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Buscar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void atualizar(TipoDeDespesa tipoDeDespesa) throws Exception {
        try {
            ListaEncadeada<TipoDeDespesa> listagem = this.listarDespesas();
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);
            for (TipoDeDespesa obj : listagem) {
                if (obj.getIdTipoDeDespesa().equals(tipoDeDespesa.getIdTipoDeDespesa())) {
                    String str = tipoDeDespesa.getIdTipoDeDespesa() + ";" + tipoDeDespesa.getDescricao() + "\n";
                    bw.write(str);
                } else {
                    String str = obj.getIdTipoDeDespesa() + ";" + obj.getDescricao() + "\n";
                    bw.write(str);
                }
            }
            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Atualizar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void remover(String idTipoDeDespesa) throws Exception {
        try {
            ListaEncadeada<TipoDeDespesa> listagem = this.listarDespesas();
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);
            for (TipoDeDespesa obj : listagem) {
                if (!obj.getIdTipoDeDespesa().equals(idTipoDeDespesa)) {
                    String str = obj.getIdTipoDeDespesa() + ";" + obj.getDescricao() + "\n";
                    bw.write(str);
                }
            }
            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Remover - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<TipoDeDespesa> listarDespesasPorVeiculo(String idDeVeiculo) throws Exception {
        try {
            MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
            ListaEncadeada<Movimentacao> movimentacoesDoVeiculo = movimentacaoDAO.listarMovimentacoes();
            ListaEncadeada<TipoDeDespesa> despesasDoVeiculo = new ListaEncadeada<>();

            for (Movimentacao mov : movimentacoesDoVeiculo) {
                if (mov.getIdDeVeiculo().equals(idDeVeiculo)) {
                    TipoDeDespesa despesa = buscarPorID(mov.getIdTipoDeDespesa());
                    if (despesa != null) {
                        despesasDoVeiculo.adicionar(despesa);
                    }
                }
            }
            return despesasDoVeiculo;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Despesas Por Veiculo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}