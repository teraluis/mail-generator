package apim;

import javax.swing.*;

import jakarta.mail.MessagingException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmailUI extends JFrame {
    private JTextField apiField, abonementField, productField;
    private JButton startButton;
    private EmailConfigListener listener;

    public EmailUI(EmailConfigListener listener) {
        this.listener = listener;
        initUI();
    }

    private void initUI() {
        setTitle("Configuration de Mail");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout(4, 2));

        add(new JLabel("API:"));
        apiField = new JTextField();
        add(apiField);

        add(new JLabel("Abonnement:"));
        abonementField = new JTextField();
        add(abonementField);

        add(new JLabel("Produit:"));
        productField = new JTextField();
        add(productField);

        startButton = new JButton("Démarrer");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appel de la fonction pour démarrer le traitement avec les paramètres saisis
                try {
					startProcess(apiField.getText(), abonementField.getText(), productField.getText());
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
            }
        });
        add(startButton);
    }

    private void startProcess(String api, String abonement, String product) throws MessagingException {
        if (listener != null) {
            listener.onSubmit(api, abonement, product);
        }
    }
    
}