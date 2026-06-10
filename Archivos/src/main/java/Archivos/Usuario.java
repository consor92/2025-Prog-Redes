import java.util.List;

public class Usuario {
    private String nombre;
    private int edad;
    private boolean activo;
    private List<String> roles; // Array en JSON -> List en Java

    // Constructor
    public Usuario(String nombre, int edad, boolean activo, List<String> roles) {
        this.nombre = nombre;
        this.edad = edad;
        this.activo = activo;
        this.roles = roles;
    }

    // Getters (para poder mostrar los datos al leerlos)
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public boolean isActivo() { return activo; }
    public List<String> getRoles() { return roles; }
}