package br.com.astrevo.astrevo_crm.service;

import br.com.astrevo.astrevo_crm.dto.AtualizarClienteDto;
import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import br.com.astrevo.astrevo_crm.dto.InformarClienteDto;
import br.com.astrevo.astrevo_crm.entity.Cliente;
import br.com.astrevo.astrevo_crm.entity.Status;
import br.com.astrevo.astrevo_crm.infra.exception.ClienteNaoEncontradoException;
import br.com.astrevo.astrevo_crm.infra.exception.DeletarClienteComStatusInativoException;
import br.com.astrevo.astrevo_crm.infra.exception.DocumentoExistenteException;
import br.com.astrevo.astrevo_crm.repository.ClienteRepository;
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
        if (clienteRepository.existsByDocumento(dto.documento())){
            throw new DocumentoExistenteException("Documenta já cadastrado");
        }
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
        if (dto.documento() != null &&
                clienteRepository.existsByDocumentoAndIdNot(dto.documento(), id)) {
            throw new DocumentoExistenteException("Documento já cadastrado por outro cliente");
        }

        Cliente cliente = buscarClienteEntity(id);
        cliente.atualizarCliente(dto);
        clienteRepository.save(cliente);
        return new InformarClienteDto(cliente);
    }

    public InformarClienteDto atualizarParcialmenteCliente(UUID id, AtualizarClienteDto dto) {
        if (dto.documento() != null &&
                clienteRepository.existsByDocumentoAndIdNot(dto.documento(), id)) {
            throw new DocumentoExistenteException("Documento já cadastrado por outro cliente");
        }

        Cliente cliente = buscarClienteEntity(id);
        cliente.atualizarParcialmente(dto);
        clienteRepository.save(cliente);
        return new InformarClienteDto(cliente);
    }

    public void deletarCliente(UUID id) {
        Cliente cliente = buscarClienteEntity(id);
        if (cliente.getStatus() != Status.INATIVO){
            throw new DeletarClienteComStatusInativoException("Para deletar um cliente ele deve ter o satus INATIVO");
        }
        clienteRepository.delete(cliente);
    }

    //MÉTODOS AUXILIARES
    private Cliente buscarClienteEntity(UUID id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
    }
}
