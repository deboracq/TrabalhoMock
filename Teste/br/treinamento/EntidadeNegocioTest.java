package br.treinamento;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntidadeNegocioTest {
	private EntidadeNegocio entidade;
	private EntidadeDAOInterface persistencia;

	@Before
	public void setUp() throws Exception {
		persistencia = EasyMock.createMock(EntidadeDAOInterface.class);
		entidade = new EntidadeNegocio();
		entidade.setPersistencia(persistencia);
	}

	@After
	public void tearDown() throws Exception {
		EasyMock.reset(persistencia);
	}

	public void testValidarCamposObrigatorios_salvarSucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		entidadeEntrada = getEntidadeValida();

		entidadeEsperada = getEntidadeValida();
		entidadeEsperada.setId((long) 1);

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(true);
		EasyMock.expect(persistencia.salvar(entidadeEntrada)).andReturn(
				entidadeEsperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.salvar(entidadeEntrada);

		assertNotNull("Cenário 1: Salvamento com sucesso.",
				entidadeAtual.getId());

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarCamposObrigatorios_semNome() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setNome(null);

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);
		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O nome é obrigatório", e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarCamposObrigatorios_semNroDocumento()
			throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setNumeroDocumento(null);

		EasyMock.reset(persistencia);

		EasyMock.replay(persistencia);
		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O número do documento é obrigatório", e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarCamposObrigatorios_semTipoDocumento()
			throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setTipoDocumento(null);

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O tipo do documento é obrigatório", e.getMessage());
		}
		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarCamposObrigatorios_semDataCompleta()
			throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setDataFinal(null);

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O período deve ser informado por completo",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_salvarSucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEesperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();

		entidadeEesperada = getEntidadeValida();
		entidadeEesperada.setId((long) 1);

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(true);
		EasyMock.expect(persistencia.salvar(entidadeEntrada)).andReturn(
				entidadeEesperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.salvar(entidadeEntrada);

		assertNotNull("Cenário 1: Salvamento com sucesso.",
				entidadeAtual.getId());

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_nomeMenosde5Caracteres() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setNome("Nome");

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O nome não pode ter menos que 5 caracteres",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_nomeMaisde30Caracteres() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada
				.setNome("Nome completo do usuário com mais de trinta caracteres");

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O nome não pode ter mais que 30 caracteres",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_nroDocZeroOuMenos() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setNumeroDocumento((long) -1);

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("O número do documento deve ser maior que zero",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_dtInicialMenorQueAtual() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		entidadeEntrada.setDataInicial(calendario.getTime());

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("A data inicial não pode ser menor que a data atual",
					e.getMessage());
		}
		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_dtFinalMenorQueInicial() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_MONTH, -1);
		entidadeEntrada.setDataFinal(calendario.getTime());

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("A data final não pode ser menor que a data inicial",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_tipoDocInvalido() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setTipoDocumento(4);

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("Tipo de documento inválido", e.getMessage());
		}
		EasyMock.verify(persistencia);
	}

	@Test
	public void testValidarRegras_emailInvalido() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Calendar calendario;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setEmail("emailcom");

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("Endereço de email inválido", e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testSalvar_unicidade() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();

		entidadeEntrada = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(false);
		EasyMock.replay(persistencia);

		try {
			entidadeAtual = entidade.salvar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("Já existe entidade cadastrada com este nome.",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testSalvar_sucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();

		entidadeEntrada = getEntidadeValida();

		entidadeEsperada = getEntidadeValida();
		entidadeEsperada.setId((long) 1);

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(true);
		EasyMock.expect(persistencia.salvar(entidadeEntrada)).andReturn(
				entidadeEsperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.salvar(entidadeEntrada);

		assertNotNull("Dados cadastrados com sucesso.", entidadeAtual.getId());

		EasyMock.verify(persistencia);
	}

	@Test
	public void testGetById_sucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();

		entidadeEsperada = getEntidadeValida();
		entidadeEsperada.setId((long) 10);

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getById((long) 10)).andReturn(
				entidadeEsperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.getById((long) 10);

		assertEquals("Usuário Retornado com Sucesso",
				entidadeEsperada.getNome(), entidadeAtual.getNome());

		EasyMock.verify(persistencia);
	}

	@Test
	public void testGetById_naoEncontrado() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada = null;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getById((long) 11)).andReturn(
				entidadeEsperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.getById((long) 11);

		assertNull("Usuário não encontrado", entidadeAtual);

		EasyMock.verify(persistencia);
	}

	@Test
	public void testAlterar_sucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Entidade entidadeGetById;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();
		// Get by ID
		testGetById_naoEncontrado();
		testGetById_sucesso();

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setEmail("email@email.com");
		entidadeEntrada.setId((long) 12);

		entidadeGetById = getEntidadeValida();
		entidadeGetById.setId((long) 12);

		entidadeEsperada = getEntidadeValida();
		entidadeEsperada.setEmail("email@email.com");
		entidadeEsperada.setId((long) 12);

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getById((long) 12)).andReturn(
				entidadeGetById);
		EasyMock.expect(persistencia.alterar(entidadeEntrada)).andReturn(
				entidadeEsperada);
		EasyMock.replay(persistencia);

		entidadeAtual = entidade.alterar(entidadeEntrada);

		assertNotNull("Usuário alterado com sucesso.", entidadeAtual.getId());

		EasyMock.verify(persistencia);
	}

	@Test
	public void testAlterar_nome() throws Exception {
		Entidade entidadeEntrada;
		Entidade entidadeGetById;

		// Campos obrigatórios
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarCamposObrigatorios_semDataCompleta();
		testValidarCamposObrigatorios_semNome();
		testValidarCamposObrigatorios_semNroDocumento();
		testValidarCamposObrigatorios_semTipoDocumento();
		// Validar regras
		testValidarRegras_dtFinalMenorQueInicial();
		testValidarRegras_dtInicialMenorQueAtual();
		testValidarRegras_emailInvalido();
		testValidarRegras_nomeMaisde30Caracteres();
		testValidarRegras_nomeMenosde5Caracteres();
		testValidarRegras_nroDocZeroOuMenos();
		// Get by ID
		testGetById_naoEncontrado();
		testGetById_sucesso();

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setEmail("email@email.com");
		entidadeEntrada.setNome("Debora Queiroz");
		entidadeEntrada.setId((long) 12);

		entidadeGetById = getEntidadeValida();
		entidadeGetById.setEmail("email@email.com");
		entidadeGetById.setNome("Debora Queiroz");
		entidadeGetById.setId((long) 12);


		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getById((long) 12)).andReturn(
				entidadeGetById);
		EasyMock.replay(persistencia);

		try {
			entidadeEntrada.setNome("Teste ");

			entidade.alterar(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("Não é possível alterar o nome da entidade",
					e.getMessage());
		}
		EasyMock.verify(persistencia);
		
	
	}

	@Test
	public void testExcluir_sucesso() throws Exception {
		Entidade entidadeAtual;
		Entidade entidadeEsperada;
		Entidade entidadeEntrada;
		Entidade entidadeGetById;

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setTipoDocumento(2);

		EasyMock.reset(persistencia);
		persistencia.excluir(entidadeEntrada);
		EasyMock.expectLastCall().once();
		EasyMock.replay(persistencia);

		entidade.excluir(entidadeEntrada);

		EasyMock.verify(persistencia);
	}

	@Test
	public void testExcluir_tipoDocCPF() throws Exception {
		Entidade entidadeEntrada;
		entidadeEntrada = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.replay(persistencia);

		try {
			entidade.excluir(entidadeEntrada);
			fail("Nao deveria chegar nesta linha");
		} catch (Exception e) {
			assertEquals("Não é possível excluir entidades com CPF",
					e.getMessage());
		}

		EasyMock.verify(persistencia);
	}

	@Test
	public void testGetQuantidadeRegistros() throws Exception {
		int quantidadeRegistrosActual;
		int quantidadeRegistrosExpected = 10;

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(
				quantidadeRegistrosExpected);
		EasyMock.replay(persistencia);

		quantidadeRegistrosActual = entidade.getQuantidadeRegistros();

		assertEquals("Quantidade de Registro retornada com sucesso",
				quantidadeRegistrosExpected, quantidadeRegistrosActual);

		EasyMock.verify(persistencia);
	}

	@Test
	public void testVerificarUnicidadeNome_Unico() throws Exception {
		Entidade entidadeEntrada;
		boolean respostaAtual;

		entidadeEntrada = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(true);
		EasyMock.replay(persistencia);

		respostaAtual = entidade.verificarUnicidadeNome(entidadeEntrada);

		assertTrue("Entidade única", respostaAtual);
	}

	@Test
	public void testVerificarUnicidadeNome_naoUnico() throws Exception {
		Entidade entidadeEntrada;
		boolean respostaAtual;

		entidadeEntrada = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(false);
		EasyMock.replay(persistencia);

		respostaAtual = entidade.verificarUnicidadeNome(entidadeEntrada);

		assertFalse("Entidade não é única", respostaAtual);
	}
