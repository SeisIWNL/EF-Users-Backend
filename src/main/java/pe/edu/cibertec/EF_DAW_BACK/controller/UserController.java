package pe.edu.cibertec.EF_DAW_BACK.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.EF_DAW_BACK.dto.LoginRequestDTO;
import pe.edu.cibertec.EF_DAW_BACK.dto.LoginResponseDTO;
import pe.edu.cibertec.EF_DAW_BACK.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/autenticar")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            String[] dataUser = userService.validarUser(loginRequest);
            System.out.println("Resultado: " + Arrays.toString(dataUser));
            if (dataUser == null || dataUser.length == 0) {
                return new LoginResponseDTO("NO","USER","FOUND","IN", "integrantes.txt","X.X");
            }
            return new LoginResponseDTO(dataUser[0],dataUser[1],dataUser[2],dataUser[3],dataUser[4],dataUser[5]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-integrantes")
    public List<LoginResponseDTO> list() {
        try {
            List<String[]> userList = userService.listarUsers();
            List<LoginResponseDTO> responseList = new ArrayList<>();

            for (String[] user : userList) {
                LoginResponseDTO userDto = new LoginResponseDTO(
                        user[0],
                        user[1],
                        user[2],
                        user[3],
                        user[4],
                        user[5]
                );
                responseList.add(userDto);
            }
            return responseList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
