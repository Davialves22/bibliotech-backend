package com.br.bibliotech.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.bibliotech.model.person.Person;
import com.br.bibliotech.repository.PersonRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Person person = personRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));
    return person;
  }
}
