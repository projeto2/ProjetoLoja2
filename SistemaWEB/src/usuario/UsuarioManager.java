package usuario;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import pedido.Item;
import pedido.PedidoManager;

@ManagedBean(name="usuarioManager")
@SessionScoped
public class UsuarioManager 
{
	private Usuario usuario;
	private List<Endereco> enderecos;
	private String email;
	private String senha;
	private Endereco endereco;
	
	public UsuarioManager()
	{
		endereco = new Endereco();
		novo();
	}
	
	public void novo()
	{
		usuario = new Usuario();
		
		enderecos = new ArrayList<Endereco>();
		enderecos.add(new Endereco());
		enderecos.add(new Endereco());
	}
	
	public String salvar()
	{
		String mensagem = "";
		
		if(usuario.getId() != null)
		{
			mensagem = "Cadastro atualizado com sucesso!";
		}
		else
		{
			mensagem="Cadastro realizado com sucesso!";
		}
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		enderecos.get(0).setNome(usuario.getNome());
		
		usuario.setEnderecos(enderecos);
		
		usuarioDAO.salvar(usuario);
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
		return "/principal";
	}
	
	public String obter()
	{
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuario = usuarioDAO.obter(usuario.getId());
		enderecos = usuario.getEnderecos();
		
		return "/usuario";
	}
	
	public String logar()
	{
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		usuario = usuarioDAO.logar(email, senha);
		
		if(usuario != null)
		{
			enderecos = new ArrayList<Endereco>();
			for(int i = 0; i < usuario.getEnderecos().size(); i++)
			{
				enderecos.add(usuario.getEnderecos().get(i));
			}
			return "/principal";
		}
		else
		{
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou Senha incorreto!", "Usuário ou Senha incorreto!"));
			return "/login";
		}
	}
	
	public String sair()
	{
		novo();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		PedidoManager pedidoManager = (PedidoManager)session.getAttribute("pedidoManager");
		
		if(pedidoManager != null)
		{
			pedidoManager.setProdutos(new ArrayList<Item>());
		}
		return "/principal";
	}
	
	public String salvarEndereco()
	{
		usuario.getEnderecos().add(endereco);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		enderecos = new ArrayList<Endereco>();
		for(int i = 0; i < usuario.getEnderecos().size(); i++)
		{
			enderecos.add(usuario.getEnderecos().get(i));
		}
		
		endereco = new Endereco();
		return "/finalizarcompra";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
