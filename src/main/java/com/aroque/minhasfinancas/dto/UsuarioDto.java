package com.aroque.minhasfinancas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {

    private String nome;
    private String email;
    private String senha;

}
