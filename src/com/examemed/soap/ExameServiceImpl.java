package com.examemed.soap;

import com.examemed.model.Exame;
import com.examemed.service.ExameService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public class ExameServiceImpl {
    private ExameService exameService = new ExameService();

    @WebMethod
    public void addExame(Exame exame) {
        exameService.adicionarExame(exame, null);
    }

    @WebMethod
    public Exame getExameById(int id) {
        return exameService.findExameById(id);
    }

    @WebMethod
    public List<Exame> getAllExames() {
        return exameService.getAllExames();
    }

    @WebMethod
    public void updateExame(Exame exame) {
        exameService.atualizarExame(exame, null);
    }

    @WebMethod
    public void deleteExame(int id) {
        exameService.deletarExame(id);
    }
}
