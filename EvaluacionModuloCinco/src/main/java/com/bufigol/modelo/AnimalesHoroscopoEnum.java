package com.bufigol.modelo;

public enum AnimalesHoroscopoEnum {
    MONO("Mono", "Inteligente, ingenioso y versátil"),
    GALLO("Gallo", "Observador, trabajador y valiente"),
    PERRO("Perro", "Leal, honesto y amigable"),
    CERDO("Cerdo", "Diligente, compasivo y generoso"),
    RATA("Rata", "Inteligente, adaptable y astuto"),
    BUEY("Buey", "Paciente, amable y confiable"),
    TIGRE("Tigre", "Valiente, competitivo y carismático"),
    CONEJO("Conejo", "Elegante, amable y prudente"),
    DRAGON("Dragón", "Fuerte, orgulloso y exitoso"),
    SERPIENTE("Serpiente", "Sabio, enigmático y intuitivo"),
    CABALLO("Caballo", "Energético, independiente y aventurero"),
    CABRA("Cabra", "Creativo, elegante y artístico");

    private final String nombre;
    private final String caracteristicas;
    private static final int CICLO_ZODIACAL = 12;

    AnimalesHoroscopoEnum(String nombre, String caracteristicas) {
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    /**
     * Obtiene el animal del horóscopo chino basado en el año proporcionado
     * @param año El año para calcular el animal
     * @return El animal correspondiente al año
     */
    public static AnimalesHoroscopoEnum getAnimalPorAño(int año) {
        // El ciclo comienza desde el año 1900 con RATA
        int indice = (año - 1900) % CICLO_ZODIACAL;
        if (indice < 0) indice += CICLO_ZODIACAL;
        return values()[indice];
    }

    /**
     * Obtiene el siguiente animal en el ciclo
     * @return El siguiente animal en el ciclo zodiacal
     */
    public AnimalesHoroscopoEnum getSiguienteAnimal() {
        int siguienteIndice = (this.ordinal() + 1) % values().length;
        return values()[siguienteIndice];
    }

    /**
     * Obtiene el animal anterior en el ciclo
     * @return El animal anterior en el ciclo zodiacal
     */
    public AnimalesHoroscopoEnum getAnimalAnterior() {
        int indiceAnterior = (this.ordinal() - 1 + values().length) % values().length;
        return values()[indiceAnterior];
    }

    /**
     * Calcula la compatibilidad con otro animal del horóscopo
     * @param otroAnimal El animal con el que se quiere comparar
     * @return Un nivel de compatibilidad del 1 al 5
     */
    public int calcularCompatibilidad(AnimalesHoroscopoEnum otroAnimal) {
        int diferencia = Math.abs(this.ordinal() - otroAnimal.ordinal());
        if (diferencia == 0) return 5; // Mismo animal
        if (diferencia == 4 || diferencia == 8) return 5; // Alta compatibilidad
        if (diferencia == 2 || diferencia == 10) return 4; // Buena compatibilidad
        if (diferencia == 3 || diferencia == 9) return 3; // Compatibilidad media
        if (diferencia == 1 || diferencia == 11) return 2; // Baja compatibilidad
        return 1; // Compatibilidad muy baja
    }

    /**
     * Obtiene el elemento asociado al animal según la astrología china
     * @return El elemento correspondiente (Madera, Fuego, Tierra, Metal, Agua)
     */
    public String getElemento() {
        switch(this) {
            case TIGRE:
            case CONEJO:
                return "Madera";
            case SERPIENTE:
            case CABALLO:
                return "Fuego";
            case BUEY:
            case DRAGON:
            case CABRA:
                return "Tierra";
            case MONO:
            case GALLO:
                return "Metal";
            case RATA:
            case CERDO:
                return "Agua";
            default:
                return "Desconocido";
        }
    }

    /**
     * Verifica si el año proporcionado corresponde a este animal
     * @param año El año a verificar
     * @return true si el año corresponde a este animal
     */
    public boolean esAñoDeEsteAnimal(int año) {
        return getAnimalPorAño(año) == this;
    }

    /**
     * Obtiene todos los animales que son compatibles con este animal
     * (compatibilidad >= 4)
     * @return Array con los animales más compatibles
     */
    public AnimalesHoroscopoEnum[] getAnimalesCompatibles() {
        return java.util.Arrays.stream(values())
                .filter(animal -> calcularCompatibilidad(animal) >= 4)
                .toArray(AnimalesHoroscopoEnum[]::new);
    }

    /**
     * Obtiene el animal opuesto en el ciclo zodiacal
     * @return El animal opuesto
     */
    public AnimalesHoroscopoEnum getAnimalOpuesto() {
        return values()[(this.ordinal() + 6) % CICLO_ZODIACAL];
    }

    /**
     * Calcula el próximo año de este animal
     * @param añoActual El año actual
     * @return El próximo año que corresponde a este animal
     */
    public int getProximoAño(int añoActual) {
        int años = (this.ordinal() - getAnimalPorAño(añoActual).ordinal() + CICLO_ZODIACAL) % CICLO_ZODIACAL;
        return añoActual + (años == 0 ? CICLO_ZODIACAL : años);
    }

    /**
     * Obtiene una descripción completa del animal
     * @return String con toda la información del animal
     */
    public String getDescripcionCompleta() {
        return String.format(
                "Animal: %s%n" +
                        "Características: %s%n" +
                        "Elemento: %s%n" +
                        "Posición en el ciclo: %d de %d",
                nombre, caracteristicas, getElemento(),
                ordinal() + 1, values().length
        );
    }
}