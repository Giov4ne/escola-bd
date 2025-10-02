
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;


public class AlunoModel {
    public static void create(Aluno aluno, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO alunos (id_aluno, nome, telefone, endereco, cpf, dt_nascimento) VALUES (?,?,?,?,?,?)");
            st.setInt(1, aluno.getId());
            st.setString(2, aluno.getNome());
            st.setString(3, aluno.getTelefone());
            st.setString(4, aluno.getEndereco());
            st.setString(5, aluno.getCpf());
            st.setDate(6, aluno.getDtNascimento());
            st.execute();
            st.close();  
    }
    
    static LinkedHashSet listAll(Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "SELECT id_aluno, nome, telefone, endereco, cpf, dt_nascimento, id_responsavel FROM alunos ORDER BY id_aluno";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Aluno(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getDate(6),
                        result.getInt(7)
                ));
            }
        return list;
    }
    
    static void remove(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM alunos WHERE id_aluno = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        st.execute();
        st.close();
    }

    static void update(Aluno aluno, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("UPDATE alunos SET nome=?, telefone=?, endereco=?, cpf=?, dt_nascimento=? WHERE id_aluno=?");
        st.setString(1, aluno.getNome());
        st.setString(2, aluno.getTelefone());
        st.setString(3, aluno.getEndereco());
        st.setString(4, aluno.getCpf());
        st.setDate(5, aluno.getDtNascimento());
        st.setInt(6, aluno.getId());
        st.execute();
        st.close();          
    }
    
    public static void assignResponsavel(int idAluno, int idResponsavel, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("UPDATE alunos SET id_responsavel=? WHERE id_aluno=?");
            st.setInt(1, idResponsavel);
            st.setInt(2, idAluno);
            st.execute();
            st.close();
    }
    
    public static void unassignResponsavel(int idAluno, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("UPDATE alunos SET id_responsavel=NULL WHERE id_aluno=?");
            st.setInt(1, idAluno);
            st.execute();
            st.close();
    }
}
