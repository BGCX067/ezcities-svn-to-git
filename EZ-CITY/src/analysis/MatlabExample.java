package analysis;

import matlabcontrol.*;

public class MatlabExample
{
    public static void main(String[] args)
        throws Exception
    {
        
        MatlabProxy proxy = MatlabConnection.getConnection();

        // call builtin function
        proxy.eval("disp('hello world')");

     // call user-defined function (must be on the path)
     // proxy.eval("addpath('C:somepath')");
     // proxy.feval("myfunc");
     // proxy.eval("rmpath('C:somepath')");
        
        proxy.eval("help");
 
        proxy.eval("x=5");
        proxy.eval("sqrt(x)");
      
    }
}
