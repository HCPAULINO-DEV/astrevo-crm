package br.com.astrevo.astrevo_crm.controller;

import br.com.astrevo.astrevo_crm.dto.AtualizarClienteDto;
import br.com.astrevo.astrevo_crm.dto.CadastrarClienteDto;
import br.com.astrevo.astrevo_crm.dto.InformarClienteDto;
import br.com.astrevo.astrevo_crm.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<InformarClienteDto> cadastrarCliente(@RequestBody @Valid CadastrarClienteDto dto, UriComponentsBuilder uriComponentsBuilder){
        var cliente = clienteService.cadastrarCliente(dto);
        var uri = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(cliente.id()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping
    public ResponseEntity<Page<InformarClienteDto>> buscarClientes(@PageableDefault(sort = "id", size = 10) Pageable pageable){
        var clientes = clienteService.buscarClientes(pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformarClienteDto> buscarCliente(@PathVariable UUID id){
        var cliente = clienteService.buscarCliente(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InformarClienteDto> atualizarCliente(@PathVariable UUID id, @RequestBody @Valid AtualizarClienteDto dto){
        var cliente = clienteService.atualizarCliente(id, dto);
        return ResponseEntity.ok(cliente);
    }

}
