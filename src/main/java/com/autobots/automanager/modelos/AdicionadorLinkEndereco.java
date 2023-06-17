package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {

        @Override
        public void adicionarLink(Endereco objeto) {
                long id = objeto.getId();
                Link getall = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(EnderecoControle.class)
                                                .getall())
                                .withRel("Endereço - Get All Endereços");
                objeto.add(getall);
                Link getOne = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(EnderecoControle.class)
                                                .getbyidend(id))
                                .withRel("Endereço - Get One Endereço");
                objeto.add(getOne);
        }

        @Override
        public void adicionarLink(List<Endereco> lista) {
                for (Endereco tel : lista) {
                        long id = tel.getId();
                        Link getall = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(EnderecoControle.class)
                                                        .getall())
                                        .withRel("Endereço - Get All Endereço");
                        tel.add(getall);
                        Link getOne = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(EnderecoControle.class)
                                                        .getbyidend(id))
                                        .withRel("Endereço - Get One Endereço");
                        tel.add(getOne);
                }
        }

        public void adicionarLink(List<Endereco> lista, long id) {
                for (Endereco tel : lista) {
                        Link getByIdCli = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(EnderecoControle.class)
                                                        .getbyid(id))
                                        .withRel("Endereço - Get By Id Cliente");
                        tel.add(getByIdCli);
                        Link create = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(EnderecoControle.class)
                                                        .create(tel, id))
                                        .withRel("Endeeço - Create Endereço");
                        tel.add(create);
                        Link update = WebMvcLinkBuilder
                                        .linkTo(WebMvcLinkBuilder
                                                        .methodOn(EnderecoControle.class)
                                                        .update(tel, id))
                                        .withRel("Endereço - Update Endereço");
                        tel.add(update);
                }
        }

        public void adicionarLink(Endereco objeto, long id) {
                Link getByIdCli = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(EnderecoControle.class)
                                                .getbyid(id))
                                .withRel("Telefone - Get By Id Cliente");
                objeto.add(getByIdCli);
                Link create = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(EnderecoControle.class)
                                                .create(objeto, id))
                                .withRel("Telefone - Create Telefone");
                objeto.add(create);
                Link update = WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder
                                                .methodOn(EnderecoControle.class)
                                                .update(objeto, id))
                                .withRel("Telefone - Update Telefone");
                objeto.add(update);
        }

}
