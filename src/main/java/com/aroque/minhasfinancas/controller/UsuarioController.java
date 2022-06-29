package com.aroque.minhasfinancas.controller;

import com.aroque.minhasfinancas.dto.UsuarioDto;
import com.aroque.minhasfinancas.exception.ErroAutentificacao;
import com.aroque.minhasfinancas.exception.RegraNegocioExecption;
import com.aroque.minhasfinancas.model.UsuarioModel;
import com.aroque.minhasfinancas.service.LancamentoService;
import com.aroque.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    LancamentoService lancamentoService;

    public UsuarioController(UsuarioService service, LancamentoService lancamentoService){
        this.service = service;
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity salvar( @RequestBody UsuarioDto dto){
        UsuarioModel usuario = UsuarioModel.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

        try{
            UsuarioModel usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch(RegraNegocioExecption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDto dto){
        try {
            UsuarioModel usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return new ResponseEntity(usuarioAutenticado, HttpStatus.OK);
        } catch (ErroAutentificacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable(value = "id") Long id){
        Optional<UsuarioModel> usuario = service.obterPorId(id);
        if(!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }

}
