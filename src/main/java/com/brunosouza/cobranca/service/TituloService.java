package com.brunosouza.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.brunosouza.cobranca.model.StatusTitulo;
import com.brunosouza.cobranca.model.Titulo;
import com.brunosouza.cobranca.repository.TituloRepository;
import com.brunosouza.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {
	
	@Autowired
	private TituloRepository tituloRepository;
	
	public void salvar(Titulo titulo) {
		try {
			tituloRepository.save(titulo);
		}catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long id) {
		tituloRepository.delete(id);
	}

	public String receber(Long id) {
		Titulo titulo = tituloRepository.findOne(id);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return tituloRepository.findByDescricaoContaining(descricao); 
	}
}
