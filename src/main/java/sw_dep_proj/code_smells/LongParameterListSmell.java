package sw_dep_proj.code_smells;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.beans.MethodBean;

import java.util.List;

public class LongParameterListSmell {

    public boolean hasLongParamMethodList(MethodBean mb){
        List<SingleVariableDeclaration> paramList = mb.getParameters();

        if(paramList.isEmpty()){
            System.out.println("this method " + mb.getBelongingClass().getName() + " has no parameters");
        }
        return paramList.size() >= 4;
    }
}
