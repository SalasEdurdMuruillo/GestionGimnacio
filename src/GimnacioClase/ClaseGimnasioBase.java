package GimnacioClase;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author USER
 */
public class ClaseGimnasioBase {

    private static final Map<String, ClaseGimnasio> prototipos = new HashMap<>();

    public static void registrarPrototipo(String nombre, ClaseGimnasio clase) {
        prototipos.put(nombre, clase);
    }

    public static ClaseGimnasio clonar(String nombre) {
        ClaseGimnasio c = prototipos.get(nombre);
        return c != null ? c.clone() : null;
    }
}
