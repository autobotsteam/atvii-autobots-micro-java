package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {

        @Override
        public void adicionarLink(Telefone objeto) {
                long id = objeto.getId();
                Link getall = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .getall())
                                .withRel("Telefone - Get All Telefones");
                objeto.add(getall);
                Link getOne = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .getbytelid(id))
                                .withRel("Telefone - Get One Telefone");
                objeto.add(getOne);
                Link delete = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .delete(objeto, id))
                                .withRel("Telefone - Delete Telefone");
                objeto.add(delete);
        }

        @Override
        public void adicionarLink(List<Telefone> lista) {
                for (Telefone tel : lista) {
                        long id = tel.getId();
                        Link getall = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .getall())
                                        .withRel("Telefone - Get All Telefones");
                        tel.add(getall);
                        Link getOne = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .getbytelid(id))
                                        .withRel("Telefone - Get One Telefone");
                        tel.add(getOne);
                        Link delete = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .delete(tel, id))
                                        .withRel("Telefone - Delete Telefone");
                        tel.add(delete);
                }
        }

        public void adicionarLink(List<Telefone> lista, long id) {
                for (Telefone tel : lista) {
                        Link getByIdCli = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .getbyid(id))
                                        .withRel("Telefone - Get By Id Cliente");
                        tel.add(getByIdCli);
                        Link create = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .create(id, tel))
                                        .withRel("Telefone - Create Telefone");
                        tel.add(create);
                        Link update = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(TelefoneControle.class)
                                                        .update(id, tel))
                                        .withRel("Telefone - Update Telefone");
                        tel.add(update);
                }
        }

        public void adicionarLink(Telefone objeto, long id) {
                Link getByIdCli = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .getbyid(id))
                                .withRel("Telefone - Get By Id Cliente");
                objeto.add(getByIdCli);
                Link create = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .create(id, objeto))
                                .withRel("Telefone - Create Telefone");
                objeto.add(create);
                Link update = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(TelefoneControle.class)
                                                .update(id, objeto))
                                .withRel("Telefone - Update Telefone");
                objeto.add(update);
        }

}
