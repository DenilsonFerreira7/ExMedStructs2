package com.examemed.soap;

import com.examemed.model.Exame;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ExameService {

    String addExame(Exame exame);

    Exame getExameById(int id);

    List<Exame> getAllExames();

    String updateExame(Exame exame);

    String deleteExame(int id);
}
