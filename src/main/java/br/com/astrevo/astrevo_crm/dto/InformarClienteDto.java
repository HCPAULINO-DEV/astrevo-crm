package br.com.astrevo.astrevo_crm.dto;

import br.com.astrevo.astrevo_crm.entity.Cliente;
import br.com.astrevo.astrevo_crm.entity.Endereco;
import br.com.astrevo.astrevo_crm.entity.Status;
import br.com.astrevo.astrevo_crm.entity.TipoPessoa;

public record InformarClienteDto(
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
    public InformarClienteDto(Cliente cliente){
        this(cliente.getId().toString(), cliente.getContato(), cliente.getEmail(), cliente.getTelefone(), cliente.getDocumento(), cliente.getTipoPessoa(), cliente.getNomeEmpresa(), cliente.getStatus(), cliente.getEndereco());
    }
}
