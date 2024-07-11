package com.br.unisales.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.br.unisales.controller.ProprietarioController;
import com.br.unisales.table.Proprietario;

public class ProprietarioView extends JFrame {

    private final ProprietarioController controller;
    private JTextField txtId, txtNome, txtSexo, txtCpf, txtEmail, txtCelular;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProprietarioView() {
        controller = new ProprietarioController();
        initialize();
    }

    private void initialize() {
        setTitle("Gestão de Proprietários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        // Formulário
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        formPanel.add(txtId);

        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("Sexo:"));
        txtSexo = new JTextField();
        formPanel.add(txtSexo);

        formPanel.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        formPanel.add(txtCpf);

        formPanel.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Celular:"));
        txtCelular = new JTextField();
        formPanel.add(txtCelular);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarProprietario();
            }
        });
        formPanel.add(btnSave);

        JButton btnUpdate = new JButton("Alterar");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarProprietario();
            }
        });
        formPanel.add(btnUpdate);

        panel.add(formPanel, BorderLayout.NORTH);

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Sexo", "CPF", "E-mail", "Celular"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel();

        JButton btnDelete = new JButton("Excluir");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirProprietario();
            }
        });
        buttonPanel.add(btnDelete);

        JButton btnList = new JButton("Listar");
        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProprietarios();
            }
        });
        buttonPanel.add(btnList);

        JButton returnButton = new JButton("Voltar");
        returnButton.addActionListener((e -> voltar()));

        buttonPanel.add(returnButton);



        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void salvarProprietario() {
        String nome = txtNome.getText();
        String sexo = txtSexo.getText();
        String cpf = txtCpf.getText();
        String email = txtEmail.getText();
        String celular = txtCelular.getText();

        Proprietario novoProprietario = controller.salvar(null, nome, sexo, cpf, email, celular);
        if (novoProprietario != null) {
            JOptionPane.showMessageDialog(this, "Proprietário salvo com sucesso!");
            listarProprietarios();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar proprietário. Verifique os dados informados.");
        }
    }

    private void alterarProprietario() {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String nome = txtNome.getText();
                String sexo = txtSexo.getText();
                String cpf = txtCpf.getText();
                String email = txtEmail.getText();
                String celular = txtCelular.getText();
                Boolean vazio = true;
                if (nome.isBlank() && sexo.isBlank() && cpf.isBlank() && email.isBlank() && celular.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Nenhuma alteração identificada.");
                    listarProprietarios();
                    limparCampos();
                } else {
                    vazio = false;
                }
                if (nome.isBlank()) {
                    nome = (String) tableModel.getValueAt(selectedRow, 1);
                }
                if (sexo.isBlank()) {
                    sexo = (String) tableModel.getValueAt(selectedRow, 2);
                }
                if (cpf.isBlank()) {
                    cpf = (String) tableModel.getValueAt(selectedRow, 3);
                }
                if (email.isBlank()) {
                    email = (String) tableModel.getValueAt(selectedRow, 4);
                }
                if (celular.isBlank()) {
                    celular = (String) tableModel.getValueAt(selectedRow, 5);
                }
                if (!vazio) {
                    Proprietario proprietarioAtualizado = controller.salvar(id, nome, sexo, cpf, email, celular);

                    if (proprietarioAtualizado != null) {
                        JOptionPane.showMessageDialog(this, "Proprietário alterado com sucesso!");
                        listarProprietarios();
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao alterar proprietário. Verifique os dados informados.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void voltar(){
        this.dispose();
        new MainView().setVisible(true);

    }

    private void excluirProprietario() {
        try {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um proprietário para excluir.");
                return;
            }

            int id = (int) tableModel.getValueAt(row, 0);
            String resultadoExcluir = controller.excluir(id);
            if (resultadoExcluir.equals("excluido")) {
                JOptionPane.showMessageDialog(this, "Proprietário excluído com sucesso!");
                listarProprietarios();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir proprietário.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir proprietário: " + e.getMessage());
        }
    }

    private void listarProprietarios() {
        List<Proprietario> lista = controller.listar();
        tableModel.setRowCount(0);
        for (Proprietario proprietario : lista) {
            tableModel.addRow(new Object[]{
                proprietario.getId(),
                proprietario.getNome(),
                proprietario.getSexo(),
                proprietario.getCpf(),
                proprietario.getEmail(),
                proprietario.getCelular()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtSexo.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
    }

    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             new ProprietarioView().setVisible(true);
    //         }
    //     });
    // }
}
