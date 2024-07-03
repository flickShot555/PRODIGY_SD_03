package com.example.prodigy_sd_03;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Contact {
    String name;
    String phoneNumber;
    String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}

public class ContactManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Contact> contacts;
    private File file;

    public ContactManager() {

            file = new File("contacts.txt");
            contacts = new ArrayList<>();

            tableModel = new DefaultTableModel(new String[]{"Name", "Phone Number", "Email"}, 0);
            table = new JTable(tableModel);

            loadContacts();

        /*tableModel = new DefaultTableModel(new String[]{"Name", "Phone Number", "Email"}, 0);
        table = new JTable(tableModel);*/

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new AddContactListener());

        JButton viewButton = new JButton("View Contacts");
        viewButton.addActionListener(new ViewContactsListener());

        JButton editButton = new JButton("Edit Contact");
        editButton.addActionListener(new EditContactListener());

        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.addActionListener(new DeleteContactListener());

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(viewButton);
        panel.add(editButton);
        panel.add(deleteButton);

        JScrollPane scrollPane = new JScrollPane(table);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(",");
                Contact contact = new Contact(parts[0], parts[1], parts[2]);
                contacts.add(contact);
                tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Contact contact : contacts) {
                writer.write(contact.name + "," + contact.phoneNumber + "," + contact.email + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AddContactListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Enter name:");
            String phoneNumber = JOptionPane.showInputDialog("Enter phone number:");
            String email = JOptionPane.showInputDialog("Enter email:");

            Contact contact = new Contact(name, phoneNumber, email);
            contacts.add(contact);
            tableModel.addRow(new Object[]{name, phoneNumber, email});

            saveContacts();
        }
    }

    private class ViewContactsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String contactsString = "";
            for (Contact contact : contacts) {
                contactsString += "Name: " + contact.name + ", Phone Number: " + contact.phoneNumber + ", Email: " + contact.email + "\n";
            }
            JOptionPane.showMessageDialog(null, contactsString);
        }
    }

    private class EditContactListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row!= -1) {
                String name = JOptionPane.showInputDialog("Enter new name:");
                String phoneNumber = JOptionPane.showInputDialog("Enter new phone number:");
                String email = JOptionPane.showInputDialog("Enter new email:");

                contacts.get(row).name = name;
                contacts.get(row).phoneNumber = phoneNumber;
                contacts.get(row).email = email;

                tableModel.setValueAt(name, row, 0);
                tableModel.setValueAt(phoneNumber, row, 1);
                tableModel.setValueAt(email, row, 2);

                saveContacts();
            }
        }
    }

    private class DeleteContactListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if (row!= -1) {
                contacts.remove(row);
                tableModel.removeRow(row);

                saveContacts();
            }
        }
    }

    public static void main(String[] args) {
        new ContactManager();
    }
}