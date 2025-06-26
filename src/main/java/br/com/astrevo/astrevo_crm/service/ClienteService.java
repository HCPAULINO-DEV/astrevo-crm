package br.com.astrevo.astrevo_crm.service;

import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import br.com.astrevo.astrevo_crm.dto.InformarClienteDto;
import br.com.astrevo.astrevo_crm.entity.Cliente;
import br.com.astrevo.astrevo_crm.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public InformarClienteDto cadastrarCliente(CadastrarClienteDto dto){
        Cliente cliente = new Cliente(dto);
        clienteRepository.save(cliente);

        return new InformarClienteDto(cliente);
    }

}
