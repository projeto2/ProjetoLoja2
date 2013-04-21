package db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import pedido.Item;
import pedido.Pedido;
import produto.Produto;


import usuario.Endereco;
import usuario.Usuario;

public class Conexao
{
	private static SessionFactory sessionFactory;
	
	static 
	{
		Configuration configuration = new Configuration();
		
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		
		configuration.setProperty("hibernate.connection.driver", "com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/sistema?autoReconnect=true");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "root");
		
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		configuration.setProperty("hibernate.current_session_context_class", "thread");
		
		configuration.addAnnotatedClass(Usuario.class);
		configuration.addAnnotatedClass(Endereco.class);
		configuration.addAnnotatedClass(Produto.class);
		configuration.addAnnotatedClass(Pedido.class);
		configuration.addAnnotatedClass(Item.class);
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static SessionFactory getConnection()
	{
		return sessionFactory;
	}
}
