
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;


public class ProfessorModel {
    public static void create(Professor professor, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO professores (id_professor, nome, telefone, endereco, cpf, especialidade) VALUES (?,?,?,?,?,?)");
            st.setInt(1, professor.getId());
            st.setString(2, professor.getNome());
            st.setString(3, professor.getTelefone());
            st.setString(4, professor.getEndereco());
            st.setString(5, professor.getCpf());
            st.setString(6, professor.getEspecialidade());
            st.execute();
            st.close();  
    }
    
    static LinkedHashSet listAll(Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "SELECT id_professor, nome, telefone, endereco, cpf, especialidade FROM professores ORDER BY id_professor";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Professor(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
                ));
            }
        return list;
    }
    
    static void remove(int id, Connection conn) throws SQLException {
        String sql = "DELETE FROM professores WHERE id_professor = ?";
        PreparedStatement st = conn.prepareStatement(sql);
        st.setInt(1, id);
        st.execute();
        st.close();
    }
    
    static void update(Professor professor, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("UPDATE professores SET nome=?, telefone=?, endereco=?, cpf=?, especialidade=? WHERE id_professor=?");
        st.setString(1, professor.getNome());
        st.setString(2, professor.getTelefone());
        st.setString(3, professor.getEndereco());
        st.setString(4, professor.getCpf());
        st.setString(5, professor.getEspecialidade());
        st.setInt(6, professor.getId());
        st.execute();
        st.close();          
    }
}
