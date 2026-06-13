/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelos.interfaces;
import estruturas.ListaEncadeada;
import modelos.classes.Veiculo;
// import modelos.classes.EstadoDoVeiculo;

public interface IVeiculosCRUD {
    void salvar(Veiculo veiculo) throws Exception;
    void remover(String veiculoID) throws  Exception;
    void atualizar(Veiculo veiculo) throws Exception;
    ListaEncadeada<Veiculo> listarVeiculos() throws Exception;
    ListaEncadeada<Veiculo> listarVeiculosInativos() throws Exception;
    Veiculo buscarPorId(String idVeiculo) throws Exception;
    String contadorVeiculosAtivos() throws Exception;
    // Veiculo buscarPorPlaca(int placa) throws Exception;
    // ArrayList<Veiculo> buscarPorMarca(String marca) throws Exception;
    // ArrayList<Veiculo> buscarPorModelo(String modelo) throws Exception;
    // ArrayList<Veiculo> buscarPorAnoDeFabricacao(int anoDeFabricacao) throws Exception;
    // ArrayList<Veiculo> buscarPorEstadoDoVeiculo(EstadoDoVeiculo estadoDoVeiculo) throws Exception;
}