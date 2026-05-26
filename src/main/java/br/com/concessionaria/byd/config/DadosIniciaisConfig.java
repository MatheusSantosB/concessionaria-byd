package br.com.concessionaria.byd.config;

import br.com.concessionaria.byd.model.Carro;
import br.com.concessionaria.byd.repository.CarroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DadosIniciaisConfig {

    @Bean
    public CommandLineRunner carregarCarros(CarroRepository repository) {
        return args -> {
            salvarOuAtualizar(repository, new Carro(null, null, "/img/dolphin.png", "BYD Dolphin",
                    "BYD-DOLPHIN", new BigDecimal("149800.00"), "Ate 291 km",
                    "95 cv", "Hatch eletrico com bom espaco interno e uso urbano."));

            salvarOuAtualizar(repository, new Carro(null, null, "/img/dolphin-mini.webp", "BYD Dolphin Mini",
                    "BYD-DOLPHIN-MINI", new BigDecimal("115800.00"), "Ate 280 km",
                    "75 cv", "Compacto 100% eletrico para cidade e baixo consumo."));

            salvarOuAtualizar(repository, new Carro(null, null, "/img/shark.png", "BYD Shark",
                    "BYD-SHARK", new BigDecimal("379800.00"), "57 km no modo eletrico",
                    "437 cv", "Caminhonete super hibrida plug-in com tracao e cacamba."));

            salvarOuAtualizar(repository, new Carro(null, null, "/img/king.webp", "BYD King",
                    "BYD-KING", new BigDecimal("175800.00"), "Uso eletrificado plug-in",
                    "235 cv", "Sedan hibrido plug-in com foco em conforto e economia."));

            salvarOuAtualizar(repository, new Carro(null, null, "/img/seal.webp", "BYD Seal",
                    "BYD-SEAL", new BigDecimal("299800.00"), "Ate 372 km",
                    "531 cv", "Sedan 100% eletrico de alto desempenho."));
        };
    }

    // Atualiza os dados fixos quando o banco ja existe, sem duplicar registros.
    private void salvarOuAtualizar(CarroRepository repository, Carro novo) {
        Carro carro = repository.findByCodigo(novo.getCodigo()).orElse(novo);
        carro.setImgUrl(novo.getImgUrl());
        carro.setNome(novo.getNome());
        carro.setPreco(novo.getPreco());
        carro.setAutonomia(novo.getAutonomia());
        carro.setPotencia(novo.getPotencia());
        carro.setDescricao(novo.getDescricao());
        repository.save(carro);
    }
}
