package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

@SuppressWarnings("unused")
public class Sobre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		getContentPane().setBackground(Color.WHITE);
		setModal(true);
		setTitle("Sobre");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/about.png")));
		setBounds(100, 100, 430, 373);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblAutora = new JLabel("AUTOR:");
		lblAutora.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutora.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAutora.setBounds(12, 295, 428, 14);
		getContentPane().add(lblAutora);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(12, 231, 128, 103);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblThainara = new JLabel("Thainara Pires");
		lblThainara.setHorizontalAlignment(SwingConstants.CENTER);
		lblThainara.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblThainara.setBounds(12, 307, 428, 14);
		getContentPane().add(lblThainara);
		
		JLabel lblVersao = new JLabel("VERSÃO: 1.0 BETA");
		lblVersao.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersao.setFont(new Font("Arial", Font.PLAIN, 10));
		lblVersao.setBounds(0, 148, 428, 14);
		getContentPane().add(lblVersao);
		
		JLabel txtMIT = new JLabel("Sistema para gestão de serviços e controle de estoque da:");
		txtMIT.setHorizontalAlignment(SwingConstants.CENTER);
		txtMIT.setFont(new Font("Dialog", Font.BOLD, 11));
		txtMIT.setBounds(0, 109, 420, 14);
		getContentPane().add(txtMIT);
		
		JLabel lblNewLabel = new JLabel("SP Assistência TV");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel.setBounds(0, 124, 420, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 try {
					 JOptionPane.showMessageDialog(null, "Voce será encaminhado para o repositório do projeto no GitHub.");
					 URI uri = new URI("https://github.com/thainarapires/sistemaos");
					 Desktop.getDesktop().browse(uri);
			        }catch(Exception erro){
			            System.out.println(erro); 
			        }
				}
			});
		btnNewButton.setBorderPainted(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnNewButton.setBounds(330, 257, 64, 64);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon(Sobre.class.getResource("/img/LogoSP.png")));
		lblNewLabel_1.setBounds(0, 11, 428, 98);
		getContentPane().add(lblNewLabel_1);
		
	
	}
	/**
	 * Metodo responsavel por: encaminhar o usuario a um link quando clicar no botao.
	 */
	private void link(String site) { 
		 Desktop desktop = Desktop.getDesktop();
		 try {
			 JOptionPane.showMessageDialog(null, "Voce será encaminhado para o repositório do projeto no GitHub.");
			 URI uri = new URI("https://github.com/thainarapires/sistemaOS");
			 desktop.browse(uri);
			
		} catch (Exception e) {
			
		
		}
		 }
	 }


