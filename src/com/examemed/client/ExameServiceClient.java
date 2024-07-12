package com.examemed.client;

import com.examemed.model.Exame;
import com.examemed.soap.ExameService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.List;

public class ExameServiceClient {

    public static void main(String[] args) throws Exception {
        URL wsdlURL = new URL("http://localhost:8080/ExameMed/ws/ExameService?wsdl");
        QName qname = new QName("http://soap.examemed.com/", "ExameServiceImplService");

        Service service = Service.create(wsdlURL, qname);
        ExameService exameService = service.getPort(ExameService.class);

        Exame exame = new Exame();
        exame.setName("Exame SOAP");
        exame.setActive(true);
        exame.setDetail("Detalhes do exame SOAP");
        exame.setDetail1("Mais detalhes do exame SOAP");

        String addResponse = exameService.addExame(exame);
        System.out.println(addResponse);

        List<Exame> exames = exameService.getAllExames();
        for (Exame ex : exames) {
            System.out.println("Exame ID: " + ex.getId() + ", Nome: " + ex.getName());
        }
    }
}
