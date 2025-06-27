package br.com.astrevo.astrevo_crm.service;

import br.com.astrevo.astrevo_crm.dto.AtualizarClienteDto;
import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import br.com.astrevo.astrevo_crm.dto.InformarClienteDto;
import br.com.astrevo.astrevo_crm.entity.Cliente;
import br.com.astrevo.astrevo_crm.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public InformarClienteDto cadastrarCliente(CadastrarClienteDto dto){
        Cliente cliente = new Cliente(dto);
        clienteRepository.save(cliente);
        return new InformarClienteDto(cliente);
    }

    public Page<InformarClienteDto> buscarClientes(Pageable pageable) {
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return clientes.map(InformarClienteDto::new);
    }

    public InformarClienteDto buscarCliente(UUID id) {
        return new InformarClienteDto(buscarClienteEntity(id));
    }

    public InformarClienteDto atualizarCliente(UUID id, AtualizarClienteDto dto) {
        Cliente cliente = buscarClienteEntity(id);
        cliente.atualizarCliente(dto);
        clienteRepository.save(cliente);
        return new InformarClienteDto(cliente);
    }

    public InformarClienteDto atualizarParcialmenteCliente(UUID id, AtualizarClienteDto dto) {
        Cliente cliente = buscarClienteEntity(id);
        cliente.atualizarParcialmente(dto);
        clienteRepository.save(cliente);
        return new InformarClienteDto(cliente);
    }

    public void deletarCliente(UUID id) {
        Cliente cliente = buscarClienteEntity(id);
        clienteRepository.delete(cliente);
    }

    //MÉTODOS AUXILIARES
    private Cliente buscarClienteEntity(UUID id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
}
