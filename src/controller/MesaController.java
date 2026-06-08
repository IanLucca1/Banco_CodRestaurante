package controller;

import java.util.ArrayList;
import java.util.List;

import dao.MesaDAO;
import javafx.fxml.FXML;
import model.Conexao;

public class MesaController {

	private MesaDAO dao;
	
	@FXML
	public int selecionarMesaDisponivel(){
		int codMesa = 0;
		try {
			Conexao.conectar();
			dao = new MesaDAO(Conexao.conexao);
			codMesa = dao.selecionarMesaDisponivel();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return codMesa;
	}
	
	public List<Integer> listarMesasEmUso(){
		List<Integer> lista = new ArrayList<>();
		try {
			Conexao.conectar();
			dao = new MesaDAO(Conexao.conexao);
			lista = dao.listarMesasEmUso();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return lista;
	}
	
	public void usarMesa(int codMesa){
		try {
			Conexao.conectar();
			dao = new MesaDAO(Conexao.conexao);
			dao.usarMesa(codMesa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
	
	public void desocuparMesa(int codMesa) {
		try {
			Conexao.conectar();
			dao = new MesaDAO(Conexao.conexao);
			dao.desocuparMesa(codMesa);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
}
