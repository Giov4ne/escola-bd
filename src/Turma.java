
public class Turma {
    private int id;
    private Integer idProfessor;
    private int idDisciplina;
    private String codigo;
    private String sala;

    public Turma(int id, int idDisciplina, String codigo, String sala) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.codigo = codigo;
        this.sala = sala;
    }

    public Turma(int id, int idDisciplina, String codigo, String sala, Integer idProfessor) {
        this.id = id;
        this.idDisciplina = idDisciplina;
        this.codigo = codigo;
        this.sala = sala;
        this.idProfessor = idProfessor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "id: " + id + 
                ", idDisciplina: " + idDisciplina + 
                ", codigo: " + codigo + 
                ", sala: " + sala +
                ", idProfessor: " + (idProfessor != null ? idProfessor : "Não informado");
    }
    
    
}
