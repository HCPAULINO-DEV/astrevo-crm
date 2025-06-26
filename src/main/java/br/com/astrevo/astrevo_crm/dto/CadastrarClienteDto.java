package br.com.astrevo.astrevo_crm.dto;

import br.com.astrevo.astrevo_crm.entity.Endereco;
import br.com.astrevo.astrevo_crm.entity.Status;
import br.com.astrevo.astrevo_crm.entity.TipoPessoa;

public record CadastrarClienteDto(
        String id,
        String contato,
        String email,
        String telefone,
        String documento,
        TipoPessoa tipoPessoa,
        String nomeEmpresa,
        Status status,
        Endereco endereco
) {
}
