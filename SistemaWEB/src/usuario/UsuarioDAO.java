package usuario;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import produto.Produto;

import db.Conexao;

public class UsuarioDAO 
{
	private Session session;
	public UsuarioDAO()
	{
		session = Conexao.getConnection().getCurrentSession();
	}
	
	public void salvar(Usuario usuario)
	{
		this.session.saveOrUpdate(usuario);
	}
	
	public Usuario obter(Integer id)
	{
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.createAlias("enderecos", "end");
		
		return (Usuario)criteria.uniqueResult();
	}
	
	public List<Produto> obterListaDesejo(Integer id)
	{
		return session.createSQLQuery("SELECT p.* FROM produto p INNER JOIN listadesejo l ON l.listaDesejo_id = p.id WHERE l.usuario_id = :idUser").addEntity(Produto.class).setInteger("idUser", id).list();
	}
	
	public Usuario logar(String email, String senha)
	{
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("senha", senha));
		criteria.createAlias("enderecos", "end");
		
		
		return (Usuario)criteria.uniqueResult();
	}
	
	public Endereco obterEndereco(Integer id)
	{
		Criteria criteria = session.createCriteria(Endereco.class);
		criteria.add(Restrictions.eq("id", id));
		
		return (Endereco)criteria.uniqueResult();
	}
}
