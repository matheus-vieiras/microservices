package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDto> getPagamentosList(@PageableDefault(size = 10) Pageable pageable) {
        return pagamentoService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> getPagamentoById(@PathVariable @NotNull Long id){
        PagamentoDto dto = pagamentoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> create(@Valid @RequestBody PagamentoDto pagamentoDto, UriComponentsBuilder uriBuilder) {
        pagamentoService.insertPagamento(pagamentoDto);
        URI endereco = uriBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDto.getId()).toUri();
        return ResponseEntity.created(endereco).body(pagamentoDto);
    }

    @PutMapping("/pagamentos/{id}")
    public ResponseEntity<PagamentoDto> update(@PathVariable @NotNull Long id, @Valid @RequestBody PagamentoDto pagamentoDto) {
        pagamentoService.updatePagamento(id, pagamentoDto);
        return ResponseEntity.ok(pagamentoDto);
    }

    @DeleteMapping("/pagamentos/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        pagamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
