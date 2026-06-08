package controller;

import java.util.ArrayList;
import java.util.List;

import dao.PedidoDAO;
import javafx.scene.control.Alert;
import model.Conexao;
import model.Pedido;

public class PedidoController {

	private PedidoDAO dao;
	
	public PedidoController() {
		super();
	}
	
	public void inserir(Pedido pedido) {
		try {
			Conexao.conectar();
			dao = new PedidoDAO(Conexao.conexao);
			dao.inserir(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public void alterar(Pedido pedido) {
		try {
			Conexao.conectar();
			dao = new PedidoDAO(Conexao.conexao);
			dao.alterar(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public void deletar(Pedido pedido) {
		try {
			Conexao.conectar();
			dao = new PedidoDAO(Conexao.conexao);
			dao.deletar(pedido);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public boolean repetirPedido(int codConta, int codItem) {
		try {
			Conexao.conectar();
			dao = new PedidoDAO(Conexao.conexao);		
			return dao.repetirPedido(codConta, codItem);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return false;
	}
	
	public List<Pedido> listarPedido() {
		List<Pedido> lista = new ArrayList<>();
		try {
			Conexao.conectar();
			dao = new PedidoDAO(Conexao.conexao);
			lista = dao.listarPedido();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return lista;
	}	
}
