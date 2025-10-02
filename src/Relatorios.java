
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Relatorios {

    
    public void mediaNotasPorAlunoDisciplina(Connection conexao) throws SQLException {
        String sql = "SELECT a.nome AS aluno, d.nome AS disciplina, AVG(av.nota) AS media " +
                     "FROM avaliacoes av " +
                     "INNER JOIN alunos_turmas at ON av.id_aluno_turma = at.id_aluno_turma " +
                     "INNER JOIN alunos a ON at.id_aluno = a.id_aluno " +
                     "INNER JOIN turmas t ON at.id_turma = t.id_turma " +
                     "INNER JOIN disciplinas d ON t.id_disciplina = d.id_disciplina " +
                     "GROUP BY a.nome, d.nome " +
                     "ORDER BY a.nome, d.nome";

        System.out.println("\n--- Media de Notas por Aluno em cada Disciplina ---");
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("Aluno: %s, Disciplina: %s, Media: %.2f\n",
                        rs.getString("aluno"),
                        rs.getString("disciplina"),
                        rs.getDouble("media"));
            }
        }
    }

    public void disciplinasPorCurso(Connection conexao) throws SQLException {
        String sql = "SELECT c.nome AS curso, d.nome AS disciplina, cd.obrigatoriedade_disciplina AS obrigatoria, cd.carga_horaria_disciplina AS carga_horaria " +
                     "FROM cursos_disciplinas cd " +
                     "INNER JOIN cursos c ON cd.id_curso = c.id_curso " +
                     "INNER JOIN disciplinas d ON cd.id_disciplina = d.id_disciplina " +
                     "ORDER BY c.nome, d.nome";
        
        System.out.println("\n--- Disciplinas por Curso ---");
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String obrigatoriedade = rs.getBoolean("obrigatoria") ? "Sim" : "Não";
                System.out.printf("Curso: %s, Disciplina: %s, Obrigatoria: %b, Duracao (h): %d\n",
                        rs.getString("curso"),
                        rs.getString("disciplina"),
                        obrigatoriedade,
                        rs.getInt("carga_horaria"));
            }
        }
    }

    public void quantidadeAlunosPorCurso(Connection conexao) throws SQLException {
        String sql = "SELECT c.nome AS curso, COUNT(ac.id_aluno) AS quantidade_alunos " +
                     "FROM alunos_cursos ac " +
                     "INNER JOIN cursos c ON ac.id_curso = c.id_curso " +
                     "GROUP BY c.nome " +
                     "ORDER BY c.nome";
        
        System.out.println("\n--- Quantidade de Alunos por Curso ---");
        try (Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("Curso: %s, Quantidade de Alunos: %d\n",
                        rs.getString("curso"),
                        rs.getInt("quantidade_alunos"));
            }
        }
    }
}