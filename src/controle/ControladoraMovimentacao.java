package controle;

import estruturas.ListaEncadeada;
import estruturas.No;
import modelos.classes.Movimentacao;
import modelos.classes.TipoDeDespesa;
import modelos.classes.Veiculo;
import persistencia.MovimentacaoDAO;
import persistencia.TipoDeDespesasDAO;
import persistencia.VeiculosDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.Comparator;
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

public class ControladoraMovimentacao {
    private MovimentacaoDAO dao;
    private VeiculosDAO veiculosDao;
    private TipoDeDespesasDAO despesasDao;

    public ControladoraMovimentacao() {
        dao = new MovimentacaoDAO();
        veiculosDao = new VeiculosDAO();
        despesasDao = new TipoDeDespesasDAO();
    }

    /**
     * FUNÇÃO RECURSIVA EXIGIDA PELO REQUISITO ACADÊMICO (Relatório 3 e salvaguardas)
     * Utilizada no somatório de combustível por mês para conformidade.
     */
    private double somarValoresRecursivo(No<Movimentacao> noAtual) {
        if (noAtual == null) return 0.0;
        return noAtual.getValor().getValor() + somarValoresRecursivo(noAtual.getProximo());
    }

    /**
     * Comparador cronológico manual para datas no formato brasileiro (dd/MM/yyyy).
     * Garante consistência na ordenação do Insertion Sort.
     */
    private Comparator<Movimentacao> obterComparadorPorData() {
        return (m1, m2) -> {
            try {
                String[] p1 = m1.getData().split("/");
                String[] p2 = m2.getData().split("/");
                String s1 = p1[2] + p1[1] + p1[0]; // Formato yyyyMMdd
                String s2 = p2[2] + p2[1] + p2[0]; // Formato yyyyMMdd
                return s1.compareTo(s2);
            } catch (Exception e) {
                return m1.getData().compareTo(m2.getData());
            }
        };
    }

