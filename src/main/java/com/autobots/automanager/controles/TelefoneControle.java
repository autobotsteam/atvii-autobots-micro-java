package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio telRep;
    @Autowired
    private ClienteRepositorio cliRep;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<?> getall() {
        try {
            List<Telefone> telefones = telRep.findAll();
            adicionadorLink.adicionarLink(telefones);
            return ResponseEntity.status(202).body(telefones);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyid(@PathVariable long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
            Cliente cliente = clienteSelecionador.selecionar(clientes, id);
            List<Telefone> telefones = cliente.getTelefones();
            adicionadorLink.adicionarLink(telefones);
            adicionadorLink.adicionarLink(telefones, id);
            return ResponseEntity.status(202).body(telefones);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/tel/{id}")
    public ResponseEntity<?> getbytelid(@PathVariable Long id) {
        try {
            Optional<Telefone> telefone = telRep.findById(id);
            if (telefone.isPresent()) {
                Telefone tel = telefone.get();
                adicionadorLink.adicionarLink(tel);
                adicionadorLink.adicionarLink(tel, id);
                return ResponseEntity.status(202).body(telefone.get());
            } else {
                return ResponseEntity.status(404).body("Telefone não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody Telefone tel) {
        try {
            Optional<Cliente> cliente = cliRep.findById(id);
            if (cliente.isPresent()) {
                Cliente cli = cliente.get();
                Telefone telCriado = telRep.save(tel);
                List<Telefone> tels = cli.getTelefones();
                tels.add(telCriado);
                adicionadorLink.adicionarLink(tels);
                adicionadorLink.adicionarLink(tels, id);
                cli.setTelefones(tels);
                cliRep.save(cli);
                return ResponseEntity.status(201).body(tels);
            } else {
                return ResponseEntity.status(404).body("Cliente não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Telefone tel) {
        try {
            Optional<Cliente> cliente = cliRep.findById(id);
            if (cliente.isPresent()) {
                Cliente cli = cliente.get();
                Optional<Telefone> tels = telRep.findById(tel.getId());
                if (tels.isPresent()) {
                    Telefone atlTel = tels.get();
                    List<Telefone> telefones = cli.getTelefones();
                    Telefone target = telefones.get(telefones.indexOf(atlTel));
                    if (target != null) {
                        TelefoneAtualizador atualizador = new TelefoneAtualizador();
                        atualizador.atualizar(target, tel);
                        adicionadorLink.adicionarLink(target);
                        adicionadorLink.adicionarLink(target, id);
                        telRep.save(target);
                        return ResponseEntity.status(202).body(target);
                    } else {
                        return ResponseEntity.status(404).body("Telefone não pertence ao cliente");
                    }
                } else {
                    return ResponseEntity.status(404).body("Telefone não encontrado");
                }
            } else {
                return ResponseEntity.status(404).body("Cliente não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestBody Telefone tel, @PathVariable Long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
            Cliente cliente = clienteSelecionador.selecionar(clientes, id);
            if (cliente != null) {
                Optional<Telefone> telefone = telRep.findById(id);
                if (telefone.isPresent()) {
                    Telefone telDel = telefone.get();
                    cliente.getTelefones().remove(telDel);
                    cliRep.save(cliente);
                    return ResponseEntity.status(202).body("Telefone deletado");
                } else {
                    return ResponseEntity.status(404).body("Telefone não encontrado");
                }
            } else{
                return ResponseEntity.status(404).body("Cliente não encontrado");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
