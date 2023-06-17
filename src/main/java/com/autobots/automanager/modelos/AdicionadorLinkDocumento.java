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
        Link linkGetById = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getbyiddoc(id))
                .withRel("Documento - Get by ID do documento");
        objeto.add(linkGetById);
        Link linkGetAll = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getall())
                .withRel("Documento - Get all");
        objeto.add(linkGetAll);

    }

    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            long id = documento.getId();
            Link linkGetById = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getbyiddoc(id))
                    .withRel("Documento - Get by ID do documento");
            documento.add(linkGetById);
            Link linkGetAll = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getall())
                    .withRel("Documento - Get all");
            documento.add(linkGetAll);
        }
    }

    public void adicionarLink(List<Documento> lista, long id) {
        for (Documento documento : lista) {
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .getbyid(id))
                    .withRel("Documento - Get by ID do cliente");
            documento.add(linkProprio);
            Link linkPost = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .create(documento, id))
                    .withRel("Documento - Create");
            documento.add(linkPost);
            Link linkPut = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .update(documento, id))
                    .withRel("Documento - Put");
            documento.add(linkPut);
            Link linkDelete = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoControle.class)
                            .delete(documento, id))
                    .withRel("Documento - Delete");
            documento.add(linkDelete);
        }
    }

    public void adicionarLink(Documento objeto, long id) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .getbyid(id))
                .withRel("Documento - Get by ID do cliente");
        objeto.add(linkProprio);
        Link linkPost = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .create(objeto, id))
                .withRel("Documento - Create");
        objeto.add(linkPost);
        Link linkPut = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .update(objeto, id))
                .withRel("Documento - Put");
        objeto.add(linkPut);
        Link linkDelete = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .delete(objeto, id))
                .withRel("Documento - Delete");
        objeto.add(linkDelete);
    }
}