    private String gerarIdAleatorio() throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        try {
            return uuid.substring(0, 7).toUpperCase();
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Gerar Id Aleatorio - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void incluirMovimentacao(String idDeVeiculo, String idTipoDeDespesa,
            String descricao, String data, double valor, JTable tabela) throws Exception {
        try {
            String idMovimentacao = gerarIdAleatorio();
            Movimentacao m = new Movimentacao(idMovimentacao, idDeVeiculo, idTipoDeDespesa, descricao, data, valor);
            dao.salvar(m);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Incluir Movimentacao - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void carregarTabela(JTable tabela) throws Exception {
        ListaEncadeada<Movimentacao> lista = dao.listarMovimentacoes();

        DefaultTableModel modeloTabela = (DefaultTableModel) tabela.getModel();
        modeloTabela.setRowCount(0);

        for (Movimentacao m : lista) {
            modeloTabela.addRow(new Object[] {
                    m.getIdMovimentacao(),
                    m.getIdDeVeiculo(),
                    m.getIdTipoDeDespesa(),
                    m.getDescricao(),
                    m.getData(),
                    m.getValor()
            });
        }
    }

    public void atualizarMovimentacao(Movimentacao movimentacao, JTable tabela) throws Exception {
        try {
            dao.atualizar(movimentacao);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Atualizar Movimentacao - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void removerMovimentacao(String movimentacaoID, JTable tabela) throws Exception {
        try {
            dao.remover(movimentacaoID);
            carregarTabela(tabela);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Remover Movimentacao - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public Movimentacao buscarMovimentacaoPorId(String idMovimentacao) throws Exception {
        try {
            return dao.buscarPorID(idMovimentacao);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Buscar Movimentacao Por ID - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public ListaEncadeada<Veiculo> listarVeiculos() throws Exception {
        try {
            return veiculosDao.listarVeiculos();
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Listar Veiculos - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public ListaEncadeada<TipoDeDespesa> listarTiposDeDespesa() throws Exception {
        try {
            return despesasDao.listarDespesas();
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Listar Tipos De Despesa - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public Veiculo buscarVeiculoPorId(String idVeiculo) throws Exception {
        try {
            return veiculosDao.buscarPorId(idVeiculo);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Buscar Veiculo Por ID - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public TipoDeDespesa buscarTipoDeDespesaPorId(String idTipoDeDespesa) throws Exception {
        try {
            return despesasDao.buscarPorID(idTipoDeDespesa);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo Buscar Tipo De Despesa Por ID - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public double buscarTotalGastoEmDespesas() throws Exception {
        try {
            ListaEncadeada<Movimentacao> movimentacaos = dao.listarMovimentacoes();
            double totalGasto = 0.0;
            for (Movimentacao movimentacao : movimentacaos) {
                totalGasto += movimentacao.getValor();
            }
            return totalGasto;
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo buscarTotalGastoEmDespesas - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public ListaEncadeada<Movimentacao> listarMovimentacoesPorMes(int mes, int ano) throws Exception {
        try {
            return dao.listarMovimentacoesPorMes(mes, ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarMovimentacoesPorMes - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public double calcularSomatorioPorMes(ListaEncadeada<Movimentacao> movimentacoesDoMes) throws Exception {
        try {
            return dao.calcularSomatorioPorMes(movimentacoesDoMes);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarMovimentacoesPorMes - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public ListaEncadeada<Movimentacao> listarGastosCombustivelPorMes(int mes, int ano) throws Exception {
        try {
            return dao.listarGastosCombustivelPorMes(mes, ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarGastosCombustivelPorMes - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    // RELATORIO 2
    public void gerarRelatorio2MensalPDF(int mes, int ano, String mesNome, double totalGasto,
            ListaEncadeada<Movimentacao> movimentacaos) throws Exception {
        String fileName = "Relatorio_" + mesNome + "_" + ano + ".pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String totalFormatado = currencyFormat.format(totalGasto);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO MENSAL DE DESPESAS DA FROTA", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Mês/Ano de Referência: " + mesNome + " / " + ano, fonteNormal));
            document.add(new Paragraph("\n"));

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            float[] larguraColunas = { 1.5f, 2f, 2f, 3f, 2f, 2f };
            tabela.setWidths(larguraColunas);

            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Veículo", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Tipo Despesa", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Descrição", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Data", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Valor", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Movimentacao mov : movimentacaos) {
                PdfPCell cellId = new PdfPCell(new Phrase(mov.getIdMovimentacao(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                PdfPCell cellVeiculo = new PdfPCell(new Phrase(mov.getIdDeVeiculo(), fonteDados));
                cellVeiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellVeiculo);

                PdfPCell cellTipoDespesa = new PdfPCell(new Phrase(mov.getIdTipoDeDespesa(), fonteDados));
                cellTipoDespesa.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellTipoDespesa);

                PdfPCell cellDescricao = new PdfPCell(new Phrase(mov.getDescricao(), fonteDados));
                cellDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellDescricao);

                PdfPCell cellData = new PdfPCell(new Phrase(mov.getData(), fonteDados));
                cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellData);

                String valorFormatado = currencyFormat.format(mov.getValor());
                PdfPCell cellValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
                cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabela.addCell(cellValor);
            }

            document.add(tabela);

            document.add(new Paragraph("\n"));
            Font fonteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Somatório Geral de Gastos da Frota:", fonteSubtitulo));

            Font fonteValor = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph valorTotal = new Paragraph(totalFormatado, fonteValor);
            valorTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(valorTotal);

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório gerado com sucesso!\nSalvo em: " + filePath,
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

    // RELATORIO 3
    public void gerarRelatorio3MensalPDF(int mes, int ano, String mesNome, double totalGasto,
            ListaEncadeada<Movimentacao> movimentacaos) throws Exception {
        String fileName = "Relatorio_" + mesNome + "_" + ano + ".pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        // REQUISITO ACADÊMICO: Garante o cálculo recursivo do total de combustível para o Relatório 3
        totalGasto = somarValoresRecursivo(movimentacaos.getCabeca());

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String totalFormatado = currencyFormat.format(totalGasto);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO DE DESPESAS DA FROTA COM GASOLINA EM UM MÊS", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Mês/Ano de Referência: " + mesNome + " / " + ano, fontNormal));
            document.add(new Paragraph("\n"));

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            float[] larguraColunas = { 1.5f, 2f, 2f, 3f, 2f, 2f };
            tabela.setWidths(larguraColunas);

            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Veículo", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Tipo Despesa", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Descrição", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Data", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Valor", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Movimentacao mov : movimentacaos) {
                PdfPCell cellId = new PdfPCell(new Phrase(mov.getIdMovimentacao(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                PdfPCell cellVeiculo = new PdfPCell(new Phrase(mov.getIdDeVeiculo(), fonteDados));
                cellVeiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellVeiculo);

                PdfPCell cellTipoDespesa = new PdfPCell(new Phrase(mov.getIdTipoDeDespesa(), fonteDados));
                cellTipoDespesa.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellTipoDespesa);

                PdfPCell cellDescricao = new PdfPCell(new Phrase(mov.getDescricao(), fonteDados));
                cellDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellDescricao);

                PdfPCell cellData = new PdfPCell(new Phrase(mov.getData(), fonteDados));
                cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellData);

                String valorFormatado = currencyFormat.format(mov.getValor());
                PdfPCell cellValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
                cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabela.addCell(cellValor);
            }

            document.add(tabela);

            document.add(new Paragraph("\n"));
            Font fonteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Total de gastos da frota com combustível:", fonteSubtitulo));

            Font fonteValor = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph valorTotal = new Paragraph(totalFormatado, fonteValor);
            valorTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(valorTotal);

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório gerado com sucesso!\nSalvo em: " + filePath,
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

    // RELATORIO 4
    public ListaEncadeada<Movimentacao> listarGastosIPVAPorAno(int ano) throws Exception {
        try {
            return dao.listarGastosIPVAPorAno(ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarGastosIPVAPorAno - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public double calcularTotalIPVAPorAno(int ano) throws Exception {
        try {
            return dao.calcularTotalIPVAPorAno(ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarGastosIPVAPorAno - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void gerarRelatorio4MPDF(int ano, double totalGasto, ListaEncadeada<Movimentacao> movimentacaos)
            throws Exception {
        String fileName = "Relatorio_IPVA_" + ano + ".pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String totalFormatado = currencyFormat.format(totalGasto);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO DE GASTOS COM IPVA DA FROTA", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("Ano de Referência: " + ano, fonteNormal));
            document.add(new Paragraph("\n"));

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            float[] larguraColunas = { 1.5f, 2f, 2f, 3f, 2f, 2f };
            tabela.setWidths(larguraColunas);

            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Veículo", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Tipo Despesa", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Descrição", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Data", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Valor", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Movimentacao mov : movimentacaos) {
                PdfPCell cellId = new PdfPCell(new Phrase(mov.getIdMovimentacao(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                PdfPCell cellVeiculo = new PdfPCell(new Phrase(mov.getIdDeVeiculo(), fonteDados));
                cellVeiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellVeiculo);

                PdfPCell cellTipoDespesa = new PdfPCell(new Phrase(mov.getIdTipoDeDespesa(), fonteDados));
                cellTipoDespesa.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellTipoDespesa);

                PdfPCell cellDescricao = new PdfPCell(new Phrase(mov.getDescricao(), fonteDados));
                cellDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellDescricao);

                PdfPCell cellData = new PdfPCell(new Phrase(mov.getData(), fonteDados));
                cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellData);

                String valorFormatado = currencyFormat.format(mov.getValor());
                PdfPCell cellValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
                cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabela.addCell(cellValor);
            }

            document.add(tabela);

            document.add(new Paragraph("\n"));
            Font fonteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Somatório Total de IPVA da Frota em " + ano + ":", fonteSubtitulo));

            Font fonteValor = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph valorTotal = new Paragraph(totalFormatado, fonteValor);
            valorTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(valorTotal);

            document.add(new Paragraph("\n"));
            Font fonteInfo = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            // .size() alterado para .tamanho()
            document.add(new Paragraph("Total de veículos com IPVA pago: " + movimentacaos.tamanho(), fonteInfo));

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório de IPVA gerado com sucesso!\nSalvo em: " + filePath,
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

    // RELATORIO 6
    public ListaEncadeada<Movimentacao> listarMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception {
        try {
            return dao.listarMultasPorVeiculoEAno(idDeVeiculo, ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarGastosIPVAPorAno - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public double calcularTotalMultasPorVeiculoEAno(String idDeVeiculo, int ano) throws Exception {
        try {
            return dao.calcularTotalMultasPorVeiculoEAno(idDeVeiculo, ano);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarGastosIPVAPorAno - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void gerarRelatorio6PDF(String idDeVeiculo, int ano, double totalGasto,
            ListaEncadeada<Movimentacao> movimentacaos) throws Exception {
        String fileName = "Relatorio_Multas_Veiculo_" + idDeVeiculo + "_" + ano + ".pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        // REQUISITO ACADÊMICO: Algoritmo de ordenação manual (Insertion Sort) aplicado antes do PDF
        algoritmos.Ordenador.insertionSort(movimentacaos, obterComparadorPorData());

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String totalFormatado = currencyFormat.format(totalGasto);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO DE MULTAS PAGAS POR VEÍCULO", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("ID do Veículo: " + idDeVeiculo, fonteNormal));
            document.add(new Paragraph("Ano de Referência: " + ano, fonteNormal));
            document.add(new Paragraph("\n"));

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            float[] larguraColunas = { 1.5f, 2f, 2f, 3f, 2f, 2f };
            tabela.setWidths(larguraColunas);

            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Veículo", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Tipo Despesa", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Descrição", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Data", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Valor", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Movimentacao mov : movimentacaos) {
                PdfPCell cellId = new PdfPCell(new Phrase(mov.getIdMovimentacao(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                PdfPCell cellVeiculo = new PdfPCell(new Phrase(mov.getIdDeVeiculo(), fonteDados));
                cellVeiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellVeiculo);

                PdfPCell cellTipoDespesa = new PdfPCell(new Phrase(mov.getIdTipoDeDespesa(), fonteDados));
                cellTipoDespesa.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellTipoDespesa);

                PdfPCell cellDescricao = new PdfPCell(new Phrase(mov.getDescricao(), fonteDados));
                cellDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellDescricao);

                PdfPCell cellData = new PdfPCell(new Phrase(mov.getData(), fonteDados));
                cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellData);

                String valorFormatado = currencyFormat.format(mov.getValor());
                PdfPCell cellValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
                cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabela.addCell(cellValor);
            }

            document.add(tabela);

            document.add(new Paragraph("\n"));
            Font fonteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Total de Multas Pagas pelo Veículo em " + ano + ":", fonteSubtitulo));

            Font fonteValor = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph valorTotal = new Paragraph(totalFormatado, fonteValor);
            valorTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(valorTotal);

            document.add(new Paragraph("\n"));
            Font fonteInfo = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            // .size() alterado para .tamanho()
            document.add(new Paragraph("Quantidade de multas pagas: " + movimentacaos.tamanho(), fonteInfo));

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório de Multas gerado com sucesso!\nSalvo em: " + filePath,
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

    // RELATORIO 1
    public ListaEncadeada<Movimentacao> listarDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            return dao.listarDespesasDeUmDeterminadoVeiculo(idDeVeiculo);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo listarDespesasDeUmDeterminadoVeiculo - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public double calcularTotalDespesasDeUmDeterminadoVeiculo(String idDeVeiculo) throws Exception {
        try {
            return dao.calcularTotalDespesasDeUmDeterminadoVeiculo(idDeVeiculo);
        } catch (Exception e) {
            String msg = "ControladoraMovimentacao - Metodo calcularTotalDespesasDeUmDeterminadoVeiculo - " + e.getMessage();
            throw new Exception(msg);
        }
    }

    public void gerarRelatorio1PDF(String idDeVeiculo, double totalGasto, ListaEncadeada<Movimentacao> movimentacaos)
            throws Exception {
        String fileName = "Relatorio_Despesas_Veiculo_" + idDeVeiculo + ".pdf";
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator;
        String filePath = desktopPath + fileName;

        // REQUISITO ACADÊMICO: Algoritmo de ordenação manual (Insertion Sort) aplicado antes do PDF
        algoritmos.Ordenador.insertionSort(movimentacaos, obterComparadorPorData());

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String totalFormatado = currencyFormat.format(totalGasto);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font fonteTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("RELATÓRIO DE DESPESAS REALIZADAS NO VEÍCULO", fonteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            Font fonteNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            document.add(new Paragraph("ID do Veículo: " + idDeVeiculo, fonteNormal));
            document.add(new Paragraph("\n"));

            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            float[] larguraColunas = { 1.5f, 2f, 2f, 3f, 2f, 2f };
            tabela.setWidths(larguraColunas);

            Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

            PdfPCell cellCabecalho1 = new PdfPCell(new Phrase("ID", fonteCabecalho));
            cellCabecalho1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho1);

            PdfPCell cellCabecalho2 = new PdfPCell(new Phrase("Veículo", fonteCabecalho));
            cellCabecalho2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho2);

            PdfPCell cellCabecalho3 = new PdfPCell(new Phrase("Tipo Despesa", fonteCabecalho));
            cellCabecalho3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho3);

            PdfPCell cellCabecalho4 = new PdfPCell(new Phrase("Descrição", fonteCabecalho));
            cellCabecalho4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho4);

            PdfPCell cellCabecalho5 = new PdfPCell(new Phrase("Data", fonteCabecalho));
            cellCabecalho5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho5);

            PdfPCell cellCabecalho6 = new PdfPCell(new Phrase("Valor", fonteCabecalho));
            cellCabecalho6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellCabecalho6.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabela.addCell(cellCabecalho6);

            Font fonteDados = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            for (Movimentacao mov : movimentacaos) {
                PdfPCell cellId = new PdfPCell(new Phrase(mov.getIdMovimentacao(), fonteDados));
                cellId.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellId);

                PdfPCell cellVeiculo = new PdfPCell(new Phrase(mov.getIdDeVeiculo(), fonteDados));
                cellVeiculo.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellVeiculo);

                PdfPCell cellTipoDespesa = new PdfPCell(new Phrase(mov.getIdTipoDeDespesa(), fonteDados));
                cellTipoDespesa.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellTipoDespesa);

                PdfPCell cellDescricao = new PdfPCell(new Phrase(mov.getDescricao(), fonteDados));
                cellDescricao.setHorizontalAlignment(Element.ALIGN_LEFT);
                tabela.addCell(cellDescricao);

                PdfPCell cellData = new PdfPCell(new Phrase(mov.getData(), fonteDados));
                cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabela.addCell(cellData);

                String valorFormatado = currencyFormat.format(mov.getValor());
                PdfPCell cellValor = new PdfPCell(new Phrase(valorFormatado, fonteDados));
                cellValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tabela.addCell(cellValor);
            }

            document.add(tabela);

            document.add(new Paragraph("\n"));
            Font fonteSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Total de Despesas do Veículo:", fonteSubtitulo));

            Font fonteValor = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph valorTotal = new Paragraph(totalFormatado, fonteValor);
            valorTotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(valorTotal);

            document.add(new Paragraph("\n"));
            Font fonteInfo = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
            // .size() alterado para .tamanho()
            document.add(new Paragraph("Quantidade de despesas realizadas: " + movimentacaos.tamanho(), fonteInfo));

        } catch (DocumentException | IOException e) {
            throw new Exception("Erro ao criar ou escrever no arquivo PDF: " + e.getMessage());
        } finally {
            document.close();
        }

        javax.swing.JOptionPane.showMessageDialog(null,
                "Relatório de Despesas do Veículo gerado com sucesso!\nSalvo em: " + filePath,
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