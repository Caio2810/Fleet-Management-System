package controle;

import modelos.classes.EstadoDoVeiculo;
import modelos.classes.Movimentacao;
import modelos.classes.Veiculo;
import persistencia.VeiculosDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class ControladoraVeiculos {

    private VeiculosDAO dao;

    public ControladoraVeiculos() {
        dao = new VeiculosDAO();
    }

    private String gerarIdAleatorio() throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        try {
            return uuid.substring(0, 7).toUpperCase();
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo Gerar Id Aleatorio - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void incluirVeiculo(String placa, String marca, String modelo,
            int ano, EstadoDoVeiculo estado, JTable tabela) throws Exception {

        try {
            String veiculoID = gerarIdAleatorio();
            Veiculo v = new Veiculo(veiculoID, placa, marca, modelo, ano, estado);
            dao.salvar(v);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo Incluir Veiculo - " + e.getMessage();
            throw new Exception(msg);
        }

    }

    public void carregarTabela(JTable tabela) throws Exception {
        ArrayList<Veiculo> lista = dao.listarVeiculos();

        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setRowCount(0);

        for (Veiculo v : lista) {
            modeloTabela.addRow(new Object[]{
                v.getIdDeVeiculo(),
                v.getPlaca(),
                v.getMarca(),
                v.getModelo(),
                v.getAnoDeFabricacao(),
                v.isEstadoDoVeiculo()
            });
        }
    }

    public void removerVeiculo(String veiculoID, JTable tabela) throws Exception {
        try {
            dao.remover(veiculoID);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo Remover Veiculo - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void atualizarVeiculo(Veiculo veiculo, JTable tabela) throws Exception {
        try {
            dao.atualizar(veiculo);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo Atualizar Veiculo - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public Veiculo buscarVeiculoPorId(String veiculoID) throws Exception {
        try {
            return dao.buscarPorId(veiculoID);
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo Buscar Veiculo Por ID - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public String contadorVeiculosAtivos() throws Exception {
        try {
            ArrayList<Veiculo> todosVeiculos = dao.listarVeiculos();
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

    public ArrayList<Veiculo> listarVeiculosInativos() throws Exception {
        try {
            return dao.listarVeiculosInativos();
        } catch (Exception e) {
            String msg = "ControladoraVeiculos - Metodo listarVeiculosInativos - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void gerarRelatorio5PDF(ArrayList<Veiculo> veiculos) throws Exception {
        String fileName = "Relatorio_Veiculos_Inativos.pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // TÍTULO
            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO DE VEÍCULOS INATIVOS DA FROTA", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            // INFORMAÇÃO
            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Total de veículos inativos: " + veiculos.size(), fonteNormal));
            document.add(new Paragraph("\n"));

            // CRIAR TABELA
            PdfPTable tabela = new PdfPTable(6); // 6 colunas
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            // Definir largura das colunas (proporcionalmente)
            float[] larguraColunas = {2f, 2f, 2f, 2.5f, 2.5f, 2f};
            tabela.setWidths(larguraColunas);

            // CABEÇALHO DA TABELA
            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID Veículo", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Placa", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Marca", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Modelo", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Ano Fabricação", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Estado", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            // PREENCHER TABELA COM OS VEÍCULOS INATIVOS
            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Veiculo veiculo : veiculos) {
                // ID Veículo
                PdfPCell cellId = new PdfPCell(new Phrase(veiculo.getIdDeVeiculo(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                // Placa
                PdfPCell cellPlaca = new PdfPCell(new Phrase(veiculo.getPlaca(), fonteDados));
                cellPlaca.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellPlaca);

                // Marca
                PdfPCell cellMarca = new PdfPCell(new Phrase(veiculo.getMarca(), fonteDados));
                cellMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellMarca);

                // Modelo
                PdfPCell cellModelo = new PdfPCell(new Phrase(veiculo.getModelo(), fonteDados));
                cellModelo.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellModelo);

                // Ano Fabricação
                PdfPCell cellAno = new PdfPCell(new Phrase(String.valueOf(veiculo.getAnoDeFabricacao()), fonteDados));
                cellAno.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellAno);

                // Estado
                PdfPCell cellEstado = new PdfPCell(new Phrase(veiculo.isEstadoDoVeiculo().toString(), fonteDados));
                cellEstado.setHorizontalAlignment(Element.ALIGN_CENTER);
                // Destaque em vermelho para veículos inativos
                cellEstado.setBackgroundColor(new BaseColor(255, 200, 200));
                tabela.addCell(cellEstado);
            }

            document.add(tabela);

            // OBSERVAÇÃO
            document.add(new Paragraph("\n"));
            Font fonteObs = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            document.add(new Paragraph("Observação: Veículos inativos não estão em operação na frota.", fonteObs));

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório de Veículos Inativos gerado com sucesso!\nSalvo em: " + filePath,
                "Sucesso", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        File generatedFile = new File(filePath);
        if (java.awt.Desktop.isDesktopSupported() && generatedFile.exists()) {
            try {
                java.awt.Desktop.getDesktop().open(generatedFile);
            } catch (java.io.IOException e) {
                System.err.println("Não foi possível abrir o arquivo automaticamente: " + e.getMessage());
            }
        }
    }

}
