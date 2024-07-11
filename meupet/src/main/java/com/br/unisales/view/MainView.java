package com.br.unisales.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    public MainView() {
        initialize();
    }

    private void initialize() {
        setTitle("Gestão de Proprietários e Pets");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        getContentPane().add(panel);

        JLabel label = new JLabel("Escolha uma opção:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JButton btnCadastroProprietario = new JButton("Cadastro de Proprietário");
        btnCadastroProprietario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProprietarioView().setVisible(true);
            }
        });
        panel.add(btnCadastroProprietario);

        JButton btnCadastroPet = new JButton("Cadastro de Pet");
        btnCadastroPet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new PetView().setVisible(true);
            }
        });
        panel.add(btnCadastroPet);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }
}
