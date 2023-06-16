package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {

    @Override
    public void adicionarLink(Documento objeto) {
        long id = objeto.getId();
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getbyid(id))
                .withRel("Cliente - Get by ID do cliente");
        objeto.add(linkProprio);
        Link linkGetById = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getbyiddoc(id))
                .withRel("Cliente - Get by ID do documento");
        objeto.add(linkGetById);
        Link linkGetAll = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getall())
                .withRel("Clientes - Get all");
        objeto.add(linkGetAll);
        Link linkPost = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .create(objeto, id))
                .withRel("Clientes - Create");
        objeto.add(linkPost);
        Link linkPut = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .update(objeto, id))
                .withRel("Clientes - Put");
        objeto.add(linkPut);
        Link linkDelete = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .delete(objeto, id))
                .withRel("Clientes - Delete");
        objeto.add(linkDelete);

    }

    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            long id = documento.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getbyid(id))
                    .withRel("Cliente - Get by ID do cliente");
            documento.add(linkProprio);
            Link linkGetById = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getbyiddoc(id))
                    .withRel("Cliente - Get by ID do documento");
            documento.add(linkGetById);
            Link linkGetAll = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getall())
                    .withRel("Clientes - Get all");
            documento.add(linkGetAll);
            Link linkPost = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .create(documento, id))
                    .withRel("Clientes - Create");
            documento.add(linkPost);
            Link linkPut = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .update(documento, id))
                    .withRel("Clientes - Put");
            documento.add(linkPut);
            Link linkDelete = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .delete(documento, id))
                    .withRel("Clientes - Delete");
            documento.add(linkDelete);
        }
    }
}
