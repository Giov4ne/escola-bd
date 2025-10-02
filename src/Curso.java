
public class Curso {
    private int id;
    private String nome;
    private String tipo;
    private String turno;

    public Curso(int id, String nome, String tipo, String turno) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.turno = turno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "id: " + id + ", nome: " + nome + ", tipo: " + tipo + ", turno: " + turno;
    }
}
