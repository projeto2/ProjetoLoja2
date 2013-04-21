package pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import produto.Produto;
import usuario.Endereco;
import usuario.Usuario;
import usuario.UsuarioDAO;
import usuario.UsuarioManager;

@ManagedBean
@SessionScoped
public class PedidoManager 
{
	private Integer quantidade = 1;
	private List<Item> produtos = new ArrayList<Item>(), produtosVisualizar = new ArrayList<Item>();
	private List<Produto> listaDesejo;
	private Produto produto;
	private Integer index, numero,indexPedido;
	private Endereco endereco, enderecoVisualizar;
	private Integer formaPagamento;
	private Pedido pedido,pedidoVisualizar;
	private Date inicio, fim;
	public String adicionarCarrinho()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		if(session.getAttribute("usuarioManager") != null)
		{
			UsuarioManager usuario = (UsuarioManager)session.getAttribute("usuarioManager");
			if(usuario.getUsuario().getId() == null)
			{
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você precisa está logado para fazer compras!", "Você precisa está logado para fazer compras!"));
				return "/login";
			}
		}
		else
		{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você precisa está logado para fazer compras!", "Você precisa está logado para fazer compras!"));
			return "/login";
		}
		
		Item item = new Item();
		item.setQuantidade(quantidade);
		item.setProduto(produto);
		
		produtos.add(item);
		
		quantidade = 1;
		produto = null;
		
