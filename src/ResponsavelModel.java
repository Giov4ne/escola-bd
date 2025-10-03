
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;

public class ResponsavelModel {
    public static void create(Responsavel responsavel, Connection conn) throws SQLException {
        PreparedStatement st;
            st = conn.prepareStatement("INSERT INTO responsaveis (id_responsavel, nome, telefone, cpf) VALUES (?,?,?,?)");
            st.setInt(1, responsavel.getId());
            st.setString(2, responsavel.getNome());
            st.setString(3, responsavel.getTelefone());
            st.setString(4, responsavel.getCpf());
            st.execute();
            st.close();  
    }
    
    static LinkedHashSet listAll(Connection conn) throws SQLException {
        Statement st;
        LinkedHashSet list = new LinkedHashSet();
            st = conn.createStatement();
            String sql = "SELECT id_responsavel, nome, telefone, cpf FROM responsaveis ORDER BY id_responsavel";
            
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()) {
                list.add(new Responsavel(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                ));
            }
        return list;
    }
    
    static void remove(int id, Connection conn) throws SQLException {
        String sqlUnsetResponsavel = "UPDATE alunos SET id_responsavel = NULL WHERE id_responsavel = ?";
        String sqlDeleteResponsavel = "DELETE FROM responsaveis WHERE id_responsavel = ?";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stUnset = conn.prepareStatement(sqlUnsetResponsavel)) {
                stUnset.setInt(1, id);
                stUnset.executeUpdate();
            }

            try (PreparedStatement stDelete = conn.prepareStatement(sqlDeleteResponsavel)) {
                stDelete.setInt(1, id);
                stDelete.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    static void update(Responsavel responsavel, Connection con) throws SQLException {
        PreparedStatement st;
        st = con.prepareStatement("UPDATE responsaveis SET nome=?, telefone=?, cpf=? WHERE id_responsavel=?");
        st.setString(1, responsavel.getNome());
        st.setString(2, responsavel.getTelefone());
        st.setString(3, responsavel.getCpf());
        st.setInt(4, responsavel.getId());
        st.execute();
        st.close();          
    }
}
