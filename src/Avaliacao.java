
public class Avaliacao {
    private int id;
    private String descricao;
    private String data;
    private double nota;
    private int idAlunoTurma;

    public Avaliacao(int id, String descricao, String data, double nota, int idAlunoTurma) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.nota = nota;
        this.idAlunoTurma = idAlunoTurma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getIdAlunoTurma() {
        return idAlunoTurma;
    }

    public void setIdAlunoTurma(int idAlunoTurma) {
        this.idAlunoTurma = idAlunoTurma;
    }

    @Override
    public String toString() {
        // TODO: buscar nome do aluno no último campo
        return "id: " + id + 
                ", descricao: " + descricao + 
                ", data: " + data + 
                ", nota: " + nota + 
                ", idAlunoTurma: " + idAlunoTurma;
    }
}
