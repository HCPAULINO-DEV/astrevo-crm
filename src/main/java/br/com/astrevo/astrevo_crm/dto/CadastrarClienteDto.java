package br.com.astrevo.astrevo_crm.dto;

import br.com.astrevo.astrevo_crm.entity.Endereco;
import br.com.astrevo.astrevo_crm.entity.Status;
import br.com.astrevo.astrevo_crm.entity.TipoPessoa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarClienteDto(
        @NotBlank
        String contato,

        @Email
        @NotNull
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        String documento,

        @NotNull
        TipoPessoa tipoPessoa,

        @NotBlank
        String nomeEmpresa,

        @NotNull
        Status status,

        @NotNull
        Endereco endereco
) {
}