/*
	@Test
	public void testGeral() throws Exception {
		// Cenário 1: Verifica a quantidade de registros.
		int quantidadeRegistrosAtual;
		int quantidadeRegistrosEsperada = 1;

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(
				quantidadeRegistrosEsperada);
		EasyMock.replay(persistencia);

		quantidadeRegistrosAtual = entidade.getQuantidadeRegistros();

		assertEquals("Cenário 1: Verifica a quantidade de registros.",
				quantidadeRegistrosEsperada, quantidadeRegistrosAtual);

		// EasyMock.verify(persistencia);

		// Cenário 2: Insere um novo registro.
		Entidade entidadeActual;
		Entidade entidadeExpected;
		Entidade entidadeEntrada;
		Calendar dataCalendario = Calendar.getInstance();

		// colocar aqui os testes
		testValidarCamposObrigatorios_salvarSucesso();
		testValidarRegras();

		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setTipoDocumento(2);

		entidadeExpected = getEntidadeValida();
		entidadeExpected.setId((long) 2);
		entidadeExpected.setDataGravacao(dataCalendario.getTime());

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(true);
		EasyMock.expect(persistencia.salvar(entidadeEntrada)).andReturn(
				entidadeExpected);
		EasyMock.replay(persistencia);

		entidadeActual = entidade.salvar(entidadeEntrada);

		assertNotNull("Cenário 2: Insere um novo registro.",
				entidadeActual.getId());

		// EasyMock.verify(persistencia);

		// Cenário 3: Verifica que a quantidade de registros foi incrementada.
		quantidadeRegistrosAtual = 0;
		quantidadeRegistrosEsperada = 2;

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(
				quantidadeRegistrosEsperada);
		EasyMock.replay(persistencia);

		quantidadeRegistrosAtual = entidade.getQuantidadeRegistros();

		assertEquals(
				"Cenário 3: Verifica que a quantidade de registros foi incrementada.",
				quantidadeRegistrosEsperada, quantidadeRegistrosAtual);

		// EasyMock.verify(persistencia);

		// Cenário 4: Tenta inserir o mesmo registro mas recebe uma exceção.
		entidadeEntrada = getEntidadeValida();

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.verificarUnicidadeNome(entidadeEntrada))
				.andReturn(false);
		EasyMock.replay(persistencia);

		try {
			entidadeActual = entidade.salvar(entidadeEntrada);

			fail("Cenário 4: Tenta inserir o mesmo registro mas recebe uma exceção. Deveria retornar uma exceção.");
		} catch (Exception e) {
			assertEquals(
					"Cenário 4: Tenta inserir o mesmo registro mas recebe uma exceção.",
					"Já existe entidade cadastrada com este nome.",
					e.getMessage());
		}

		// EasyMock.verify(persistencia);

		// Cenário 5: Exclui o registro.
		entidadeEntrada = getEntidadeValida();
		entidadeEntrada.setTipoDocumento(2);

		EasyMock.reset(persistencia);
		persistencia.excluir(entidadeEntrada);
		EasyMock.expectLastCall().once();
		EasyMock.replay(persistencia);

		entidade.excluir(entidadeEntrada);

		// EasyMock.verify(persistencia);

		// Cenário 6: Verifica a quantidade de registros decrementada.
		quantidadeRegistrosAtual = 0;
		quantidadeRegistrosEsperada = 1;

		EasyMock.reset(persistencia);
		EasyMock.expect(persistencia.getQuantidadeRegistros()).andReturn(
				quantidadeRegistrosEsperada);
		EasyMock.replay(persistencia);

		quantidadeRegistrosAtual = entidade.getQuantidadeRegistros();

		assertEquals(
				"Cenário 6: Verifica a quantidade de registros decrementada.",
				quantidadeRegistrosEsperada, quantidadeRegistrosAtual);

		EasyMock.verify(persistencia);
	}

	/**
	 * Gera um objeto de Entidade válido e corretamente preenchido.
	 * 
	 * @return Entidade
	 */
	private Entidade getEntidadeValida() {
		Calendar dataCalendario = Calendar.getInstance();

		Entidade entidade = new Entidade();
		entidade.setNome("Debora Queiroz");
		dataCalendario.set(2014, 7, 7);
		entidade.setDataInicial(new Date(dataCalendario.getTimeInMillis()));
		dataCalendario.set(2014, 7, 22);
		entidade.setDataFinal(new Date(dataCalendario.getTimeInMillis()));
		entidade.setTipoDocumento(1);
		entidade.setNumeroDocumento(new Long(1234567890));

		return entidade;
	}

}