
public class Professor extends Pessoa {
    private String endereco;
    private String especialidade;

    public Professor(int id, String nome, String telefone, String cpf, String endereco, String especialidade) {
        super(id, nome, telefone, cpf);
        this.endereco = endereco;
        this.especialidade = especialidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", endereco: " + endereco +
                ", especialidade: " + especialidade;
    }
}
