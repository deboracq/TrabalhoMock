package br.treinamento;

import java.util.Date;

public class ExercicioJUnit {

	public boolean verificarEntidadeValida(Entidade entidade) throws Exception{
		validarCamposObrigatorios(entidade);
		validarRegras(entidade);
		return true;
	}
	
	private void validarCamposObrigatorios(Entidade entidade) throws Exception{
		if(entidade.getNome() == null)
			throw new Exception("O nome e obrigatorio");
		if(entidade.getNumeroDocumento() == null)
			throw new Exception("O numero do documento e obrigatorio");
		if(entidade.getTipoDocumento() == null)
			throw new Exception("O tipo do documento e obrigatorio");
		if((entidade.getDataInicial() != null) && (entidade.getDataFinal() == null))
			throw new Exception("O periodo deve ser informado por completo");
	}
	
	private void validarRegras(Entidade entidade) throws Exception{
		if(entidade.getNome().length() <= 5)
			throw new Exception("O nome nao pode ter menos que 5 caracteres");
		if(entidade.getNome().length() >= 30)
			throw new Exception("O nome nao pode ter mais que 30 caracteres");
		if(entidade.getDataInicial()!= null && entidade.getDataInicial().compareTo(new Date()) < 0)
			throw new Exception("A data inicial nao pode ser menor que a data atual");
		if(entidade.getDataInicial()!= null && entidade.getDataFinal().compareTo(entidade.getDataInicial()) < 0)
			throw new Exception("A data final nao pode ser menor que a data inicial");
		if(entidade.getTipoDocumento() != 1 && entidade.getTipoDocumento() != 2)
			throw new Exception("Tipo de documento invalido");
		if(entidade.getEmail() != null && !(entidade.getEmail().contains("@") || entidade.getEmail().contains(".")))
			throw new Exception("Endereco de email invalido");
	}
	
	public boolean validaCPF(String CPF) {
		CPF = CPF.replace(".", "");
		CPF = CPF.replace("-", "");
		if (CPF.length() != 11) {
			return false;
		}

		if (CPF.equals("00000000000") || CPF.equals("11111111111")
				|| CPF.equals("22222222222") || CPF.equals("33333333333")
				|| CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777")
				|| CPF.equals("88888888888") || CPF.equals("99999999999"))
			return (false);

		String numDig = CPF.substring(0, 9);
		return calcDigVerif(numDig).equals(CPF.substring(9, 11));
	}
	
	private String calcDigVerif(String num) {      
        Integer primDig, segDig;      
        int soma = 0, peso = 10;      
        for (int i = 0; i < num.length(); i++) {     
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;      
        }
        if (soma % 11 == 0 | soma % 11 == 1) {      
            primDig = new Integer(0);      
        } else {      
            primDig = new Integer(11 - (soma % 11));      
        }
        soma = 0;      
        peso = 11;      
        for (int i = 0; i < num.length(); i++) {      
                soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;      
        }
        soma += primDig.intValue() * 2;      
        if (soma % 11 == 0 | soma % 11 == 1) {      
            segDig = new Integer(0);      
        } else {      
            segDig = new Integer(11 - (soma % 11));
        }
     
        return primDig.toString() + segDig.toString();      
    }
}
