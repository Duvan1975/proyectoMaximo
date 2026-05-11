package com.proyectoMaximo.proyectoMaximoSpringBoot.manoObra;

import com.proyectoMaximo.proyectoMaximoSpringBoot.configuracion.ConfiguracionActa;
import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.Unidades;
import com.proyectoMaximo.proyectoMaximoSpringBoot.unidades.UnidadesRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoService {

    private final UnidadesRepository unidadesRepository;

    public MoService(UnidadesRepository unidadesRepository) {
        this.unidadesRepository = unidadesRepository;
    }

    private final List<ConfiguracionActa> configuraciones = List.of(

            new ConfiguracionActa(
                    "MO_M31883",
                    0,
                    1,
                    12,
                    2
            ),

            new ConfiguracionActa(
                    "RESUMEN_MO",
                    null,
                    2,
                    null,
                    null
            )
    );

    public List<MoRegistroDTO> leerArchivoMO(InputStream archivo) throws IOException {

        List<MoRegistroDTO> lista = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(archivo);

        ConfiguracionActa config = obtenerConfiguracionActa(workbook);

        if (config == null) {
            System.out.println("No se encontró configuración compatible");
            workbook.close();
            return lista;
        }

        System.out.println("Configuración encontrada: "
                + config.nombreHoja());

        Sheet sheet = workbook.getSheet(config.nombreHoja());

        //Variable para controlar Lectura de datos después del campo asignado
        boolean iniciarLectura = false;

        for (Row row : sheet) {

            Cell estructuraCell = config.columnaEstructura() != null
                    ? row.getCell(config.columnaEstructura())
                    : null;

            Cell detalleCell = config.columnaDetalle() != null
                    ? row.getCell(config.columnaDetalle())
                    : null;

            Cell nivelCell = config.columnaNivel() != null
                    ? row.getCell(config.columnaNivel())
                    : null;

            Cell cantCell = config.columnaCant() != null
                    ? row.getCell(config.columnaCant())
                    : null;

            String estructura = getStringCellValue(estructuraCell);
            String detalle = getStringCellValue(detalleCell);
            Integer nivelTension = getIntegerCellValue(nivelCell);
            Integer cant = getIntegerCellValue(cantCell);

            //Controlamos la lectura de los datos sin encabezados
            if (!iniciarLectura) {

                if (detalle != null &&
                        detalle.equalsIgnoreCase("detalle")) {

                    iniciarLectura = true;
                }

                continue;
            }

            //Controlamos la lectura de los datos hasta dónde terminan
            if (detalle != null &&
                        detalle.toUpperCase().contains("PORCENTAJE")) {
                break;
            }

            // filtro de seguridad
            if (detalle == null || detalle.isBlank()) continue;
            lista.add(new MoRegistroDTO(
                    estructura,
                    detalle,
                    cant,
                    nivelTension
            ));

            // dentro del for, después de extraerFiltro
            String filtro = extraerFiltro(detalle);
            String filtroNormalizado = normalizarFiltro(filtro);

            System.out.println("Buscando: " + filtroNormalizado);

            List<Unidades> resultados = unidadesRepository.findByDescripcionContainingIgnoreCase(filtroNormalizado);

            if (resultados.isEmpty() && !filtro.equals(filtroNormalizado)) {
                // Si con normalizado no encuentra, intenta con el original
                resultados = unidadesRepository.findByDescripcionContainingIgnoreCase(filtro);
            }

            System.out.println("Coincidencias: " + resultados.size());

            for (Unidades u : resultados) {
                System.out.println("  -> " + u.getDescripcion());
            }

        }

        workbook.close();
        return lista;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;

        // Primero evaluar si es fórmula
        if (cell.getCellType() == CellType.FORMULA) {
            try {
                return cell.getStringCellValue().trim();
            } catch (Exception e) {
                return null;
            }
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }

        return null;
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null) return null;

        // Primero evaluar si es fórmula
        if (cell.getCellType() == CellType.FORMULA) {
            try {
                return (int) cell.getNumericCellValue();
            } catch (Exception e) {
                return null;
            }
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }

        if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    private String extraerFiltro(String detalle) {

        if (detalle == null || detalle.isBlank()) {
            return null;
        }

        String textoProcesado = detalle;

        //Caso cuando existe "- "
        if (detalle.contains("-")) {

            String[] partes = detalle.split("- ", 3);

            if (partes.length > 1) {
                textoProcesado = partes[1].trim();
            }
        }

        //Caso especial si aún comienza con código pegado
        if (textoProcesado.contains("-")) {

            String primeraPalabra = textoProcesado.split("\\s+")[0];

            //Si la primera tiene "-"
            if (primeraPalabra.contains("-")) {

                String[] partesInternas = textoProcesado.split("-", 3);

                if (partesInternas.length >= 1) {
                    textoProcesado = partesInternas[1].trim();
                }
            }
        }

        //Separar palabras
        String[] palabras = textoProcesado.split("\\s+");

        List<String> palabrasValidas = new ArrayList<>();

        for (String palabra : palabras) {

            //Ignorar palabras empiecen por número
            if (Character.isDigit(palabra.charAt(0))) {
                continue;
            }

            palabrasValidas.add(palabra);

            if (palabrasValidas.size() == 3) {
                break;
            }

        }

        if (palabrasValidas.size() >= 3) {
            return palabrasValidas.get(0)
                    + " " +palabrasValidas.get(1)
                    + " " +palabrasValidas.get(2);
        }

        //Validar mínimo tres palabras
        if (palabras.length >= 3) {
            return palabras[0] + " " + palabras[1] + " " + palabras[2];
        }

        //Caso extremo solo una palabra
        return textoProcesado;

    }

    private String normalizarFiltro(String texto) {
        if (texto == null) return null;

        return texto.toLowerCase()
                .replace("y", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private ConfiguracionActa obtenerConfiguracionActa(Workbook workbook) {

        for (Sheet sheet : workbook) {

            String nombreHoja = sheet.getSheetName();

            for (ConfiguracionActa config : configuraciones) {

                if (config.nombreHoja().equals(nombreHoja)) {
                    return config;
                }
            }
        }
        return null;
    }

}
