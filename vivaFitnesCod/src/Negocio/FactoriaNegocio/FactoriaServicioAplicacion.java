/**
 * 
 */
package Negocio.FactoriaNegocio;

/** 
 * Abstract factory for Service Application objects.
 * Concrete implementation is FactoriaSAImp.
 * @author azuri
 */
public class FactoriaServicioAplicacion {

private static FactoriaServicioAplicacion instance;

public static FactoriaServicioAplicacion getInstance() {
if (instance == null) {
instance = new FactoriaSAImp();
}
return instance;
}

public SASesion getSASesion() {
return null;
}

public SACliente getSACliente() {
return null;
}

public SASala getSASala() {
return null;
}
}
