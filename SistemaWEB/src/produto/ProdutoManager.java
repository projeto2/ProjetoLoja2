package produto;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="produtoManager")
@SessionScoped
public class ProdutoManager 
{
	private Integer id;
	private Produto produto;
	private String categoria;
	private String buscar;
	private List<Produto> lista;
	
	public ProdutoManager()
	{
		
	}
	public List<Produto> getListaInicial()
	{
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.buscaInicial();
	}
	
	public String obter()
	{
		if(id != null)
		{
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produto = produtoDAO.obter(id);
			
			return "/detalheproduto";
		}
		
		return "principal";
	}
	
	public List<Produto> getListaCategoria()
	{
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.buscaCategoria(categoria);
	}
	
	public String pesquisa()
	{
		ProdutoDAO produtoDAO = new ProdutoDAO();
		lista = produtoDAO.buscar(buscar);
		
		return "/buscaproduto";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public List<Produto> getLista() {
		return lista;
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}
}
