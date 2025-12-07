package GeneradorFacturaPDF;

import GimnacioPago.Pago;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
/**
 *
 * @author Sebastian
 */

public final class ReportePagosPDF {

    private ReportePagosPDF() {
    }

    public static void exportarPagos(List<Pago> pagos, Path archivo) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(archivo)) {
            writer.write("ID;CEDULA;FECHA;MONTO\n");
            for (Pago p : pagos) {
                writer.write(p.getId() + ";"
                        + p.getCedulaCliente() + ";"
                        + p.getFecha() + ";"
                        + p.getMonto() + "\n");
            }
        }
    }
}
