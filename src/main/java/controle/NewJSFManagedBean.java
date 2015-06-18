/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import bean.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.json.JSONTokener;

/**
 *
 * @author popovicz
 */
@ManagedBean
@ViewScoped
public class NewJSFManagedBean {

    private Cliente cliente;
    private ArrayList<Cliente> clientes;

    //pega o Json de outra aplicação
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target("http://localhost:8080/SysHotel");
    WebTarget resourceWebTarget = webTarget.path("rest");
    WebTarget pathdWebTarget = resourceWebTarget.path("cliente");
    //WebTarget pathdWebTargetQuery = pathdWebTarget.queryParam("id", 1);
    Invocation.Builder invocationBuilder = pathdWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
    Response response = invocationBuilder.get();
    private Object JSON;

    public NewJSFManagedBean() throws JSONException {
        cliente = new Cliente();
        clientes = new ArrayList<Cliente>();
        JSONObject my_obj = new JSONObject (response.readEntity(Cliente.class));
        JSONArray todosCliente = my_obj.getJSONArray("cliente");        
        for (int i = 0; i < todosCliente.length(); i++) {
            clientes.add((Cliente) todosCliente.get(i));
            System.out.println("(" + i + ") " + todosCliente.get(i));            
            //clientes.add((Cliente) clientos.get(i));
        }
        System.out.println();
        //JSONObject my_obj = new JSONObject(response.readEntity(Cliente.class).toString());

        //clientes.add(response.readEntity(Cliente.class));
    }

    public void delete(Cliente cliente) {
        clientes.remove(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

}
