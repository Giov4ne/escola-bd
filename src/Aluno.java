
import java.sql.Date;


public class Aluno extends Pessoa {
    private String endereco;
    private Date dtNascimento;
    private int idResponsavel;

    public Aluno(int id, String nome, String telefone, String cpf, String endereco, Date dtNascimento) {
        super(id, nome, telefone, cpf);
        this.endereco = endereco;
        this.dtNascimento = dtNascimento;
    }

    public Aluno(int id, String nome, String telefone, String cpf, String endereco, Date dtNascimento, int idResponsavel) {
        super(id, nome, telefone, cpf);
        this.endereco = endereco;
        this.dtNascimento = dtNascimento;
        this.idResponsavel = idResponsavel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", endereco: " + endereco +
                ", dt_nascimento: " + dtNascimento +
                (idResponsavel != 0 ? ", id_responsavel: " + idResponsavel : "");
    }
}
