package br.com.astrevo.astrevo_crm.repository;

import br.com.astrevo.astrevo_crm.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