		return "/carrinho";
	}
	
	public String adicionarListaDesejo()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		if(session.getAttribute("usuarioManager") != null)
		{
			UsuarioManager usuario = (UsuarioManager)session.getAttribute("usuarioManager");
			if(usuario.getUsuario().getId() == null)
			{
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você precisa está logado para fazer compras!", "Você precisa está logado para fazer compras!"));
				return "/login";
			}
			else
			{
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				List<Produto> listaDesejoUsuario = usuarioDAO.obterListaDesejo(usuario.getUsuario().getId());
				
				listaDesejo = new ArrayList<Produto>();
				
				Usuario u = usuario.getUsuario();
				if(listaDesejoUsuario != null)
				{
					for(int i = 0; i < listaDesejoUsuario.size(); i++)
					{
						if(produto.getId().intValue() == listaDesejoUsuario.get(i).getId().intValue())
						{
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este produto já está na lista de desejos!", "Este produto já está na lista de desejos!"));
							return obterListaDesejo();
						}
					}
					
					listaDesejoUsuario.add(produto);
					
					u.setListaDesejo(listaDesejoUsuario);
					usuarioDAO.salvar(u);
				
					for(int i = 0; i < listaDesejoUsuario.size(); i++)
					{
						listaDesejo.add(listaDesejoUsuario.get(i));
					}
				}
				else
				{
					listaDesejo.add(produto);
					u.setListaDesejo(listaDesejo);
					
					usuarioDAO.salvar(u);
				}
			}
		}
		else
		{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você precisa está logado para fazer compras!", "Você precisa está logado para fazer compras!"));
			return "/login";
		}
		
		
		return "/listadesejo";
	}
	
	public String obterListaDesejo()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		UsuarioManager usuario = (UsuarioManager)session.getAttribute("usuarioManager");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<Produto> listaDesejoUsuario = usuarioDAO.obterListaDesejo(usuario.getUsuario().getId());
		
		listaDesejo = new ArrayList<Produto>();
		
		if(listaDesejoUsuario != null)
		{		
			for(int i = 0; i < listaDesejoUsuario.size(); i++)
			{
				listaDesejo.add(listaDesejoUsuario.get(i));
			}
		}
		
		return "/listadesejo";
	}
	
	public String excluir()
	{
		produtos.remove(index.intValue());
		
		return "/carrinho"; 
	}
	
	public String excluirListaDesejo()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		UsuarioManager usuario = (UsuarioManager)session.getAttribute("usuarioManager");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		Usuario u  = usuario.getUsuario();
		listaDesejo.remove(index.intValue());
		u.setListaDesejo(listaDesejo);
		
		usuarioDAO.salvar(u);
		
		return "/listadesejo"; 
	}
	
	public String getQuantidadeProduto()
	{
		return String.valueOf(produtos.size());
	}
	
	public String getQuantidadeListaDesejo()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
		
		UsuarioManager usuario = (UsuarioManager)session.getAttribute("usuarioManager");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<Produto> listaDesejoUsuario = usuarioDAO.obterListaDesejo(usuario.getUsuario().getId());
		
		if(listaDesejoUsuario != null)
			return String.valueOf(listaDesejoUsuario.size());
		
		return "0";
	}
	
	public Double getTotal()
	{
		Double total = 0.0;
		
		for(int i = 0; i < produtos.size(); i++)
		{
			total += produtos.get(i).getQuantidade() * produtos.get(i).getProduto().getValor();
		}
		
		return total;
	}
	
	public String salvarPedido()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		Usuario usuario = ((UsuarioManager)session.getAttribute("usuarioManager")).getUsuario();
		
		if(usuario == null)
			return "/login";
		
		pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setData(new Date());
		List<Endereco> enderecos = new ArrayList<Endereco>();
		enderecos.add(endereco);
		
		pedido.setEndereco(enderecos);
		for(int i = 0; i < produtos.size(); i++)
		{
			produtos.get(i).setPedido(pedido);
		}
		
		pedido.setItens(produtos);
		
		pedido.setFormaPagamento(formaPagamento);
		pedido.setStatus("AGUARDANDO");
		
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoDAO.salvar(pedido);
		
		endereco = null;
		produtos = new ArrayList<Item>();
		formaPagamento = null;
		index = null;
		produto = null;
		
		return "/detalhescompra";
	}
	
	public List<Pedido> getListaPedido()
	{
		PedidoDAO pedidoDAO = new PedidoDAO();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		Usuario usuario = ((UsuarioManager)session.getAttribute("usuarioManager")).getUsuario();
		
		return pedidoDAO.listaPedido(usuario.getId(), numero, inicio, fim);
	}
	
	public String buscar()
	{
		return "/pedidos";
	}
	
	public void obterPedido()
	{
		PedidoDAO pedidoDAO = new PedidoDAO();
		pedidoVisualizar = pedidoDAO.obter(indexPedido);
		
		enderecoVisualizar = pedidoVisualizar.getEndereco().get(0);
		produtosVisualizar = new ArrayList<Item>();
		
		for(int i = 0; i < pedidoVisualizar.getItens().size(); i++)
		{
			produtosVisualizar.add(pedidoVisualizar.getItens().get(i));
		}
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public List<Item> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Item> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Integer getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Integer formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public List<Produto> getListaDesejo() {
		return listaDesejo;
	}

	public void setListaDesejo(List<Produto> listaDesejo) {
		this.listaDesejo = listaDesejo;
	}

	public Integer getIndexPedido() {
		return indexPedido;
	}

	public void setIndexPedido(Integer indexPedido) {
		this.indexPedido = indexPedido;
	}

	public List<Item> getProdutosVisualizar() {
		return produtosVisualizar;
	}

	public void setProdutosVisualizar(List<Item> produtosVisualizar) {
		this.produtosVisualizar = produtosVisualizar;
	}

	public Endereco getEnderecoVisualizar() {
		return enderecoVisualizar;
	}

	public void setEnderecoVisualizar(Endereco enderecoVisualizar) {
		this.enderecoVisualizar = enderecoVisualizar;
	}

	public Pedido getPedidoVisualizar() {
		return pedidoVisualizar;
	}

	public void setPedidoVisualizar(Pedido pedidoVisualizar) {
		this.pedidoVisualizar = pedidoVisualizar;
	}
	
	
}
