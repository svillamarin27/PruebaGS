package com.prueba.GlobalSeguros;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class LeadExportController {

    @GetMapping("/export")
    public void exportLeadsToTXT(@RequestParam("date") String date,
                                 HttpServletResponse response) throws IOException {
        // Configurar la respuesta para generar un archivo de texto
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=leads.txt");

        String filePath = "leads.txt";

        // Verificar si el archivo leads.txt existe
        File file = new File(filePath);
        if (!file.exists()) {
            response.getWriter().write("Error: El archivo leads.txt no fue encontrado.");
            return;
        }

        // Leer todo el contenido del archivo leads.txt sin filtrar
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PrintWriter writer = response.getWriter()) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.println(line);  // Escribir cada línea en el archivo exportado
            }
        } catch (IOException e) {
            response.getWriter().write("Error: No se pudo leer el archivo leads.txt.");
        }
    }
}
