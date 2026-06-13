package persistencia;

import estruturas.ListaEncadeada;
import estruturas.No;
import modelos.classes.Movimentacao;
import modelos.interfaces.IMovimentacaoCRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MovimentacaoDAO implements IMovimentacaoCRUD {
    private String nomeDoArquivoNoDisco = null;

    public MovimentacaoDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/Movimentação.txt";
    }

    /**
     * FUNÇÃO RECURSIVA EXIGIDA PELO REQUISITO ACADÊMICO
     * Percorre os nós da ListaEncadeada somando o atributo valor de cada Movimentacao.
     */
    private double somarValoresRecursivo(No<Movimentacao> noAtual) {
        if (noAtual == null) return 0.0;
        return noAtual.getValor().getValor() + somarValoresRecursivo(noAtual.getProximo());
    }

    @Override
    public void salvar(Movimentacao movimentacao) throws Exception {
        try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String str = movimentacao.getIdMovimentacao() + ";"
                    + movimentacao.getIdDeVeiculo() + ";"
                    + movimentacao.getIdTipoDeDespesa() + ";"
                    + movimentacao.getDescricao() + ";"
                    + movimentacao.getData() + ";"
                    + movimentacao.getValor() + "\n";

            bw.write(str);
            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Cadastrar Movimentacao - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarMovimentacoes() throws Exception {
        try {
            ListaEncadeada<Movimentacao> lista = new ListaEncadeada<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vetorStr = linha.split(";");
                Movimentacao obj = new Movimentacao(
                        vetorStr[0],
                        vetorStr[1],
                        vetorStr[2],
                        vetorStr[3],
                        vetorStr[4],
                        Double.parseDouble(vetorStr[5])
                );
                lista.adicionar(obj);
            }
            br.close();
            return lista;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Lista Movimentacoes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public Movimentacao buscarPorID(String idMovimentacao) throws Exception {
        try {
            for (Movimentacao obj : listarMovimentacoes()) {
                if (obj.getIdMovimentacao().equalsIgnoreCase(idMovimentacao)) {
                    return obj;
                }
            }
            return null;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Buscar Por Id Movimentacao - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void atualizar(Movimentacao movimentacao) throws Exception {
        try {
            ListaEncadeada<Movimentacao> lista = listarMovimentacoes();

            boolean encontrou = false;
            for (int i = 0; i < lista.tamanho(); i++) {
                if (lista.obter(i).getIdMovimentacao().equals(movimentacao.getIdMovimentacao())) {
                    lista.definir(i, movimentacao);
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                throw new Exception("Movimentação não encontrada para atualizar!");
            }

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Movimentacao obj : lista) {
                String str = obj.getIdMovimentacao() + ";"
                        + obj.getIdDeVeiculo() + ";"
                        + obj.getIdTipoDeDespesa() + ";"
                        + obj.getDescricao() + ";"
                        + obj.getData() + ";"
                        + obj.getValor() + "\n";
                bw.write(str);
            }

            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Atualizar Movimentacao - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void remover(String movimentacaoID) throws Exception {
        try {
            ListaEncadeada<Movimentacao> lista = this.listarMovimentacoes();
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Movimentacao obj : lista) {
                if (!obj.getIdMovimentacao().equals(movimentacaoID)) {
                    String str = obj.getIdMovimentacao() + ";"
                            + obj.getIdDeVeiculo() + ";"
                            + obj.getIdTipoDeDespesa() + ";"
                            + obj.getDescricao() + ";"
                            + obj.getData() + ";"
                            + obj.getValor() + "\n";
                    bw.write(str);
                }
            }

            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Deletar Movimentacao - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarMovimentacoesPorMes(int mes, int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ListaEncadeada<Movimentacao> movimentacoesDoMes = new ListaEncadeada<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int mesMovimentacao = Integer.parseInt(partesData[1]);
                int anoMovimentacao = Integer.parseInt(partesData[2]);

                if (mesMovimentacao == mes && anoMovimentacao == ano) {
                    movimentacoesDoMes.adicionar(mov);
                }
            }

            return movimentacoesDoMes;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Movimentacoes Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarGastosCombustivelPorMes(int mes, int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ListaEncadeada<Movimentacao> gastosCombustivel = new ListaEncadeada<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                String descricao = mov.getDescricao().toLowerCase();
                String[] palavrasCombustivel = {
                        "gasolina",
                        "combustivel",
                        "combustível",
                        "abasteceu",
                        "abastecimento",
                };

                boolean ehCombustivel = false;
                for (String palavra : palavrasCombustivel) {
                    if (descricao.contains(palavra.toLowerCase())) {
                        ehCombustivel = true;
                        break;
                    }
                }

                int mesMovimentacao = Integer.parseInt(partesData[1]);
                int anoMovimentacao = Integer.parseInt(partesData[2]);

                if (mesMovimentacao == mes && anoMovimentacao == ano && ehCombustivel) {
                    gastosCombustivel.adicionar(mov);
                }
            }

            return gastosCombustivel;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Gastos Combustivel Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularSomatorioPorMes(ListaEncadeada<Movimentacao> movimentacoesDoMes) throws Exception {
        try {
            // Refatorado para usar a função recursiva (Relatório 2)
            return somarValoresRecursivo(movimentacoesDoMes.getCabeca());
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Somatorio Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarGastosIPVAPorAno(int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ListaEncadeada<Movimentacao> gastosIPVA = new ListaEncadeada<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int anoMovimentacao = Integer.parseInt(partesData[2]);

                if (anoMovimentacao == ano && mov.getDescricao().toLowerCase().contains("ipva")) {
                    gastosIPVA.adicionar(mov);
                }
            }

            return gastosIPVA;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Gastos IPVA Por Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularTotalIPVAPorAno(int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> gastosIPVA = listarGastosIPVAPorAno(ano);
            // Refatorado para usar a função recursiva (Relatório 4)
            return somarValoresRecursivo(gastosIPVA.getCabeca());
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total IPVA Por Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ListaEncadeada<Movimentacao> multasDoVeiculo = new ListaEncadeada<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int anoMovimentacao = Integer.parseInt(partesData[2]);

                String descricao = mov.getDescricao().toLowerCase();
                boolean ehMulta = descricao.contains("multa");

                if (mov.getIdDeVeiculo().equals(idDeVeiculo) && anoMovimentacao == ano && ehMulta) {
                    multasDoVeiculo.adicionar(mov);
                }
            }
            return multasDoVeiculo;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Multas Por Veiculo e Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularTotalMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception {
        try {
            ListaEncadeada<Movimentacao> multas = listarMultasPorVeiculoEAno(idDeVeiculo, ano);
            // Refatorado para usar a função recursiva (Relatório 6)
            return somarValoresRecursivo(multas.getCabeca());
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total Multas Por Veiculo E Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ListaEncadeada<Movimentacao> listarDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ListaEncadeada<Movimentacao> despesasDoVeiculo = new ListaEncadeada<>();

            for (Movimentacao mov : todasMovimentacoes) {
                if (mov.getIdDeVeiculo().equals(idDeVeiculo)) {
                    despesasDoVeiculo.adicionar(mov);
                }
            }

            return despesasDoVeiculo;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Despesas De Um Traduzido Veiculo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularTotalDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            ListaEncadeada<Movimentacao> despesas = listarDespesasDeUmDeterminadoVeiculo(idDeVeiculo);
            // Refatorado para usar a função recursiva (Relatório 1)
            return somarValoresRecursivo(despesas.getCabeca());
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total Despesas De Um Determinado Veiculo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public String contarTodasMovimentacoes() throws Exception {
        try {
            ListaEncadeada<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            var contador = todasMovimentacoes.tamanho();
            return Integer.toString(contador);
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Contar Todas Movimentacoes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}