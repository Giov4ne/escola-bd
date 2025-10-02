
public class Historico {
    private int idAlunoTurma;
    private int numFaltas;
    private String situacao;

    public Historico(int idAlunoTurma, int numFaltas, String situacao) {
        this.idAlunoTurma = idAlunoTurma;
        this.numFaltas = numFaltas;
        this.situacao = situacao;
    }
    
    public Historico(int idAlunoTurma) {
        this.idAlunoTurma = idAlunoTurma;
        this.numFaltas = 0;
        this.situacao = "Cursando";
    }

    public int getIdAlunoTurma() {
        return idAlunoTurma;
    }

    public void setIdAlunoTurma(int idAlunoTurma) {
        this.idAlunoTurma = idAlunoTurma;
    }

    public int getNumFaltas() {
        return numFaltas;
    }

    public void setNumFaltas(int numFaltas) {
        this.numFaltas = numFaltas;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "idAlunoTurma: " + idAlunoTurma + ", numFaltas: " + numFaltas + ", situacao: " + situacao;
    }  
}
