package br.com.astrevo.astrevo_crm.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Endereco(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String pais
) {

}
