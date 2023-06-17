package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

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
import com.autobots.automanager.modelos.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio docRep;
    @Autowired
    private ClienteRepositorio cliRep;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;

    @GetMapping("/")
    public ResponseEntity<?> getall() {
        try {
            List<Documento> documentos = docRep.findAll();
            adicionadorLink.adicionarLink(documentos);
            return ResponseEntity.status(202).body(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getbyid(@PathVariable Long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
            Cliente cli = clienteSelecionador.selecionar(clientes, id);
            List<Documento> documentos = cli.getDocumentos();
            adicionadorLink.adicionarLink(documentos);
            adicionadorLink.adicionarLink(documentos, id);
            return ResponseEntity.status(202).body(documentos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<?> getbyiddoc(@PathVariable Long id) {
        try {
            Optional<Documento> documento = docRep.findById(id);
            if (documento.isPresent()) {
                Documento doc = documento.get();
                adicionadorLink.adicionarLink(doc);
                return ResponseEntity.status(202).body(doc);
            } else {
                return ResponseEntity.status(404).body("Documento não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@RequestBody Documento documento, @PathVariable Long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            if (clientes.isEmpty()) {
                return ResponseEntity.status(404).body("Não há clientes cadastrados");
            } else {
                ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
                Cliente cli = clienteSelecionador.selecionar(clientes, id);
                Documento doc = docRep.save(documento);
                adicionadorLink.adicionarLink(doc);
                adicionadorLink.adicionarLink(doc, cli.getId());
                cli.getDocumentos().add(doc);
                cliRep.save(cli);
                return ResponseEntity.status(201).body(doc);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Documento documento, @PathVariable Long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
            Cliente cli = clienteSelecionador.selecionar(clientes, id);
            if (cli == null) {
                return ResponseEntity.status(404).body("Cliente não encontrado");
            } else {
                Optional<Documento> doc = docRep.findById(documento.getId());
                if(!doc.isPresent()){
                    return ResponseEntity.status(404).body("Documento não encontrado");
                }else{
                    DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
                    documentoAtualizador.atualizar(doc.get(), documento);
                    Documento docAtualizado = doc.get();
                    adicionadorLink.adicionarLink(docAtualizado);
                    adicionadorLink.adicionarLink(docAtualizado, cli.getId());
                    return ResponseEntity.status(202).body(docAtualizado);
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestBody Documento documento, @PathVariable Long id) {
        try {
            List<Cliente> clientes = cliRep.findAll();
            ClienteSelecionador clienteSelecionador = new ClienteSelecionador();
            Cliente cli = clienteSelecionador.selecionar(clientes, id);
            if (cli == null) {
                return ResponseEntity.status(404).body("Cliente não encontrado");
            } else {
                Optional<Documento> doc = docRep.findById(documento.getId());
                if(!doc.isPresent()){
                    return ResponseEntity.status(404).body("Documento não encontrado");
                }else{
                    Documento docu = doc.get();
                    cli.getDocumentos().remove(docu);
                    cliRep.save(cli);
                    return ResponseEntity.status(202).body("Documento deletado");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
