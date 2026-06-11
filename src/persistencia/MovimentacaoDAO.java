/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.ArrayList;
import modelos.classes.Movimentacao;
import modelos.interfaces.IMovimentacaoCRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MovimentacaoDAO implements IMovimentacaoCRUD {
    // Atributos
    private String nomeDoArquivoNoDisco = null;

    // Metodo Construtor
    public MovimentacaoDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/Movimentação.txt";
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
    public ArrayList<Movimentacao> listarMovimentacoes() throws Exception {
        try {
            ArrayList<Movimentacao> lista = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vetorStr = linha.split(";");
                Movimentacao obj = new Movimentacao(
                        vetorStr[0],
                        vetorStr[1], // idVeiculo
                        vetorStr[2], // idTipoDespesa
                        vetorStr[3], // descricao
                        vetorStr[4], // data (String)
                        Double.parseDouble(vetorStr[5]) // valor
                );
                lista.add(obj);
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
            ArrayList<Movimentacao> lista = listarMovimentacoes();

            boolean encontrou = false;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdMovimentacao().equals(movimentacao.getIdMovimentacao())) {
                    lista.set(i, movimentacao);
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
            ArrayList<Movimentacao> lista = this.listarMovimentacoes();
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
    public ArrayList<Movimentacao> listarMovimentacoesPorMes(int mes, int ano) throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ArrayList<Movimentacao> movimentacoesDoMes = new ArrayList<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int mesMovimentacao = Integer.parseInt(partesData[1]);
                int anoMovimentacao = Integer.parseInt(partesData[2]);

                if (mesMovimentacao == mes && anoMovimentacao == ano) {
                    movimentacoesDoMes.add(mov);
                }
            }

            return movimentacoesDoMes;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Movimentacoes Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Movimentacao> listarGastosCombustivelPorMes(int mes, int ano) throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ArrayList<Movimentacao> gastosCombustivel = new ArrayList<>();

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

                if (mesMovimentacao == mes && anoMovimentacao == ano
                        && ehCombustivel) {
                    gastosCombustivel.add(mov);
                }
            }

            return gastosCombustivel;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Gastos Combustivel Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularSomatorioPorMes(ArrayList<Movimentacao> movimentacoesDoMes) throws Exception {
        try {
            double somatorio = 0;

            for (Movimentacao mov : movimentacoesDoMes) {
                somatorio += mov.getValor();
            }

            return somatorio;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Somatorio Por Mes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Movimentacao> listarGastosIPVAPorAno(int ano) throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ArrayList<Movimentacao> gastosIPVA = new ArrayList<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int anoMovimentacao = Integer.parseInt(partesData[2]);

                if (anoMovimentacao == ano && mov.getDescricao().toLowerCase().contains("ipva")) {
                    gastosIPVA.add(mov);
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
            ArrayList<Movimentacao> gastosIPVA = listarGastosIPVAPorAno(ano);
            double total = 0;

            for (Movimentacao mov : gastosIPVA) {
                total += mov.getValor();
            }

            return total;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total IPVA Por Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Movimentacao> listarMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ArrayList<Movimentacao> multasDoVeiculo = new ArrayList<>();

            for (Movimentacao mov : todasMovimentacoes) {
                String data = mov.getData();
                String[] partesData = data.split("/");

                int anoMovimentacao = Integer.parseInt(partesData[2]);

                String descricao = mov.getDescricao().toLowerCase();
                boolean ehMulta = descricao.contains("multa");

                if (mov.getIdDeVeiculo().equals(idDeVeiculo)
                        && anoMovimentacao == ano
                        && ehMulta) {
                    multasDoVeiculo.add(mov);
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
            ArrayList<Movimentacao> multas = listarMultasPorVeiculoEAno(idDeVeiculo, ano);
            double total = 0;

            for (Movimentacao mov : multas) {
                total += mov.getValor();
            }

            return total;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total Multas Por Veiculo E Ano - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Movimentacao> listarDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            ArrayList<Movimentacao> despesasDoVeiculo = new ArrayList<>();

            for (Movimentacao mov : todasMovimentacoes) {
                if (mov.getIdDeVeiculo().equals(idDeVeiculo)) {
                    despesasDoVeiculo.add(mov);
                }
            }

            return despesasDoVeiculo;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Despesas De Um Determinado Veiculo - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public double calcularTotalDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            ArrayList<Movimentacao> despesas = listarDespesasDeUmDeterminadoVeiculo(idDeVeiculo);
            double total = 0;

            for (Movimentacao mov : despesas) {
                total += mov.getValor();
            }

            return total;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Calcular Total Despesas De Um Determinado Veiculo - "
                    + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public String contarTodasMovimentacoes() throws Exception {
        try {
            ArrayList<Movimentacao> todasMovimentacoes = listarMovimentacoes();
            var contador = todasMovimentacoes.size();
            return Integer.toString(contador);
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Contar Todas Movimentacoes - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}
