package br.com.residencia.api_livraria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.residencia.api_livraria.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	Optional<User> findByUserEmail(String email);
}