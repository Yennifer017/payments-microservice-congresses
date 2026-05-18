package ayd2.ps2026.demo.example.repositories;

import ayd2.ps2026.demo.example.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {
}
