package com.prueba.GlobalSeguros;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    // Método POST para guardar los datos en un archivo plano
    @PostMapping("/save")
    public String saveLead(@RequestParam Map<String, String> leadData) {
        String filePath = "leads.txt";
        String nombreCliente = leadData.get("nombreCliente");
        String rtcValue = leadData.get("rtc");

        // Obtener la fecha y hora actuales
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Validar que el RTC se un numero entre 0 y 9
        try {
            int rtc = Integer.parseInt(rtcValue);
            if (rtc < 0 || rtc > 9) {
                return "redirect:/api/leads/form?error=true";
            }
        } catch (NumberFormatException e) {
            return "redirect:/api/leads/form?error=true";
        }
        // Validar que el nombre del cliente no exceda los 100 caracteres y que solo contenga letras y espacios
        if (nombreCliente.length() > 100 || !nombreCliente.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+")) {
            return "redirect:/api/leads/form?error=true";
        }

        // Crear una cadena con los datos del formulario
        String leadInfo = "Nombre del cliente: " + leadData.get("nombreCliente") + "\n" +
                "NIT: " + leadData.get("nit") + "\n" +
                "Nombre del punto: " + leadData.get("nombrePunto") + "\n" +
                "Nombre del equipo: " + leadData.get("nombreEquipo") + "\n" +
                "Ciudad: " + leadData.get("ciudad") + "\n" +
                "Promotor: " + leadData.get("promotor") + "\n" +
                "RTC: " + leadData.get("rtc") + "\n" +
                "Captcha: " + leadData.get("captcha") + "\n" +
                "Fecha y hora: " + currentDateTime + "\n" +
                "------------------------------\n";

        // Guardar los datos en un archivo plano
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(leadInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return "Hubo un error al guardar los datos.";
        }

        // Retornar mensaje de éxito
        return "Datos guardados con éxito.";
    }
}
