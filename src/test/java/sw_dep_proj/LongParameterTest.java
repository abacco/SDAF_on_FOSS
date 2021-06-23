package sw_dep_proj;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sw_dep_proj.beans.MethodBean;
import sw_dep_proj.code_smells.LongParameterListSmell;

import java.util.ArrayList;
import java.util.List;

public class LongParameterTest {


    private MethodBean mb;
    private List<SingleVariableDeclaration> params;
    private LongParameterListSmell lpls;

    @Before
    public void setup(){
        mb = new MethodBean();
        params = new ArrayList<SingleVariableDeclaration>();
        lpls = new LongParameterListSmell();
    }

    @Test
    public void noLongParameterSmellSuccess(){

        SingleVariableDeclaration param1 = null;

        params.add(param1);
        mb.setParameters(params);

        Assert.assertFalse(lpls.hasLongParamMethodList(mb));
    }

    @Test
    public void LongParameterSmellFail(){

        SingleVariableDeclaration param1 = null;

        params.add(param1);
        params.add(param1);
        params.add(param1);
        params.add(param1);

        mb.setParameters(params);

        Assert.assertTrue(lpls.hasLongParamMethodList(mb));
    }
}
