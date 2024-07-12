package com.examemed.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import com.examemed.exception.ApplicationException;
import com.examemed.util.DBConnection;

public class GeneratePdfAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(GeneratePdfAction.class);

    private int exameId;
    private InputStream pdfStream;

    public int getExameId() {
        return exameId;
    }

    public void setExameId(int exameId) {
        this.exameId = exameId;
    }

    public InputStream getPdfStream() {
        return pdfStream;
    }

    public String execute() throws IOException {
        logger.info("Iniciando a geração do PDF para o exame com ID: {}", exameId);
        
        String sql = "SELECT nm_exame, ic_ativo, ds_detalhe_exame, ds_detalhe_exame1 FROM exame WHERE cd_exame = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exameId);
            logger.debug("Executando query: {}", stmt);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeExame = rs.getString("nm_exame");
                boolean ativo = rs.getBoolean("ic_ativo");
                String detalhe = rs.getString("ds_detalhe_exame");
                String detalhe1 = rs.getString("ds_detalhe_exame1");

                logger.debug("Dados do exame: nome={}, ativo={}, detalhe={}, detalhe1={}", 
                    nomeExame, ativo, detalhe, detalhe1);

                
                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();
                document.add(new Paragraph("Nome do Exame: " + nomeExame));
                document.add(new Paragraph("Ativo: " + ativo));
                document.add(new Paragraph("Detalhes: " + detalhe));
                document.add(new Paragraph("Detalhes 1: " + detalhe1));
                document.close();

                logger.info("Documento PDF criado com sucesso para o exame com ID: {}", exameId);

                pdfStream = new ByteArrayInputStream(baos.toByteArray());
                return SUCCESS;
            } else {
                logger.warn("Exame com ID: {} não encontrado.", exameId);
                throw new ApplicationException("Exame não encontrado.");
            }
        } catch (SQLException | DocumentException e) {
            logger.error("Erro ao gerar o PDF para o exame com ID: {}", exameId, e);
            throw new ApplicationException("Erro ao gerar o PDF.", e);
        }
    }
}
