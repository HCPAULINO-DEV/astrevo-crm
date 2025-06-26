package br.com.astrevo.astrevo_crm.controller;

import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import br.com.astrevo.astrevo_crm.dto.InformarClienteDto;
import br.com.astrevo.astrevo_crm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<InformarClienteDto> cadastrar(CadastrarClienteDto dto, UriComponentsBuilder uriComponentsBuilder){
        var cliente = clienteService.cadastrarCliente(dto);
        var uri = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(cliente.id()).toUri();

        return ResponseEntity.created(uri).body(cliente);
    }
}
