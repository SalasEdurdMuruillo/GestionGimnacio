package Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Sebastian
 */

public final class PasswordUtils {

    private PasswordUtils() {
    }

    public static String hash(String textoPlano) {
        if (textoPlano == null) {
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(textoPlano.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se encontró el algoritmo de hash", e);
        }
    }

    public static boolean verificar(String textoPlano, String hashEsperado) {
        return hash(textoPlano).equals(hashEsperado);
    }
}
