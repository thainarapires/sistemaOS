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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.DAO;

public class PainelFluxoCaixa extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtDescricao;
    private JTextField txtValor;
    private JComboBox<String> cbTipo;
    private JButton btnSalvar;
    private JButton btnExcluir; // NOVO: Botão para excluir

    // Componentes para a Tabela de Consulta
    private JTable tabelaCaixa;
    private DefaultTableModel modeloTabela;
    private JScrollPane scrollTabela;

    private DAO dao = new DAO();
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    private NumberFormat moedaFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PainelFluxoCaixa() {
        // Mantemos o tamanho adequado para caber os componentes e a tabela
        setSize(600, 380);
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(null);

        // --- COMPONENTES DE CADASTRO ---
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblTipo.setBounds(10, 11, 60, 25);
        add(lblTipo);

        cbTipo = new JComboBox<>(new String[]{"SAÍDA", "ENTRADA"});
        cbTipo.setBounds(123, 11, 120, 25);
        add(cbTipo);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblDescricao.setBounds(10, 44, 80, 25);
        add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(123, 44, 467, 25);
        add(txtDescricao);

        JLabel lblValor = new JLabel("Valor (R$):");
        lblValor.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblValor.setBounds(10, 81, 80, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(123, 81, 120, 25);
        add(txtValor);

        btnSalvar = new JButton("Registrar");
        btnSalvar.setBounds(270, 83, 150, 30);
        btnSalvar.setBackground(new Color(0, 153, 76)); // Verde para Salvar
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnSalvar);

        // NOVO: Botão Excluir posicionado ao lado do salvar
        btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.setBounds(430, 83, 160, 30);
        btnExcluir.setBackground(new Color(217, 83, 79)); // Vermelho para Excluir
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnExcluir);

        // --- COMPONENTES DA TABELA DE CONSULTA ---
        // Adicionamos a coluna "ID" de forma oculta ou visível para sabermos qual registro deletar
        modeloTabela = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Data/Hora", "Tipo", "Descrição", "Valor"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaCaixa = new JTable(modeloTabela);
        tabelaCaixa.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Ajustando largura das colunas (deixando o ID bem pequenininho)
        tabelaCaixa.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tabelaCaixa.getColumnModel().getColumn(1).setPreferredWidth(110); // Data
        tabelaCaixa.getColumnModel().getColumn(2).setPreferredWidth(60);  // Tipo
        tabelaCaixa.getColumnModel().getColumn(3).setPreferredWidth(240); // Descrição
        tabelaCaixa.getColumnModel().getColumn(4).setPreferredWidth(90);  // Valor

        scrollTabela = new JScrollPane(tabelaCaixa);
        scrollTabela.setBounds(40, 126, 550, 139); 
        add(scrollTabela);

        // Evento do Botão Salvar
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarNoCaixa();
            }
        });

        // NOVO: Evento do Botão Excluir
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirDoCaixa();
            }
        });

        // Carrega o histórico ao iniciar
        preencherTabela();
    }

    private void registrarNoCaixa() {

        String sql = "INSERT INTO fluxo_caixa (tipo, descricao, valor) VALUES (?, ?, ?)";

        try {
            con = dao.conectar();
            pst = con.prepareStatement(sql);
            
            pst.setString(1, cbTipo.getSelectedItem().toString());
            pst.setString(2, txtDescricao.getText().trim());
            
            String valorFormatado = txtValor.getText().trim().replace(",", ".");
            pst.setDouble(3, Double.parseDouble(valorFormatado));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimentação registrada com sucesso!");
            
            txtDescricao.setText("");
            txtValor.setText("");
            cbTipo.setSelectedIndex(0);

            preencherTabela();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Digite um valor numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no caixa: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    /**
     * NOVO MÉTODO: Captura a linha selecionada na tabela e deleta do banco de dados
     */
    private void excluirDoCaixa() {
        // Captura os índices de todas as linhas que o usuário selecionou
        int[] linhasSelecionadas = tabelaCaixa.getSelectedRows();
        
        // Se o array estiver vazio, ninguém foi selecionado
        if (linhasSelecionadas.length == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma ou mais linhas na tabela para excluir.");
            return;
        }

        // Monta uma mensagem personalizada avisando a quantidade
        String mensagemAviso = linhasSelecionadas.length == 1 
                ? "Tem certeza que deseja apagar o lançamento selecionado?" 
                : "Tem certeza que deseja apagar os " + linhasSelecionadas.length + " lançamentos selecionados?";

        // Caixa de confirmação de segurança
        int resposta = JOptionPane.showConfirmDialog(null, 
                mensagemAviso, 
                "Confirmar Exclusão em Lote", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            // Usamos o operador SQL 'IN' para apagar todos os IDs de uma vez só da query
            String sql = "DELETE FROM fluxo_caixa WHERE id = ?";

            try {
                con = dao.conectar();
                // Desativa o auto-commit para fazer tudo como uma transação única e segura
                con.setAutoCommit(false); 
                pst = con.prepareStatement(sql);
                
                // Roda um laço apagando ID por ID que foi selecionado
                for (int i = 0; i < linhasSelecionadas.length; i++) {
                    // Convertemos o índice visual da tabela para o modelo de dados real
                    int linhaModelo = tabelaCaixa.convertRowIndexToModel(linhasSelecionadas[i]);
                    String idRegistro = modeloTabela.getValueAt(linhaModelo, 0).toString();
                    
                    pst.setInt(1, Integer.parseInt(idRegistro));
                    pst.addBatch(); // Adiciona no pacote de execução
                }
                
                // Executa todos de uma vez só no banco
                pst.executeBatch();
                con.commit(); // Confirma as alterações
                
                JOptionPane.showMessageDialog(null, "Lançamento(s) excluído(s) com sucesso!");
                
                // Recarrega a tabela e limpa a seleção
                preencherTabela();

            } catch (Exception e) {
                try { if (con != null) con.rollback(); } catch (Exception ex) { } // Desfaz se der erro
                JOptionPane.showMessageDialog(null, "Erro ao excluir registros: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * Busca os dados do banco e joga dentro do JTable incluindo o ID
     */
    public void preencherTabela() {
        modeloTabela.setRowCount(0);
        
        // Agora buscamos também a coluna 'id' do banco
        String sql = "SELECT id, data_movimentacao, tipo, descricao, valor FROM fluxo_caixa ORDER BY id DESC";

        try {
            con = dao.conectar();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String dataStr = dateFormat.format(rs.getTimestamp("data_movimentacao"));
                String tipo = rs.getString("tipo");
                String descricao = rs.getString("descricao");
                String valorStr = moedaFormat.format(rs.getDouble("valor"));

                // Adiciona o ID como primeira coluna para o método de exclusão funcionar
                modeloTabela.addRow(new Object[]{id, dataStr, tipo, descricao, valorStr});
            }

        } catch (Exception e) {
            System.out.println("Erro ao preencher tabela de fluxo de caixa: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}