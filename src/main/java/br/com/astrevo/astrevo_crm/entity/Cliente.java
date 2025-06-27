package br.com.astrevo.astrevo_crm.entity;

import br.com.astrevo.astrevo_crm.dto.AtualizarClienteDto;
import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String contato;
    private String email;
    private String telefone;
    private String documento;
    private TipoPessoa tipoPessoa;
    private String nomeEmpresa;
    private Status status;

    @Embedded
    private Endereco endereco;

    public Cliente(CadastrarClienteDto dto){
        this.contato = dto.contato();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.documento = dto.documento();
        this.tipoPessoa = dto.tipoPessoa();
        this.nomeEmpresa = dto.nomeEmpresa();
        this.status = dto.status();
        this.endereco = dto.endereco();
    }

    public void atualizarCliente(AtualizarClienteDto dto) {
        this.contato = dto.contato();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.documento = dto.documento();
        this.tipoPessoa = dto.tipoPessoa();
        this.nomeEmpresa = dto.nomeEmpresa();
        this.status = dto.status();
        this.endereco = dto.endereco();
    }

    public void atualizarParcialmente(AtualizarClienteDto dto) {
        if (dto.contato() != null) this.contato = dto.contato();
        if (dto.email() != null) this.email = dto.email();
        if (dto.telefone() != null) this.telefone = dto.telefone();
        if (dto.documento() != null) this.documento = dto.documento();
        if (dto.tipoPessoa() != null) this.tipoPessoa = dto.tipoPessoa();
        if (dto.nomeEmpresa() != null) this.nomeEmpresa = dto.nomeEmpresa();
        if (dto.status() != null) this.status = dto.status();
        if (dto.endereco() != null) this.endereco = dto.endereco();
    }

}
