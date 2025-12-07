package GimnacioMembresia;
/**
 *
 * @author Sebastian
 */

public enum TipoMembresia {
    BASICA(1),
    PREMIUM(3),
    VIP(12);

    private final int meses;

    TipoMembresia(int meses) {
        this.meses = meses;
    }

    public int getMeses() {
        return meses;
    }
}
