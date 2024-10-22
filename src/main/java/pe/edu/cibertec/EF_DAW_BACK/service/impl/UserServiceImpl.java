package pe.edu.cibertec.EF_DAW_BACK.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.EF_DAW_BACK.dto.LoginRequestDTO;
import pe.edu.cibertec.EF_DAW_BACK.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ResourceLoader resourceLoader;

    private Resource resource;

    @Override
    public String[] validarUser(LoginRequestDTO loginRequestDTO) throws IOException {
        List<String[]> allUsers = leerUsuarios();

        for (String[] user : allUsers) {
            if (loginRequestDTO.userCode().equals(user[0]) && loginRequestDTO.password().equals(user[6])) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<String[]> listarUsers() throws IOException {
        List<String[]> allUsers = leerUsuarios();
        List<String[]> usersList = new ArrayList<>();

        for (String[] user : allUsers) {
            String[] filteredUser = new String[]{user[0], user[1], user[2], user[3], user[4], user[5]};
            usersList.add(filteredUser);
        }
        return usersList;
    }

    private List<String[]> leerUsuarios() throws IOException {
        if (resource == null) {
            resource = resourceLoader.getResource("classpath:integrantes.txt");
        }

        List<String[]> usuarios = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(resource.getFile().toURI()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                usuarios.add(data);
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo de integrantes.txt", e);
        }
        return usuarios;
    }
}
