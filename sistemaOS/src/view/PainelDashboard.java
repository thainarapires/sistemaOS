package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import model.DAO;

public class PainelDashboard extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Componentes visuais para os valores
    private JLabel lblFaturamentoDia;
    private JLabel lblFaturamentoSemana;
    private JLabel lblFaturamentoMes;
    private JLabel lblLucroEstimado;
    
    // Panéis dos cards para podermos mudar a cor de fundo dinamicamente
    private JPanel cardDia;
    private JPanel cardSemana;
    private JPanel cardMes;
    private JPanel cardLucro;
    
    // Conexão com o Banco de Dados
    private DAO dao = new DAO();
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    
    // Formatador de Moeda (Real R$)
    private NumberFormat moedaFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public PainelDashboard() {
        // Aumentando o espaçamento interno (gap) entre os cards de 15 para 10 para economizar espaço lateral
        setLayout(new GridLayout(2, 4, 10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1), 
            "INDICADORES DE DESEMPENHO FINANCEIRO", 
            TitledBorder.LEFT, 
            TitledBorder.TOP, 
            new Font("Arial", Font.BOLD, 11), 
            Color.GRAY
        ));

        // Inicializa e adiciona os cards guardando a referência dos JPanels
        cardDia = criarCardContainer("FATURAMENTO DIA", new Color(0, 153, 76)); 
        lblFaturamentoDia = (JLabel) cardDia.getComponent(1);

        cardSemana = criarCardContainer("FATURAMENTO SEMANA", new Color(0, 102, 204)); 
        lblFaturamentoSemana = (JLabel) cardSemana.getComponent(1);

        cardMes = criarCardContainer("FATURAMENTO MÊS", new Color(153, 0, 153)); 
        lblFaturamentoMes = (JLabel) cardMes.getComponent(1);

        cardLucro = criarCardContainer("LUCRO REAL (MÊS)", new Color(230, 138, 0)); 
        lblLucroEstimado = (JLabel) cardLucro.getComponent(1);

        // Carrega os dados do banco
        atualizarDados();
    }

    /**
     * Cria o container do card e insere os labels de Título e Valor
     */
    private JPanel criarCardContainer(String titulo, Color corFundo) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(2, 1, 0, 2));
        card.setBackground(corFundo);
        // Padding interno reduzido para as laterais (8px) para evitar o corte por falta de espaço
        card.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 10)); // Fonte compactada para caber sem cortar
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblValor = new JLabel("R$ 0,00", SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 16)); // Reduzido de 20 para 16 para evitar o reticências (...)
        lblValor.setForeground(Color.WHITE);

        card.add(lblTitulo);
        card.add(lblValor);
        
        this.add(card);
        return card;
    }

    /**
     * Consulta o Banco de Dados para atualizar os indicadores.
     */
    public void atualizarDados() {
        try {
            con = dao.conectar();
            if (con == null) {
                lblFaturamentoDia.setText("R$ 0,00");
                lblFaturamentoSemana.setText("R$ 0,00");
                lblFaturamentoMes.setText("R$ 0,00");
                lblLucroEstimado.setText("R$ 0,00");
                return;
            }

            // 1. Faturamento do Dia (OS)
            String sqlDia = "SELECT SUM(valor) FROM servicos WHERE DATE(dataOS) = CURDATE()";
            pst = con.prepareStatement(sqlDia);
            rs = pst.executeQuery();
            if (rs.next()) {
                lblFaturamentoDia.setText(moedaFormat.format(rs.getDouble(1)));
            }
            rs.close();
            pst.close();

            // 2. Faturamento da Semana (OS)
            String sqlSemana = "SELECT SUM(valor) FROM servicos WHERE dataOS >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
            pst = con.prepareStatement(sqlSemana);
            rs = pst.executeQuery();
            if (rs.next()) {
                lblFaturamentoSemana.setText(moedaFormat.format(rs.getDouble(1)));
            }
            rs.close();
            pst.close();

            // 3. Faturamento do Mês Atual (Vindo das OS)
            String sqlMes = "SELECT SUM(valor) FROM servicos WHERE MONTH(dataOS) = MONTH(CURDATE()) AND YEAR(dataOS) = YEAR(CURDATE())";
            pst = con.prepareStatement(sqlMes);
            rs = pst.executeQuery();
            double faturamentoMesOS = 0;
            if (rs.next()) {
                faturamentoMesOS = rs.getDouble(1);
            }
            rs.close();
            pst.close();

            // 3b. NOVO: Busca Entradas Avulsas registradas no Fluxo de Caixa no mês atual
            String sqlCaixaEntradas = "SELECT SUM(valor) FROM fluxo_caixa WHERE tipo = 'ENTRADA' AND MONTH(data_movimentacao) = MONTH(CURDATE()) AND YEAR(data_movimentacao) = YEAR(CURDATE())";
            pst = con.prepareStatement(sqlCaixaEntradas);
            rs = pst.executeQuery();
            double totalEntradasCaixa = 0;
            if (rs.next()) {
                totalEntradasCaixa = rs.getDouble(1);
            }
            rs.close();
            pst.close();

            // O FATURAMENTO TOTAL DO MÊS é a soma das Ordens de Serviço + as Entradas Avulsas do Caixa
            double faturamentoTotalMes = faturamentoMesOS + totalEntradasCaixa;
            lblFaturamentoMes.setText(moedaFormat.format(faturamentoTotalMes));

            // 4. Custo Total das Peças das OS no Mês Atual
            String sqlCustoPecas = "SELECT SUM(custo_pecas) FROM servicos WHERE MONTH(dataOS) = MONTH(CURDATE()) AND YEAR(dataOS) = YEAR(CURDATE())";
            pst = con.prepareStatement(sqlCustoPecas);
            rs = pst.executeQuery();
            double totalCustoPecasMes = 0;
            if (rs.next()) {
                totalCustoPecasMes = rs.getDouble(1);
            }
            rs.close();
            pst.close();

            // 5. Busca todas as despesas variáveis (SAÍDAS) salvas no fluxo de caixa do mês atual
            String sqlCaixaSaidas = "SELECT SUM(valor) FROM fluxo_caixa WHERE tipo = 'SAÍDA' AND MONTH(data_movimentacao) = MONTH(CURDATE()) AND YEAR(data_movimentacao) = YEAR(CURDATE())";
            pst = con.prepareStatement(sqlCaixaSaidas);
            rs = pst.executeQuery();
            double totalSaidasCaixa = 0;
            if (rs.next()) {
                totalSaidasCaixa = rs.getDouble(1);
            }
            rs.close();
            pst.close();

            // Despesas Fixas Estruturais da Oficina Mapeadas (R$ 8.650,00)
            double despesasFixasMensais = 8000.00;
            
            // CÁLCULO DO LUCRO REAL LÍQUIDO PERFEITO:
            // (Faturamento OS + Entradas Avulsas) - Peças das OS - Custos Fixos - Saídas do Caixa
            double lucroCalculado = faturamentoTotalMes - totalCustoPecasMes - despesasFixasMensais - totalSaidasCaixa;

            if (faturamentoTotalMes == 0) {
                cardLucro.setBackground(new Color(230, 138, 0)); 
                lblLucroEstimado.setText("R$ 0,00");
            } else if (lucroCalculado < 0) {
                cardLucro.setBackground(new Color(217, 83, 79)); // Fundo vermelho se fechar no prejuízo
                lblLucroEstimado.setText(moedaFormat.format(lucroCalculado));
            } else {
                cardLucro.setBackground(new Color(40, 167, 69)); // Fundo verde se tiver lucro real limpo
                lblLucroEstimado.setText(moedaFormat.format(lucroCalculado));
            }

        } catch (Exception e) {
            System.out.println("Erro ao atualizar Dashboard: " + e.getMessage());
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