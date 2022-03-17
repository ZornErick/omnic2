package br.com.omnic2.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepo extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByNome(String nome);
}
