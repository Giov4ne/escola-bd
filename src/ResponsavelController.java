
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.time.format.DateTimeParseException;


public class ResponsavelController {
    public void cadastrarResponsavel(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Insira os seguintes dados para cadastrar um novo responsavel: ");
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print("Cpf: ");
        String cpf = input.nextLine();        
        
        Responsavel novoResp = new Responsavel(getProximoIdResponsavel(conexao), nome, telefone, cpf);
        
        ResponsavelModel.create(novoResp, conexao);
        System.out.println("Responsavel criado com sucesso!!");
    }
    
    public int getProximoIdResponsavel(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_responsavel) FROM responsaveis";
        
        int maxId = 0;

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maxId = rs.getInt(1);
            }
        }
        
        return maxId + 1;
    }

    public void listarResponsaveis(Connection conexao) throws SQLException {
        LinkedHashSet all = ResponsavelModel.listAll(conexao);
        Iterator<Responsavel> it = all.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }
    }

    public void removerResponsavel(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        listarResponsaveis(conexao);
        System.out.println("Informe o id do responsavel a remover: ");
        int n = input.nextInt();
        ResponsavelModel.remove(n, conexao);  
        System.out.println("Responsavel removido com sucesso!!");
    }

    public void alterarResponsavel(Connection conexao) throws SQLException {
        Scanner input = new Scanner(System.in);
        
        listarResponsaveis(conexao);
        
        System.out.println("Informe o id do responsavel a alterar: ");
        int id = input.nextInt();
        input.nextLine();
        
        System.out.print("Nome: ");
        String nome = input.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = input.nextLine();
        
        System.out.print("Cpf: ");
        String cpf = input.nextLine();
        
        Responsavel resp = new Responsavel(id, nome, telefone, cpf);
        
        ResponsavelModel.update(resp, conexao);
        System.out.println("Responsavel atualizado com sucesso!!");
    }
}
