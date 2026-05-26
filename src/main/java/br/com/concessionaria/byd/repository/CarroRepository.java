package br.com.concessionaria.byd.repository;

import br.com.concessionaria.byd.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarroRepository extends JpaRepository<Carro, Long> {

    List<Carro> findByIsDeletedIsNullOrderByNomeAsc();

    Optional<Carro> findByCodigo(String codigo);
}
