package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PesquisarClientesOS extends JDialog {
	
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();

	private static final long serialVersionUID = 1L;
	private JLabel lblResultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PesquisarClientesOS dialog = new PesquisarClientesOS();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public PesquisarClientesOS() {
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 28));
		setBounds(100, 100, 1024, 765);
		getContentPane().setLayout(null);
		
		lblResultado = new JLabel("TODAS AS OS");
		lblResultado.setHorizontalAlignment(SwingConstants.LEFT);
		lblResultado.setBackground(Color.LIGHT_GRAY);
		lblResultado.setFont(new Font("Arial", Font.PLAIN, 18));
		lblResultado.setBounds(10, 55, 988, 660);
		getContentPane().add(lblResultado);
		
		JButton btnNewButton = new JButton("Nome do cliente:");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnNewButton.setBounds(299, 11, 191, 33);
		getContentPane().add(btnNewButton);

	}
	private void buscarOS() {
	    String nomeCliente = JOptionPane.showInputDialog("Nome do cliente:");

	    if (nomeCliente != null) {
	        // Consulta SQL para buscar as informações relevantes
	        String read = "SELECT servicos.os, servicos.dataOS, servicos.equipamento, servicos.marca, "
	                    + "servicos.modelo, servicos.serie, servicos.defeito, servicos.valor, servicos.idcli, "
	                    + "clientes.nome "
	                    + "FROM servicos "
	                    + "INNER JOIN clientes ON servicos.idcli = clientes.idcli "
	                    + "WHERE clientes.nome LIKE ?";

	        try {
	            con = dao.conectar();  // Conectar ao banco

	            pst = con.prepareStatement(read);
	            pst.setString(1, "%" + nomeCliente + "%");  // Usar % para busca parcial

	            rs = pst.executeQuery();

	            // StringBuilder para armazenar todas as OS encontradas
	            StringBuilder resultado = new StringBuilder();

	            // Se encontrar resultados
	            if (rs.isBeforeFirst()) {  // Verifica se há algum resultado
	                while (rs.next()) {
	                    // Concatenar as informações da OS em uma única string
	                    resultado.append("OS: ").append(rs.getString("os")).append("\n");
	                    resultado.append("Data: ").append(rs.getString("dataOS")).append("\n");
	                    resultado.append("Equipamento: ").append(rs.getString("equipamento")).append("\n");
	                    resultado.append("Marca: ").append(rs.getString("marca")).append("\n");
	                    resultado.append("Modelo: ").append(rs.getString("modelo")).append("\n");
	                    resultado.append("Série: ").append(rs.getString("serie")).append("\n");
	                    resultado.append("Defeito: ").append(rs.getString("defeito")).append("\n");
	                    resultado.append("Valor: ").append(rs.getString("valor")).append("\n");
	                    resultado.append("Cliente: ").append(rs.getString("nome")).append("\n\n");

	                    // Perguntar se deseja continuar visualizando a próxima OS
	                    int resposta = JOptionPane.showConfirmDialog(null, "Mostrar próxima OS?", "OS Encontrada",
	                            JOptionPane.YES_NO_OPTION);
	                    
	                    if (resposta == JOptionPane.NO_OPTION) {
	                        break;  // Sai do loop se o usuário não quiser continuar
	                    }
	                }

	                // Definir o texto concatenado no JLabel (exibindo tudo de uma vez)
	                lblResultado.setText("<html>" + resultado.toString().replace("\n", "<br>") + "</html>");

	            } else {
	                JOptionPane.showMessageDialog(null, "Nenhuma OS encontrada para esse cliente.");
	                lblResultado.setText("Nenhuma OS encontrada.");
	            }

	            con.close();  // Fechar a conexão

	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
	}
}
