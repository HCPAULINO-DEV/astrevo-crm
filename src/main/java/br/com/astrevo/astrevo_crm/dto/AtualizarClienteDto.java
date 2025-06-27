package br.com.astrevo.astrevo_crm.dto;

import br.com.astrevo.astrevo_crm.entity.Endereco;
import br.com.astrevo.astrevo_crm.entity.Status;
import br.com.astrevo.astrevo_crm.entity.TipoPessoa;
import jakarta.validation.constraints.Email;

public record AtualizarClienteDto(
        String contato,

        @Email
        String email,

        String telefone,
        String documento,
        TipoPessoa tipoPessoa,
        String nomeEmpresa,
        Status status,
        Endereco endereco
) {

}
