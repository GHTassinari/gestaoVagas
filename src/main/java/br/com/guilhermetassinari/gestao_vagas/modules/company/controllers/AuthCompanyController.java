package br.com.guilhermetassinari.gestao_vagas.modules.company.controllers;

import br.com.guilhermetassinari.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.guilhermetassinari.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthCompanyController {

    private final AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/company")
    public ResponseEntity<Object> create (@RequestBody AuthCompanyDTO authCompanyDTO) {
        try{
            var result =  this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(result);
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
