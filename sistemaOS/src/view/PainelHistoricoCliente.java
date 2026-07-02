package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import model.DAO;

public class PainelHistoricoCliente extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtBusca;
    private JButton btnBuscar;
    
    private JTable tabelaHistorico;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollTabela;

    private DAO dao = new DAO();
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    private NumberFormat moedaFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PainelHistoricoCliente() {
        setSize(485, 577);
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1), 
            "HISTÓRICO COMPLETO DO CLIENTE (ORDENS DE SERVIÇO)", 
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 12), Color.GRAY
        ));

        // --- COMPONENTES DE BUSCA ---
        JLabel lblBusca = new JLabel("Nome ou CPF:");
        lblBusca.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblBusca.setBounds(20, 30, 150, 25);
        add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(110, 29, 140, 25);
        add(txtBusca);
        txtBusca.setToolTipText("Digite o nome completo, parte do nome ou o CPF do cliente");

        btnBuscar = new JButton("Buscar Histórico");
        btnBuscar.setBounds(260, 27, 154, 28);
        btnBuscar.setBackground(new Color(0, 102, 204));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnBuscar);

        // --- TABELA DE HISTÓRICO ---
        modeloTabela = new DefaultTableModel(
            new Object[][]{},
            new String[]{"OS", "Data", "Cliente", "Equipamento / Defeito", "Peças", "Valor"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição direta na tabela
            }
        };

        tabelaHistorico = new JTable(modeloTabela);
        tabelaHistorico.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaHistorico.setForeground(Color.BLACK);
        
        // Ajustando tamanho das colunas para os dados da oficina
        tabelaHistorico.getColumnModel().getColumn(0).setPreferredWidth(35);  // Nº OS
        tabelaHistorico.getColumnModel().getColumn(1).setPreferredWidth(125);  // Data
        tabelaHistorico.getColumnModel().getColumn(2).setPreferredWidth(115); // Cliente
        tabelaHistorico.getColumnModel().getColumn(3).setPreferredWidth(175); // Equipamento/Defeito
        tabelaHistorico.getColumnModel().getColumn(4).setPreferredWidth(115); // Peças
        tabelaHistorico.getColumnModel().getColumn(5).setPreferredWidth(85);  // Valor Total

        scrollTabela = new JScrollPane(tabelaHistorico);
        scrollTabela.setBounds(20, 75, 444, 462);
        add(scrollTabela);

        // Ação do Botão Buscar
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarHistoricoNoBanco();
            }
        });
        
        // Permite pressionar "Enter" no campo de texto para também realizar a busca
        txtBusca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarHistoricoNoBanco();
            }
        });
    }

    private void buscarHistoricoNoBanco() {
        String filtro = txtBusca.getText().trim();
        
        if (filtro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, digite o Nome ou CPF do cliente para pesquisar.");
            return;
        }

        modeloTabela.setRowCount(0); // Limpa resultados anteriores da tabela visual

        // QUERY PERFEITA: Cruza 'servicos' (s) com 'clientes' (c) usando 'idcli'
        String sql = "SELECT s.os, s.dataOS, c.nome, s.equipamento, s.marca, s.modelo, s.material, s.valor "
                   + "FROM servicos s "
                   + "INNER JOIN clientes c ON s.idcli = c.idcli "
                   + "WHERE c.nome LIKE ? OR c.cpf = ? "
                   + "ORDER BY s.os DESC";

        try {
            con = dao.conectar();
            pst = con.prepareStatement(sql);
            
            pst.setString(1, "%" + filtro + "%"); // Busca por qualquer parte do nome
            pst.setString(2, filtro);             // Busca pelo CPF exato (11 dígitos, sem pontos/traços)
            
            rs = pst.executeQuery();

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                
                // Pegando os dados usando os nomes exatos das suas colunas do banco
                String numeroOS = rs.getString("s.os");
                String dataStr = dateFormat.format(rs.getTimestamp("s.dataOS"));
                String nomeCliente = rs.getString("c.nome");
                
                // Junta Marca + Modelo + Equipamento para ficar bem descritivo na tabela
                String aparelho = rs.getString("s.marca") + " " + rs.getString("s.modelo") + " (" + rs.getString("s.equipamento") + ")";
                
                String materialUsado = rs.getString("s.material");
                String valorTotal = moedaFormat.format(rs.getDouble("s.valor"));

                // Se o campo material estiver em branco, joga um aviso limpo
                if (materialUsado == null || materialUsado.trim().isEmpty()) {
                    materialUsado = "Nenhum material/peça utilizado";
                }

                // Adiciona a linha estruturada no JTable
                modeloTabela.addRow(new Object[]{numeroOS, dataStr, nomeCliente, aparelho, materialUsado, valorTotal});
            }

            if (!encontrou) {
                JOptionPane.showMessageDialog(null, "Nenhum histórico de Ordens de Serviço localizado para: " + filtro);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar histórico no banco: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println("Erro ao fechar conexões: " + e.getMessage());
            }
        }
    }
}