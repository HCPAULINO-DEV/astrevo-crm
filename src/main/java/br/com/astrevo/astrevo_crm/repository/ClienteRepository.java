package br.com.astrevo.astrevo_crm.repository;

import br.com.astrevo.astrevo_crm.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Boolean existsByDocumento(@NotBlank String documento);

    Boolean existsByDocumentoAndIdNot(String documento, UUID id);
}
