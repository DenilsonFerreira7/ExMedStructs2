package com.examemed.util;

import com.examemed.model.ExameRealizado;
import com.examemed.model.Exame;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGenerator {

    public void generateExameRealizadoPdf(OutputStream outputStream, List<ExameRealizado> examesRealizados, List<Exame> detalhesExames) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Adicionar título grande
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph title = new Paragraph("Relatório de Exames Realizados", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Criar tabela de exames realizados
        PdfPTable tableRealizados = new PdfPTable(5);
        tableRealizados.setWidthPercentage(100);
        tableRealizados.setSpacingBefore(10);
        tableRealizados.setSpacingAfter(10);

        // Adicionar cabeçalho da tabela de exames realizados
        addTableHeaderRealizados(tableRealizados);

        // Adicionar linhas da tabela de exames realizados
        addRowsRealizados(tableRealizados, examesRealizados);

        // Adicionar tabela ao documento
        document.add(tableRealizados);

        // Adicionar título para detalhes do exame
        Paragraph detalhesTitle = new Paragraph("Detalhes dos Exames", titleFont);
        detalhesTitle.setAlignment(Element.ALIGN_CENTER);
        detalhesTitle.setSpacingBefore(20);
        detalhesTitle.setSpacingAfter(20);
        document.add(detalhesTitle);

        // Adicionar detalhes dos exames como quadrados
        addExameDetailBlocks(document, detalhesExames);

        document.close();
    }

    private void addTableHeaderRealizados(PdfPTable table) {
        Stream.of("ID Funcionário", "Nome Funcionário", "ID Exame", "Nome Exame", "Data Realização")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addRowsRealizados(PdfPTable table, List<ExameRealizado> examesRealizados) {
        for (ExameRealizado exameRealizado : examesRealizados) {
            table.addCell(String.valueOf(exameRealizado.getFuncionarioId()));
            table.addCell(exameRealizado.getFuncionarioNome());
            table.addCell(String.valueOf(exameRealizado.getExameId()));
            table.addCell(exameRealizado.getExameNome());
            table.addCell(exameRealizado.getDataRealizacao().toString());
        }
    }

    private void addExameDetailBlocks(Document document, List<Exame> detalhesExames) throws DocumentException {
        for (Exame exame : detalhesExames) {
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(80); // Tamanho da largura do bloco
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            table.setHorizontalAlignment(Element.ALIGN_CENTER); // Centraliza o bloco na página

            PdfPCell cell = new PdfPCell();
            cell.setPadding(10);
            cell.setBorder(PdfPCell.BOX);

            Paragraph exameId = new Paragraph("ID Exame: " + exame.getId(), new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD));
            exameId.setSpacingAfter(5);
            cell.addElement(exameId);

            Paragraph exameNome = new Paragraph("Nome Exame: " + exame.getName(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            exameNome.setSpacingAfter(5);
            cell.addElement(exameNome);

            Paragraph exameAtivo = new Paragraph("Ativo: " + (exame.isActive() ? "Sim" : "Não"), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            exameAtivo.setSpacingAfter(5);
            cell.addElement(exameAtivo);

            Paragraph exameDetalhe = new Paragraph("Detalhes: " + exame.getDetail(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
            exameDetalhe.setSpacingAfter(15);
            cell.addElement(exameDetalhe);

            table.addCell(cell);
            document.add(table);
        }
    }
}
