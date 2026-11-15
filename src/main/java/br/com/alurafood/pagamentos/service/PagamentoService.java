package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(p -> modelMapper.map(p, PagamentoDto.class));
    }

    public PagamentoDto findById(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto insertPagamento(PagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto updatePagamento(Long id, PagamentoDto pagamentoDto) {
        Pagamento pagamento = modelMapper.map(pagamentoDto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
