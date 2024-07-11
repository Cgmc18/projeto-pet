package com.br.unisales.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.br.unisales.controller.PetController;
import com.br.unisales.table.Pet;

public class PetView extends JFrame {
    private final PetController petController;
    private final DefaultTableModel tableModel;
    private final JTable table; 

    public PetView() {
        petController = new PetController();
        setTitle("Gerenciamento de Pets");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Peso", "Idade", "Sexo", "Especie", "Raça",  "Proprietário"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton updateButton = new JButton("Alterar");
        JButton listButton = new JButton("Listar");
        JButton deleteButton = new JButton("Excluir");
        JButton returnButton = new JButton("Voltar");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(returnButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adicionando o painel principal ao frame
        add(panel);

        // Ações dos botões
        addButton.addActionListener(e -> adicionarPet());
        updateButton.addActionListener(e -> alterarPet());
        listButton.addActionListener(e -> listarPets());
        deleteButton.addActionListener(e -> excluirPet());
        returnButton.addActionListener((e -> voltar()));

        // Exibir a janela
        setVisible(true);
    }

    private void voltar(){
        this.dispose();
        new MainView().setVisible(true);

    }

    private void adicionarPet() {
        Pet pet = obterDadosPet(null);
        if (pet != null) {
            petController.salvar(null, pet.getNome(), pet.getPeso(), pet.getIdade(), pet.getSexo() , pet.getEspecie() , pet.getRaca(), pet.getProprietario() , pet.getIdProprietario());
            listarPets();
        }
    }

    private void alterarPet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Pet existingPet = petController.buscarPorId(id);
            Pet pet = obterDadosPet(existingPet);
            if (pet != null) {
                petController.salvar(id, pet.getNome(), pet.getPeso(), pet.getIdade(), pet.getSexo() , pet.getEspecie() , pet.getRaca(), pet.getProprietario(), pet.getIdProprietario());
                listarPets();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pet para alterar.");
        }
    }

    private void listarPets() {
        tableModel.setRowCount(0);
        List<Pet> pets = petController.listar();
        for (Pet pet : pets) {
            tableModel.addRow(new Object[]{pet.getId(), pet.getNome(), pet.getPeso(), pet.getIdade(), pet.getSexo(), pet.getEspecie(), pet.getRaca(), pet.getProprietario(), pet.getIdProprietario()});
        }
    }

    private void excluirPet() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            petController.excluir(id);
            listarPets();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pet para excluir.");
        }
    }

    private Pet obterDadosPet(Pet pet) {
        JTextField nomeField = new JTextField(pet != null ? pet.getNome() : "");
        JTextField pesoField = new JTextField(pet != null ? String.valueOf(pet.getPeso()) : "");
        JTextField idadeField = new JTextField(pet != null ? String.valueOf(pet.getIdade()) : "");
        JTextField sexoField = new JTextField(pet != null ? pet.getSexo() : "");
        JTextField especieField = new JTextField(pet != null ? pet.getEspecie() : "");
        JTextField racaField = new JTextField(pet != null ? pet.getRaca() : "");
        JTextField proprietarioField = new JTextField(pet != null ? pet.getProprietario() : "");

        Object[] message = {
                "Nome:", nomeField,
                "Peso: ", pesoField,
                "Idade:", idadeField,
                "Sexo: ", sexoField,
                "Especie: ", especieField,
                "Raça:", racaField,
                "Proprietário:", proprietarioField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Dados do Pet", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                double peso = Double.parseDouble(pesoField.getText());
                int idade = Integer.parseInt(idadeField.getText());
                String sexo = sexoField.getText();
                String especie = especieField.getText();
                String raca = racaField.getText();
                String proprietario = proprietarioField.getText();
                return Pet.builder().nome(nome).peso(peso).idade(idade).sexo(sexo).especie(especie).raca(raca).proprietario(proprietario).build();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Dados inválidos.");
            }
        }
        return null;
    }
}

