package controller;

import java.util.ArrayList;
import java.util.List;

import dao.ContaDAO;
import model.Conexao;
import model.Conta;
import model.Pedido;

public class ContaController {

	private ContaDAO dao;
	
	public ContaController() {
		super();
	}
	
	public void criarConta(Conta conta) {
		try {
			Conexao.conectar();
			dao = new ContaDAO(Conexao.conexao);
			dao.criarConta(conta);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public void pagarConta(Conta conta) {
		try {
			Conexao.conectar();
			dao = new ContaDAO(Conexao.conexao);
			dao.pagarConta(conta);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public List<Conta> listarContasAbertas() {
		List<Conta> lista = new ArrayList<>();
		try {
			Conexao.conectar();
			dao = new ContaDAO(Conexao.conexao);
			lista = dao.listarContasAbertas();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return lista;
	}
	
	public void salvarTotalConta(Conta conta) {
		try {
	        Conexao.conectar();
	        dao = new ContaDAO(Conexao.conexao);
	        dao.salvarTotalConta(conta);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        Conexao.desconectar();
	    }
	}
	
	public double buscarTotalConta(int codconta) {
	    try {
	        Conexao.conectar();
	        dao = new ContaDAO(Conexao.conexao);
	        return dao.buscarTotalConta(codconta);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    } finally {
	        Conexao.desconectar();
	    }
	}
	
	public void alterarTotalConta(Pedido pedido) {
		try {
	        Conexao.conectar();
	        dao = new ContaDAO(Conexao.conexao);
	        dao.alterarTotalConta(pedido);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        Conexao.desconectar();
	    }
	}
}
