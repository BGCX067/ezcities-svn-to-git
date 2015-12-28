package analysis;

import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;


public class MatlabConnection {
	
	public static synchronized MatlabProxy getConnection() throws Exception{
		// create proxy
		 MatlabProxyFactoryOptions options =
		    new MatlabProxyFactoryOptions.Builder()
		        .setUsePreviouslyControlledSession(true)
		        .build();
		MatlabProxyFactory factory = new MatlabProxyFactory(options);
		
		MatlabProxy proxy = factory.getProxy();
		return proxy;
	}
	
	public static synchronized void disConnection(MatlabProxy proxy) throws Exception{
		// create proxy
		proxy.disconnect();
	}
	
}




