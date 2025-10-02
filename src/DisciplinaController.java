
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class DisciplinaController {
    public void cadastrarDisciplina(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar uma nova disciplina: ");
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Ementa: ");
        String ementa = input.nextLine();
                
        Disciplina novaDisciplina = new Disciplina(getProximoIdDisciplina(conexao), nome, ementa);
        
        DisciplinaModel.create(novaDisciplina, conexao);
        System.out.println("Disciplina criada com sucesso!!");
    }
    
    public int getProximoIdDisciplina(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_disciplina) FROM disciplinas";
        
        int maxId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        
        return maxId + 1;
    }

    public void listarDisciplinas(Connection conexao) throws SQLException {
        LinkedHashSet all = DisciplinaModel.listAll(conexao);
        Iterator<Disciplina> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void removerDisciplina(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarDisciplinas(conexao);
        System.out.println("Informe o id da disciplina a remover: ");
        int n = input.nextInt();
        DisciplinaModel.remove(n, conexao);  
        System.out.println("Disciplina removida com sucesso!!");
    }

    public void alterarDisciplina(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarDisciplinas(conexao);
        
        System.out.println("Informe o id da disciplina a alterar: ");
        int id = input.nextInt();
        input.nextLine();
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Ementa: ");
        String ementa = input.nextLine();
        
        
        Disciplina disciplina = new Disciplina(id, nome, ementa);
        
        DisciplinaModel.update(disciplina, conexao);
        System.out.println("Disciplina atualizada com sucesso!!");
    }
}
