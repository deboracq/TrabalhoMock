package br.treinamento;

import java.util.Date;

public class EntidadeNegocio {
	private EntidadeDAOInterface persistencia;

	public EntidadeNegocio() {
		//persistencia = new EntidadeDAO();
	}

	public void setPersistencia(EntidadeDAOInterface persistencia) {
		this.persistencia = persistencia;
	}

	private void validarCamposObrigatorios(Entidade entidade) throws Exception{
		if(entidade.getNome() == null)
			throw new Exception("O nome Ž obrigat—rio");
		if(entidade.getNumeroDocumento() == null)
			throw new Exception("O nœmero do documento Ž obrigat—rio");
		if(entidade.getTipoDocumento() == null)
			throw new Exception("O tipo do documento Ž obrigat—rio");
		if((entidade.getDataInicial() != null) && (entidade.getDataFinal() == null))
			throw new Exception("O per’odo deve ser informado por completo");
	}

	private void validarRegras(Entidade entidade) throws Exception{
		if(entidade.getNome().length() < 5)
			throw new Exception("O nome n‹o pode ter menos que 5 caracteres");
		if(entidade.getNome().length() > 30)
			throw new Exception("O nome n‹o pode ter mais que 30 caracteres");
		if(entidade.getNumeroDocumento() <= 0)
			throw new Exception("O nœmero do documento deve ser maior que zero");
		if(entidade.getDataInicial()!= null && entidade.getDataInicial().compareTo(new Date()) < 0)
			throw new Exception("A data inicial n‹o pode ser menor que a data atual");
		if(entidade.getDataInicial()!= null && entidade.getDataFinal().compareTo(entidade.getDataInicial()) < 0)
			throw new Exception("A data final n‹o pode ser menor que a data inicial");
		if(entidade.getTipoDocumento() != 1 && entidade.getTipoDocumento() != 2)
			throw new Exception("Tipo de documento inv‡lido");
		if(entidade.getEmail() != null && !(entidade.getEmail().contains("@") || entidade.getEmail().contains(".")))
			throw new Exception("Endereo de email inv‡lido");
	}

	public Entidade salvar(Entidade entidade) throws Exception{
		validarCamposObrigatorios(entidade);
		validarRegras(entidade);

		if(!persistencia.verificarUnicidadeNome(entidade))
			throw new Exception("J‡ existe entidade cadastrada com este nome.");

		//Salvando...
		entidade = persistencia.salvar(entidade);


		return entidade;
	}

	public Entidade alterar(Entidade entidade) throws Exception{
		validarCamposObrigatorios(entidade);
		validarRegras(entidade);

		Entidade entidadePersistida = getById(entidade.getId());
		if(!entidade.getNome().equals(entidadePersistida.getNome()))
			throw new Exception("N‹o Ž poss’vel alterar o nome da entidade");

		//Alterando...
		entidade = persistencia.alterar(entidade);

		return entidade;
	}

	public void excluir(Entidade entidade) throws Exception{
		if(entidade.getTipoDocumento() == 1)
			throw new Exception("N‹o Ž poss’vel excluir entidades com CPF");

		persistencia.excluir(entidade);
	}

	public Entidade getById(Long id) throws Exception{
		return persistencia.getById(id);
	}

	public int getQuantidadeRegistros() throws Exception{
		return persistencia.getQuantidadeRegistros();
	}

	public boolean verificarUnicidadeNome(Entidade entidade) throws Exception{
		return persistencia.verificarUnicidadeNome(entidade);
	}
}