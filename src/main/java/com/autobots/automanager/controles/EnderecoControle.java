package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private ClienteRepositorio repositorioCliente;
    @Autowired
    private AdicionadorLinkEndereco adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<?> getall() {
        try {
            List<Endereco> enderecos = repositorio.findAll();
            adicionadorLink.adicionarLink(enderecos);
            if (enderecos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há endereços cadastrados");
            } else {
                
                return ResponseEntity.status(HttpStatus.FOUND).body(enderecos);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getbyid(@PathVariable long id) {
        try {
            Optional<Cliente> cliente = repositorioCliente.findById(id);
            if (!cliente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Endereco endereco = cliente.get().getEndereco();
            adicionadorLink.adicionarLink(endereco, id);
            return ResponseEntity.status(HttpStatus.FOUND).body(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/end/{id}")
    public ResponseEntity<Endereco> getbyidend(@PathVariable long id) {
        try {
            Optional<Endereco> endereco = repositorio.findById(id);
            if (!endereco.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Endereco end = endereco.get();
            adicionadorLink.adicionarLink(end);
            return ResponseEntity.status(HttpStatus.FOUND).body(end);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Endereco> create(@RequestBody Endereco endereco, @PathVariable long id) {
        try {
            Optional<Cliente> cliente = repositorioCliente.findById(id);
            if (!cliente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Cliente newCli = cliente.get();
            newCli.setEndereco(endereco);
            repositorioCliente.save(newCli);
            adicionadorLink.adicionarLink(endereco, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Endereco> update(@RequestBody Endereco endereco, @PathVariable long id) {
        try {
            Optional<Cliente> cliente = repositorioCliente.findById(id);
            if (!cliente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Endereco end = cliente.get().getEndereco();
            EnderecoAtualizador atualizar = new EnderecoAtualizador();
            atualizar.atualizar(end, endereco);
            Cliente newCli = cliente.get();
            newCli.setEndereco(end);
            repositorioCliente.save(newCli);
            adicionadorLink.adicionarLink(endereco, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(endereco);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
