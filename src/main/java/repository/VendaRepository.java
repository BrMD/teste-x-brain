package repository;

import model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {
}
