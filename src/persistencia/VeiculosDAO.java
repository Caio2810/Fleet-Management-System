/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.ArrayList;
import modelos.classes.Veiculo;
import modelos.classes.EstadoDoVeiculo;
import modelos.interfaces.IVeiculosCRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class VeiculosDAO implements IVeiculosCRUD {

    // Atributos
    private String nomeDoArquivoNoDisco = null;

    // Construtor
    public VeiculosDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/TiposDeVeiculos.txt";
    }

    @Override
    public void salvar(Veiculo veiculo) throws Exception {
        try {
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // Grava todos os atributos
            String str = veiculo.getIdDeVeiculo() + ";";
            str += veiculo.isEstadoDoVeiculo().name() + ";";
            str += veiculo.getPlaca() + ";";
            str += veiculo.getMarca() + ";";
            str += veiculo.getModelo() + ";";
            str += veiculo.getAnoDeFabricacao() + "\n";
            bw.write(str);
            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Cadastrar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Veiculo> listarVeiculos() throws Exception {
        try {
            ArrayList<Veiculo> listaDeVeiculos = new ArrayList<>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);

            String linha = "";
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty())
                    continue;
                String vetorStr[] = linha.split(";");
                if (vetorStr.length < 6)
                    continue;
                Veiculo objVeiculo = new Veiculo(
                        vetorStr[0], // id
                        vetorStr[2], // placa
                        vetorStr[3], // marca
                        vetorStr[4], // modelo
                        Integer.parseInt(vetorStr[5]), // ano
                        EstadoDoVeiculo.valueOf(vetorStr[1].trim().toUpperCase()));
                listaDeVeiculos.add(objVeiculo);
            }
            br.close();
            return listaDeVeiculos;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Lista - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public Veiculo buscarPorId(String idDeVeiculo) throws Exception {
        try {
            for (Veiculo obj : listarVeiculos()) {
                if (obj.getIdDeVeiculo().equals(idDeVeiculo)) {
                    return obj;
                }
            }
            return null;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Buscar Por Id - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void atualizar(Veiculo veiculo) throws Exception {
        try {
            ArrayList<Veiculo> lista = listarVeiculos();

            boolean encontrou = false;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getIdDeVeiculo().equals(veiculo.getIdDeVeiculo())) {
                    lista.set(i, veiculo);
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                throw new Exception("Veículo não encontrado para atualizar!");
            }

            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Veiculo obj : lista) {
                String str = obj.getIdDeVeiculo() + ";";
                str += obj.isEstadoDoVeiculo().name() + ";";
                str += obj.getPlaca() + ";";
                str += obj.getMarca() + ";";
                str += obj.getModelo() + ";";
                str += obj.getAnoDeFabricacao() + "\n";
                bw.write(str);
            }

            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Atualizar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void remover(String veiculoID) throws Exception {
        try {
            ArrayList<Veiculo> lista = this.listarVeiculos();
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Veiculo obj : lista) {
                if (!obj.getIdDeVeiculo().equals(veiculoID)) {
                    String str = obj.getIdDeVeiculo() + ";";
                    str += obj.isEstadoDoVeiculo().name() + ";";
                    str += obj.getPlaca() + ";";
                    str += obj.getMarca() + ";";
                    str += obj.getModelo() + ";";
                    str += obj.getAnoDeFabricacao() + "\n";
                    bw.write(str);
                }
            }

            bw.close();
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Deletar - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Veiculo> listarVeiculosInativos() throws Exception {
        try {
            ArrayList<Veiculo> todosVeiculos = listarVeiculos();
            ArrayList<Veiculo> veiculosInativos = new ArrayList<>();

            for (Veiculo veiculo : todosVeiculos) {
                if (veiculo.isEstadoDoVeiculo() == EstadoDoVeiculo.INATIVO) {
                    veiculosInativos.add(veiculo);
                }
            }

            return veiculosInativos;
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Listar Veiculos Inativos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public String contadorVeiculosAtivos() throws Exception {
        try {
            ArrayList<Veiculo> todosVeiculos = listarVeiculos();
            int contador = 0;

            for (Veiculo veiculo : todosVeiculos) {
                contador++;
            }

            return Integer.toString(contador);
        } catch (Exception erro) {
            String msg = "Persistencia - Metodo Contador Veiculos Ativos - " + erro.getMessage();
            throw new Exception(msg);
        }
    }
}
