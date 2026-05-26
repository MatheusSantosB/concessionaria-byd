package br.com.concessionaria.byd.service;

import br.com.concessionaria.byd.exception.RecursoNaoEncontradoException;
import br.com.concessionaria.byd.form.CarroForm;
import br.com.concessionaria.byd.model.Carro;
import br.com.concessionaria.byd.repository.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CarroService {

    private final CarroRepository carroRepository;
    private final Random random = new Random();

    private final String[] imagens = {
            "/img/dolphin.png",
            "/img/dolphin-mini.webp",
            "/img/shark.png",
            "/img/king.webp",
            "/img/seal.webp"
    };

    public List<Carro> listarAtivos() {
        return carroRepository.findByIsDeletedIsNullOrderByNomeAsc();
    }

    public List<Carro> listarTodos() {
        return carroRepository.findAll();
    }

    public Carro buscarPorId(Long id) {
        return carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro nao encontrado."));
    }

    public void salvar(CarroForm form) {
        Carro carro = form.getId() == null ? new Carro() : buscarPorId(form.getId());
        carro.setNome(form.getNome());
        carro.setCodigo(form.getCodigo());
        carro.setPreco(form.getPreco());
        carro.setAutonomia(form.getAutonomia());
        carro.setPotencia(form.getPotencia());
        carro.setDescricao(form.getDescricao());

        if (carro.getImgUrl() == null || carro.getImgUrl().isBlank()) {
            carro.setImgUrl(imagens[random.nextInt(imagens.length)]);
        }

        carroRepository.save(carro);
    }

    public void deletar(Long id) {
        Carro carro = buscarPorId(id);
        carro.setIsDeleted(LocalDateTime.now());
        carroRepository.save(carro);
    }

    public void restaurar(Long id) {
        Carro carro = buscarPorId(id);
        carro.setIsDeleted(null);
        carroRepository.save(carro);
    }
}
